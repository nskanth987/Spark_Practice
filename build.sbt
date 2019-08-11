name := "Spark_Practice"

version := "1.0"
val framelessVersion = "0.8.0"

scalaVersion := "2.11.12"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.3.0"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.0"

libraryDependencies += "com.frugalmechanic" %% "scala-optparse" % "1.1.2"

// https://mvnrepository.com/artifact/org.apache.pig/pig
libraryDependencies += "org.apache.pig" % "pig" % "0.16.0"

// https://mvnrepository.com/artifact/com.tresata/spark-sorted
libraryDependencies += "com.tresata" %% "spark-sorted" % "1.7.0"

// https://mvnrepository.com/artifact/org.apache.poi/poi
libraryDependencies += "org.apache.poi" % "poi" % "3.17"

