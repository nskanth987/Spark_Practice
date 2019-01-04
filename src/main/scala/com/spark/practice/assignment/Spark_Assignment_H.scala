package com.spark.practice.assignment

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Spark_Assignment_H {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Hottest 7 Day period")
      .config("spark.master", "local[*]")
      .getOrCreate()

    val weatherDataDF = spark.read.format("csv").option("header", "true").load(this.getClass.getClassLoader.getResource("Weather.csv").getPath)

    val diffWithTS = weatherDataDF.as("w1")
      .crossJoin(weatherDataDF.as("w2"))
      .where("unix_timestamp(w2.dateutc) > unix_timestamp(w1.dateutc) and (unix_timestamp(w2.dateutc) - unix_timestamp(w1.dateutc) < 86400)")
      .selectExpr("w1.dateutc", "w2.dateutc", "abs(cast(w1.TemperatureF as double) - cast(w2.TemperatureF as double)) as diff")

    val result = diffWithTS.orderBy(desc("diff"))
    result.show(5)
  }

}
