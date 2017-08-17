package clb_data_udf;


import org.apache.commons.lang3.StringUtils;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
/**
 * 自定义函数NameMaskUDF实现将指定位置的内容替换隐藏
 */
public class NameMaskUDF extends UDF{

	public Text evaluate(Text text,int start,int end){
		
		 if (StringUtils.isBlank(text.toString())) {  
	            return null;  
	     } 
		 //如果开始截取位置数小于0，则默认为0位置开始截取
		 if(start<0){
			 start=0;
		 }
	     StringBuilder builder = new StringBuilder(text.toString().substring(0, start));
	        int i = start;
	        while (i < Math.min(text.toString().length(), end)) {
			    builder.append("*");
			    i += 1;
			}
			if (end < text.toString().length()) {
			    builder.append(text.toString().substring(end));	
			}
		
		return new Text(builder.toString());
	}
	
	public static void main(String[] args) {
		System.out.println(new NameMaskUDF().evaluate(new Text("詹姆斯"),-1,24));
	}
}
