import org.apache.spark.sql.SparkSession
import com.datastax.spark.connector._
import org.apache.spark.sql.cassandra._

case class sample_txt (
                        orderkey: String,
                        custkey: String,
                        orderstatus: String,
                        totalprice: String,
                        orderdate: String,
                        clerk: String,
                        shippriority: String,
                        comment: String
                      )

object Main {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("Orc to Cassandra Spark Example")
      .getOrCreate()
    import spark.implicits._


    val orcTable = spark
      .sqlContext
      .read
      .format("orc")
      .load("hdfs://" + args(0) + "/user/hive/warehouse/sample_database.db/sample_orc/")
      .map(x =>
        sample_txt.apply(
          x.get(0).asInstanceOf[String],
          x.get(1).asInstanceOf[String],
          x.get(2).asInstanceOf[String],
          x.get(3).asInstanceOf[String],
          x.get(4).asInstanceOf[String],
          x.get(5).asInstanceOf[String],
          x.get(6).asInstanceOf[String],
          x.get(7).asInstanceOf[String]
        )
      )
    orcTable
      .rdd
      .saveToCassandra(
        "orc",
        "sample_txt",
        SomeColumns(
          "orderkey",
          "custkey",
          "orderstatus",
          "totalprice",
          "orderdate",
          "clerk",
          "shippriority",
          "comment"
        )
      )
  }
}
