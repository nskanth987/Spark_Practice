package com.scala.practice

import java.lang.reflect.Constructor

import org.apache.spark.sql.SparkSession

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

    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .config("spark.master", "local[*]")
      .getOrCreate()

    spark.conf.set("spark.sql.crossJoin.enabled", "true")

    import spark.implicits._

    val k =
      q"""case class Emp(id: Int){}
         scala.reflect.classTag[Emp].runtimeClass
       """

    val a = List(Emp(123)).toDF

    val res = toolbox.compile(k)().asInstanceOf[Class[Emp]]
    val obj = res.getDeclaredConstructors()(0).newInstance(new Integer(111))
    println(obj)
    val ab = classOf[Emp].getDeclaredConstructors()(0).newInstance(new Integer(111))
    println(a)
  }
}

case class Emp(id: Int)
