package com.spark.practice.assignment

import java.io.File

import com.spark.practice.data.Weather
import org.apache.spark.SparkContext

object Spark_Assignment_G {

  def main(args: Array[String]): Unit = {

    val bogusVal = -9999F
    val sc = new SparkContext("local[*]", "DayWithMaxTempDiff")
    val lines = sc.textFile(new File(getClass.getClassLoader.getResource("Weather.csv").toURI).getPath)
    val weatherData = lines.mapPartitionsWithIndex((idx, iter) => if (idx == 0) iter.drop(1) else iter).map(parse)

    //val tempDiffEachDay = weatherData.aggregate(List[Float]())((x, y) => x :+ y._2, (p, q) => p ++ q)
    val tempDiffEachDay = weatherData.reduceByKey((t1, t2) => t1 ++ t2).mapValues(_.filter(_ != bogusVal)).filter(_._2.nonEmpty).mapValues(temps => temps.max - temps.min)

    val dayWithMaxTempDiff = tempDiffEachDay.collect.maxBy(_._2)
    println(s"Max TEMP diff is ${dayWithMaxTempDiff._2} on day ${dayWithMaxTempDiff._1._1}-${dayWithMaxTempDiff._1._2}-${dayWithMaxTempDiff._1._3}")
  }

  def parse(x: String) = {
    val data = x.split(",")
    val w = Weather(data(0).toInt, data(1).toInt, data(2).toInt, data(3), data(4).toFloat, data(5).toFloat, data(6), data(7).toFloat, data(8).toFloat, data(9), data(10),
      data(11), data(12), data(13), data(14), data(15).toInt, data(16))
    ((w.year, w.month, w.day), List(w.tempF))
  }
}
