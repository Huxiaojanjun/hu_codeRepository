package clb.hbase.utils;

public class SubRandom2 {

	/**
	 *  对产生的随机数从小数点后一位开始截取2位数字
	 */
	public static String getRandomNumber(){    
        String ranStr = Math.random()+"";    
        int pointIndex = ranStr.indexOf(".");    
        return ranStr.substring(pointIndex+1, pointIndex+3);    
    } 
}
