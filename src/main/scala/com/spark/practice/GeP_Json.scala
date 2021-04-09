package com.spark.practice

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ArrayType, StructType}
import org.apache.spark.sql.{Column, Encoders, SparkSession}

object GEP_JSON {

  def getColAtIndex(id: Int): Column = col(s"feature_flat")(id).as(s"column1_${id + 1}")

  def flattenSchema(schema: StructType, prefix: String = null): Array[Column] = {
    schema.fields.flatMap(f => {
      val colName = if (prefix == null) f.name else (prefix + "." + f.name)

      f.dataType match {
        case st: StructType => flattenSchema(st, colName)
        case at: ArrayType => at.elementType match {
          case ist: StructType => flattenSchema(ist, colName)
        }
        case _ => Array(col(colName).alias(colName))
      }
    })
  }

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("GEP")
      .master("local[4]")
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .getOrCreate()

    val sc = spark.sparkContext

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    spark.sqlContext.setConf("hive.exec.dynamic.partition", "true")
    spark.sqlContext.setConf("hive.exec.dynamic.partition.mode", "nonstrict")
    spark.conf.set("spark.sql.crossJoin.enabled", "true")
    import spark.implicits._

    val exprs = (colName: String) => (0 until 3).map(i => $"$colName".getItem(i).alias(s"$colName$i"))

    val data = spark.read.option("multiLine", true).json("E:\\Shravan-Spark\\test.json")
    data.printSchema()
    val comp = new Comp
    val df = data.select($"id", comp.toJson($"tripDistanceInHours.miles", $"tripDistanceInHours.timeRangeCd").as("tripDistanceInHours"))
    df.printSchema()
    df.toJSON.collect().foreach(println)

  }

}

class Comp extends Serializable {

  case class TD(miles: Double, timeRangeCd: String, kms: Double)

  val toJson = udf((miles: Seq[Double], timeRangeCd: Seq[String]) => {
    miles.zip(timeRangeCd).map(x => TD(x._1, x._2, x._1))
  }, new ArrayType(Encoders.product[TD].schema, true))

}
