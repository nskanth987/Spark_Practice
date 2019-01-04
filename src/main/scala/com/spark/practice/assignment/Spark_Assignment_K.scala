package com.spark.practice.assignment

import java.io.File

import com.spark.practice.data.Weather
import org.apache.spark.SparkContext

object Spark_Assignment_K {

  def main(args: Array[String]): Unit = {

    val clear = "Clear"
    val heavyRain = "Heavy Rain"
    val sc = new SparkContext("local[*]", "NiceDaysEndedPoor")
    val lines = sc.textFile(new File(getClass.getClassLoader.getResource("Weather.csv").toURI).getPath)
    val weatherConditionData = lines.mapPartitionsWithIndex((idx, iter) => if (idx == 0) iter.drop(1) else iter).map(parse).reduceByKey(_ ++ _)
      .map(d => ((d._1._1, d._1._2, d._1._3), (d._1._4, List[String](), d._2))).reduceByKey((v1, v2) => {
      if (v1._1 == "AM")
        (v1._1, v1._3, v2._3)
      else
        (v1._1, v2._3, v1._3)
    }).mapValues(c => (c._2, c._3))

    val reqDays = weatherConditionData.filter(p => p._2._1.exists(_ == clear) && p._2._2.exists(_ == heavyRain)).count
    println(s"No of days started nice and ended poor were: $reqDays")
  }

  def parse(x: String) = {
    val data = x.split(",")
    val w = Weather(data(0).toInt, data(1).toInt, data(2).toInt, data(3), data(4).toFloat, data(5).toFloat, data(6), data(7).toFloat, data(8).toFloat, data(9), data(10),
      data(11), data(12), data(13), data(14), data(15).toInt, data(16))
    ((w.year, w.month, w.day, w.timeCST.split(":")(1) takeRight 2), List(w.conditions))
  }
}
