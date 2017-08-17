package clb.hu.scala

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.SparkConf

/**
 * DataFrame方法
 */
object DataFrameDemo {
 def main(args: Array[String]):Unit = {
   val conf = new SparkConf().setMaster("local").setAppName("DataFrameDemo")
   val sc = new SparkContext(conf)
   val sqlContext = new SQLContext(sc)
   
   //读取Json格式的文件
   val df = sqlContext.read.json("hdfs://master:8020/test/people.json")
   df.show()
   
   // 输出表结构 
   df.printSchema()
   
   // 选择所有年龄大于21岁的人，只保留name字段 
   df.filter(df("age") > 21).select("name").show()
   
   // 选择name，并把age字段自增
   df.select(df("name"), df("age")+5).show()
   
   // 按年龄分组计数 
   df.groupBy("age").count().show()
   
   // 左联表（注意是3个等号！） 	

   //df.join(df2, df(“name”) === df2(“name”), “left”).show() 
   
   
   //将DataFrame对象转为虚拟表
   df.registerTempTable("people")
   
   sqlContext.sql("select * from people").show()
   sqlContext.sql("select age,count(*) from people group by age").show()   //等同于  df.groupBy("age").count().show() 
   
   
   
 }
}