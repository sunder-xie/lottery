package com.test;

import java.io.UnsupportedEncodingException;

public class TestString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ss = "夊凹娴佹氮鑰�";
		String gbk = "鐗规媺缁村か椹崱姣斿";
		try {
			String iso = new String(gbk.getBytes("gbk"),"UTF-8");
			System.out.println(iso);
			String[] testStr = iso.split("�");
			System.out.println(testStr[0]);
//			System.out.println(new String(iso.getBytes("ISO-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
