package com.scala.practice

import scala.io.Source

/**
 * Created By: GGK
 * Date: 06-02-2019
 */
object FileRead {
  def main(args: Array[String]): Unit = {
    val name = "Srikanth"
    val lines = Source.fromFile("E:\\test.txt").getLines()
    lines.foreach(x => {
      val a = x.format(name)
      println(a)
    })
  }
}
