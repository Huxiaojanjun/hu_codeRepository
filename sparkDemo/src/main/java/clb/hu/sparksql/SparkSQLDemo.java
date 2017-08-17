package clb.hu.sparksql;

import org.apache.spark.SparkConf;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

public class SparkSQLDemo {

	public static void main(String[] args) {
		// 1.创建SparkConf对象
		SparkConf conf = new SparkConf().setMaster("local").setAppName("SparkSQLDemo");
		
		//2.创建JavaSparkContext对象
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		//3.创建SqlContext对象
		SQLContext sqlContext = new SQLContext(sc);
		
		/*
		 * 使用sqlContext读取hdfs文件
		 * json文件
		 */
       DataFrame df = sqlContext.read().json("hdfs://master:8020/test/province.json");
      // DataFrame df = sqlContext.read().format("json").load("hdfs://master:8020/test/province.json");
		
		df.show();
		
		df.registerTempTable("tb_province");
		
		sqlContext.sql("select * from tb_province limit 3").show();
		
		//sqlContext不需要进行关闭,直接关闭SparkContext即可
		sc.stop();

	}

}
