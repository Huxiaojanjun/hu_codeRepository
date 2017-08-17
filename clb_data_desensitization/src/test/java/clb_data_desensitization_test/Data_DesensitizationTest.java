package clb_data_desensitization_test;

import clb_data_desensitization.DataMaskUtils;

public class Data_DesensitizationTest {

	public static void main(String[] args) {

		DataMaskUtils dmu = new DataMaskUtils();
		
		//姓名脱敏
		String chineseName = dmu.chineseName("柯林布瑞",1,3);
		System.out.println("中文姓名只显示第一个汉子，其余隐藏为："+chineseName);
		
		//身份证号脱敏
		String idCardNumMask = dmu.idCardNumMask("341226198908091097", 6, 15);
		System.out.println("身份证号按照指定的位置使用*替换数字后结果为："+idCardNumMask);//341226*********097
		
		
		//固定电话脱敏
		String fixedPhone = dmu.fixedPhone("010-4353951", 7, 11);
		System.out.println("固定电话按照指定位置隐藏后的内容为："+fixedPhone);
		
		//手机号脱敏
		String mobilePhone = dmu.mobilePhone("18856987672", 0, 7);
		System.out.println("手机号按照指定位置隐藏后的内容为："+mobilePhone);
		
		//地址脱敏
		String addressMask = dmu.addressMask("上海市徐汇区宜山路700号", 7,"#");
		System.out.println("地址脱敏结果为："+addressMask);
	}

}
