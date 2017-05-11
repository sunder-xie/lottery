package com.test;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

public class TestYzm {

	public static WebDriver driver;

	public static void main(String[] args) {
//		testSelect();
		test();
	}

	public static void testSelect() {
		System.out.println("执行开始");
		String cmd = "cmd /c start E://yanzhengm.bat";
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("执行结束");
	}
	
	public static void test() {
		String code = "1235d";
		System.out.println(code.matches("^[0-9]*$"));
	}
	
}
