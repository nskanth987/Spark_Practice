package com.scala.practice.hckrerth.leonteq

import scala.io.StdIn

object MatrixCells {

  def main(args: Array[String]): Unit = {

    val inp = StdIn.readLine().split(" ").map(_.toInt)
    val arrDim = inp(0)
    val tasks = inp(1)
    var empty = arrDim * arrDim
    val rows = (1 to arrDim).toArray
    val cols = (1 to arrDim).toArray

    val rowsAndCols = (1 to tasks).map(line => {
      val inp = StdIn.readLine().split(" ").map(_.toInt)
      (inp(0), inp(1))
    })

    val res = rowsAndCols.map(rc => {
      if (rows(rc._1 - 1) != 0) {
        empty -= (arrDim - cols.count(_ == 0))
        rows(rc._1 - 1) = 0
      }
      if (cols(rc._2 - 1) != 0) {
        empty -= (arrDim - rows.count(_ == 0))
        cols(rc._2 - 1) = 0
      }
      empty
    })

    print(res.mkString(" "))
  }
}
