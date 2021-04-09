package com.spark.sid

import org.apache.spark.sql.SparkSession

/*
empid,deptid,saleid,amt~
1,IT,123,400~
2,OPS,124,320~
3,IT,24,300~
4,OPS,124,321~
3,IT,24,200~
5,IT,123,20~

empid,deptid, amt~
3,IT,500~
4,OPS,321~
*/

object SIDPractice {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("SID").master("local[*]").getOrCreate()

    import spark.implicits._

    val inp = Seq(
      EmpSale(1, "IT", 123, 400),
      EmpSale(2, "OPS", 124, 320),
      EmpSale(3, "IT", 24, 300),
      EmpSale(4, "OPS", 124, 321),
      EmpSale(3, "IT", 24, 200),
      EmpSale(5, "IT", 123, 20)
    ).toDS()

    val n = 2

    val agg = inp.groupByKey(emp => (emp.id, emp.depId)).reduceGroups((v1, v2) => v1.copy(amount = v1.amount + v2.amount)).map(_._2)

    val res = agg.groupByKey(_.depId).mapGroups((key, group) => {
      group.toList.sortBy(_.amount)(Ordering[Int].reverse)(n - 1)
    })

    res.show(false)
  }

}

case class EmpSale(id: Int, depId: String, saleId: Int, amount: Int)
