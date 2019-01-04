package com.spark.practice.assignment

import java.io.File

import com.spark.practice.data.Weather
import org.apache.spark.SparkContext

object Spark_Assignment_B {

  def main(args: Array[String]): Unit = {

    val bogusVal = -9999F
    val sc = new SparkContext("local[*]", "AvgTempByMonth")
    val lines = sc.textFile(new File(getClass.getClassLoader.getResource("Weather.csv").toURI).getPath)
    val weatherData = lines.mapPartitionsWithIndex((idx, iter) => if (idx == 0) iter.drop(1) else iter).map(parse).cache
    val avgHighTempPerMonth = weatherData.reduceByKey((t1, t2) => if (t1 > t2) t1 else t2).filter(_._2 != bogusVal).map(kvp => ((kvp._1._1, kvp._1._2), (kvp._2, 1)))
      .reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2)).mapValues(kvp => kvp._1 / kvp._2).collect.sortBy(_._1)
    //.groupByKey.mapValues(highTemps => highTemps.sum / highTemps.size).collectAsMap()
    val avgLowTempPerMonth = weatherData.reduceByKey((t1, t2) => if (t1 > t2) t2 else t1).filter(_._2 != bogusVal).map(kvp => ((kvp._1._1, kvp._1._2), (kvp._2, 1)))
      .reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2)).mapValues(kvp => kvp._1 / kvp._2).collect.sortBy(_._1)
    //.groupByKey.mapValues(lowTemps => lowTemps.sum / lowTemps.size).collectAsMap()

    //avgHighTempPerMonth.foreach(kvp => println(s"Avg high temp for month ${kvp._1._1}-${kvp._1._2} is ${kvp._2}"))
    //avgLowTempPerMonth.foreach(kvp => println(s"Avg low temp for month ${kvp._1._1}-${kvp._1._2} is ${kvp._2}"))

    avgHighTempPerMonth.zip(avgLowTempPerMonth)
      .foreach(kvp => println(s"Avg temp for month ${kvp._1._1._1}-${kvp._1._1._2} (${kvp._2._1._1}-${kvp._2._1._2}) is HIGH: ${kvp._1._2} LOW: ${kvp._2._2} ${if(kvp._1._2 < kvp._2._2) "ERROR" else "OK"}"))
  }

  def parse(x: String) = {
    val data = x.split(",")
    val w = Weather(data(0).toInt, data(1).toInt, data(2).toInt, data(3), data(4).toFloat, data(5).toFloat, data(6), data(7).toFloat, data(8).toFloat, data(9), data(10),
      data(11), data(12), data(13), data(14), data(15).toInt, data(16))
    ((w.year, w.month, w.day), w.tempF)
  }

}
