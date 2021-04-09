package com.spark.deltalake

import java.io.{PrintWriter, StringWriter}

object SparkDL {

  def main(args: Array[String]): Unit = {

    /*implicit val spark: SparkSession = SparkSession
      .builder()
      .appName("Spark DL")
      .master("local[2]")
      .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
      .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
      .getOrCreate()

    //    val data = spark.range(0, 5)
    //    data.write.format("delta").save("/spark-dl/delta-table")

    //    val df = spark.read.format("delta").load("/spark-dl/delta-table")
    //    df.show()

    val deltaTable = DeltaTable.forPath("/spark-dl/delta-table")
    //    deltaTable.delete(condition = expr("id < 100"))

    //    val newData = spark.range(0, 20).toDF
    //    deltaTable.as("oldData")
    //      .merge(newData.as("newData"), "oldData.id = newData.id")
    //      .whenMatched
    //      .update(Map("id" -> expr("newData.id + 100")))
    //      .whenNotMatched
    //      .insert(Map("id" -> col("newData.id")))
    //      .execute()

    deltaTable.toDF.show()*/

    val den = 0
    val num = 5

    try {
      println(num / den)
    } catch {
      case e: Exception =>
        val err = new StringWriter
        e.printStackTrace(new PrintWriter(err))
        println(err.toString)
    }

  }

}
