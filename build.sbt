name := "orctocassandrasparkexample"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.1.2"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.1.2"
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.8"
libraryDependencies += "org.apache.spark" % "spark-hive_2.11" % "2.1.2"