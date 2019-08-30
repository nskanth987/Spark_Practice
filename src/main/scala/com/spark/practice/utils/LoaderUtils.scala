package com.spark.practice.utils

import scala.reflect.ClassTag
import scala.reflect.runtime.universe.TypeTag

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

import org.apache.spark.rdd.RDD
import org.apache.spark.sql._

/**
 * Created By: Srikanth.nelluri
 * Date: 03-07-2019
 */
class LoaderUtils {

  private val spark: SparkSession = SparkSession.builder().getOrCreate()

  import spark.implicits._

  val fs: FileSystem = FileSystem.get(new Configuration())

  object Implicits {
    implicit def stringToDataHolder(s: String): DataHolder = {
      DataHolder(s)
    }
  }

  def readText(input: String): RDD[String] = {
    spark.sparkContext.textFile(input)
  }

  def readTextDF(input: String): DataFrame = {
    spark.read.text(input)
  }

  def readTextDS(input: String): Dataset[String] = {
    spark.read.textFile(input)
  }

  def readDelimitedText[T <: Product : ClassTag : TypeTag]
  (inputPath: String, delimiter: String = "\t", header: Boolean = false): Dataset[T] = {

    spark.read.option("delimiter", delimiter)
      .option("header", header.toString)
      .schema(Encoders.product[T].schema)
      .csv(inputPath).as[T]
  }

  def readParquet(inputPath: String): DataFrame = spark.read.parquet(inputPath)

  def readParquetDS[T <: Product : ClassTag : TypeTag](inputPath: String): Dataset[T] = {
    readParquet(inputPath).as[T]
  }

  def deleteOutput(outputPath: String): Unit = {
    if (fs.exists(new Path(outputPath))) {
      fs.delete(new Path(outputPath), true)
    }
  }

  def renamePath(oldPath: String, newPath: String): Unit = {
    if (fs.exists(new Path(oldPath))) {
      fs.rename(new Path(oldPath), new Path(newPath))
    }
  }

  def writeDelimitedText[T <: Product : ClassTag : TypeTag]
  (inputDataset: Dataset[T], outputPath: String, delimiter: String = "\t",
   codec: String = "none", saveMode: SaveMode = SaveMode.ErrorIfExists,
   header: Boolean = false): Unit = {

    inputDataset.write.option("compression", codec)
      .option("delimiter", delimiter)
      .option("header", header.toString)
      .mode(saveMode)
      .csv(outputPath)
  }

  def writeParquet[T <: Serializable](outputDataset: Dataset[T],
                                      outputPath: String,
                                      codec: String = "none",
                                      saveMode: SaveMode = SaveMode.ErrorIfExists)
  : Unit = {
    outputDataset.write
      .option("compression", codec)
      .mode(saveMode)
      .parquet(outputPath)
  }

  case class DataHolder(private val path: String) {
    def toDF: DataFrame = readParquet(path)
    def toRDD: RDD[String] = readText(path)
  }

}

