package com.scala.practice

/**
 * Created By: GGK
 * Date: 04-02-2019
 */
object PracticeReflec {

  def main(args: Array[String]): Unit = {
    import scala.reflect.runtime.universe._
    import scala.reflect.runtime.currentMirror
    import scala.tools.reflect.ToolBox
    val toolbox = currentMirror.mkToolBox()

    val k = q"5.equals(5)"
    val res = toolbox.eval(k)
    println(res)
  }
}
