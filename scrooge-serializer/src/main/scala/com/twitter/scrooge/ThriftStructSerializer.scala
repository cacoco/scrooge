package com.twitter.scrooge

import com.twitter.app.GlobalFlag
import com.twitter.util.Base64StringEncoder
import com.twitter.util.StringEncoder
import java.io.ByteArrayInputStream
import java.io.InputStream
import org.apache.thrift.protocol._
import org.apache.thrift.transport.TIOStreamTransport

object maxReusableBufferSize
    extends GlobalFlag[Int](
      16 * 1024,
      "Max bytes for ThriftStructSerializers reusable transport buffer"
    ) {
  override val name = "com.twitter.scrooge.ThriftStructSerializer.maxReusableBufferSize"
}

private object ThriftStructSerializer {

  private val maxRBS = maxReusableBufferSize()

  private val reusableTransport = TReusableBuffer(
    initialSize = maxRBS,
    maxThriftBufferSize = maxRBS,
    ctor = TByteArrayMemoryTransport.apply
  )
}

trait ThriftStructSerializer[T <: ThriftStruct] {
  import ThriftStructSerializer._

  def codec: ThriftStructCodec[T]
  def protocolFactory: TProtocolFactory
  def encoder: StringEncoder = Base64StringEncoder

  def toBytes(obj: T): Array[Byte] = {
    val trans = reusableTransport.take()
    try {
      val proto = protocolFactory.getProtocol(trans)
      codec.encode(obj, proto)
      val bytes = new Array[Byte](trans.length())
      trans.read(bytes, 0, trans.length())
      bytes
    } finally {
      reusableTransport.reset()
    }
  }

  def fromBytes(bytes: Array[Byte]): T = {
    fromInputStream(new ByteArrayInputStream(bytes))
  }

  def fromInputStream(stream: InputStream): T = {
    val proto = protocolFactory.getProtocol(new TIOStreamTransport(stream))
    codec.decode(proto)
  }

  def toString(obj: T): String = {
    encoder.encode(toBytes(obj))
  }

  def fromString(string: String): T = {
    fromBytes(encoder.decode(string))
  }
}

trait BinaryThriftStructSerializer[T <: ThriftStruct] extends ThriftStructSerializer[T] {
  val protocolFactory: TBinaryProtocol.Factory = new TBinaryProtocol.Factory

  override def fromBytes(bytes: Array[Byte]): T = {
    val stream = new ByteArrayInputStream(bytes)
    // create a new factory in order to limit the string/binary sizes
    val factory = new TBinaryProtocol.Factory(bytes.length, -1)
    val proto = factory.getProtocol(new TIOStreamTransport(stream))
    codec.decode(proto)
  }

}

object BinaryThriftStructSerializer {
  def apply[T <: ThriftStruct](_codec: ThriftStructCodec[T]): BinaryThriftStructSerializer[T] =
    new BinaryThriftStructSerializer[T] {
      def codec: ThriftStructCodec[T] = _codec
    }
}

object LazyBinaryThriftStructSerializer {
  private val reusuableProtocolAndTransport =
    new ThreadLocal[(TArrayByteTransport, TLazyBinaryProtocol)] {
      override def initialValue(): (TArrayByteTransport, TLazyBinaryProtocol) = {
        val transport = new TArrayByteTransport
        val proto = new TLazyBinaryProtocol(transport)
        (transport, proto)
      }
    }

  def apply[T <: ThriftStruct](_codec: ThriftStructCodec[T]): LazyBinaryThriftStructSerializer[T] =
    new LazyBinaryThriftStructSerializer[T] {
      def codec: ThriftStructCodec[T] = _codec
    }
}

trait LazyBinaryThriftStructSerializer[T <: ThriftStruct] extends ThriftStructSerializer[T] {
  import LazyBinaryThriftStructSerializer._

  // Since we only support the fast path reading from the TArrayByteTransport
  // we provide the default if someone hits it to be the TBinaryProtocol which we are wire compatible with.
  override val protocolFactory: TBinaryProtocol.Factory = new TBinaryProtocol.Factory

  override def toBytes(obj: T): Array[Byte] = {
    val (transport, proto) = reusuableProtocolAndTransport.get()
    transport.reset()
    codec.encode(obj, proto)
    transport.toByteArray
  }

  override def fromBytes(bytes: Array[Byte]): T = {
    val (transport, proto) = reusuableProtocolAndTransport.get()
    transport.setBytes(bytes)
    codec.decode(proto)
  }

}

trait CompactThriftSerializer[T <: ThriftStruct] extends ThriftStructSerializer[T] {
  val protocolFactory: TCompactProtocol.Factory = new TCompactProtocol.Factory
}

object CompactThriftSerializer {
  def apply[T <: ThriftStruct](_codec: ThriftStructCodec[T]): CompactThriftSerializer[T] =
    new CompactThriftSerializer[T] {
      def codec: ThriftStructCodec[T] = _codec
    }
}

/**
 * Thrift serializer using the TSimpleJSONProtocol. This serializes thrift using field
 * names, and currently does NOT support deserialization of the resulting json.
 * @note see [[com.twitter.scrooge.TJSONProtocolThriftSerializer]] if you want to be able
 *       to deserialize from json.
 */
trait JsonThriftSerializer[T <: ThriftStruct] extends ThriftStructSerializer[T] {
  override def encoder: StringEncoder = StringEncoder
  val protocolFactory: TSimpleJSONProtocol.Factory = new TSimpleJSONProtocol.Factory
}

object JsonThriftSerializer {
  def apply[T <: ThriftStruct](_codec: ThriftStructCodec[T]): JsonThriftSerializer[T] =
    new JsonThriftSerializer[T] {
      def codec = _codec
    }
}

/**
 * Thrift serializer / deserializer using the TJSONProtocol. This serializes thrift using
 * field ids (numbers), and does support deserialization of the resulting json.
 */
trait TJSONProtocolThriftSerializer[T <: ThriftStruct] extends ThriftStructSerializer[T] {
  override def encoder: StringEncoder = StringEncoder
  val protocolFactory: TJSONProtocol.Factory = new TJSONProtocol.Factory
}

object TJSONProtocolThriftSerializer {
  def apply[T <: ThriftStruct](_codec: ThriftStructCodec[T]): TJSONProtocolThriftSerializer[T] =
    new TJSONProtocolThriftSerializer[T] {
      def codec: ThriftStructCodec[T] = _codec
    }
}
