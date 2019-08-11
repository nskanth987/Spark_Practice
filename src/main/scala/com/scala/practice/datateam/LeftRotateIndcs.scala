package com.scala.practice.datateam

/**
 * Created By: Srikanth.nelluri
 * Date: 08-08-2019
 */
object LeftRotateIndcs {

  def main(args: Array[String]): Unit = {
    numberOfAlerts(3, 10, Seq(0,11,10,10,7).toArray)
  }

  def numberOfAlerts(preceedingMinutes: Int, alertThreshold: Int, numberCalls: Array[Int]): Int = {
    // Write your code here
    for (i <- 1 until numberCalls.length) {
      numberCalls(i) = math.ceil((numberCalls(i - 1) * i + numberCalls(i)) / (i + 1).toDouble).toInt
    }
    var res = 0
    for (i <- preceedingMinutes - 1 until numberCalls.length) {
      if (numberCalls(i) > alertThreshold) res += 1
    }
    res
  }

  def getMaxElementIndexes(a: Array[Int], rotate: Array[Int]): Array[Int] = {
    // Write your code here
    var max = a(0)
    var idx = 0
    val len = a.length
    for (i <- 1 until len) {
      if (a(i) > max) {
        idx = i
        max = a(i)
      }
    }
    rotate.map(x => {
      var res = idx - x
      while (res < 0) res = len + res
      res
    })
  }

}
