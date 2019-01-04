package com.spark.practice.assignment

import java.io.File

import com.spark.practice.data.Weather
import org.apache.spark.SparkContext

object Spark_Assignment_F {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext("local[*]", "ColdestTime")
    val lines = sc.textFile(new File(getClass.getClassLoader.getResource("Weather.csv").toURI).getPath)
    val tempDataEachHour = lines.mapPartitionsWithIndex((idx, iter) => if (idx == 0) iter.drop(1) else iter).map(parse)
      .reduceByKey((t1, t2) => (t1._1 + t2._1, t1._2 + t2._2)).mapValues(t => t._1 / t._2)

    val coldestHourEachDay = tempDataEachHour.map(kvp => ((kvp._1._1, kvp._1._2, kvp._1._3), (kvp._1._4, kvp._2)))
      .reduceByKey((t1, t2) => if (t1._2 < t2._2) t1 else t2)

    val coldestTime = coldestHourEachDay.values.mapValues(v => 1).reduceByKey((v1, v2) => v1 + v2).collect.maxBy(_._2)

    println(s"The coldest time of the day is ${coldestTime._1} and the occurences are ${coldestTime._2}")
  }

  def parse(x: String) = {
    val data = x.split(",")
    val w = Weather(data(0).toInt, data(1).toInt, data(2).toInt, data(3), data(4).toFloat, data(5).toFloat, data(6), data(7).toFloat, data(8).toFloat, data(9), data(10),
      data(11), data(12), data(13), data(14), data(15).toInt, data(16))
    val time = w.timeCST.split(":")(0) + (w.timeCST.split(":")(1) takeRight 2)
    ((w.year, w.month, w.day, time), (w.tempF, 1))
  }
}
