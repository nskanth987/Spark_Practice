package com.spark.practice.assignment

import java.io.File

import com.spark.practice.data.Weather
import org.apache.spark.SparkContext

object Spark_Assignment_C {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext("local[*]", "CommonWindDir")
    val lines = sc.textFile(new File(getClass.getClassLoader.getResource("Weather.csv").toURI).getPath)
    val windData = lines.mapPartitionsWithIndex((idx, iter) => if (idx == 0) iter.drop(1) else iter).map(parse).reduceByKey((c1, c2) => c1 + c2).collect
    val commonWindDir = windData.maxBy(_._2)
    println(s"The most common wind direction is ${commonWindDir._1} with occurences ${commonWindDir._2}")
  }

  def parse(x: String) = {
    val data = x.split(",")
    val w = Weather(data(0).toInt, data(1).toInt, data(2).toInt, data(3), data(4).toFloat, data(5).toFloat, data(6), data(7).toFloat, data(8).toFloat, data(9), data(10),
      data(11), data(12), data(13), data(14), data(15).toInt, data(16))
    (w.windDir, 1)
  }

}
