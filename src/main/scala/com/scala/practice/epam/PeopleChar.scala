package com.scala.practice.epam

object PeopleChar {

  def main(args: Array[String]): Unit = {
    println(solution("cdeo", Array(3, 2, 0, 1)))
  }

  def solution(s: String, a: Array[Int]): String = {
    var res = s(0).toString
    var next = a(0)
    while (next != 0) {
      res = res + s(next)
      next = a(next)
    }
    res
  }
}
