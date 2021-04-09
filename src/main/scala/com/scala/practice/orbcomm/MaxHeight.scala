package com.scala.practice.orbcomm

import scala.annotation.tailrec

/*
We have a building with n floors and m glass cups. Write a function which will take n and m and will
calculate the maximum height from where glass cup will not break.
Also calculate the time complexity of your algorithm.
 */
object MaxHeight {

  def main(args: Array[String]): Unit = {
    var n = 50
    var m = 1000
    println(findMax(end = n, m = m))
  }

  @tailrec
  def findMax(start: Int = 0, end: Int, m: Int): Int = {
    if (end - start == 1)
      if (isBroken(end)) start else end
    else if (m == 1) {
      (start to end).find(isBroken).getOrElse(start) - 1
    } else if (isBroken((start + end) / 2)) {
      findMax(start, ((start + end) / 2) - 1, m - 1)
    } else {
      findMax(((start + end) / 2), end, m)
    }
  }

  def isBroken(height: Int): Boolean = {
    height >= 27
  }

}
