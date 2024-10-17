package com.twitter.scrooge.benchmark

import java.io.ByteArrayOutputStream
import org.apache.thrift.transport.TTransport

object TRewindable {
  private class ExposedBAOS extends ByteArrayOutputStream {
    def get: Array[Byte] = buf

    def len: Int = count
  }
}
class TRewindable extends TTransport {
  private[this] var pos = 0
  private[this] val arr = new TRewindable.ExposedBAOS()

  override def isOpen = true
  override def open(): Unit = {}
  override def close(): Unit = {}
  override def flush(): Unit = {}

  override def read(buf: Array[Byte], off: Int, len: Int): Int = {
    val amtToRead = if (len > arr.len - pos) arr.len - pos else len
    if (amtToRead > 0) {
      System.arraycopy(arr.get, pos, buf, off, amtToRead)
      pos += amtToRead
    }
    amtToRead
  }

  override def write(buf: Array[Byte], off: Int, len: Int): Unit = {
    arr.write(buf, off, len)
  }

  def rewind(): Unit = {
    pos = 0;
  }

  def resetBuf(): Unit = {
    arr.reset()
  }

  def inspect: String = {
    var buf = ""
    var i = 0
    val bytes = arr.toByteArray()
    bytes foreach { byte =>
      buf += (if (pos == i) "==>" else "") + Integer.toHexString(byte & 0xff) + " "
      i += 1
    }
    buf
  }
}
