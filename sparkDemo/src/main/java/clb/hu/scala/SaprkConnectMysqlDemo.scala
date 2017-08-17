package clb.hu.scala

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.SparkConf
import java.util.Properties
/**
 * 读取数据库指定表数据
 */
object SaprkConnectMysqlDemo {
  def main(args: Array[String]): Unit = {
    
   val conf = new SparkConf().setMaster("local").setAppName("DataFrameDemo")
   val sc = new SparkContext(conf)
   val sqlContext = new SQLContext(sc)
   
   //指定url,test为mysql中的数据库，master为mysql主机ip
   val url = "jdbc:mysql://master:3306/test?useUnicode=true&useSSL=false"
   val prop = new Properties()
   //指定连接数据库的user和password
   prop.setProperty("user", "root")
   prop.setProperty("password","root")
   //添加驱动
//   prop.put("driver", "com.mysql.jdbc.Driver")
   //读取mysql数据库中表数据并显示
   val readMysql = sqlContext.read.jdbc(url,"user_t",prop)
   
   //将读取到mysql中的表数据保存到hdfs上(注意这里的路径需要给全路径名)
   readMysql.saveAsParquetFile("hdfs://master:8020/test/readmysql2hdfs.txt")
   
   //将读取到的数据写入到表user_write中并显示,写入时只能写入一次，再次写入会报错此表已存在
   val table = "user_write"
   readMysql.write.jdbc(url,table,prop)
   sqlContext.read.jdbc(url,table,prop).show()
   
  }
   
   
}