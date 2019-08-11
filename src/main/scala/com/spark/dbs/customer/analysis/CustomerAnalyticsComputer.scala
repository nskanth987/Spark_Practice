package com.dbs.customer.analysis

import java.util.Calendar

import com.dbs.customer.analysis.schema._
import org.apache.spark.sql.{Dataset, SparkSession}
import com.dbs.customer.analysis.utils.CAConstants._

/**
 * Created By: Srikanth.nelluri
 * Date: 10-08-2019
 */
class CustomerAnalyticsComputer(implicit val spark: SparkSession) {


  import spark.implicits._

  def totalAmountNotRefunded(salesData: Dataset[Sales], refundData: Dataset[Refund]): Int = {

    val notRefunded = salesData.filter(x => {
      val cal = Calendar.getInstance()
      cal.setTime(x.timestamp)
      cal.get(Calendar.YEAR) == FILTER_YEAR
    }).joinWith(refundData, salesData(TRANSACTION_ID) === refundData(ORIGINAL_TRANSACTION_ID), LEFT_OUTER)
      .filter(x => Option(x._2).isEmpty)
      .map(_._1.total_amount)

    notRefunded.reduce(_ + _)
  }

  def getProductsNotSold(productData: Dataset[ProductSchema], salesData: Dataset[Sales]): Dataset[ProductSchema] = {

    val notSoldData = productData.joinWith(salesData, productData(PRODUCT_ID) === salesData(PRODUCT_ID), LEFT_OUTER)
      .filter(x => Option(x._2).isEmpty).map(_._1)

    notSoldData
  }

  def getUsersPurchasedSameProduct(custData: Dataset[Customer], salesData: Dataset[Sales]): Long = {
    val joined = custData.joinWith(salesData, custData(CUSTOMER_ID) === salesData(CUSTOMER_ID))
      .map(_._2)
      .groupByKey(x => {
        val cal = Calendar.getInstance()
        cal.setTime(x.timestamp)
        (x.customer_id, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
      }).mapGroups {
      case (k, vs) =>
        val salesOnSameDay = vs.toArray.sortBy(_.timestamp.getTime)
        var count = 0

        for (i <- 1 until salesOnSameDay.length) {
          if (salesOnSameDay(i).product_id == salesOnSameDay(i - 1).product_id)
            count += 1
        }
        FilteredUsers(k._1, count)
    }.filter(_.count > 1)

    joined.count()
  }

  def getCustomerWith2ndMostPurchase(custData: Dataset[Customer],
                                     salesData: Dataset[Sales], refundData: Dataset[Refund]): String = {

    val notRefunded = salesData.joinWith(refundData, salesData(TRANSACTION_ID) === refundData(ORIGINAL_TRANSACTION_ID), LEFT_OUTER)
      .filter(x => Option(x._2).isEmpty)
      .map(_._1)

    val joined = custData.joinWith(notRefunded.filter(x => {
      val cal = Calendar.getInstance()
      cal.setTime(x.timestamp)
      cal.get(Calendar.YEAR) == FILTER_YEAR && cal.get(Calendar.MONTH) == MAY_MONTH
    }), custData(CUSTOMER_ID) === notRefunded(CUSTOMER_ID))
      .map(x => (x._1, 1)).groupByKey(_._1.customer_id)
      .reduceGroups((v1, v2) => (v1._1, v1._2 + v2._2))
      .map(x => CustomerCount(x._2._1.customer_first_name, x._2._1.customer_last_name, x._2._2))
      .orderBy($"count".desc)

    val customer = joined.take(2).last

    customer.customer_first_name + " " + customer.customer_last_name
  }

  def getProductDistribution(productData: Dataset[ProductSchema], salesData: Dataset[Sales])
  : Dataset[ProductCount] = {

    val joined = productData.joinWith(salesData, productData(PRODUCT_ID) === salesData(PRODUCT_ID))
      .map(x => ProductCount(x._1.product_name, x._1.product_type, x._2.total_quantity, x._2.total_amount))
      .groupByKey(x => (x.product_name, x.product_type))
      .reduceGroups((v1, v2) =>
        v1.copy(total_quantity = v1.total_quantity + v2.total_quantity,
          total_amount = v1.total_amount + v2.total_amount))
      .map(_._2)

    joined
  }

}
