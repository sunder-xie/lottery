package com.newwing.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.newwing.entity.BewinBO;
import com.newwing.service.BakIBewinService;
import com.newwing.util.DateUtil;
import com.newwing.util.ExcelUtil;
import com.newwing.util.Logger;

public class BakBewinServiceImpl extends BaseServiceImpl implements BakIBewinService {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	public void autoReptile(List<BewinBO> bewinBOList) throws Exception {
		logger.info(">>>>>>>>>>>>>>>>>>>> 开始采集葡京数据 <<<<<<<<<<<<<<<<<<<<");
		WebDriver driver = null;
		try {
//			System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
//			ChromeOptions options = new ChromeOptions();  
//			options.addArguments("user-data-dir=C:/Users/Teddy/AppData/Local/Google/Chrome/User Data");
//			options.addArguments("--start-maximized", "allow-running-insecure-content", "--test-type");
//			driver = new ChromeDriver(options);
			
			System.setProperty("phantomjs.binary.path", "E:/phantomjs.exe");  
	        driver = new PhantomJSDriver();
			String url = "http://22207.com/MatchInfoServlet?task=matches&Type=3010000&pageNo=1&Lsids=&special=";
			
			driver.get(url);
			Thread.sleep(2000);
			WebElement we = driver.findElement(By.tagName("pre"));
			String resultTemp = we.getText();
			JSONObject jsonResultTemp = JSONObject.fromObject(resultTemp);
			Integer pageSize = Integer.valueOf(jsonResultTemp.getString("pageSize"));
			String zqInfo = "";
			for (int i = 1; i <= pageSize; i++) {
				url = "http://22207.com/MatchInfoServlet?task=matches&Type=3010000&pageNo=" + i + "&Lsids=&special=";
				driver.get(url);
				Thread.sleep(1000);
				we = driver.findElement(By.tagName("pre"));
				String result = we.getText();
				JSONObject jsonResult = JSONObject.fromObject(result);
				zqInfo = jsonResult.getString("zqInfo");
				JSONArray zqInfoList = JSONArray.fromObject(zqInfo);
				for (int j = 0; j < zqInfoList.size(); j++) {
					BewinBO bewinBO = new BewinBO();
					JSONObject obj = zqInfoList.getJSONObject(j);
					String hteam = obj.getString("hteam");
					String gteam = obj.getString("gteam");
					String startTimeStr = obj.getString("startTimeStr");
					String a_concede_num = "0";// 让球
					String a_concede_h = "0";// 主水
					String a_concede_g = "0";// 客水
					String a_dq_num = "0";// 大球
					String a_d_num = "0";
					String a_xq_num = "0";// 小球
					String a_x_num = "0";
					if (obj.containsKey("a_concede_num")) {
						a_concede_num = obj.getString("a_concede_num").replace(" ", "");
					}
					if (obj.containsKey("a_concede_h")) {
						a_concede_h = obj.getString("a_concede_h");
					}
					if (obj.containsKey("a_concede_g")) {
						a_concede_g = obj.getString("a_concede_g");
					}
					if (obj.containsKey("a_dq_num")) {
						a_dq_num = obj.getString("a_dq_num").replace(" ", "");
					}
					if (obj.containsKey("a_d_num")) {
						a_d_num = obj.getString("a_d_num");
					}
					if (obj.containsKey("a_xq_num")) {
						a_xq_num = obj.getString("a_xq_num").replace(" ", "");
					}
					if (obj.containsKey("a_x_num")) {
						a_x_num = obj.getString("a_x_num");
					}
					bewinBO.setShijian(DateUtil.dateToStrDate(new Date()));// 日期时间
					bewinBO.setQiuduiMain(hteam.replace(" ", ""));// 主队名称
					bewinBO.setQiuduiClient(gteam.replace(" ", ""));// 客队名称
					bewinBO.setWholeRangqiu(a_concede_num);// 全场让球数
					bewinBO.setWholeRangqiu1(new Double(a_concede_h));// 主水
					bewinBO.setWholeRangqiu2(new Double(a_concede_g));// 客水
					
					bewinBO.setWholeDaxiao(a_dq_num);// 大小让球
					bewinBO.setWholeDaxiao1(new Double(a_d_num));// 主水
					bewinBO.setWholeDaxiao2(new Double(a_x_num));// 客水
					bewinBO.setStatus("0");// 状态
					bewinBO.setStartTimeStr(startTimeStr.substring(11, 16));// 比赛开始时间
					bewinBO.setUpdateTime(new Date());
					bewinBOList.add(bewinBO);
				}
				Thread.sleep(2000);
			}
			this.save(bewinBOList);
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error("采集葡京数据出现异常 >>>>>>>>>> " + e.getMessage());
			throw e;
//			Thread.sleep(1000);
//			driver.close();
//			this.autoReptile(new ArrayList<BewinBO>());
		} finally {
			driver.close();
			driver.quit();
		}
		logger.info(">>>>>>>>>>>>>>>>>>>> 采集葡京数据结束 <<<<<<<<<<<<<<<<<<<<");
	}
	
