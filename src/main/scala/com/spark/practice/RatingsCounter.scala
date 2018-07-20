package com.spark.practice

import java.io.File

import org.apache.spark.SparkContext

/**
  * Practice "countByValue"
  */
object RatingsCounter {

  def main(args: Array[String]): Unit = {

    val start = System.currentTimeMillis()
    val sc = new SparkContext("local[*]", "RatingsCounter")
    val lines = sc.textFile(new File(getClass.getClassLoader.getResource("u.data").toURI).getPath)
    val ratings = lines.map(_.split("\t")(2)).countByValue.toSeq.sortBy(_._1)
    ratings.foreach(println)
    val stop = System.currentTimeMillis()
    println(s"processing took ${(stop - start)} ms.")

  }

}
