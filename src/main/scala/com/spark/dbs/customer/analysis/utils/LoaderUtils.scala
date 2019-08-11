package com.dbs.customer.analysis.utils

import java.sql.Date
import java.text.SimpleDateFormat

import com.dbs.customer.analysis.schema.{Customer, ProductSchema, Refund, Sales}
import com.dbs.customer.analysis.utils.exception.InvalidDataException
import org.apache.spark.sql.{Dataset, SparkSession}


/**
 * Created By: Srikanth.nelluri
 * Date: 10-08-2019
 */
class LoaderUtils(implicit val spark: SparkSession) {

  import spark.implicits._

  @throws(classOf[InvalidDataException])
  def readProductData(inpPath: String, delimiter: String): Dataset[ProductSchema] = {
    val inp = spark.sparkContext.textFile(inpPath)
      .map(line => {
        val tokens = line.split(delimiter, -1)
        val productId = tokens(0).trim.toLong
        val prodPrice = tokens(4).trim.replace("$", "").toInt
        ProductSchema(productId, tokens(1).trim, tokens(2).trim, tokens(3).trim, prodPrice)
      })
    inp.toDS
  }

  @throws(classOf[InvalidDataException])
  def readCustomerData(inpPath: String, delimiter: String): Dataset[Customer] = {
    val inp = spark.sparkContext.textFile(inpPath)
      .map(line => {
        val tokens = line.split(delimiter, -1)
        val custId = tokens(0).trim.toLong
        val phoneNum = tokens(3).trim.toLong
        Customer(custId, tokens(1).trim, tokens(2).trim, phoneNum)
      })
    inp.toDS
  }

  @throws(classOf[InvalidDataException])
  def readRefundData(inpPath: String, delimiter: String): Dataset[Refund] = {
    val inp = spark.sparkContext.textFile(inpPath)
      .map(line => {
        val tokens = line.split(delimiter, -1)
        val refundId = tokens(0).trim.toLong
        val transcId = tokens(1).trim.toLong
        val refundAmount = tokens(5).trim.replace("$", "").toInt
        val datetimeFormatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
        val ts = datetimeFormatter1.parse(tokens(4).trim)

        Refund(refundId, transcId,
          tokens(2).trim.toLong,
          tokens(3).trim.toLong,
          new Date(ts.getTime),
          refundAmount,
          tokens(6).trim.toInt)
      })
    inp.toDS
  }

  @throws(classOf[InvalidDataException])
  def readSalesData(inpPath: String, delimiter: String): Dataset[Sales] = {
    val inp = spark.sparkContext.textFile(inpPath)
      .map(line => {
        val tokens = line.split(delimiter, -1)
        val tId = tokens(0).trim.toLong
        val cId = tokens(1).trim.toLong
        val datetimeFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
        val ts = datetimeFormatter.parse(tokens(3).trim)

        Sales(tId, cId,
          tokens(2).trim.toLong,
          new Date(ts.getTime),
          tokens(4).trim.replace("$", "").toInt,
          tokens(5).trim.toInt)
      })
    inp.toDS
  }

}
