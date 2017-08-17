package org.ibm.string.hash;

public class StringtoHash {

	public static void main(String[] args) {
		String str = "Dim_employee001";
		System.out.println(str.hashCode());//561068908
		
		String str1 = "Dim_employee0004";
		System.out.println(str1.hashCode());//213266985
		
		String str2 = "Dim_employee0005";
		System.out.println(str2.hashCode());//213266986
		
		String str3 = "Dim_employee0006";
		System.out.println(str3.hashCode());//213266987
		
		System.out.println("######"+(char)97);
		
		String str0 = "";
		String s = ""+213266987;
		System.out.println("===="+s);
		for(int i=0;i<s.length();i++){
			//从字符串的左边开始计算  
	        char charAt = (char)(s.charAt(i));//将获取到的字符串转换成数字，比如a的码值是97，则97-96=1 就代表a的值，同理b=2；  
	        str0+=charAt;
	    }  
		System.out.println("hash转字符串： "+str0);
		
		
		
		
		System.out.println("Dim_employee0006="+DJBHash("Dim_employee0006"));//1620101033501437476
		
		System.out.println("Dim_employee0005="+DJBHash("Dim_employee0005"));//1620101033501437475
		
		System.out.println("Dim_employee0004="+DJBHash("Dim_employee0004"));//1620101033501437474
		
		System.out.println("Dim_employee0105="+DJBHash("Dim_employee0105"));//1620101033501438564
		
		System.out.println("Dim_emploeeeqwertyee0105="+DJBHash("Dim_emploeeeqwertyee0105"));//5780102511572585062
	   
		
		
		System.out.println("pppp"+"Dim_employee0105".hashCode());
		System.out.println("pppp"+"5010eeyolpme_miD".hashCode());
		System.out.println("pppp"+"dim_employee0105".hashCode());
		System.out.println("pppp"+"dim_eMployee0105".hashCode());
		System.out.println("pppp"+"dmi_eMpoleey0105".hashCode());
		
		
	}
	
	   public static long DJBHash(String str)
	   {
	      long hash = 5381;

	      for(int i = 0; i < str.length(); i++)
	      {
	         hash = ((hash << 5) + hash) + str.charAt(i);
	      }

	      return hash;
	   }
	
	   
	   
	
}
