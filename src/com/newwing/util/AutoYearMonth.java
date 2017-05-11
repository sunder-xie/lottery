package com.newwing.util;

import java.util.Calendar;

/**
 * 获取各种时间日期工具类
 * @author zhang_yu
 *
 */
public class AutoYearMonth {

	/**
	 * 获取当前日期(yyyy-MM-dd)
	 */
	public String getCurrentDate() {
		
		Calendar c = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf(c.get(Calendar.YEAR))).append("-")
							      .append(c.get(Calendar.MONTH) + 1).append("-")
							      .append(c.get(Calendar.DATE));
		return sb.toString();
		
	}
	
}
