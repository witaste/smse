package cn.zno.smse.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static String standardFormat(Date d){
		SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_FORMAT);
		return sdf.format(d);
	}

}
