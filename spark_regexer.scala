import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import scala.collection.mutable.ArrayBuffer
import scala.io.Source


object SparkRegexer {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SparkRegexer")
    val sc = new SparkContext(conf)
    val s3File = args(0)
    val s3Out = args(1)
    val pattern =  args(2)
    val lines = sc.textFile(s"s3a://$s3File")
    val matches: RDD[String] =
      lines.filter(
        line => {
          pattern.r.findFirstIn(line) != None
        }
      )
    matches.saveAsTextFile(s"s3a://$s3Out")
    sc.stop()
  }
}