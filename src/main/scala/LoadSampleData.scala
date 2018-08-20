import org.apache.spark.sql.SparkSession

object LoadSampleData {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("Load Orc Data")
//      .master("local[1]")
      .getOrCreate()

    // Load data into Cloudera HDFS Docker Container
    spark
    .sparkContext
    .textFile(args(1))
    .saveAsTextFile("hdfs://" + args(0) + "/sample.dat")
  }
}
