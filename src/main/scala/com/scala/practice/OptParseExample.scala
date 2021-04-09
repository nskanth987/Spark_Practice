package com.scala.practice

import com.frugalmechanic.optparse.OptParse

/**
 * Created By: GGK
 * Date: 08-02-2019
 */
object OptParseExample extends OptParse {

  val isEnabled = MultiStrOpt()

  def main(args: Array[String]): Unit = {
    parse(args)
    println(isEnabled.get)
  }

}
