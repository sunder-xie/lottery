package com.newwing.service.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.newwing.entity.BewinBO;
import com.newwing.entity.JinshaBO;
import com.newwing.entity.RelativeBO;
import com.newwing.service.BakIJinshaService;
import com.newwing.util.DateUtil;
import com.newwing.util.ExcelUtil;
import com.newwing.util.Logger;

public class BakJinshaServiceImpl extends BaseServiceImpl implements BakIJinshaService {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	public void autoReptile(List<JinshaBO> jinshaBOList) throws Exception {
		logger.info(">>>>>>>>>>>>>>>>>>>> 开始采集金沙数据 <<<<<<<<<<<<<<<<<<<<");
		WebDriver driver = null;
		try {
			String url = "http://www.2999m.com/sport/?game_type=FT";
//			System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
//			ChromeOptions options = new ChromeOptions();  
//			options.addArguments("user-data-dir=C:/Users/Teddy/AppData/Local/Google/Chrome/User Data");
//			options.addArguments("--start-maximized", "allow-running-insecure-content", "--test-type");
//			driver = new ChromeDriver(options); 
//			driver.get("http://www.16660029.com/sport/?page=odds_today&jdfwkey=ugwip");
			
			System.setProperty("phantomjs.binary.path", "E:/phantomjs.exe");  
	        driver = new PhantomJSDriver();
	        driver.get(url);
			Thread.sleep(2000);
//	        WebElement divTemp = driver.findElement(By.id("js-odds-wrap"));
//	        WebElement div = divTemp.findElement(By.id("js-odds"));
			WebElement div = driver.findElement(By.id("js-odds"));
			WebElement table = div.findElement(By.tagName("table"));
			List<WebElement> tbodyList = table.findElements(By.tagName("tbody"));
			for(WebElement tbody : tbodyList) {
				List<WebElement> trList = tbody.findElements(By.tagName("tr"));
				if (trList != null && trList.size() == 2) {
					JinshaBO jinshaBO = new JinshaBO();
					List<WebElement> tdList = trList.get(0).findElements(By.tagName("td"));
					if (tdList != null && tdList.size() == 11) {
						WebElement div0 = tdList.get(0).findElement(By.className("gametime"));
						String startTimeStr = div0.getText();
						
						WebElement div1 = tdList.get(1).findElement(By.tagName("div"));
						List<WebElement> subDiv1List = div1.findElements(By.tagName("div"));
						String qiuduiMain = subDiv1List.get(0).getText();// 主队名
						String qiuduiClient = subDiv1List.get(1).getText();// 客队名
						jinshaBO.setQiuduiMain(qiuduiMain.replace(" ", ""));
						jinshaBO.setQiuduiClient(qiuduiClient.replace(" ", ""));
							
						List<WebElement> div3List = tdList.get(3).findElements(By.tagName("div"));
						List<WebElement> subDiv3List0 = div3List.get(0).findElements(By.tagName("div"));
						String wholeRangqiu = subDiv3List0.get(0).getText();
						String wholeRangqiuTemp = subDiv3List0.get(1).getText();
						
						if (wholeRangqiu != null && !"".equals(wholeRangqiu)) {
							jinshaBO.setWholeRangqiu(wholeRangqiu);
						}
						if (wholeRangqiuTemp != null && !"".equals(wholeRangqiuTemp)) {
							jinshaBO.setWholeRangqiu(wholeRangqiuTemp);
						}
						List<WebElement> subDiv3List1 = div3List.get(3).findElements(By.tagName("div"));
						String wholeRangqiu1 = subDiv3List1.get(0).findElement(By.tagName("a")).getText();
						String wholeRangqiu2 = subDiv3List1.get(1).findElement(By.tagName("a")).getText();
						
						// 用于获取大小相关数据
						List<WebElement> div4List = tdList.get(4).findElements(By.tagName("div"));
						List<WebElement> subDiv4List0 = div4List.get(0).findElements(By.tagName("div"));
						String wholeDaxiao = subDiv4List0.get(0).getText();
						String wholeDaxiaoTemp = subDiv4List0.get(1).getText();
						if (wholeDaxiao != null && !"".equals(wholeDaxiao)) {
							jinshaBO.setWholeDaxiao(wholeDaxiao);
						}
						if (wholeDaxiaoTemp != null && !"".equals(wholeDaxiaoTemp)) {
							jinshaBO.setWholeDaxiao(wholeDaxiaoTemp);
						}
						List<WebElement> subDiv4List1 = div4List.get(3).findElements(By.tagName("div"));
						String wholeDaxiao1 = subDiv4List1.get(0).findElement(By.tagName("a")).getText();
						String wholeDaxiao2 = subDiv4List1.get(1).findElement(By.tagName("a")).getText();

						if (wholeRangqiu1 != null && !"".equals(wholeRangqiu1)) {
							if (new Double(wholeRangqiu1).doubleValue() < 0) {
								throw new Exception("主水负数，本次采集的是马来数据");
							}
							jinshaBO.setWholeRangqiu1(new Double(wholeRangqiu1));
						}
						if (wholeRangqiu2 != null && !"".equals(wholeRangqiu2)) {
							if (new Double(wholeRangqiu2).doubleValue() < 0) {
								throw new Exception("客水负数，本次采集的是马来数据");
							}
							jinshaBO.setWholeRangqiu2(new Double(wholeRangqiu2));
						}
						jinshaBO.setShijian(DateUtil.dateToStrDate(new Date()));
						jinshaBO.setStatus("0");
						jinshaBO.setStartTimeStr(startTimeStr);
						jinshaBO.setUpdateTime(new Date());
						
						jinshaBO.setWholeDaxiao(wholeDaxiao);
						if (wholeDaxiao1 != null && !"".equals(wholeDaxiao1)) {
							jinshaBO.setWholeDaxiao1(new Double(wholeDaxiao1));
						}
						if (wholeDaxiao2 != null && !"".equals(wholeDaxiao2)) {
							jinshaBO.setWholeDaxiao2(new Double(wholeDaxiao2));
						}
					}
					jinshaBOList.add(jinshaBO);
				}
			}
			this.save(jinshaBOList);
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error("采集金沙数据出现异常 >>>>>>>>>> " + e.getMessage());
			throw e;
//			Thread.sleep(1000);
//			driver.close();
//			this.autoReptile(new ArrayList<JinshaBO>());
		} finally {
			driver.close();
			driver.quit();
		}
		logger.info(">>>>>>>>>>>>>>>>>>>> 金沙数据采集结束 <<<<<<<<<<<<<<<<<<<<");
	}
	
