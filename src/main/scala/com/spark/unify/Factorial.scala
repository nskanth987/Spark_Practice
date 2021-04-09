package com.spark.unify

object Factorial {

  def main(args: Array[String]): Unit = {

    val n = 5
    if (n == 0)
      println(1)
    else
      println(factorial((1 to n).toList))

  }

  def factorial(n: List[Int], res: Int = 1): Int = {
    n match {
      case Nil => res
      case x :: xs => factorial(xs, res * x)
    }

  }

}
