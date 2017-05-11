package com.test;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestDate {

	public static void main(String[] args) {
//		test();
		testDouble();
	}
	
	public static void test() {
		String dataStr = "Wed Mar 15 03:45:00 CST 2017";
		SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
		try {
			Date date = sdf1.parse(dataStr);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sDate = sdf.format(date);
			System.out.println(sDate);
			System.out.println(sDate.substring(11, 16));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void testDouble() {
		Double wholeRangqiu1Double = new Double("1.1205");
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(df.format(wholeRangqiu1Double));
	}
	
}
