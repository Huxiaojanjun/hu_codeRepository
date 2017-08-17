package clb_data_udf;


import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * 自定义函数ReplaceMaskDataUDF实现将某一相同类型的数据替换为指定的字符
 * 替换：如统一将女性用户名替换为F，这种方法更像“障眼法”，对内部人员可以完全保持信息完整性，但易破解。
 */
public class ReplaceMaskDataUDF extends UDF{
	
	  private String str = null;  
	  public String evaluate(String urlStr){  
	    if (urlStr == null) {  
	      return null;  
	    }  
	  if(urlStr.equals("男")){
		 str = urlStr.replaceAll(urlStr, "F");  
	  }else if(urlStr.equals("女")){
		  str = urlStr.replaceAll(urlStr, "M");
	  }else{
		  str = urlStr.replaceAll(urlStr, "!");
	  }
	    
	  
	    return str;  
	  }  
	
	public static void main(String[] args) {
		
		System.out.println(new ReplaceMaskDataUDF().evaluate("女"));
	}
}
