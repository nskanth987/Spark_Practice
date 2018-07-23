package com.spark.practice

import java.io.File
import java.nio.charset.CodingErrorAction

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

import scala.io.{Codec, Source}

/**
  * Practice "broadcast", "reduceByKey"
  */
object MovieNameDisplay {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val start = System.currentTimeMillis()

    val sc = new SparkContext("local[*]", "MovieNameDisplay")
    val lines = sc.textFile(new File(getClass.getClassLoader.getResource("u.data").toURI).getPath)
    val mNames = sc.broadcast(getMovieNames)
    val movies = lines.map(l => (l.split("\t")(1).toInt, 1))
    val movieCount = movies.reduceByKey((x, y) => x + y)
    val result = movieCount.map(m => (mNames.value(m._1), m._2)).sortBy(_._2, false).collect()
    result.foreach(println)

    val stop = System.currentTimeMillis()
    println(s"processing took ${(stop - start)} ms.")
  }

  def getMovieNames = {

    implicit val codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

    val movieData = Source.fromFile(new File(getClass.getClassLoader.getResource("u.item").toURI)).getLines
    val idToNameMap = movieData.map(l => {
      val data = l.split("\\|")
      (data(0).toInt, data(1))
    })
    idToNameMap.toMap
  }

}
