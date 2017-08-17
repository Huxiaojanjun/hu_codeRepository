package clb.hu.scala
import org.apache.spark.SparkContext

import org.apache.spark.sql.SQLContext
import org.apache.spark.SparkConf
import scala.collection.Set

/**
 * 对数据文件进行去重计数
 */

object DistinctReduceDemo {
  def main(args: Array[String]): Unit = {
   val conf = new SparkConf().setMaster("local").setAppName("TestDemo")
   val sc = new SparkContext(conf)
   val sqlContext = new SQLContext(sc)
   
   val files = sc.textFile("file:///E:/test/hh.txt")
   //打印读取的文件数据
   files.collect.foreach(println(_))
   /**
    * 对读取内容进行以分隔符【,】拆分
    * 将拆分后的数据进行map映射,并对第二个字段进行集合合并，然后对其进行合并
    * 
    */
   val result = files.map{d => val tmp = d.split(",");(tmp(0),Set(tmp(1)))}.reduceByKey(_++_)
   val result1 = files.map{d => val tmp = d.split(",");(tmp(0),Array(tmp(1)))}.reduceByKey(_++_)
   
   val r = files.map{d=>val tmp=d.split(","); (tmp(1),tmp(0))}.distinct.map{case(key,_) => (key,1)}.reduceByKey(_+_)

   
   print("=======result======="+result.collect().foreach(println(_)))
  // print("=======result1======="+result1.collect().foreach(println(_)))
   
   print("=======r======="+r.collect().foreach(println(_)))
   
   
   
   
  }
}