	private void save(List<JinshaBO> jinshaBOList) throws Exception {
		if (jinshaBOList != null && jinshaBOList.size() > 0) {
			this.baseDAO.executeHql("delete from JinshaBO ");// 直接删除历史数据
			for (JinshaBO jinshaBO : jinshaBOList) {
//				if (jinshaBO.getQiuduiMain().contains("球")) {
//					continue;
//				}
//				if (jinshaBO.getQiuduiMain() == null || "".equals(jinshaBO.getQiuduiMain().trim())) {
//					continue;
//				}
//				if (jinshaBO.getWholeRangqiu1() == null || "".equals(jinshaBO.getWholeRangqiu1().toString().trim())
//						|| jinshaBO.getWholeRangqiu1() == 0) {
//					continue;
//				}
				
				// 处理【主队】的名称问题：1、如果在当天的葡京中的主队名字存在，就两个字段都存储一样，如果在葡京当天的主队名字中不存在，则只保存一个字段
				List<RelativeBO> relativeBOList = this.baseDAO.find(" from RelativeBO where qiuduiJinsha = '" + jinshaBO.getQiuduiMain() + "'");
				if (relativeBOList == null || relativeBOList.size() <= 0) {// 如果对应关系表不存在，则需要维护
					RelativeBO relativeBO = new RelativeBO();
					List<BewinBO> bewinBOList = this.baseDAO.find(" from BewinBO where qiuduiMain = '" + jinshaBO.getQiuduiMain() + "'");
					if (bewinBOList != null && bewinBOList.size() > 0) {// 葡京中存在
						relativeBO.setQiuduiBewin(jinshaBO.getQiuduiMain());
					}
					relativeBO.setShijian(DateUtil.dateToStrDate(new Date()));
					relativeBO.setQiuduiJinsha(jinshaBO.getQiuduiMain());
					relativeBO.setUpdateTime(new Date());
					this.baseDAO.saveOrUpdate(relativeBO);
				} else {
					if (relativeBOList.get(0).getQiuduiBewin() != null 
							&& !"".equals(relativeBOList.get(0).getQiuduiBewin().trim())) {
						jinshaBO.setQiuduiMain(relativeBOList.get(0).getQiuduiBewin());	
					}
				}
				
				// 处理【客队】的名称问题：1、如果在当天的葡京中的客队名字存在，就两个字段都存储一样，如果在葡京当天的客队名字中不存在，则只保存一个字段
				relativeBOList = this.baseDAO.find(" from RelativeBO where qiuduiJinsha = '" + jinshaBO.getQiuduiClient() + "'");
				if (relativeBOList == null || relativeBOList.size() <= 0) {
					RelativeBO relativeBO = new RelativeBO();
					List<BewinBO> bewinBOList = this.baseDAO.find(" from BewinBO where qiuduiClient = '" + jinshaBO.getQiuduiClient() + "'");
					if (bewinBOList != null && bewinBOList.size() > 0) {// 葡京中存在
						relativeBO.setQiuduiBewin(jinshaBO.getQiuduiClient());
					}
					relativeBO.setShijian(DateUtil.dateToStrDate(new Date()));
					relativeBO.setQiuduiJinsha(jinshaBO.getQiuduiClient());
					relativeBO.setUpdateTime(new Date());
					this.baseDAO.saveOrUpdate(relativeBO);
				} else {
					if (relativeBOList.get(0).getQiuduiBewin() != null
							&& !"".equals(relativeBOList.get(0).getQiuduiBewin().trim())) {
						jinshaBO.setQiuduiClient(relativeBOList.get(0).getQiuduiBewin());	
					}
				}
				this.baseDAO.saveOrUpdate(jinshaBO);
			}
		}
	}
	
