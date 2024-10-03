package com.twitter.scrooge.benchmark

import com.twitter.scrooge.ThriftStruct
import com.twitter.scrooge.ThriftStructCodec
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit
import java.util.Random
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.protocol.TProtocol
import org.apache.thrift.transport.TTransport
import org.openjdk.jmh.annotations._
import thrift.benchmark._
import scala.collection.mutable

private class ExposedBAOS extends ByteArrayOutputStream {
  def get: Array[Byte] = buf
  def len: Int = count
}

class TRewindable extends TTransport {
  private[this] var pos = 0
  private[this] val arr = new ExposedBAOS()

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
    pos = 0
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

class Collections(size: Int) {
  val map: TRewindable = new TRewindable
  val mapProt: TBinaryProtocol = new TBinaryProtocol(map)

  val set: TRewindable = new TRewindable
  val setProt: TBinaryProtocol = new TBinaryProtocol(set)

  val list: TRewindable = new TRewindable
  val listProt: TBinaryProtocol = new TBinaryProtocol(list)

  val listDouble: TRewindable = new TRewindable
  val listDoubleProt: TBinaryProtocol = new TBinaryProtocol(listDouble)

  val rng: Random = new Random(31415926535897932L)

  val mapVals: mutable.Builder[(Long, String), Map[Long, String]] = Map.newBuilder[Long, String]
  val setVals: mutable.Builder[Long, Set[Long]] = Set.newBuilder[Long]
  val listVals: mutable.Builder[Long, Seq[Long]] = Seq.newBuilder[Long]
  val arrayVals = new Array[Long](size)
  val arrayDoublesVals = new Array[Double](size)

  val m: Unit = for (i <- (0 until size)) {
    val num = rng.nextLong()
    mapVals += (num -> num.toString)
    setVals += num
    listVals += num
    arrayVals(i) = num
    arrayDoublesVals(i) = num
  }

  val mapCollections: MapCollections = MapCollections(mapVals.result)
  val setCollections: SetCollections = SetCollections(setVals.result)
  val listCollections: ListCollections = ListCollections(listVals.result)
  val arrayCollections: ListCollections = ListCollections(arrayVals)
  val arrayDoubleCollections: ListDoubleCollections = ListDoubleCollections(arrayDoublesVals)

  MapCollections.encode(mapCollections, mapProt)
  SetCollections.encode(setCollections, setProt)
  ListCollections.encode(listCollections, listProt)
  ListDoubleCollections.encode(arrayDoubleCollections, listDoubleProt)

  def decode(codec: ThriftStructCodec[_], prot: TProtocol, buff: TRewindable): Unit = {
    codec.decode(prot)
    buff.rewind()
  }

  def encode[T <: ThriftStruct](
    codec: ThriftStructCodec[T],
    prot: TProtocol,
    buff: TRewindable,
    obj: T
  ): Unit = {
    codec.encode(obj, prot)
    buff.rewind()
  }
}

object CollectionsBenchmark {
  @State(Scope.Thread)
  class CollectionsState {
    @Param(Array("1", "5", "10", "100", "500"))
    var size: Int = 1

    var col: Collections = _

    @Setup(Level.Trial)
    def setup(): Unit = {
      col = new Collections(size)
    }
  }
}

@OutputTimeUnit(TimeUnit.SECONDS)
@BenchmarkMode(Array(Mode.Throughput))
@Fork(1)
@Warmup(iterations = 3, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
class CollectionsBenchmark {
  import CollectionsBenchmark._

  @Benchmark
  def timeEncodeList(state: CollectionsState): Unit =
    state.col.encode(
      ListCollections,
      state.col.listProt,
      state.col.list,
      state.col.listCollections
    )

  @Benchmark
  def timeEncodeArray(state: CollectionsState): Unit =
    state.col.encode(
      ListCollections,
      state.col.listProt,
      state.col.list,
      state.col.arrayCollections
    )

  @Benchmark
  def timeEncodeDoubleArray(state: CollectionsState): Unit =
    state.col.encode(
      ListDoubleCollections,
      state.col.listDoubleProt,
      state.col.listDouble,
      state.col.arrayDoubleCollections
    )

  @Benchmark
  def timeDecodeMap(state: CollectionsState): Unit =
    state.col.decode(MapCollections, state.col.mapProt, state.col.map)

  @Benchmark
  def timeDecodeSet(state: CollectionsState): Unit =
    state.col.decode(SetCollections, state.col.setProt, state.col.set)

  @Benchmark
  def timeDecodeList(state: CollectionsState): Unit =
    state.col.decode(ListCollections, state.col.listProt, state.col.list)
}
