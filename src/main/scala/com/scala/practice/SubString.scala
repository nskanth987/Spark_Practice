package com.scala.practice

import scala.io.Source

object SubString {

  def main(args: Array[String]): Unit = {
    /*val a = Array(9, 1, 2, 3, 1, 4, 1, 1, 1, 12)
    val op = a.zipWithIndex.groupBy(_._1).map(x => (x._2.map(_._2).max - x._2.map(_._2).min)).max
    println(op + 1) // answer: 10 -> length of array*/


    val total = scala.io.StdIn.readLine()
    val more = scala.io.StdIn.readLine()

    val t = BigInt(total)
    val m = BigInt(more)

    val maddie = (t - m) / 2

    println(maddie + m)
    println(maddie)


  }

}
