package clb.hu.scala

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
/**
 * 分别统计包括字符a，b的行数
 */

object SimpleApp {
  def main(args: Array[String]): Unit = {
   val conf = new SparkConf().setMaster("local").setAppName("SimpleApp")
   val sc = new SparkContext(conf)
   
   val texts = sc.textFile("hdfs://master:8020/test/wordcount.txt", 2)
   //Broadcast Variables
   //sc.broadcast(texts)
   
   val numAs = texts.filter(_.contains("a")).count()
   val numBs = texts.filter(_.contains("b")).count()
   println(s"Lines with a: $numAs, Lines with b: $numBs")
   sc.stop()
   
 }
}