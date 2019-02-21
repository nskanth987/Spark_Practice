package com.spark.practice

import java.io.{File, FileInputStream}
import java.sql.Date

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.spark.sql.SparkSession


object SparkSQLTest {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .config("spark.master", "local[*]")
      .getOrCreate()

    spark.conf.set("spark.sql.crossJoin.enabled", "true")

    import spark.implicits._

    val f = new File("E:\\DQChecks.xls")
    val fis = new FileInputStream(f)
    val myWorkbook = new HSSFWorkbook(fis)
    val mySheet = myWorkbook.getSheetAt(0)

  }

  def toLCC(x: String) = {
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


case class Employee(empId: Double, firstName: String, lastName: String, birthDay: Date, salary: Double)

case class Dept(empId: Double, depName: String)
