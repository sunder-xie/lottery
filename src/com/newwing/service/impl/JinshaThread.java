package com.newwing.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.newwing.entity.JinshaBO;
import com.newwing.util.DateUtil;
import com.newwing.util.Logger;

public class JinshaThread extends Thread {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	private static List<Thread> runningThreads = new ArrayList<Thread>();
	private int pageIndex;
	private List<JinshaBO> jinshaBOList = new ArrayList<JinshaBO>();
	private WebDriver driver;

	public JinshaThread() {
	}
	
	public JinshaThread(WebDriver driver, int pageIndex, List<JinshaBO> jinshaBOList) {
		this.pageIndex = pageIndex;
		this.jinshaBOList = jinshaBOList;
		this.driver = driver;
		regist(this);//线程开始时注册 
	}
	@Override
	public void run() {
		try {
			logger.info("正在处理的页数：" + pageIndex);//打印结束标记
			System.setProperty("phantomjs.binary.path", "E:/phantomjs.exe");// windows环境
//			System.setProperty("phantomjs.binary.path", "/mydata/phantomjs-2.0.0/bin/phantomjs");// linux环境
			driver = new PhantomJSDriver();
			String url = "http://mkt.2999m.com/onebook?lang=cs&act=";
			driver.get(url);
			WebElement divDropdown = driver.findElement(By.className("dropdown"));
			divDropdown.findElement(By.tagName("a")).click();
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("match-container")));
			if (exsitElememt(divDropdown, By.tagName("ul"))) {
				WebElement ul = divDropdown.findElement(By.tagName("ul"));
				ul.findElements(By.tagName("li")).get(pageIndex).click();
				WebElement divMatch = driver.findElement(By.className("match-container"));
				List<WebElement> rowList = divMatch.findElements(By.className("row"));
				for (WebElement row : rowList) {
					List<WebElement> divList = row.findElements(By.tagName("div"));
					WebElement div1 = divList.get(1);
					List<WebElement> liList1 = div1.findElement(By.tagName("ul")).findElements(By.tagName("li"));
					String qiuduiMain = liList1.get(0).findElement(By.tagName("span")).getText();
					String qiuduiClient = liList1.get(1).findElement(By.tagName("span")).getText();
					if (qiuduiMain == null || "".equals(qiuduiMain.trim())) {
						continue;
					}
					if (qiuduiMain.contains("球")) {
						continue;
					}
					
					WebElement div0 = divList.get(0);
					WebElement div3 = divList.get(3);
					if (divList.size() == 4) {
						div3 = divList.get(2);
					}
					String startTimeStr = "";
					if (!exsitElememt(div0, By.tagName("ul"))) {
						startTimeStr = "";
					} else {
						startTimeStr = div0.findElement(By.tagName("ul")).findElement(By.tagName("li")).findElements(By.tagName("span")).get(1).getText();
					}
					
					List<WebElement> ulList3 = div3.findElements(By.tagName("ul"));
					
					String wholeRangqiu = "0";
					List<WebElement> li3List1 = ulList3.get(1).findElements(By.tagName("li"));
					WebElement li0 = li3List1.get(0);
					if (exsitElememt(li0, By.tagName("em"))) {
						wholeRangqiu = li0.findElement(By.tagName("em")).getText();
					}
					String wholeRangqiu1 = "0";
					if (exsitElememt(li0, By.tagName("b"))) {
						wholeRangqiu1 = li0.findElement(By.tagName("b")).getText();
					}
					if (wholeRangqiu1 == null || "".equals(wholeRangqiu1.trim())
							|| new Double(wholeRangqiu1) == 0) {
						continue;
					}
					
					String wholeRangqiuTemp = "0";
					String wholeRangqiu2 = "0";
					if (li3List1.size() == 2) {
						WebElement li1 = li3List1.get(1);
						if (exsitElememt(li1, By.tagName("em"))) {
							wholeRangqiuTemp = li1.findElement(By.tagName("em")).getText();
						}
						if (exsitElememt(li1, By.tagName("b"))) {
							wholeRangqiu2 = li1.findElement(By.tagName("b")).getText();
						}
					}
					if (wholeRangqiu == null || "".equals(wholeRangqiu)) {
						wholeRangqiu = wholeRangqiuTemp;
					}
					String wholeDaxiao = "";
					String wholeDaxiao1 = "0";
					String wholeDaxiao2 = "0";
					List<WebElement> li3List2 = ulList3.get(2).findElements(By.tagName("li"));
					if (exsitElememt(li3List2.get(0), By.tagName("em"))) {
						wholeDaxiao = li3List2.get(0).findElement(By.tagName("em")).getText();
						wholeDaxiao1 = li3List2.get(0).findElement(By.tagName("b")).getText();
						wholeDaxiao2 = li3List2.get(1).findElement(By.tagName("b")).getText();
					}
					
					JinshaBO jinshaBO = new JinshaBO();
					jinshaBO.setShijian(DateUtil.dateToStrDate(new Date()));
					jinshaBO.setStatus("0");
					jinshaBO.setStartTimeStr(startTimeStr);
					jinshaBO.setUpdateTime(new Date());
					jinshaBO.setQiuduiMain(qiuduiMain.replace(" ", ""));
					jinshaBO.setQiuduiClient(qiuduiClient.replace(" ", ""));
					jinshaBO.setWholeRangqiu(wholeRangqiu);
					jinshaBO.setWholeRangqiu1(new Double(wholeRangqiu1));
					jinshaBO.setWholeRangqiu2(new Double(wholeRangqiu2));
					jinshaBO.setWholeDaxiao(wholeDaxiao.replace("大", "").replace(" ", ""));
					jinshaBO.setWholeDaxiao1(new Double(wholeDaxiao1));
					jinshaBO.setWholeDaxiao2(new Double(wholeDaxiao2));
					jinshaBOList.add(jinshaBO);
//					logger.info("单行采集结束 >>>>>>>>>> " + pageIndex);//打印结束标记 
				}
			}
		} catch (Exception e) {
			logger.error("金沙采集第[" + pageIndex + "]异常 >>>>> " + e.getMessage());
		} finally {
			logger.info("已处理完的页数：" + pageIndex);//打印结束标记 
//			driver.close();
//			driver.quit();
			unRegist(this);//线程结束时取消注册 
		}
	} 
	
	public void regist(Thread t){ 
		synchronized(runningThreads){  
			runningThreads.add(t); 
		} 
	} 
	public void unRegist(Thread t){ 
		synchronized(runningThreads){  
			runningThreads.remove(t); 
		} 
	} 
	public static boolean hasThreadRunning() { 
		return (runningThreads.size() > 0);//通过判断runningThreads是否为空就能知道是否还有线程未执行完 
	} 
	
	private static boolean exsitElememt(WebElement webElement,By locator) {
		try {
			webElement.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
	}
}