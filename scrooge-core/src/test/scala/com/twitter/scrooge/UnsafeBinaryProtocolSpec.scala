package com.twitter.scrooge

import java.util
import java.util.concurrent.ThreadLocalRandom
import org.apache.thrift.protocol.TBinaryProtocol
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner
import org.scalatestplus.scalacheck.Checkers

@RunWith(classOf[JUnitRunner])
class UnsafeBinaryProtocolSpec extends AnyFunSuite with Checkers {
  test("serialized double read unchanged") {
    check {
      val writeTransport = new TUnsafeMemoryTransport(128 * 1024)
      val writeProtocol = new TUnsafeBinaryProtocol(
        writeTransport
      )
      val rnd = ThreadLocalRandom.current().nextDouble()
      writeProtocol.writeDouble(rnd)

      val readProtocol = new TBinaryProtocol(
        TArrayByteTransport(util.Arrays.copyOf(writeTransport.getArray(), writeTransport.length()))
      )
      val rnd2 = readProtocol.readDouble()

      rnd == rnd2
    }
  }

  test("serialized i64 read unchanged") {
    check {
      val writeTransport = new TUnsafeMemoryTransport(128 * 1024)
      val writeProtocol = new TUnsafeBinaryProtocol(
        writeTransport
      )
      val rnd = ThreadLocalRandom.current().nextLong()
      writeProtocol.writeI64(rnd)

      val readProtocol = new TBinaryProtocol(
        TArrayByteTransport(util.Arrays.copyOf(writeTransport.getArray(), writeTransport.length()))
      )
      val rnd2 = readProtocol.readI64()

      rnd == rnd2
    }
  }

  test("serialized i32 read unchanged") {
    check {
      val writeTransport = new TUnsafeMemoryTransport(128 * 1024)
      val writeProtocol = new TUnsafeBinaryProtocol(
        writeTransport
      )
      val rnd = ThreadLocalRandom.current().nextInt()
      writeProtocol.writeI32(rnd)

      val readProtocol = new TBinaryProtocol(
        TArrayByteTransport(util.Arrays.copyOf(writeTransport.getArray(), writeTransport.length()))
      )
      val rnd2 = readProtocol.readI32()

      rnd == rnd2
    }
  }

  test("serialized i16 read unchanged") {
    check {
      val writeTransport = new TUnsafeMemoryTransport(128 * 1024)
      val writeProtocol = new TUnsafeBinaryProtocol(
        writeTransport
      )
      val rnd = ThreadLocalRandom.current().nextInt(java.lang.Short.MAX_VALUE).toShort
      writeProtocol.writeI16(rnd)

      val readProtocol = new TBinaryProtocol(
        TArrayByteTransport(util.Arrays.copyOf(writeTransport.getArray(), writeTransport.length()))
      )
      val rnd2 = readProtocol.readI16()

      rnd == rnd2
    }
  }

  test("serialized i8 read unchanged") {
    check {
      val writeTransport = new TUnsafeMemoryTransport(128 * 1024)
      val writeProtocol = new TUnsafeBinaryProtocol(
        writeTransport
      )
      val rnd = new Array[Byte](1)
      ThreadLocalRandom.current().nextBytes(rnd)
      writeProtocol.writeByte(rnd(0))

      val readProtocol = new TBinaryProtocol(
        TArrayByteTransport(util.Arrays.copyOf(writeTransport.getArray(), writeTransport.length()))
      )
      val rnd2 = readProtocol.readByte()

      rnd(0) == rnd2
    }
  }
}
