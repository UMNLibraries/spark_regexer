# Spark Regexer

Takes a large file from S3, searches it with your provided regular expression, and outputs the found records to an S3 bucket of your choice.


## Run locally

```
git clone git@github.com:UMNLibraries/spark_regexer.git \
cd spark_regexer \
sbt package; /usr/local/spark/bin/spark-submit --class "SparkRegexer" --packages com.amazonaws:aws-java-sdk-pom:1.10.34,org.apache.hadoop:hadoop-aws:2.7.2  ./target/scala-2.11/spark-regexer_2.11-1.0.jar "your-big-file-bucket/your-big-file.json" "your-output-bucket" "(?i)your regex pattern here"
```

## Run on AWS EMR with Spark

Read through the [Amazon EMR Spark Tutorial](https://docs.aws.amazon.com/emr/latest/ReleaseGuide/emr-spark.html)

### TL;DR

* Launch an EMR Spark instance in the US East (N. Virginia) region (that is where our Spark jar file is located in S3)
* Load your large file in to an S3 bucket in this same region
* Enable SSH access to the master node
* Shell into the master node and copy over the spark-regexer jar file

```
aws s3 cp s3://umnlib-spark-builds/spark-regexer_2.11-1.0.jar .
spark-submit --class "SparkRegexer" ./spark-regexer_2.11-1.0.jar "your-big-file-bucket/your-big-file.json" "your-output-bucket" "your regex pattern here"
```