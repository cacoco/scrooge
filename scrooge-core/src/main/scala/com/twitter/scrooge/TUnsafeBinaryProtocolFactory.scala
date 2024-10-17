package com.twitter.scrooge

import org.apache.thrift.protocol.TProtocol
import org.apache.thrift.protocol.TProtocolFactory
import org.apache.thrift.transport.TTransport

class TUnsafeBinaryProtocolFactory(fallback: TProtocolFactory) extends TProtocolFactory {
  override def getProtocol(trans: TTransport): TProtocol = {
    trans match {
      case memoryTransport: TByteArrayMemoryTransport =>
        TUnsafeBinaryProtocol(memoryTransport)
      case _ =>
        fallback.getProtocol(trans)
    }
  }
}
