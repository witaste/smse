package cn.zno.smse.common.util;

public class StringUtils {

	public static boolean isBlank(String str) {
		if (str == null)
			return true;
		if (str.trim().equals(""))
			return true;
		if(str.equals("null"))
			return true;
		return false;
	}
	
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}
}
