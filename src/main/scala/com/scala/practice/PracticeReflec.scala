package com.scala.practice

import java.lang.reflect.Constructor

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

    val k =
      """case class Emp(id: Int){}
         scala.reflect.classTag[Emp].runtimeClass
       """
    // val res = toolbox.eval(k)
    val res = toolbox.compile(toolbox.parse(k))().asInstanceOf[Class[_]]
    val obj = res.getDeclaredConstructors()(0).newInstance(new Integer(123))
    println(obj)
  }
}

case class Emp(id: Int)
