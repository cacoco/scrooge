package com.twitter.scrooge

import com.twitter.scrooge.serializer.thriftscala.SerializerTest
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ThriftStructSerializerTest extends AnyFunSuite {

  test("toBytes and fromBytes round trip") {
    val instance = SerializerTest(5)

    val tss = BinaryThriftStructSerializer(SerializerTest)

    val bytes = tss.toBytes(instance)
    val andBack = tss.fromBytes(bytes)
    assert(instance == andBack)
  }
}
