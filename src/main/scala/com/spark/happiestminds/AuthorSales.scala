/*
package com.spark.happiestminds

import org.apache.spark.sql.{Dataset, SparkSession}

object AuthorSales {

  implicit val spark: SparkSession = SparkSession
    .builder()
    .appName("Spark example")
    .master("local[*]")
    .getOrCreate()

  import spark.implicits._

  val df1: Dataset[DF1] = _
  val df2: Dataset[DF2] = _
  val df3: Dataset[DF3] = _

  df1.joinWith(df2, df1("a_id") === df2("a_id"))
    .joinWith(df3, df2("b_id") === df3("b_id"))
    .map(x => Res(x._1._1.a_name, x._2.sales))
    .groupByKey(_.a_name)
    .reduceGroups((v1, v2) => v1.copy(v1.a_name, v1.sales + v2.sales))
    .orderBy($"sales".desc)
    .take(10)
}

case class DF1(a_id: Int, a_name: String)

case class DF2(b_id: Int, a_id: Int)

case class DF3(b_id: Int, sales: Long)

case class Res(a_name: String, sales: Long)
*/
