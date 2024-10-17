package com.twitter.scrooge

import com.twitter.util.Unsafe
import java.nio.ByteOrder
import java.util

object TByteArrayMemoryTransport {

  private[scrooge] val MaxBufferSize = 1024 * 1024 * 1024

  private[scrooge] val unsafe = Unsafe()
  private[scrooge] val ByteArrayOffset: Long =
    unsafe.arrayBaseOffset(classOf[Array[Byte]]).toLong

  def apply(initialSize: Int = 512): TByteArrayMemoryTransport = {
    if (unsafe ne null) {
      new TUnsafeMemoryTransport(initialSize)
    } else {
      new TByteArrayMemoryTransport(initialSize)
    }
  }

}

sealed class TByteArrayMemoryTransport private[scrooge] (
  initialSize: Int,
  maxSize: Int = TByteArrayMemoryTransport.MaxBufferSize)
    extends TBaseReusableMemoryTransport {

  /**
   * The buffer where data is stored.
   */
  @inline
  protected var buf: Array[Byte] = new Array[Byte](initialSize)

  /**
   * The number of valid bytes in the buffer.
   */
  @inline
  protected var writePos = 0

  @inline
  protected var readPos = 0

  /**
   * Resets both reads and writes.
   */
  override def reset(): Unit = {
    writePos = 0
    readPos = 0
  }

  // Here for drop-in api compatibility with TMemoryBuffer
  override def length(): Int = writePos

  // Here for drop-in api compatibility with TMemoryBuffer
  override def getArray(): Array[Byte] = buf

  /**
   * Total bytes currently allowed in the struct.
   *
   * Note: more writes beyond this length will cause the struct to grow.
   */
  override def currentCapacity: Int = buf.length

  override def isOpen: Boolean = true

  override def close(): Unit = {}

  override def open(): Unit = {}

  override def write(from: Array[Byte], off: Int, len: Int): Unit = {
    if ((off < 0) || (off > from.length)
      || (len < 0) || ((off + len) - from.length > 0)) {
      throw new IndexOutOfBoundsException()
    }
    ensureCapacity(writePos + len)
    System.arraycopy(from, off, buf, writePos, len)
    writePos += len
  }

  override def read(into: Array[Byte], off: Int, len: Int): Int = {
    val bytesToRead = if (len > writePos - readPos) {
      writePos - readPos
    } else {
      len
    }
    if (bytesToRead > 0) {
      System.arraycopy(buf, readPos, into, off, bytesToRead)
      readPos += bytesToRead
    }
    bytesToRead
  }

  @inline
  protected def ensureCapacity(minCapacity: Int): Unit = {
    if (minCapacity - buf.length > 0) {
      expandCapacity(minCapacity)
    }
  }

  private def expandCapacity(minCapacity: Int): Unit = {
    val oldCapacity = buf.length
    var newCapacity = Math.min(oldCapacity << 1, maxSize)
    if (newCapacity - minCapacity < 0) newCapacity = minCapacity
    buf = util.Arrays.copyOf(buf, newCapacity)
  }
}

/**
 * A version of TMemoryTransport that allows for reuse in order to minimize
 * object allocations.
 */
class TUnsafeMemoryTransport private[scrooge] (
  initialSize: Int,
  maxSize: Int = TByteArrayMemoryTransport.MaxBufferSize)
    extends TByteArrayMemoryTransport(initialSize, maxSize) {

  import TByteArrayMemoryTransport._

  def writeI8(data: Byte, byteOrder: ByteOrder): Unit = {
    ensureCapacity(writePos + 1)
    unsafe.putByte(
      buf,
      ByteArrayOffset + writePos,
      data
    )
    writePos += 1
  }

  def writeI16(data: Short, byteOrder: ByteOrder): Unit = {
    ensureCapacity(writePos + 2)
    unsafe.putShort(
      buf,
      ByteArrayOffset + writePos,
      if (byteOrder ne ByteOrder.nativeOrder()) java.lang.Short.reverseBytes(data)
      else data
    )
    writePos += 2
  }

  def writeI32(data: Int, byteOrder: ByteOrder): Unit = {
    ensureCapacity(writePos + 4)
    unsafe.putInt(
      buf,
      ByteArrayOffset + writePos,
      if (byteOrder ne ByteOrder.nativeOrder()) java.lang.Integer.reverseBytes(data)
      else data
    )
    writePos += 4
  }

  def writeI64(data: Long, byteOrder: ByteOrder): Unit = {
    ensureCapacity(writePos + 8)
    unsafe.putLong(
      buf,
      ByteArrayOffset + writePos,
      if (byteOrder ne ByteOrder.nativeOrder()) java.lang.Long.reverseBytes(data)
      else data
    )
    writePos += 8
  }
}
