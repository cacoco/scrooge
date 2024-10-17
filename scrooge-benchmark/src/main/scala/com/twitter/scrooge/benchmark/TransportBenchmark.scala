package com.twitter.scrooge.benchmark

import com.twitter.scrooge.TBaseReusableMemoryTransport
import com.twitter.scrooge.TByteArrayMemoryTransport
import com.twitter.scrooge.TReusableMemoryTransport
import com.twitter.scrooge.TUnsafeBinaryProtocol
import com.twitter.scrooge.TUnsafeMemoryTransport
import com.twitter.scrooge.ThriftStruct
import com.twitter.scrooge.ThriftStructCodec
import java.io.ByteArrayOutputStream
import java.util.Random
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.protocol.TProtocol
import org.apache.thrift.transport.TTransport
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations._
import scala.collection.mutable
import thrift.benchmark._

class TransportBenchmarkFields(size: Int) {

  val reusableTransport: TReusableMemoryTransport = TReusableMemoryTransport(128 * 1024)
  val binaryProtocolForReusableTransport: TBinaryProtocol = new TBinaryProtocol(reusableTransport)

  val unsafeMemoryTransport: TByteArrayMemoryTransport = TByteArrayMemoryTransport(128 * 1024)
  val binaryProtocolForUnsafeTransport: TBinaryProtocol = new TBinaryProtocol(unsafeMemoryTransport)

  val optimizedBinaryProtocolForUnsafeTransport: TBinaryProtocol = TUnsafeBinaryProtocol(
    unsafeMemoryTransport)

  val rng: Random = new Random(31415926535897932L)

  val arrayDoublesVals = new Array[Double](size)

  val airlines: Array[Airline] = AirlineGenerator.buildAirlines(rng, 100)

  val m: Unit = for (i <- (0 until size)) {
    val num = rng.nextLong()
    arrayDoublesVals(i) = num
  }

  val arrayDoubleCollections: ListDoubleCollections = ListDoubleCollections(arrayDoublesVals)

  def encode[T <: ThriftStruct](
    codec: ThriftStructCodec[T],
    prot: TProtocol,
    buff: TBaseReusableMemoryTransport,
    obj: T
  ): Unit = {
    codec.encode(obj, prot)
    buff.reset()
  }

}

object TransportBenchmark {
  @State(Scope.Thread)
  class CollectionsState {
    @Param(Array("5", "10", "50", "500"))
    var size: Int = 1

    var fields: TransportBenchmarkFields = _

    @Setup(Level.Trial)
    def setup(): Unit = {
      fields = new TransportBenchmarkFields(size)
    }
  }

  @State(Scope.Thread)
  class AirlinesState {

    var fields: TransportBenchmarkFields = _

    @Setup(Level.Trial)
    def setup(): Unit = {
      fields = new TransportBenchmarkFields(1)
    }
  }
}

@OutputTimeUnit(TimeUnit.SECONDS)
@BenchmarkMode(Array(Mode.Throughput))
@Fork(value = 1 /*, jvmArgsAppend = Array[String] { "-XX:-UseBiasedLocking" }*/ )
@Warmup(iterations = 3, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
class TransportBenchmark {
  import TransportBenchmark._

  @Benchmark
  def encodeDoubleArrayBaseline(state: CollectionsState): Unit =
    state.fields.encode(
      ListDoubleCollections,
      state.fields.binaryProtocolForReusableTransport,
      state.fields.reusableTransport,
      state.fields.arrayDoubleCollections
    )

  @Benchmark
  def encodeDoubleArrayByteArrayTransport(state: CollectionsState): Unit =
    state.fields.encode(
      ListDoubleCollections,
      state.fields.binaryProtocolForUnsafeTransport,
      state.fields.unsafeMemoryTransport,
      state.fields.arrayDoubleCollections
    )

  @Benchmark
  def encodeDoubleArrayUnsafeTransport(state: CollectionsState): Unit =
    state.fields.encode(
      ListDoubleCollections,
      state.fields.optimizedBinaryProtocolForUnsafeTransport,
      state.fields.unsafeMemoryTransport,
      state.fields.arrayDoubleCollections
    )

  @Benchmark
  def encodeAirlineBaseline(state: AirlinesState): Unit =
    state.fields.encode(
      Airline,
      state.fields.binaryProtocolForReusableTransport,
      state.fields.reusableTransport,
      state.fields.airlines(ThreadLocalRandom.current().nextInt(state.fields.airlines.length))
    )

  @Benchmark
  def encodeAirlineByteArrayTransport(state: AirlinesState): Unit =
    state.fields.encode(
      Airline,
      state.fields.binaryProtocolForUnsafeTransport,
      state.fields.unsafeMemoryTransport,
      state.fields.airlines(ThreadLocalRandom.current().nextInt(state.fields.airlines.length))
    )

  @Benchmark
  def encodeAirlineUnsafeTransport(state: AirlinesState): Unit =
    state.fields.encode(
      Airline,
      state.fields.optimizedBinaryProtocolForUnsafeTransport,
      state.fields.unsafeMemoryTransport,
      state.fields.airlines(ThreadLocalRandom.current().nextInt(state.fields.airlines.length))
    )
}
