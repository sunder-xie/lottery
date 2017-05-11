package com.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.newwing.entity.BewinBO;
import com.newwing.util.DateUtil;
import com.newwing.util.Logger;

public class BewinTest {
	
	protected static Logger logger = Logger.getLogger(BewinTest.class);
	
	public static WebDriver driver;
	
	public static void main(String[] args) {
		testSelect();
	}
	
	public static void testSelect() {
		logger.info(">>>>>>>>>>>>>>>>>>>> 开始采集数据 <<<<<<<<<<<<<<<<<<<<");
		List<BewinBO> bewinBOList = new ArrayList<BewinBO>();
		WebDriver driver = null;
		try {
			System.setProperty("phantomjs.binary.path", "E:/phantomjs.exe");
	        driver = new PhantomJSDriver();
			String url = "http://22207.com/MatchInfoServlet?task=matches&Type=3010000&pageNo=1&Lsids=&special=";
			driver.get(url);
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("pre")));
			WebElement we = driver.findElement(By.tagName("pre"));
			String resultTemp = we.getText();
			JSONObject jsonResultTemp = JSONObject.fromObject(resultTemp);
			Integer pageSize = Integer.valueOf(jsonResultTemp.getString("pageSize"));
			String zqInfo = jsonResultTemp.getString("zqInfo");
			autoReptileSingle(zqInfo, bewinBOList);
			for (int i = 2; i <= pageSize; i++) {
				url = "http://22207.com/MatchInfoServlet?task=matches&Type=3010000&pageNo=" + i + "&Lsids=&special=";
				driver.get(url);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("pre")));
				we = driver.findElement(By.tagName("pre"));
				String result = we.getText();
				JSONObject jsonResult = JSONObject.fromObject(result);
				zqInfo = jsonResult.getString("zqInfo");
				autoReptileSingle(zqInfo, bewinBOList);
				Thread.sleep(300);
			}
//			this.save(bewinBOList);
		} catch (Exception e) {
			logger.error("采集数据出现异常 >>>>>>>>>> " + e.getMessage());
//			throw e;
		} finally {
			driver.close();
			driver.quit();
		}
		logger.info(">>>>>>>>>>>>>>>>>>>> 采集数据结束 <<<<<<<<<<<<<<<<<<<< [ " + bewinBOList.size() + " ] 条记录" );
	}
	
	
	private static void autoReptileSingle(String zqInfo, List<BewinBO> bewinBOList) throws Exception {
		JSONArray zqInfoList = JSONArray.fromObject(zqInfo);
		for (int j = 0; j < zqInfoList.size(); j++) {
			BewinBO bewinBO = new BewinBO();
			JSONObject obj = zqInfoList.getJSONObject(j);
			String hteam = obj.getString("hteam");
			String gteam = obj.getString("gteam");
			if (gteam.contains("球")) {
				continue;
			}
			String startTimeStr = obj.getString("startTimeStr");
			String a_concede_num = "0";// 让球
			String a_concede_h = "0";// 主水
			String a_concede_g = "0";// 客水
			String a_dq_num = "0";// 大球
			String a_d_num = "0";
//			String a_xq_num = "0";// 小球
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
//			if (obj.containsKey("a_xq_num")) {
//				a_xq_num = obj.getString("a_xq_num").replace(" ", "");
//			}
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
	}
	
}
