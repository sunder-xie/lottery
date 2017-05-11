package com.test;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.newwing.entity.JinshaBO;
import com.newwing.service.impl.JinshaThread;
import com.newwing.util.Logger;

public class JinshaTest {
	
	protected static Logger logger = Logger.getLogger(JinshaTest.class);
	
	public static WebDriver driver;
	
	public static void main(String[] args) {
		test();
	}
	
	public static void test() {
		logger.info(">>>>>>>>>>>>>>>>>>>> 开始采集数据 <<<<<<<<<<<<<<<<<<<<");
		List<JinshaBO> jinshaBOList = new ArrayList<JinshaBO>();
		System.setProperty("phantomjs.binary.path", "E:/phantomjs.exe");
		WebDriver driver = null;
		String url = "http://mkt.2999m.com/onebook?lang=cs&act=&webskintype=2";
		try {
			driver = new PhantomJSDriver();
			driver.get(url);
			WebDriverWait wait = new WebDriverWait(driver, 3);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("match-container")));
			
			WebElement divPage = driver.findElement(By.className("page"));
			Integer pageNo = new Integer(divPage.findElements(By.tagName("span")).get(2).getText().substring(0,1));
			logger.info(" >>>>>>>>>> pageNo <<<<<<<<<< " + pageNo);
			Thread tt = new JinshaThread(driver, 0, jinshaBOList);
			tt.start();
			while(true){//等待所有子线程执行完 
				if(!JinshaThread.hasThreadRunning()){ 
					break; 
				} 
				Thread.sleep(200);
			} 
			for (int ii = 1; ii < pageNo; ii++) {
				Thread t = new JinshaThread(driver, ii, jinshaBOList);
				t.start();
			}
			while(true){//等待所有子线程执行完 
				if(!JinshaThread.hasThreadRunning()){ 
					break; 
				} 
				Thread.sleep(200);
			} 
		} catch (Exception e) {
			logger.error("采集数据出现异常 >>>>>>>>>> " + e.getMessage());
		} finally {
			driver.close();
			driver.quit();
		}
		logger.info(">>>>>>>>>>>>>>>>>>>> 数据采集结束 <<<<<<<<<<<<<<<<<<<< [ " + jinshaBOList.size() + " ] 条记录");
	}
	
}
