package com.spark.practice

import com.spark.practice.utils.ConcurrentContext
import org.apache.spark.sql.SparkSession
import org.apache.log4j.Logger
import org.apache.log4j.Level

/**
 * Created By: Srikanth.nelluri
 * Date: 30-08-2019
 */
object SparkConcurrency {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)

    implicit val spark: SparkSession = SparkSession
      .builder()
      .appName("Spark Concurrency example")
      .master("local[1]")
      .getOrCreate()

    val st = System.currentTimeMillis()

    val res = spark.sparkContext.parallelize(1 to 10)
      .map(fastFoo)
      .map(x => ConcurrentContext.executeAsync(slowFoo(x)))
      .mapPartitions(it => ConcurrentContext.awaitSliding(it))
      .foreach(x => println(s"Finishing with $x"))

    println(s"Time: ${(System.currentTimeMillis() - st) / 1000.0}")

  }

  def slowFoo[T](x: T): T = {
    println(s"slowFoo start ($x)")
    Thread.sleep(3000)
    println(s"slowFoo end($x)")
    x
  }

  def fastFoo[T](x: T): T = {
    println(s"fastFoo($x)")
    x
  }

}
