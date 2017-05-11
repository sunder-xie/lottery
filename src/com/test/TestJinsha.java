package com.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.newwing.util.Logger;

public class TestJinsha {
	
	protected static Logger logger = Logger.getLogger(TestJinsha.class);
	
	public static WebDriver driver;
	
	public static void main(String[] args) {
		test();
	}
	
	public static void test() {
		System.setProperty("phantomjs.binary.path", "E:/phantomjs.exe");
		WebDriver driver = null;
		String url = "http://mkt.2999m.com/onebook?lang=cs&act=&webskintype=2";
		try {
			driver = new PhantomJSDriver();
			driver.get(url);
			WebDriverWait wait = new WebDriverWait(driver, 3);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("match-container")));
//			logger.info("driver.getPageSource() >>>>>>>>>> " + driver.getPageSource());
//			logger.info("driver.getCurrentUrl() >>>>>>>>>> " + driver.getCurrentUrl());
//			logger.info("driver.getWindowHandle() >>>>>>>>>> " + driver.getWindowHandle());
//			logger.info("driver.getWindowHandles() >>>>>>>>>> " + driver.getWindowHandles());
			
		} catch (Exception e) {
			logger.error("采集数据出现异常 >>>>>>>>>> " + e.getMessage());
		} finally {
			driver.close();
			driver.quit();
		}
	}
	
}
