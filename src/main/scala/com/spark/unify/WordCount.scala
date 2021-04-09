package com.spark.unify

import org.apache.spark.sql.SparkSession

object WordCount {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("WC").master("local").getOrCreate()

    import spark.implicits._

    val inp = spark.read.textFile("")

    val words = inp.flatMap(l => l.split(" "))

    val wc = words.map((_, 1)).groupByKey(_._1).reduceGroups((l, r) => (l._1, l._2 + r._2)).map(t => (t._1, t._2._2))

  }

}
