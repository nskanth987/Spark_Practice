package com.scala.practice.hckrerth.leonteq

import scala.io.StdIn

object MaximumXOR {

  def main(args: Array[String]): Unit = {

    val nes = StdIn.readLine().toInt
    val arr = StdIn.readLine().split(" ").map(_.toInt).sorted(Ordering.Int.reverse)

    (0 until (nes)).foreach(tc => {
      val kBits = (1 to 32).map(_ => 1).toArray
      val xor = arr.drop(tc).reduce(_ ^ _).toBinaryString
      val curr = (1 to 32 - xor.length).map(_ => 0).mkString + "" + xor
      curr.zipWithIndex.foreach(pr => {
        if (pr._1 == '1') kBits(pr._2) = 0
      })
      println(java.lang.Long.parseLong(kBits.mkString, 2))
    })

  }
}
