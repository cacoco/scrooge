package com.twitter.scrooge

/**
 * This is a per-thread managed resource and must be reset after use
 * {{{
 *   import com.twitter.scrooge.TReusableBuffer
 *
 *   class Example {
 *     private[this] val reusableBuffer = new TReusableBuffer()
 *
 *     def someMethod(): Unit = {
 *       val buffer = reusableBuffer.get()
 *       try {
 *         // code that uses buffer
 *       } finally {
 *         buffer.reset()
 *       }
 *     }
 *   }
 * }}}
 *
 * @param initialSize The initial buffer size, default is 512.
 * @param maxThriftBufferSize The buffer will reset if it exceeds max buffer
 *                            size, default is 16K.
 */

object TReusableBuffer {
  def apply(
    initialSize: Int = 512,
    maxThriftBufferSize: Int = 16 * 1024
  ): TReusableBuffer = apply(
    initialSize,
    maxThriftBufferSize,
    TReusableMemoryTransport.apply
  )
}

case class TReusableBuffer(
  initialSize: Int,
  maxThriftBufferSize: Int,
  ctor: Int => TBaseReusableMemoryTransport) {

  def this(
    initialSize: Int,
    maxThriftBufferSize: Int
  ) = this(
    initialSize,
    maxThriftBufferSize,
    TReusableMemoryTransport.apply
  )

  private[this] val tlReusableBuffer = new ThreadLocal[TBaseReusableMemoryTransport] {
    override def initialValue(): TBaseReusableMemoryTransport = ctor.apply(initialSize)
  }

  /**
   * Resets the underlying TReusableMemoryTransport before returning it.
   * @deprecated This method is kept only for binary backwards compatibility with
   *             old code outside of source
   */
  def get(): TReusableMemoryTransport = {
    take().asInstanceOf[TReusableMemoryTransport]
  }

  /**
   * Resets the underlying TReusableMemoryTransport before returning it.
   */
  def take(): TBaseReusableMemoryTransport = {
    val buf = tlReusableBuffer.get()
    buf.reset()
    buf
  }

  def reset(): Unit = {
    if (tlReusableBuffer.get().currentCapacity > maxThriftBufferSize) {
      tlReusableBuffer.remove()
    }
  }
}