	private void save(List<BewinBO> bewinBOList) throws Exception {
		if (bewinBOList != null && bewinBOList.size() > 0) {
			this.baseDAO.executeHql("delete from BewinBO");// 直接删除历史数据
			for (BewinBO bewinBO : bewinBOList) {
//				if (bewinBO.getQiuduiMain().contains("球")) {
//					continue;
//				}
//				if (bewinBO.getWholeRangqiu1() == null || bewinBO.getWholeRangqiu1() == 0) {
//					continue;
//				}
				this.baseDAO.saveOrUpdate(bewinBO);
			}
		}
	}
	
	private String sendGet(String urlStr) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			String xml = sb.toString();
			return xml;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String analyzeAndSave(String filePath) throws Exception {
		InputStream is = new FileInputStream(filePath);
		XSSFWorkbook xSFWorkbook = new XSSFWorkbook(is);
		List<BewinBO> bewinBOList = new ArrayList();// 解析返回参数数据
		this.analyzeBewin(xSFWorkbook, bewinBOList);// 解析接口数据
		// 循环保存相关的数据
		if (bewinBOList != null && bewinBOList.size() > 0) {
			// 把存量数据的状态都变更为“1”
			this.baseDAO.executeHql("update BewinBO set status='1'");
			for (BewinBO bewinBO : bewinBOList) {
				this.baseDAO.saveOrUpdate(bewinBO);
			}
		}
		return "SUCCESS";
	}
	
	private void analyzeBewin(XSSFWorkbook xSFWorkbook, List bewinBOList) throws Exception {
		BewinBO bewinBO = null;

		XSSFSheet sheet = xSFWorkbook.getSheetAt(0);// 获取第1张sheet
		if (null == sheet || "".equals(sheet)) {
			return;
		}

		XSSFRow row = null;
		for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) { // 从第1行开始解析
			int cellNum = 0; // 从第1列开始梳理数据
			row = sheet.getRow(rowNum); // 设置结束循环Excel的时机
			if (!ExcelUtil.getCellStringValue(row.getCell(0)).contains("\"")
					&& row.getCell(0).getCellStyle().getDataFormatString() != null) {
				continue;
			}

			XSSFRow row0 = sheet.getRow(rowNum);
			XSSFRow row1 = sheet.getRow(rowNum + 1);
			XSSFRow row2 = sheet.getRow(rowNum + 2);
			bewinBO = new BewinBO();
//			String shijian = ExcelUtil.getCellStringValue(row0.getCell(0));// 日期时间
			String qiuduiMain = ExcelUtil.getCellStringValue(row0.getCell(1));// 主队名称
			String qiuduiClient = ExcelUtil.getCellStringValue(row1.getCell(1));// 客队名称
			
			String wholeRangqiu = "";
			String wholeRangqiu1 = "";
			String wholeRangqiu2 = "";
			if (ExcelUtil.getCellStringValue(row0.getCell(3)) != null 
					&& !"".equals(ExcelUtil.getCellStringValue(row0.getCell(3)).trim())
					&& !"null".equals(ExcelUtil.getCellStringValue(row0.getCell(3)).trim())) {
				wholeRangqiu = ExcelUtil.getCellStringValue(row0.getCell(3));// 全场让球数
				wholeRangqiu1 = ExcelUtil.getCellStringValue(row1.getCell(3));// 主水
				wholeRangqiu2 = ExcelUtil.getCellStringValue(row2.getCell(3));// 客水
			} else {
				wholeRangqiu = ExcelUtil.getCellStringValue(row1.getCell(3));// 全场让球数
				if (wholeRangqiu == null || "".equals(wholeRangqiu)) {
					continue;
				}
				wholeRangqiu1 = ExcelUtil.getCellStringValue(row2.getCell(3));// 主水
				
				XSSFRow row3 = sheet.getRow(rowNum+3);;
				wholeRangqiu2 = ExcelUtil.getCellStringValue(row3.getCell(3));// 客水
			}
			bewinBO.setShijian(DateUtil.dateToStrDate(new Date()));// 日期时间
			bewinBO.setQiuduiMain(qiuduiMain.replace("[主]", ""));// 主队名称
			bewinBO.setQiuduiClient(qiuduiClient);// 客队名称
			bewinBO.setWholeRangqiu(wholeRangqiu);// 全场让球数
			bewinBO.setWholeRangqiu1(new Double(wholeRangqiu1));// 主水
			bewinBO.setWholeRangqiu2(new Double(wholeRangqiu2));// 客水
			bewinBO.setStatus("0");// 状态
			bewinBO.setUpdateTime(new Date());
			
			bewinBOList.add(bewinBO);
		}

	}

}