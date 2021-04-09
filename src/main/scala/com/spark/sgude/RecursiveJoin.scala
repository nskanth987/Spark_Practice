package com.spark.sgude

import org.apache.spark.sql.SparkSession

object RecursiveJoin {

  def main(args: Array[String]): Unit = {

    implicit val spark: SparkSession = SparkSession
      .builder()
      .appName("Recursive Join example")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val childDS = Seq(AccountLink(5, 4), AccountLink(6, 2), AccountLink(7, 5)).toDS
    val ambs = Seq(AccountLink(4, 3), AccountLink(3, 2), AccountLink(2, 1), AccountLink(1, null)).toDS
    val ambsColl = ambs.collect()
    val ambsLookUp = ambsColl.map(doRecursion(_, ambsColl.toList)).toMap

    val res = childDS.flatMap(ca => {
      ambsLookUp.get(ca.transferAcct).map(es => es.map(e => ca.copy(transferAcct = e.acct))).getOrElse(List(ca))
    })
    res.show()
  }

  def doRecursion(amb: AccountLink, ambs: List[AccountLink]): (Int, List[AccountLink]) = {
    var nxtAmb = amb
    var res: List[AccountLink] = List(amb)
    while (nxtAmb.transferAcct != null) {
      val m = ambs.find(e => e.acct == nxtAmb.transferAcct).get
      res = res :+ m
      nxtAmb = m
    }
    amb.acct -> res
  }
}

case class AccountLink(acct: Int, transferAcct: Integer)
