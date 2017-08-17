package clb.hu.spark.Spark_apps;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class SumListSpark {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		for(int i=1;i<=100;i++){
			list.add(i);
		}
      //可以使用以下方法创建一个list集合
      //List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		SparkConf conf = new SparkConf().setMaster("local").setAppName("SumListSpark");
		
		JavaSparkContext sc = new JavaSparkContext(conf);
       //并行化集合创建RDD
		JavaRDD<Integer> parallelizeRdd = sc.parallelize(list);
		
		Integer sum = parallelizeRdd.reduce(new org.apache.spark.api.java.function.Function2<Integer, Integer, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				return v1+v2;
			}
		});
		
		System.out.println("1-10之间的和为："+sum);

	}

}
