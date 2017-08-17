package clb.hu.scala

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object SumListSparkd{
  def main(args: Array[String]) {
    //定义一个list集合的数据集
    val list = List(1,2,3,4,5,6,7,8,9,10)
    //获取SparkConf对象conf
    val conf = new SparkConf().setAppName("SumListSpark").setMaster("local")

    //SparkContext对象sc
    val sc = new SparkContext(conf)

    //获取rdd
    val rdd = sc.parallelize(list)

    //rdd操作(对list进行reduce进行聚合操作)
    val sum = rdd.reduce(_+_)
    println("1-10 sum="+sum)
  }
}