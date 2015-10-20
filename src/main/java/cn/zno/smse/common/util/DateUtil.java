package cn.zno.smse.common.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String standardFormat(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_FORMAT);
		return sdf.format(d);
	}

	public static String readableFormat(long l) {
		StringBuffer sb = new StringBuffer();

		long millisecond = l % 1000;
		long second = (l / 1000) % 60;
		long minute = (l / 1000 / 60) % 60;
		long hour = l / 1000 / 60 / 60;

		if (hour != 0) {
			sb.append(hour).append("小时");
		}
		if (hour != 0 || minute != 0) {
			sb.append(minute).append("分");
		}
		sb.append(second).append(".");
		sb.append(new DecimalFormat("000").format(millisecond));
		sb.append("秒");

		return sb.toString();

	}

}
