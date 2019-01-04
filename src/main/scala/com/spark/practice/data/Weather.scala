package com.spark.practice.data

case class Weather(year: Int, month: Int, day: Int, timeCST: String, tempF: Float, dewpPointF: Float, humidity: String, seaLevelPress: Float, visMPH: Float, windDir: String,
                   windSpeed: String, gustSpeed: String, precep: String, events: String, conditions: String, windDeg: Int, dateUTC: String)

case class AggWeatherConds(temps: List[Float], conds: List[String], dewPoints: List[Float], windSpeeds: List[String])
