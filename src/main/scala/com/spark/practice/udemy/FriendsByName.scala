package com.spark.practice.udemy

import java.io.File

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

/**
  * Practice "map", "mapValues", "reduceByKey"
  */
object FriendsByName {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val start = System.currentTimeMillis()
    val sc = new SparkContext("local[*]", "FriendsByAge")
    val lines = sc.textFile(new File(getClass.getClassLoader.getResource("fakefriends.csv").toURI).getPath)
    val avgFriendsByName = lines.map(parse)
      .mapValues(v => (v, 1))
      .reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))
      .mapValues(v => v._1 / v._2)
      .collect
      .toSeq
      .sortBy(_._1)

    avgFriendsByName.foreach(println)
    val stop = System.currentTimeMillis()
    println(s"processing took ${(stop - start)} ms.")
  }

  def parse(x: String) = {
    val vs = x.split(",")
    (vs(1), vs(3).toInt)
  }

}
