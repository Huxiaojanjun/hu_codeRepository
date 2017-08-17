package clb.hu.spark.Spark_apps;

import java.util.Arrays;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class SparkCoreDemo {

	public static void main(String[] args) {
		/*
		 * 获取SparkConf对象conf
		 * "local"指的是本地运行spark app
		 * "AppName"指的是Spark app的name
		 */
		SparkConf conf = new SparkConf()
			.setMaster("local")
			.setAppName("SparkCoreDemo");
		
		/*
		 * 获取SparkContext对象
		 * sc代表了JavaSparkContext的对象
		 */
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		/*
		 * 读取hdfs的文件
		 * 此时获取到的就是我们的JavaRDD的对象
		 * 也就是文件里的一行一行的数据,注意:在Java API开发中我们使用的是JavaRDD
		 */
//		JavaRDD<String> lines = sc.textFile("hdfs://nicole02.com.cn:8020/spark/wordcount/wordcount.txt");

		JavaRDD<String> lines = sc.textFile("F:\\file_repository\\wordcount.txt");
		
		/*
		 * 在Java API的算子操作中,我们使用匿名内部类的方式来处理scala中使用到的高阶函数
		 * 那么,这个时候我们需要传入算子(flatMap...)指定的匿名内部类(new FlatMapFunction<String,String>())
		 * 那么,在FlatMapFucntion<String,String>()的两个泛型参数中:
		 * 第一个泛型参数指的是输入类型
		 * 第二个泛型参数指的是输出类型
		 */
		JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterable<String> call(String line) throws Exception {
				String[] arr = line.split(" ");
				return Arrays.asList(arr);
			}
		});
		
		/*
		 * 在这里,注意使用到的是mapToPair()方法来执行
		 * 这个方法是传入一个输入参数,返回一个scala.Tuple类型的对象
		 * 所以new PairFunction<String, String, Integer>()
		 * 第一个泛型参数是输入类型
		 * 第二个泛型参数是输出的Key的类型
		 * 第三个泛型参数是输出的Value的类型
		 * 返回的是Tuple2<Key,Value>的对象
		 */
		JavaPairRDD<String, Integer> tuple = words.mapToPair(new PairFunction<String, String, Integer>() {

			private static final long serialVersionUID = 1L;
			@Override
			public Tuple2<String, Integer> call(String word) throws Exception {
				return new Tuple2<String, Integer>(word, 1);
			}
		});
		
		
		/*
		 * 前两个泛型参数都是Value的输入的类型
		 * 第三个泛型参数是输出的value类型
		 */
		JavaPairRDD<String, Integer> wordCounts = tuple.reduceByKey(new Function2<Integer, Integer, Integer>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				return v1 + v2;
			}
		});
		
		wordCounts.foreach(new VoidFunction<Tuple2<String,Integer>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void call(Tuple2<String, Integer> tuple) throws Exception {
				System.out.println("word:"+tuple._1+" appears "+tuple._2+" times");
			}
		});
		
		//关闭
		sc.close();

	}

}
