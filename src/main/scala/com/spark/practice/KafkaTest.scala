package com.spark.practice

import org.apache.spark.sql.SparkSession

/**
 * Created By: Srikanth.nelluri
 * Date: 19-09-2019
 */
object KafkaTest {


  def main(args: Array[String]): Unit = {

  }

  def maxHeight(wallPositions: Array[Int], wallHeights: Array[Int]): Int = {
    // Write your code here
    val a: Array[Int] = Array(wallPositions.max)
    wallPositions.zip(wallHeights).foreach(e => a(e._1) = e._2)

    0
  }

  /*def main(args: Array[String]): Unit = {

    implicit val spark: SparkSession = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .master("local[*]")
      .getOrCreate()

    val inp = spark.read.json("E:\\kafka_2.12-2.3.0\\sink")
    println(inp.count())

  }*/

}
