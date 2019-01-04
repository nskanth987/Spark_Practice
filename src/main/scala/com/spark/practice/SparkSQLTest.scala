package com.spark.practice

import java.io.File
import java.sql.Date

import org.apache.spark.sql.types.IntegerType
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
    import org.apache.spark.sql.functions._

    val employee1 = Employee(101, "michael", "armbrust", Date.valueOf("2016-09-30"), 100000)
    val employee2 = Employee(102, null, "wendell", Date.valueOf("2016-09-30"), 5000)

    val empDS = Seq(employee1, employee2).toDS()
    val depDS = Seq(Dept(101, "tech"), Dept(103, "hr")).toDS()

    val base = Seq(TsvOrderLookUpBase(110, "Live", 1),
      TsvOrderLookUpBase(112, "Live + SD", 2),
      TsvOrderLookUpBase(113, "Live + 1", 3),
      TsvOrderLookUpBase(115, "Live + 3", 4),
      TsvOrderLookUpBase(119, "Live + 7", 5),
      TsvOrderLookUpBase(121, "Live + 14", 6),
      TsvOrderLookUpBase(126, "Live + 35", 7),
      TsvOrderLookUpBase(210, "Live", 8),
      TsvOrderLookUpBase(212, "Live + SD", 9),
      TsvOrderLookUpBase(213, "Live + 1", 10),
      TsvOrderLookUpBase(215, "Live + 3", 11),
      TsvOrderLookUpBase(219, "Live + 7", 12),
      TsvOrderLookUpBase(221, "Live + 14", 13),
      TsvOrderLookUpBase(226, "Live + 35", 14)).toDS()

    val TSV = "tsvFlag"
    /*val tempDir = Files.createTempDirectory("testtempdir")
    val tf = new File(tempDir.toString)
    println(tf.delete())*/
    val op = base.map(x => x.copy(tsvFlag = x.tsvFlag % 100, tsvOrder = 1)).distinct().orderBy($"$TSV").map(_.tsvFlag)
      .where($"tsvOrder" === 2)
    op.show()
    /*op.write.parquet(tempDir.toString)
    val inp = spark.read.parquet(tempDir.toString)
    inp.orderBy($"$TSV").show()*/

    // println(tf.getCanonicalPath + "===>>" + (deleteRecursively(tf) && tf.delete))


    val tol = base.as("a").join(base.as("b"),
      $"a.tsvOrder" + 1 === $"b.tsvOrder"
        && ($"a.tsvFlag" / 100).cast(IntegerType) === ($"b.tsvFlag" / 100).cast(IntegerType),
      "left").select($"a.tsvFlag", $"a.tsvName", $"b.tsvFlag".as("nextTsvFlag"),
      $"b.tsvName".as("nextTsvName"), $"a.tsvOrder").as[TsvOrderLookUp]

    val ldo = Seq(LiveDvrOverlapBig(111, 960, 0, 0, 413751212, 105, 102695.98665964275, 5033786.4553350005),
      LiveDvrOverlapBig(110, 960, 0, 0, 413751212, 105, 102685.98665964275, 5033786.4553350005),
      LiveDvrOverlapBig(112, 960, 0, 0, 413751212, 105, 102681.98665964275, 5033790.4553350005),
      LiveDvrOverlapBig(112, 960, 0, 0, 413751212, 105, 102681.98665964275, 5033790.4553350005),
      LiveDvrOverlapBig(113, 960, 0, 0, 413751212, 105, 102683.98665964275, 5033770.4553350005)).toDS()

    val sameCols = Seq("weekId", "timeId", "dayPartId", "webId", "genderAgeId")
    val tsvFlags = Seq(112, 113, 115, 119, 121, 126, 212, 213, 215, 219, 221, 226)

    var liveDvrOverlapWithNxtTsv = ldo.as("a")
      .join(tol.as("b"), Seq("tsvFlag"), "left").select("a.tsvFlag",
      "weekId", "timeId", "dayPartId", "webId", "genderAgeId", "liveDvrUVDedup", "liveDvrMins", "b.nextTsvFlag")

    liveDvrOverlapWithNxtTsv
      .withColumn("data", struct($"tsvFlag".as("_1"), $"nextTsvFlag".as("_2"), $"liveDvrUVDedup".as("_3"), $"liveDvrMins".as("_4")))
      .groupBy("weekId", "timeId", "dayPartId", "webId", "genderAgeId")
      .agg(collect_list("data").as("data")).as[TsvBoundingBaseNew].map(_.st).select(explode($"data"))

  }

  def deleteRecursively(file: File): Boolean = {
    if (file.isDirectory) {
      file.listFiles.map(deleteRecursively).forall(_ == true)
    } else {
      file.delete()
    }
  }
}

case class Employee(empId: Double, firstName: String, lastName: String, birthDay: Date, salary: Double) {

  def tm = {
    println("test")
    this
  }
}

case class Dept(empId: Double, depName: String)

case class TsvOrderLookUpBase(tsvFlag: Int, tsvName: String, tsvOrder: Int)

case class TsvOrderLookUp(tsvFlag: Int, tsvName: String, nextTsvFlag: Int, nextTsvName: String, tsvOrder: Int)

case class LiveDvrOverlapBig(tsvFlag: Int, weekId: Int, timeId: Int, dayPartId: Int, webId: Long, genderAgeId: Int, liveDvrUVDedup: Double, liveDvrMins: Double)

case class TsvBoundingBase(tsvFlag: Int, weekId: Int, timeId: Int, dayPartId: Int, webId: Long,
                           genderAgeId: Int, liveDvrUVDedup: Double, liveDvrMins: Double)

case class TsvBoundingBaseNew(weekId: Int, timeId: Int, dayPartId: Int, webId: Long,
                              genderAgeId: Int, data: Array[(Int, Int, Double, Double)]) {

  def st = {
    val a = updateMetrics(data)
    this.copy(data = a)
  }

  def updateMetrics: (Array[(Int, Int, Double, Double)] => Array[(Int, Int, Double, Double)]) = (group: Array[(Int, Int, Double, Double)]) => {
    var sorted = group
    group.foreach(temp => {
      sorted = sorted.map(entry => {
        if (sorted.find(_._2 == entry._1).isDefined) {
          val prev = sorted.find(_._2 == entry._1).get
          (entry._1, entry._2, math.max(entry._3, prev._3), math.max(entry._4, prev._4))
        } else {
          entry
        }
      })
    })
    sorted
  }

}