package clb_data_udf;


import org.apache.commons.lang3.StringUtils;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * 重排：序号12345重排为54321，按照一定的顺序进行打乱，
 * 自定义函数ReorderDataMaskUDF实现将数据按照指定的方式重排数据
 * 功能：这里只是简单的实现对一串数字进行反转
 */
public class ReorderDataMaskUDF extends UDF{

	public Text evaluate(Text text){
		String str = text.toString();
	    if(StringUtils.isBlank(str)) {  
	            return null;  
	    }  
	    
		return new Text(StringUtils.reverse(str));
	}
	public static void main(String[] args) {
		System.out.println(new ReorderDataMaskUDF().evaluate(new Text("123456")));
	}
}
