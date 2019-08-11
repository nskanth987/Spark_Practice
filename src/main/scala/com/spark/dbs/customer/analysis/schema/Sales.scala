package com.dbs.customer.analysis.schema

import java.sql.Date

/**
 * Created By: Srikanth.nelluri
 * Date: 10-08-2019
 */
case class Sales(transaction_id: Long,
                 customer_id: Long,
                 product_id: Long,
                 timestamp: Date,
                 total_amount: Int,
                 total_quantity: Int)
