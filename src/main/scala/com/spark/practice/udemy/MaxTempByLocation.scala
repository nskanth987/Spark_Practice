package com.spark.practice.udemy

import java.io.File

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

/**
  * Practice "filter", "reduceByKey"
  */
object MaxTempByLocation {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val start = System.currentTimeMillis()

    val sc = new SparkContext("local[*]", "MaxTempByLocation")
    val lines = sc.textFile(new File(getClass.getClassLoader.getResource("temperatures.csv").toURI).getPath)
    val temperatureData = lines.map(parse)
    val maxTemps = temperatureData.filter(_._2 == "TMAX")
    val stationTemps = maxTemps.map(td => (td._1, td._3))
    val stationMaxTemp = stationTemps.reduceByKey((x, y) => Math.max(x, y))
    stationMaxTemp.collect.foreach(t => println(f"${t._1} Max temperature is ${t._2}%.2f F"))

    val stop = System.currentTimeMillis()
    println(s"processing took ${(stop - start)} ms.")
  }

  def parse(x: String) = {
    val ds = x.split(",")
    val id = ds(0)
    val tempType = ds(2)
    val temp = ds(3).toFloat * 0.1F * (9.0 / 5.0) + 32f
    (id, tempType, temp)
  }

}
