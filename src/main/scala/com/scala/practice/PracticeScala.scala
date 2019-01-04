package com.scala.practice

import java.lang

import com.frugalmechanic.optparse.OptParse

import scala.reflect.runtime.universe._

/**
 * Created by Srikanth.nelluri on 05/11/2018
 */
object PracticeScala extends OptParse {

  def main(args: Array[String]) = {


    val g: List[DictionaryInfo] = List()

    val even = List(DictionaryInfo(2, "Srikanth"))
    val odd = List(1, 3, 5, Nil)

    val n: DictionaryInfo = DictionaryInfo(null, null)

    //print(Option(n).map(_.name))

    val a: java.lang.Double = null

    val safeMultiply = (value1: Integer, value2: Integer) => {
      Option(value1).map(rf => new Integer((1 + rf.toInt) * 100 + value2.toInt)).orNull
    }

    println(Option(n).map(_.patternId).getOrElse(1))

    println((17 << 3))

    //println(11.0/2)

    //    even.flatMap(e =>
    //      odd.map(o => {
    //        (e.name,e.patternId, o)
    //      })).foreach(println)

    // println(g.groupBy(_.patternId).mapValues(_.head.name))

    // println(TSVFlag.ANY.getNumber)


    // classOf[DictionaryInfo].getDeclaredFields.map(_.getName).foreach(println)

  }
}

case class DictionaryInfo(patternId: Integer, name: String)
