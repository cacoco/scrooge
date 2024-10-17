package com.twitter.scrooge

import java.nio.ByteOrder
import org.apache.thrift.protocol.TBinaryProtocol

object TUnsafeBinaryProtocol {
  def apply(memoryTransport: TByteArrayMemoryTransport): TBinaryProtocol = {
    memoryTransport match {
      case unsafeMemoryTransport: TUnsafeMemoryTransport =>
        new TUnsafeBinaryProtocol(unsafeMemoryTransport)
      case _ =>
        new TBinaryProtocol(memoryTransport)
    }
  }
}
class TUnsafeBinaryProtocol private[scrooge] (memoryTransport: TUnsafeMemoryTransport)
    extends TBinaryProtocol(memoryTransport) {

  override def writeByte(i8: Byte): Unit = {
    memoryTransport.writeI8(i8, ByteOrder.BIG_ENDIAN)
  }

  override def writeI16(i16: Short): Unit = {
    memoryTransport.writeI16(i16, ByteOrder.BIG_ENDIAN)
  }

  override def writeI32(i32: Int): Unit = {
    memoryTransport.writeI32(i32, ByteOrder.BIG_ENDIAN)
  }

  override def writeI64(i64: Long): Unit = {
    memoryTransport.writeI64(i64, ByteOrder.BIG_ENDIAN)
  }

  override def writeDouble(dub: Double): Unit = {
    memoryTransport.writeI64(java.lang.Double.doubleToLongBits(dub), ByteOrder.BIG_ENDIAN)
  }
}
