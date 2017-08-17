package clb_data_udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * 自定义函数LowerUDF实现将单词转化为小写输出
 * 
 */
public class LowerUDF extends UDF{

	public Text evaluate(Text text){
		
		if(text.toString() == null){
			return null;
		}
		//转为小写
		return new Text(text.toString().toLowerCase());
	}	
	
	public static void main(String[] args) {
		System.out.println(new LowerUDF().evaluate(new Text("HaDooP")));
	}
	
}
