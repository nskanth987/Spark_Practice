package com.spark.practice.assignment

import java.sql.{Date, Timestamp}

import org.apache.spark.sql.SparkSession

object Spark_Assignment_E {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Hottest 7 Day period")
      .config("spark.master", "local[*]")
      .getOrCreate()

    val weatherDataDF = spark.read.format("csv").option("header", "true").load(this.getClass.getClassLoader.getResource("Weather.csv").getPath)

    weatherDataDF.createOrReplaceTempView("weather")

    val result = spark.sql("WITH CTE_AvgTmpDaily AS (SELECT Year, Month, Day, AVG(TemperatureF) AS AVGTemperatureDaily FROM weather GROUP BY Year, Month, Day), " +
      "CTE_7DayTemp AS (SELECT Year, Month, Day, AVG(AVGTemperatureDaily) OVER (ORDER BY Year, Month, Day ASC ROWS 6 PRECEDING) AS 7DaysTemp FROM CTE_AvgTmpDaily) " +
      "SELECT Year, Month, Day, 7DaysTemp FROM CTE_7DayTemp SORT BY 7DaysTemp DESC LIMIT 1")

    result.show

  }

}
