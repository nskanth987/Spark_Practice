package com.dbs.customer.analysis.utils.exception

/**
 * Created By: Srikanth.nelluri
 * Date: 10-08-2019
 */
final case class InvalidDataException(private val message: String = "") extends Exception(message)
