package com.scala.practice.hckrerth

import scala.io.StdIn

object FindMinimum {

  def main(args: Array[String]): Unit = {

    val tcs = StdIn.readLine().toInt

    (0 until tcs).foreach(tc => {
      val len = StdIn.readLine().toInt
      val a = StdIn.readLine().split(" ").map(_.toInt)
      val b = StdIn.readLine().split(" ").map(_.toInt).sorted
      println(a.zip(b).foldLeft(0)((agg, tup) => agg + (Math.abs(tup._1 - tup._2))))
    })

  }

}
