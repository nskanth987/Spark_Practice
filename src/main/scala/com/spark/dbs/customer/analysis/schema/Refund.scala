package com.dbs.customer.analysis.schema

import java.sql.Date

/**
 * Created By: Srikanth.nelluri
 * Date: 10-08-2019
 */
case class Refund(refund_id: Long,
                  original_transaction_id: Long,
                  customer_id: Long,
                  product_id: Long,
                  timestamp: Date,
                  refund_amount: Int,
                  refund_quantity: Int)
