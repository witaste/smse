package cn.zno.smse.common.util;

public class StringUtil {

	public static boolean isBlank(String str) {
		if (str == null)
			return true;
		if (str.trim().equals(""))
			return true;
		return false;
	}
	
	public static boolean isNotBlank(String str){
		if (str == null)
			return false;
		if (str.trim().equals(""))
			return false;
		return true;
	}
}
