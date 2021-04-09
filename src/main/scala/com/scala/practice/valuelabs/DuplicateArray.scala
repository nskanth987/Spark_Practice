package com.scala.practice.valuelabs

object DuplicateArray {

  def main(args: Array[String]): Unit = {

    val a = Array(1, 2, 4, 4, 3, 3, 2, 5, 6, 1, 6, 5)

    val b = a.groupBy(x => x).map(x => (x._1, x._2.length)).find(_._2 == 1)

    if (b.isDefined) println(b.get._1) else println("No Element")

  }

}
