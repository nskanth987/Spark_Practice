package com.spark.practice.assignment2

import java.util.Properties
import org.apache.spark.sql.SparkSession

object SparkSQLDBConnTest {

  var jdbcUrl = ""
  var connectionProperties = new Properties()

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .config("spark.master", "local[*]")
      .getOrCreate()

    setup()

    val project = spark.read.jdbc(jdbcUrl, "zcProject", connectionProperties)
    project.printSchema()
    project.select("ProjID").show(10)

  }

  def setup() = {
    val jdbcHostname = ""
    val jdbcPort = 1433
    val jdbcDatabase = ""

    jdbcUrl = s"jdbc:sqlserver://${jdbcHostname}:${jdbcPort};database=${jdbcDatabase};integratedSecurity=true"

    val driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
    connectionProperties.setProperty("Driver", driverClass)
  }

}
