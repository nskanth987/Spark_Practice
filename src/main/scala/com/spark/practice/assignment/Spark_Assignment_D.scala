package com.spark.practice.assignment

import java.io.File

import com.spark.practice.data.Weather
import org.apache.spark.SparkContext

object Spark_Assignment_D {

  def main(args: Array[String]): Unit = {

    val invalid = "N/A"
    val sc = new SparkContext("local[*]", "MostPrecpMonth")
    val lines = sc.textFile(new File(getClass.getClassLoader.getResource("Weather.csv").toURI).getPath)
    val precepMonthData = lines.mapPartitionsWithIndex((idx, iter) => if (idx == 0) iter.drop(1) else iter).map(parse).filter(_._2 != invalid).mapValues(_.toFloat)
      .reduceByKey((p1, p2) => if (p1 > p2) p1 else p2)
    val mostPrecpMonth = precepMonthData.collect.maxBy(_._2)
    println(s"The month with most precipitation is ${mostPrecpMonth._1._1 + "-" + mostPrecpMonth._1._2} and the value is ${mostPrecpMonth._2}. Considering the month with high precep value")
  }

  def parse(x: String) = {
    val data = x.split(",")
    val w = Weather(data(0).toInt, data(1).toInt, data(2).toInt, data(3), data(4).toFloat, data(5).toFloat, data(6), data(7).toFloat, data(8).toFloat, data(9), data(10),
      data(11), data(12), data(13), data(14), data(15).toInt, data(16))
    ((w.year, w.month), w.precep)
  }

}
