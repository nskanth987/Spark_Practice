/*
package com.spark.happiestminds

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object IntCount {

  def main(args: Array[String]): Unit = {

    implicit val spark: SparkSession = SparkSession
      .builder()
      .appName("Spark example")
      .master("local[*]")
      .getOrCreate()

    val a: RDD[Int] = _

    a.countByValue()
    a.map(x => (x, 1)).reduceByKey(_ + _)

  }

}
*/
