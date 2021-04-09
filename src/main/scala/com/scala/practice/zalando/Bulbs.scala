package com.scala.practice.zalando

object Bulbs {

  def main(args: Array[String]): Unit = {
    //        solution(Array(2, 1, 3, 5, 4))
    //        solution(Array(0, 1, 1, 5, 4))
    //0 -> 0 -> 1 -> 6 ->

    println(solution(Array(3, 5, 6, 3, 3, 5)))

    //    println(solution("011100") - 1) //
  }

  def solution(a: Array[Int]): Int = {
    // write your code in Scala 2.12
    val res = a.zipWithIndex.groupBy(_._1)
      .mapValues(x => x.length * (x.length - 1) / 2)
      .aggregate(0L)((a, n) => a + n._2, (a1, a2) => a1 + a2)

    if (res > 1000000000) 1000000000 else res.toInt
    //    a.foldLeft(Map.empty[Int, Int])((m, x) => m + ((x, m.getOrElse(x, 0) + 1))).foreach(println)
  }

  def solution(a: String): Int = {
    // write your code in Scala 2.12
    a.foldLeft(0)((cnt, char) => if (cnt == 0 && char == '0') cnt else if (char == '1') cnt + 2 else cnt + 1)

  }
}
