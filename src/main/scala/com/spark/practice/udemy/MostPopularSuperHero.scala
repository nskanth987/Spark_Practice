package com.spark.practice.udemy

import java.io.File
import java.nio.charset.CodingErrorAction

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

import scala.io.{Codec, Source}

/**
 * Created By: Srikanth.nelluri
 * Date: 30-08-2019
 */
object MostPopularSuperHero {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val start = System.currentTimeMillis()

    val sc = new SparkContext("local[*]", "MovieNameDisplay")
    val lines = sc.textFile(new File(getClass.getClassLoader.getResource("Marvel-graph.txt").toURI).getPath)
    val heros = lines.map(parseLine)
    val heroNameDict = sc.broadcast(getHeroNames)
    val heroCount = heros.reduceByKey((x, y) => x + y)
    val results = heroCount.map(h => (heroNameDict.value(h._1), h._2)).map(h => (h._2, h._1)).sortByKey(false).take(10)
    results.foreach(println)
    //val max = heroCount.map(h => (heroNameDict.value(h._1), h._2)).map(h => (h._2, h._1)).max()
    //println(max)

    val stop = System.currentTimeMillis()
    println(s"processing took ${(stop - start)} ms.")
  }

  def parseLine(l: String) = {
    val data = l.split(" ")
    (data.head, data.length - 1)
  }

  def getHeroNames = {

    implicit val codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

    val movieData = Source.fromFile(new File(getClass.getClassLoader.getResource("Marvel-names.txt").toURI)).getLines
    val idToNameMap = movieData.map(l => {
      val data = l.split(" ")
      (data(0).trim, data.tail.mkString(" "))
    })
    idToNameMap.toMap
  }

}
