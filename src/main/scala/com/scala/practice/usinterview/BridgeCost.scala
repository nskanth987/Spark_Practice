package com.scala.practice.usinterview

import scala.collection.mutable

object BridgeCost {

  def main(args: Array[String]): Unit = {

    //    val input3 = 5
    val input4 = Array(
      Array(0, 1),
      Array(1, 2),
      Array(3, 4),
      Array(2, 4),
      Array(2, 6),
      Array(5, 2)
    )

    //    val res = input4.flatten.groupBy(x => x).count(_._2.length > 1) * input3

    val input1 = 7
    val input3 = Array(2, 7)


    var inp = (1 to input1).toArray
    val queue = new mutable.Queue[Int]()
    input3.foreach(p => {
      queue.dequeueAll(_ => true)
      queue.enqueue(p)
      while (queue.nonEmpty && queue.head <= input1) {
        val head = queue.dequeue()
        inp = inp.filter(_ != head)
        queue.enqueue(2 * head, 2 * head + 1)
      }
    })
    inp
  }
}
