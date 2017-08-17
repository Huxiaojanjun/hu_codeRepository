package clb.hu.scala

import java.util.ArrayList
import scala.collection.mutable.ListBuffer
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext


object TestDemo {
  def main(args: Array[String]): Unit = {
//    traverseTuple2()
    
    createRDDMethod()
    
  }
  
  
  /**
   * 构建一个ListBuffer集合，泛型类型为Tuple2
   * 然后遍历list中的元素
   */
  def traverseTuple2(){
    var list = new ListBuffer[Tuple2[String,String]]()
    list.append(Tuple2("222","ddd"))
    list.append(Tuple2("333","ggg"))
    list.append(Tuple2("666","kkk"))
    for(a <- list)
      println(a._1+" => "+a._2)
  }
  
  
  /**
   * 创建RDD的方式
   */
  def createRDDMethod(){
     val conf = new SparkConf().setAppName("SumListSpark").setMaster("local")

      //SparkContext对象sc
      val sc = new SparkContext(conf)
      
      //设置RDD为3个分区
      val rddParallelize = sc.parallelize(1 to 10,3)
      
      rddParallelize.collect().foreach(print(_))
      
      println("打印分区数"+rddParallelize.partitions.size)
  }
  
}