package clb_data_desensitization;

import org.apache.commons.lang3.StringUtils;

public class DataMaskUtils {

	/** 
     * [中文姓名] 从start位置开始，到end位置的汉字内容全部替换为*
     *  
     */  
	public static String chineseName(String fullName,int start,int end) {  
		   if (StringUtils.isBlank(fullName)) {  
	            return "";  
	        }  
	        StringBuilder builder = new StringBuilder(fullName.substring(0, start));
	        int i = start;
	        while (i < Math.min(fullName.length(), end)) {
			    builder.append("*");
			    i += 1;
			}
			if (end < fullName.length()) {
			    builder.append(fullName.substring(end));
			}
			return builder.toString();
    }  
	 
	
	/** 
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762> 
     *  
     */  
    public static String idCardNumMask(String id_card,int start,int end) {  
		return chineseName(id_card, start, end);
        
    }  
    
    
    /** 
     * [固定电话] 后四位，其他隐藏<例子：****1234> 
     *  
     */  
    public static String fixedPhone(String num,int start,int end) {  
      return  chineseName(num,start,end);
      
    }  
  
    /** 
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234> 
     *  
     */  
    public static String mobilePhone(String phoneNum,int start,int end) {  
    	return chineseName(phoneNum,start,end);
    }  
    

    /**
     * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：上海市徐汇区****> 
     * @param address           地址
     * @param sensitiveSize     需要脱敏字数的个数
     * @param flag              需要使用的脱敏替换字符
     */
    public static String addressMask(String address, int sensitiveSize,String flag) {  
        if (StringUtils.isBlank(address)) {  
            return "";  
        }  
        int length = StringUtils.length(address);  
        return StringUtils.rightPad(StringUtils.left(address, length - sensitiveSize), length,flag);  
    } 
    
    
    
    
}
