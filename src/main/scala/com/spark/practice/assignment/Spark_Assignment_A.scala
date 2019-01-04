package com.spark.practice.assignment

import java.io.File

import com.spark.practice.data.Weather
import org.apache.spark.SparkContext

object Spark_Assignment_A {

  def main(args: Array[String]): Unit = {

    val bogusVal = -9999F
    val sc = new SparkContext("local[*]", "MoreCommonTempType")
    val lines = sc.textFile(new File(getClass.getClassLoader.getResource("Weather.csv").toURI).getPath)
    val weatherData = lines.mapPartitionsWithIndex((idx, iter) => if (idx == 0) iter.drop(1) else iter).map(parse).groupByKey
    val daysWithLowAndHighTemp = weatherData.filter(temps => temps._2.exists(_ > 94.9F) || temps._2.exists(t => t < -9.9F && t != bogusVal)).cache
    /*.mapValues(temps => {
          if (temps.exists(_ > 94.9F) && temps.exists(_ < -9.9F))
            (1, 1)
          else if (temps.exists(_ < -9.9F))
            (1, 0)
          else
            (0, 1)
        })*/
    val daysWithHighTemp = daysWithLowAndHighTemp.filter(_._2.exists(_ > 94.9F)).count
    val daysWithLowTemp = daysWithLowAndHighTemp.filter(_._2.exists(_ < -9.9F)).count
    println(s"daysWithHighTemp = $daysWithHighTemp, daysWithLowTemp = $daysWithLowTemp . Most of the days were of TEMP type: " + (if (daysWithHighTemp > daysWithLowTemp) "HIGH" else "LOW"))

  }

  def parse(x: String) = {
    val data = x.split(",")
    val w = Weather(data(0).toInt, data(1).toInt, data(2).toInt, data(3), data(4).toFloat, data(5).toFloat, data(6), data(7).toFloat, data(8).toFloat, data(9), data(10),
      data(11), data(12), data(13), data(14), data(15).toInt, data(16))
    ((w.year, w.month, w.day), w.tempF)
  }
}


