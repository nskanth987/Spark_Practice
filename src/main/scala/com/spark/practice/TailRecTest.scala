package com.spark.practice

import scala.annotation.tailrec

object TailRecTest {

  def main(args: Array[String]): Unit = {
    fact(5)
  }

  @tailrec
  def fact(x: Int): Unit = {
    println("called....")
    fact(x)
  }

}
