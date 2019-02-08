package com.scala.practice

/**
 * Created By: GGK
 * Date: 06-02-2019
 */
object ImplicitFunction {

  def main(args: Array[String]): Unit = {

    implicit def toLowerCase(x: String): Int = augmentString(x).toInt //x.split("_").mkString

    def f(i: Int) = i

    val a = (f("666"))

    print(a)

  }
}
