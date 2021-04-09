package com.scala.practice.valuelabs

object ScrambledWords {

  def main(args: Array[String]): Unit = {

    val dictionary = Array[String]("axpaj", "apxaj", "dnrbt", "pjxdn", "abd", "vldptfzbbdb")

    val result = dictionary.groupBy(_.length)
      .map(x => findMatchedCount(x._1, x._2, "aapxjdnrbtvldptfzbbdbbzxtndrvjblnzjfpvhdhhpxjdnrbt"))
      .sum

    println(result)
  }

  def findMatchedCount(length: Int, dictWords: Array[String], input: String): Int = {
    def wordOccurs(dictWord: String): Boolean = {
      val dictFreq = getFreq(dictWord)
      val start = dictWord.head
      val last = dictWord.last
      val subStrFreq = getFreq(input.substring(0, length))
      for (i <- 0 to input.length - length) {
        if (i > 0) {
          subStrFreq(input.charAt(i - 1) - 97) -= 1
          subStrFreq(input.charAt(i + length - 1) - 97) += 1
        }
        if (dictFreq.sameElements(subStrFreq))
          if (start == input.charAt(i) && last == input.charAt(i + length - 1))
            return true
      }
      false
    }

    dictWords.count(wordOccurs)
  }

  def getFreq(str: String): Array[Int] = {
    str.foldLeft(Array.fill(26)(0))((freqArr, ch) => {
      freqArr(ch - 97) += 1
      freqArr
    })
  }

}
