package clb_data_udf;


import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * 自定义函数SubMaskDataUDF实现将数据按照需求进行截断,保留前几位
 * 截断：13811001111截断为138，舍弃必要信息来保证数据的模糊性，是比较常用的脱敏方法，但往往对生产不够友好。
 * 
 * @param obj   :需要截断的对象
 * @param start ：截断开始的位置下标
 * @param end   ：截断截止的位置下标
 */
public class SubMaskDataUDF extends UDF{

	  public String evaluate(Object obj,int start,int end){  
		if (obj == null) {  
		   return null;  
	    }  
		
		if(start<0){
			start=0;
		}
		if(end >obj.toString().length()){
			end=obj.toString().length();
		}
	  
	    return obj.toString().substring(start, end);
	    
	  } 
	
	public static void main(String[] args) {
		
		System.out.println(new SubMaskDataUDF().evaluate("hujianjun",1,6));
	}
}
