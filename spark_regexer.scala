import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import scala.io.Source


object SparkRegexer {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SparkRegexer")
    val sc = new SparkContext(conf)
    val Array(s3File, s3Out, pattern) = args
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