package com.dbs.customer.analysis.driver

import com.dbs.customer.analysis.CustomerAnalyticsComputer
import com.dbs.customer.analysis.utils.LoaderUtils
import org.apache.spark.sql.SparkSession

/**
 * Created By: Srikanth.nelluri
 * Date: 10-08-2019
 */
object MainDriver {

  def main(args: Array[String]): Unit = {

    implicit val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName("Customer Analytics")
      .getOrCreate()

    import spark.implicits._
    val loader = new LoaderUtils

    val products = loader.readProductData("E:\\DBS\\Product.txt", "\\|")
    val customers = loader.readCustomerData("E:\\DBS\\Customer.txt", "\\|")
    val refundData = loader.readRefundData("E:\\DBS\\Refund.txt", "\\|")
    val salesData = loader.readSalesData("E:\\DBS\\Sales.txt", "\\|")

    /*val products = loader.readProductData("E:\\DBS\\cleaned\\Product.csv", ",")
    val customers = loader.readCustomerData("E:\\DBS\\cleaned\\Customer.csv", ",")
    val refundData = loader.readRefundData("E:\\DBS\\cleaned\\Refund.csv", ",")
    val salesData = loader.readSalesData("E:\\DBS\\cleaned\\Sales.csv", ",")*/

    val compute = new CustomerAnalyticsComputer

    // 2.) •	Display the distribution of sales by product name and product type.
    val productDistribution = compute.getProductDistribution(products, salesData)
    //productDistribution.repartition(1).write.option("delimiter", ",").csv("E:\\DBS\\output\\productDistribution")
    productDistribution.show(10, false)

    // 3.) Calculate the total amount of all transactions that happened in year 2013 and have not been refunded as of today
    val totalAmount = compute.totalAmountNotRefunded(salesData, refundData)
    println("3.) Total amount of all transactions that happened in year 2013 and have not been refunded as of today: " + totalAmount)

    // 4.) Display the customer name who made the second most purchases in the month of May 2013. Refunds should be excluded.
    val customer2ndMost = compute.getCustomerWith2ndMostPurchase(customers, salesData, refundData)
    println("customer name who made the second most purchases in the month of May 2013. Refunds should be excluded: " + customer2ndMost)

    // 5.)	Find a product that has not been sold at least once (if any).
    val notSoldProducts = compute.getProductsNotSold(products, salesData)
    notSoldProducts.show(10, false)
    //notSoldProducts.repartition(1).write.option("delimiter", ",").csv("E:\\DBS\\output\\notSoldProducts")

    // 6.) •	Calculate the total number of users who purchased the same product consecutively at least 2 times on a given day
    val filteredUSerCount = compute.getUsersPurchasedSameProduct(customers, salesData)
    println("Total number of users who purchased the same product consecutively at least 2 times on a given day " + filteredUSerCount)

  }

}
