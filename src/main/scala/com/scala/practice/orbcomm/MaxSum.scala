package com.scala.practice.orbcomm

import scala.collection.mutable.Queue

object MaxSum {

  def main(args: Array[String]): Unit = {

    val n = Array(1, 6, 30, 2, 20, 7)
    val length = 6
    val m = 3
    val q = Queue(n.take(m): _*)
    var sum = n.take(m).sum
    n.drop(m).foreach(e => {
      if (sum < sum - q.headOption.getOrElse(0) + e) {
        sum = sum - q.dequeue() + e
        q.enqueue(e)
      }
    })
    println(sum)
  }

}
