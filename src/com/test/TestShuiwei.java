package com.test;

import com.newwing.util.Logger;

public class TestShuiwei {

	protected static Logger logger = Logger.getLogger(TestShuiwei.class);

	public static void main(String[] args) {
		String h = "-1.25";
		String a = "1.25";
		String result = changeShuiwei(h, a);
		System.out.println(result);
	}

	public static String changeShuiwei(String h, String a) {
		String result = "";
		if ("-0.25".equals(h) && "0.25".equals(a)) {
			result = "0/0.5";
		} else if ("-0.75".equals(h) && "0.75".equals(a)) {
			result = "0.5/1";
		} else if ("-1.25".equals(h) && "1.25".equals(a)) {
			result = "1/1.5";
		} else if ("-1.75".equals(h) && "1.75".equals(a)) {
			result = "1.5/2";
		} else if ("-2.25".equals(h) && "2.25".equals(a)) {
			result = "2/2.5";
		} else if ("-2.75".equals(h) && "2.75".equals(a)) {
			result = "2.5/3";
		} else if ("-3.25".equals(h) && "3.25".equals(a)) {
			result = "3/3.5";
		} else if ("-3.75".equals(h) && "3.75".equals(a)) {
			result = "3.5/4";
		} else if ("-4.25".equals(h) && "4.25".equals(a)) {
			result = "4/4.5";
		} else if ("-4.75".equals(h) && "4.75".equals(a)) {
			result = "4.5/5";
		} 
		
		return result;
	}
}
