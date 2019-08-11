package com.spark.practice

import java.io._
import java.sql.Date

import org.apache.spark.sql.SparkSession

object SparkSQLTest {

  def main(args: Array[String]): Unit = {

    implicit val spark: SparkSession = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._

    spark.conf.set("spark.sql.crossJoin.enabled", "true")

    val depts = Seq(Dept(1, DepName("AR")), Dept(3, DepName("AR")), Dept(3, DepName("CR")), Dept(4, DepName("CR")),
      Dept(4, DepName("FR")), Dept(9, DepName("FR")), Dept(10, DepName("FR"))).toDS.repartitionByRange(2, $"empId")

    depts.rdd.mapPartitionsWithIndex((idx, vs) => {
      vs.map(x => x.copy(x.empId, x.depName.copy(idx.toString)))
    }).sortBy(_.depName.depName).take(20).foreach(println)

    val emp = Seq(Employee(1, "", "", Date.valueOf("2019-07-22"))).toDS

  }

  def toLCC(x: String): String = {
    val parts = x.split("_")
    parts.head + parts.tail.map(e => {
      e.head.toUpper + e.tail
    }).mkString
  }

  def deleteRecursively(file: File): Boolean = {
    if (file.isDirectory) {
      file.listFiles.map(deleteRecursively).forall(_ == true)
    } else {
      file.delete()
    }
  }

}

case class Employee(empId: Double, firstName: String, lastName: String, birthDay: Date)

case class Dept(empId: Int, depName: DepName)

case class DepName(depName: String)


