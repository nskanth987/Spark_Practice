package com.spark.practice.assignment

import java.io.File

import com.spark.practice.data.{AggWeatherConds, Weather}
import org.apache.spark.SparkContext

object Spark_Assignment_L {

  def main(args: Array[String]): Unit = {

    val validConds = List("Clear", "Partly Cloudy")
    val sc = new SparkContext("local[*]", "NoOfPerfectDays")
    val lines = sc.textFile(new File(getClass.getClassLoader.getResource("Weather.csv").toURI).getPath)
    val weatherData = lines.mapPartitionsWithIndex((idx, iter) => if (idx == 0) iter.drop(1) else iter).map(parse)
      .reduceByKey((w1, w2) => AggWeatherConds(w1.temps ++ w2.temps, w1.conds ++ w2.conds, w1.dewPoints ++ w2.dewPoints, w1.windSpeeds ++ w2.windSpeeds)).values

    val noOfPerfectDays = weatherData.filter(wcs => wcs.temps.forall(t => t > 59 && t < 80) && wcs.conds.forall(c => validConds.contains(c))
      && wcs.dewPoints.forall(_ < 60) && wcs.windSpeeds.forall(isValidWS)).count

    println(s"The No.of perfect days were: $noOfPerfectDays")
  }

  def isValidWS(ws: String): Boolean = {
    try {
      val s = ws.toFloat
      s <= 10F
    } catch {
      case _: Throwable => false
    }
  }

  def parse(x: String) = {
    val data = x.split(",")
    val w = Weather(data(0).toInt, data(1).toInt, data(2).toInt, data(3), data(4).toFloat, data(5).toFloat, data(6), data(7).toFloat, data(8).toFloat, data(9), data(10),
      data(11), data(12), data(13), data(14), data(15).toInt, data(16))
    ((w.year, w.month, w.day), AggWeatherConds(List(w.tempF), List(w.conditions), List(w.dewpPointF), List(w.windSpeed)))
  }

}
