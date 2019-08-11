package com.spark.nikhil

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.plans.logical.MapPartitions

/**
 * Created By: Srikanth.nelluri
 * Date: 19-07-2019
 */
object JoinsPractice {

  def main(args: Array[String]): Unit = {

    implicit val spark: SparkSession = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._
    spark.conf.set("spark.sql.crossJoin.enabled", "true")

    val empDF = Seq(
      Employee(1, "a", 10),
      Employee(2, "b", 11),
      Employee(5, "c", 12),
      Employee(4, "d", 11),
      Employee(5, "e", 12)
    ).toDF

    val depDF = Seq(
      Dept(10, "HR"),
      Dept(14, "admin"),
      Dept(12, "fmg")
    ).toDS

    val range = (1 to 7000).toDS
    println(range.rdd)

    /*val empDeptJoined = empDF.as("a").join(depDF.as("b"), "deptId")
    empDeptJoined.show(false)
    val agg = empDeptJoined.groupBy("deptId").agg(sumDistinct("id"))
    agg.show(false)*/

  }

}

case class Employee(id: Int, name: String, deptId: Int)

case class Dept(deptId: Int, depName: String)
