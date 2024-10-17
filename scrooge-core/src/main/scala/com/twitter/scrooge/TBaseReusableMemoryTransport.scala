package com.twitter.scrooge

import org.apache.thrift.transport.TTransport

abstract class TBaseReusableMemoryTransport extends TTransport {
  // Here for drop-in api compatibility with TMemoryBuffer
  def length(): Int

  // Here for drop-in api compatibility with TMemoryBuffer
  def getArray(): Array[Byte]

  def numWrittenBytes: Int = length()

  def currentCapacity: Int

  def reset(): Unit
}
