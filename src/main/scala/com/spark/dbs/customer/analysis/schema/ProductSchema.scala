package com.dbs.customer.analysis.schema

/**
 * Created By: Srikanth.nelluri
 * Date: 10-08-2019
 */
case class ProductSchema(product_id: Long,
                         product_name: String,
                         product_type: String,
                         product_version: String,
                         product_price_in_$: Int)
