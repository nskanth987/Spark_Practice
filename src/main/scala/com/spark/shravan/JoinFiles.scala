package com.spark.shravan

import org.apache.spark.sql.SparkSession

object JoinFiles {

  def main(args: Array[String]): Unit = {

    implicit val spark: SparkSession = SparkSession
      .builder()
      .appName("joinStrings parse example")
      .master("local[*]")
      .getOrCreate()

    val file1 = spark.sparkContext.textFile("E:\\file1.txt").zipWithIndex().map(x => (x._2, x._1))
    val file2 = spark.sparkContext.textFile("E:\\file2.txt").zipWithIndex().map(x => (x._2, x._1))
    val schema = spark.sparkContext.broadcast(spark.sparkContext.textFile("E:\\schema_file.csv")
      .collect().tail
      .map(line => {
        val tokens = line.split(",", -1)
        SchemaDef(tokens(0), tokens(1).toInt, tokens(2).toInt)
      })).value

    def joinStrings(str1: String, str2: String): String = {
      schema.map(field => {
        val start = field.startPos - 1
        val end = start + field.length
        if (str1.substring(start, end).forall(_ == ' ') || str1.substring(start, end).forall(_ == '0')) str2.substring(start, end) else str1.substring(start, end)
      }).mkString
    }

    file1.join(file2).map(x => joinStrings(x._2._1, x._2._2)).saveAsTextFile("E:\\result")
  }

}

case class SchemaDef(fieldName: String, startPos: Int, length: Int)
