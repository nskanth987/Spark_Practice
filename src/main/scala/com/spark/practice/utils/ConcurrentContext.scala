package com.spark.practice.utils

/**
 * Created By: Srikanth.nelluri
 * Date: 30-08-2019
 */
object ConcurrentContext {

  import scala.concurrent._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration.Duration
  import scala.concurrent.duration.Duration._

  /** Wraps a code block in a Future and returns the future */
  def executeAsync[T](f: => T): Future[T] = {
    Future(f)
  }

  def awaitSliding[T](it: Iterator[Future[T]], batchSize: Int = 3, timeout: Duration = Inf): Iterator[T] = {

    val (firstElements, lastElement) = it
      .grouped(batchSize)
      .sliding(2)
      .span(_ => it.hasNext)

    (firstElements.map(_.head) ++ lastElement.flatten).asInstanceOf[Iterator[Iterator[Future[T]]]].flatten.map(Await.result(_, timeout))

  }
}
