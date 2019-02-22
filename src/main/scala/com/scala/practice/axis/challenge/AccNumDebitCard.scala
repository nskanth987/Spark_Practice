package com.scala.practice.axis.challenge

object AccNumDebitCard {

  def main(args: Array[String]): Unit = {

    val inpPairs = scala.io.Source.stdin.getLines.map { x =>
      val line = x.split(" ")
      val left = line(0).toInt
      val right = line(1).toInt
      (left, right)
    }.toList.dropRight(1)
    //val inpPairs = List((1000, 2000), (1500, 2000), (1400, 1600), (2000, 18000))

    val total = inpPairs
      .flatMap(x => List(x._1, x._2)).groupBy(identity).mapValues(_.size)

    val (debit, account) = total.partition(_._2 == 1)

    val output = inpPairs.map { x =>
      val res = if (!(account.contains(x._1) || account.contains(x._2)))
        "-1 -1"
      else if (debit.contains(x._1))
        "1 0"
      else
        "0 1"

      res
    }
    output.foreach(println)
  }
}

/*
Bank X contains a database of account numbers and debit cards mapped to each other. Now due to some technical issues, the data suddenly got deleted. But now the bank is left with a list of pair of integers but they don’t know which of them is the debit card no. and which one is the account number.

It is clear that an account can have multiple debit cards linked to it, but it is not possible that a debit card is linked to more than one accounts.

You need to check if the given data is recoverable or not. If it is consistent then you need to also find what is the account number and what is the debit card number. It is guaranteed that the input data is designed in a way that there is only one unique correct answer.
If for a pair it is impossible to find, you need to print -1 -1.

Note:

The input data contains a pair of integers and you have to decide for each pair, which is the account number and which is the debit card.
The account number can be of any length and debit card no can also be of any length. It is possible that the same pair of the integer is repeated more than once in the data.
The data is just dummy and is built up only for the competition purpose.
Input
There are several lines of input, each line having a pair of integers. You need to write the code such that it reads until the input data ends. The input ends with the pair -1 -1 and you don't need to calculate the result for it.

Output
For each line of the output, you need to print 0 corresponding to the number which is account number and 1 corresponding to the debit card number. If there is a pair for which it is not possible to confirm if its a debit card no. or the bank account number print "-1 -1" without quotes.

Scoring
Each test data will let you score a maximum of 10 points. If observations out of are correct, then the score is given by

Constraints

Sample Input
1000 2000
1500 2000
1400 1600
2000 18000
-1 -1
Sample Output
1 0
1 0
-1 -1
0 1
Explanation
It is clear that 2000 is an account number and it has the debit cards with number 1000 and 1500 linked to it. For the third pair we can't decide which of them would be the account number and which would be the debit card number, hence the output is -1. In the last pair 2000 is the account number and 18000 is the debit card number linked to it.
 */