	public String analyzeAndSave(String filePath) throws Exception {
		InputStream is = new FileInputStream(filePath);
		XSSFWorkbook xSFWorkbook = new XSSFWorkbook(is);
		List<JinshaBO> jinshaBOList = new ArrayList();// 解析返回参数数据
		this.analyzeJinsha(xSFWorkbook, jinshaBOList);// 解析接口数据

		if (jinshaBOList != null && jinshaBOList.size() > 0) {
			this.baseDAO.executeHql("update JinshaBO set status='1'");
			for (JinshaBO jinshaBO : jinshaBOList) {
				List<RelativeBO> relativeBOList = this.baseDAO.find(" from RelativeBO where qiuduiJinsha = '" + jinshaBO.getQiuduiMain() + "'");
				if (relativeBOList != null && relativeBOList.size() > 0) {
					RelativeBO relativeBO = relativeBOList.get(0);
					if (relativeBO.getQiuduiBewin() != null && !"".equals(relativeBO.getQiuduiBewin())) {
						jinshaBO.setQiuduiMain(relativeBO.getQiuduiBewin());
					}
				} else {
					RelativeBO relativeBO = new RelativeBO();
					relativeBO.setQiuduiJinsha(jinshaBO.getQiuduiMain());
					this.baseDAO.saveOrUpdate(relativeBO);
				}
				relativeBOList = this.baseDAO.find(" from RelativeBO where qiuduiJinsha = '" + jinshaBO.getQiuduiClient() + "'");
				if (relativeBOList != null && relativeBOList.size() > 0) {
					RelativeBO relativeBO = relativeBOList.get(0);
					if (relativeBO.getQiuduiBewin() != null && !"".equals(relativeBO.getQiuduiBewin())) {
						jinshaBO.setQiuduiClient(relativeBO.getQiuduiBewin());
					}
				} else {
					RelativeBO relativeBO = new RelativeBO();
					relativeBO.setQiuduiJinsha(jinshaBO.getQiuduiClient());
					this.baseDAO.saveOrUpdate(relativeBO);
				}
				this.baseDAO.saveOrUpdate(jinshaBO);
			}
		}
		return "SUCCESS";
	}
	
	private void analyzeJinsha(XSSFWorkbook xSFWorkbook, List jinshaBOList) throws Exception {
		JinshaBO jinshaBO = null;

		XSSFSheet sheet = xSFWorkbook.getSheetAt(0);// 获取第1张sheet
		if (null == sheet || "".equals(sheet)) {
			return;
		}

		XSSFRow row = null;
		for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) { // 从第2行开始解析
			int cellNum = 0; // 从第1列开始梳理数据
			row = sheet.getRow(rowNum); // 设置结束循环Excel的时机
			if (row.getCell(0) == null 
					|| ExcelUtil.getCellStringValue(row.getCell(0)) == null
					|| "".equals(ExcelUtil.getCellStringValue(row.getCell(0)))) {
				continue;
			}
			if (!ExcelUtil.getCellStringValue(row.getCell(0)).contains("\"")
					&& row.getCell(0).getCellStyle().getDataFormatString() != null
					&& !"null".equals(row.getCell(0).getCellStyle().getDataFormatString())) {
				continue;
			}

			XSSFRow row0 = sheet.getRow(rowNum);
			XSSFRow row1 = sheet.getRow(rowNum + 1);
			XSSFRow row2 = sheet.getRow(rowNum + 2);
			
			jinshaBO = new JinshaBO();
//			String shijian = ExcelUtil.getCellStringValue(row1.getCell(0));// 日期时间
			String qiuduiMain = ExcelUtil.getCellStringValue(row0.getCell(1));// 主队名称
			String qiuduiClient = ExcelUtil.getCellStringValue(row1.getCell(1));// 客队名称
			String wholeRangqiu = ExcelUtil.getCellStringValue(row0.getCell(3));// 全场让球数
			String wholeRangqiu1 = ExcelUtil.getCellStringValue(row1.getCell(3));// 主水
			String wholeRangqiu2 = ExcelUtil.getCellStringValue(row2.getCell(3));// 客水
			
			if (wholeRangqiu == null || "".equals(wholeRangqiu)) {
				continue;
			}
			
			jinshaBO.setShijian(DateUtil.dateToStrDate(new Date()));// 日期时间
			jinshaBO.setQiuduiMain(qiuduiMain.split("[主]")[0]);// 主队名称
			jinshaBO.setQiuduiClient(qiuduiClient);// 客队名称
			jinshaBO.setWholeRangqiu(wholeRangqiu);// 全场让球数
			jinshaBO.setWholeRangqiu1(new Double(wholeRangqiu1));// 主水
			jinshaBO.setWholeRangqiu2(new Double(wholeRangqiu2));// 客水
			jinshaBO.setStatus("0");// 状态
			
			jinshaBOList.add(jinshaBO);
		}
	}

}