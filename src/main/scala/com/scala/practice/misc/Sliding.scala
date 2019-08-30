package com.scala.practice.misc

import java.sql.Date

/**
 * Created By: Srikanth.nelluri
 * Date: 14-08-2019
 */
object Sliding {

  def main(args: Array[String]): Unit = {

    val ls: Iterator[Int] = (1 to 27).toIterator

    val b = ls.grouped(4).sliding(3)
    val a = b
      .span(
        _ => ls.hasNext)

    (a._1.map(_.head) ++ a._2.flatten).foreach(println)

    println("end")

    /*while (a._1.hasNext) {
      println("=======")
      println(a._1.next().asInstanceOf[List[List[Int]]].foreach(println))
    }
    println("'''''''''''''''''''''")
    while (a._2.hasNext) {
      println("=======")
      println(a._2.next().asInstanceOf[List[List[Int]]].foreach(println))
    }*/
  }
}
