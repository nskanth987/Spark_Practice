package com.spark.optum

import org.apache.spark.sql.SparkSession

object MoviesJob {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local[*]").appName("movies").getOrCreate()
    import org.apache.spark.sql.functions.broadcast
    import spark.implicits._

    val sample = Seq(
      Movie(1, "Movie-A", 2, 2020),
      Movie(1, "Movie-B", 2, 2019),
      Movie(1, "Movie-C", 2, 2021),
      Movie(1, "Movie-D", 4, 2010),
      Movie(1, "Movie-E", 3, 2019),
      Movie(1, "Movie-E", 3, 2010)
    )/*

//    val movies = spark.read.csv("").as[Movie]
//    val ratings = broadcast(spark.read.csv("").as[Rating])

    movies.joinWith(ratings, movies("rating") === ratings("id")).groupBy("year", "desc").count()

    movies.groupBy("year", "rating").count()*/

  }

}

case class Movie(id: Int, name: String, rating: Int, year: Int)

class Rating(id: Int, desc: String)

case class MoviesTr(year: Int, rating: Int, count: Int)
