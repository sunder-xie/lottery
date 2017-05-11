package com.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.newwing.entity.BewinBO;
import com.newwing.entity.JinshaBO;
import com.newwing.util.DateUtil;

public class Main {

	public static WebDriver driver;
	
	public static void main(String[] args) {
		try {
//			String startTimeStr = "2017-02-05 09:15:00";
//			System.out.println(startTimeStr.substring(11, 16));
//			testFrame(jinshaBOList);
//			test2();
			List<JinshaBO> jinshaBOList = new ArrayList<JinshaBO>();
//			testJinsha(jinshaBOList);
//			testJinshaTiyu(jinshaBOList);
			testSelect(jinshaBOList);
			
//			List<BewinBO> bewinBOList = new ArrayList<BewinBO>();
//			testBewin(bewinBOList);
			
//			testVin(bewinBOList);
			
//			List<BewinBO> bewinBOList = new ArrayList<BewinBO>();
//			testPujing(bewinBOList);
//			testNewPujing(bewinBOList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testJinsha(List<JinshaBO> jinshaBOList) {
		while (true) {
			try {
				System.out.println("<<<<<<<<<<<<<<<<<<<< 开始采集金沙数据 >>>>>>>>>>>>>>>>>>>>");
				driver =new FirefoxDriver();
//				driver.get("http://www.16660029.com/sport/?page=odds_today");
				driver.get("http://www.16660029.com/sport/?page=odds_today&jdfwkey=ugwip");
				WebElement div = driver.findElement(By.id("js-live-odds"));
				WebElement table = div.findElement(By.tagName("table"));

				List<WebElement> tbodyList = table.findElements(By.tagName("tbody"));
//				System.out.println("tbodyList.size() >>>>>>>>>>> " + tbodyList.size());
				for(WebElement tbody : tbodyList) {
					List<WebElement> trList = tbody.findElements(By.tagName("tr"));
//					System.out.println("trList.size() >>>>>>>>>>> " + trList.size());
					if (trList != null && trList.size() == 2) {
						JinshaBO jinshaBO = new JinshaBO();
						List<WebElement> tdList = trList.get(0).findElements(By.tagName("td"));
//						System.out.println("tdList.size() >>>>>>>>>>> " + tdList.size());
						if (tdList != null && tdList.size() == 11) {
							WebElement div1 = tdList.get(1).findElement(By.tagName("div"));
							List<WebElement> subDiv1List = div1.findElements(By.tagName("div"));
//							System.out.println("subDiv1List.size() >>>>>>>>>>> " + subDiv1List.size());
							String qiuduiMain = subDiv1List.get(0).getText();// 主队名
							String qiuduiClient = subDiv1List.get(1).getText();// 客队名
							jinshaBO.setQiuduiMain(qiuduiMain);
							jinshaBO.setQiuduiClient(qiuduiClient);
							
							List<WebElement> div3List = tdList.get(3).findElements(By.tagName("div"));
//							System.out.println("div3List.size() >>>>>>>>>>> " + div3List.size());
//							for (WebElement we : div3List) {
//								System.out.println("we.getAttribute('class')" + we.getAttribute("class"));
//							}
							List<WebElement> subDiv3List0 = div3List.get(0).findElements(By.tagName("div"));
//							System.out.println("subDiv3List0.size() >>>>>>>>>>> " + subDiv3List0.size());
							String wholeRangqiu = subDiv3List0.get(0).getText();
							String wholeRangqiuTemp = subDiv3List0.get(1).getText();
							
							if (wholeRangqiu != null && !"".equals(wholeRangqiu)) {
								jinshaBO.setWholeRangqiu(wholeRangqiu);
							}
							if (wholeRangqiuTemp != null && !"".equals(wholeRangqiuTemp)) {
								jinshaBO.setWholeRangqiu(wholeRangqiuTemp);
							}
							List<WebElement> subDiv3List1 = div3List.get(3).findElements(By.tagName("div"));
//							System.out.println("subDiv3List1.size() >>>>>>>>>>> " + subDiv3List1.size());
							String wholeRangqiu1 = subDiv3List1.get(0).findElement(By.tagName("a")).getText();
							String wholeRangqiu2 = subDiv3List1.get(1).findElement(By.tagName("a")).getText();
//							System.out.println("wholeRangqiu1 >>>>>>>>>>> " + wholeRangqiu1);

							if (wholeRangqiu1 != null && !"".equals(wholeRangqiu1)) {
								jinshaBO.setWholeRangqiu1(new Double(wholeRangqiu1));
							}
							if (wholeRangqiu2 != null && !"".equals(wholeRangqiu2)) {
								jinshaBO.setWholeRangqiu2(new Double(wholeRangqiu2));
							}
						}
						jinshaBOList.add(jinshaBO);
					}
				}
				for (JinshaBO jinshaBO : jinshaBOList) {
					System.out.println(jinshaBO.getQiuduiMain() 
							+ "|" + jinshaBO.getQiuduiClient()
							+ "|" + jinshaBO.getWholeRangqiu()
							+ "|" + jinshaBO.getWholeRangqiu1()
							+ "|" + jinshaBO.getWholeRangqiu2());
				}
				if (jinshaBOList != null && jinshaBOList.size() > 0) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			} finally {
				driver.close();
			}
		}
	}
	
	public static void testBewin(List<BewinBO> bewinBOList) throws Exception {
		while (true) {
			try {
				System.out.println("开始采集必赢数据!!");
				long waitLoadBaseTime = 3000;
				int waitLoadRandomTime = 3000;
				Random random = new Random(System.currentTimeMillis());
				driver = new FirefoxDriver();
				driver.get("http://www.oo656.net/sports.html");//等待页面动态加载完毕
				Thread.sleep((waitLoadBaseTime + random.nextInt(waitLoadRandomTime))*2);
				driver.get("http://dq.gamestreedemo.com/Pages/football/football_newgamelist.aspx");//等待页面动态加载完毕
				WebElement table = driver.findElement(By.id("grvGameList"));
				WebElement tbody = table.findElement(By.tagName("tbody"));
				List<WebElement> trList = tbody.findElements(By.tagName("tr"));
//				System.out.println("trList >>>>>>> " + trList.size());
				for(WebElement tr : trList) {
//					System.out.println("we.getAttribute() >>>>>>>>>>> " + tr.getAttribute("class"));
					if ("main_table_tr04".equals(tr.getAttribute("class"))) {
						BewinBO bewinBO = new BewinBO();
						List<WebElement> tdList = tr.findElements(By.tagName("td"));
//						System.out.println("tdList >>>>>>> " + tdList.size());
						WebElement td1 = tdList.get(1);
						WebElement td3 = tdList.get(3);
						
						WebElement span1 = td1.findElements(By.tagName("span")).get(0);
						WebElement span2 = td1.findElements(By.tagName("span")).get(1);
						String qiuduiMain = span1.getText();
						String qiuduiClient = span2.getText();
						bewinBO.setQiuduiMain(qiuduiMain);
						bewinBO.setQiuduiClient(qiuduiClient);
						System.out.println("qiuduiMain >>>>>>> " + qiuduiMain);
						System.out.println("qiuduiClient >>>>>>> " + qiuduiClient);
						
						try {
							List<WebElement> divList = td3.findElements(By.tagName("div"));
							System.out.println("divList >>>>>>> " + divList.size());
							String wholeRangqiu = divList.get(0).getText();
							if (wholeRangqiu != null && !"".equals(wholeRangqiu)) {
								bewinBO.setWholeRangqiu(wholeRangqiu);
							}
							String wholeRangqiu1 = divList.get(1).findElements(By.tagName("a")).get(0).getText();
							String wholeRangqiu2 = divList.get(1).findElements(By.tagName("a")).get(1).getText();
							if (wholeRangqiu1 != null && !"".equals(wholeRangqiu1)) {
								bewinBO.setWholeRangqiu1(new Double(wholeRangqiu1));
							}
							if (wholeRangqiu2 != null && !"".equals(wholeRangqiu2)) {
								bewinBO.setWholeRangqiu2(new Double(wholeRangqiu2));
							}
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
						bewinBOList.add(bewinBO);
					}
				}
				for (BewinBO bewinBO : bewinBOList) {
					System.out.println(bewinBO.getQiuduiMain() 
							+ "|" + bewinBO.getQiuduiClient()
							+ "|" + bewinBO.getWholeRangqiu()
							+ "|" + bewinBO.getWholeRangqiu1()
							+ "|" + bewinBO.getWholeRangqiu2());
				}
				if (bewinBOList != null && bewinBOList.size() > 0) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			} finally {
				driver.close();
			}
		}
	}
	
	public static void testVin(List<BewinBO> bewinBOList) throws Exception {
//		while (true) {
			try {
				System.out.println("开始采集浩博数据!!");
				driver = new FirefoxDriver();
				driver.get("http://www.vin383.com/CEPFrontface/core/cashHead.tohead_pass.do?serviceid=0_WFNT");//等待页面动态加载完毕
				List<WebElement> football = driver.findElements(By.tagName("ul"));
				List<WebElement> liList = football.get(0).findElements(By.tagName("li"));
				System.out.println("liList.size() >>>>>>>>>>> " + liList.size());
				for(WebElement we : football) {
					System.out.println("we.getAttribute >>>>>>>>>>> " + we.getAttribute("class"));
				}
//				football.click();
				WebElement football2 = driver.findElement(By.id("cep_head_nav"));
				System.out.println("football2.getAttribute >>>>>>>>>>> " + football2.getAttribute("src"));
				football2.findElement(By.tagName("html")).findElement(By.tagName("body"));
//				WebElement em = football2.findElement(By.tagName("em"));
//				System.out.println("em >>>>>>> " + em.getText());
//				football2.click();
////				driver.get("http://www.vin383.com/betweb/core/staticBet.initPage.do?ssid=1&request_locale=zh");//等待页面动态加载完毕
//				WebElement tbody = driver.findElement(By.id("today_body"));
//				
//				List<WebElement> trList = tbody.findElements(By.tagName("tr"));
//				System.out.println("trList >>>>>>> " + trList.size());
////				for(WebElement we : tbodyList) {
////					System.out.println("we.getText() >>>>>>>>>>> " + we.getText());
////				}
////				break;
			} catch (Exception e) {
				e.printStackTrace();
//				continue;
			} finally {
				driver.close();
			}
//		}
	}
	
	// 模拟鼠标拖动
	public static void dragAndDrop() {
		driver.findElement(By.xpath("//*[@id='tbPic']")).click();
		while(true) {
			WebElement begin = driver.findElement(By.xpath("//*[@id='list']/li[1]/div[2]/span[1]"));
			WebElement end = driver.findElement(By.xpath("//*[@id='list']/li[2]/div[2]/span[1]"));
			new Actions(driver).dragAndDrop(begin, end).perform();// 像下拖动
			// 等待响应 TODO
			// 读取页面数据
			// 如果不再加载数据，则停止死循环
			boolean flag = true;// 需要根据条件进行判断
			if (flag) {
				break;
			}
		}
	}

	// 发送URL - get
	private static String sendGet(String urlStr) throws Exception {
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
	
	private static void testPujing(List<BewinBO> bewinBOList) {
		String url = "http://22207.com/MatchInfoServlet?task=matches&Type=3010000&pageNo=1&Lsids=&special=";
		try {
			String resultTemp = sendGet(url);
			JSONObject jsonResultTemp = JSONObject.fromObject(resultTemp);
			Integer pageSize = Integer.valueOf(jsonResultTemp.getString("pageSize"));
			System.out.println(resultTemp);
			String zqInfo = "";
			for (int i = 1; i <= pageSize; i++) {
				url = "http://22207.com/MatchInfoServlet?task=matches&Type=3010000&pageNo=" + i + "&Lsids=&special=";
//				System.out.println(url);
				String result = sendGet(url);
				JSONObject jsonResult = JSONObject.fromObject(result);
				zqInfo = jsonResult.getString("zqInfo");
//				System.out.println(jsonResult);
				JSONArray zqInfoList = JSONArray.fromObject(zqInfo);
//				System.out.println(zqInfoList.size());
				for (int j = 0; j < zqInfoList.size(); j++) {
					BewinBO bewinBO = new BewinBO();
					JSONObject obj = zqInfoList.getJSONObject(j);
//					System.out.println(obj);
					String hteam = obj.getString("hteam");
					String gteam = obj.getString("gteam");
					String startTime = obj.getString("startTime");
					String a_concede_num = "0";// 让球
					String a_concede_h = "0";// 主水
					String a_concede_g = "0";// 客水
					String a_dq_num = "0";// 打球
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
					System.out.println(hteam + "|" + gteam + "|" + startTime + "|" + a_concede_num 
							+ "|" + a_concede_h + "|" + a_concede_g + "|" + a_dq_num + "|" + a_d_num
							+ "|" + a_xq_num + "|" + a_x_num);
					bewinBO.setShijian(DateUtil.dateToStrDate(new Date()));// 日期时间
					bewinBO.setQiuduiMain(hteam);// 主队名称
					bewinBO.setQiuduiClient(gteam);// 客队名称
					bewinBO.setWholeRangqiu(a_concede_num);// 全场让球数
					bewinBO.setWholeRangqiu1(new Double(a_concede_h));// 主水
					bewinBO.setWholeRangqiu2(new Double(a_concede_g));// 客水
					bewinBO.setStatus("0");// 状态
					
					bewinBOList.add(bewinBO);
				}
				Thread.sleep(2000);
			}
			for (BewinBO bewinBO : bewinBOList) {
				System.out.println(bewinBO.getQiuduiMain() 
						+ "|" + bewinBO.getQiuduiClient()
						+ "|" + bewinBO.getWholeRangqiu()
						+ "|" + bewinBO.getWholeRangqiu1()
						+ "|" + bewinBO.getWholeRangqiu2());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testNewPujing(List<BewinBO> bewinBOList) throws Exception {
		try {
			System.out.println("开始采集pujing数据!!");
			driver = new FirefoxDriver();
			driver.manage();
			String url = "http://22207.com/MatchInfoServlet?task=matches&Type=3010000&pageNo=1&Lsids=&special=";
//			driver.get(url);//等待页面动态加载完毕
			driver.navigate().to(url);
			WebElement pre = driver.findElement(By.tagName("pre"));
			System.out.println("pre.getText() >>>>>>>>>>> " + pre.getText());
			
			String resultTemp = pre.getText();
			JSONObject jsonResultTemp = JSONObject.fromObject(resultTemp);
			Integer pageSize = Integer.valueOf(jsonResultTemp.getString("pageSize"));
			System.out.println(resultTemp);
			String zqInfo = "";
			for (int i = 1; i <= pageSize; i++) {
				url = "http://22207.com/MatchInfoServlet?task=matches&Type=3010000&pageNo=" + i + "&Lsids=&special=";
//				driver.get(url);
				driver.navigate().to(url);
				pre = driver.findElement(By.tagName("pre"));
				String result = pre.getText();
				result = new String(result.getBytes("gbk"),"utf-8");
				JSONObject jsonResult = JSONObject.fromObject(result);
				zqInfo = jsonResult.getString("zqInfo");
//				System.out.println(jsonResult);
				JSONArray zqInfoList = JSONArray.fromObject(zqInfo);
//				System.out.println(zqInfoList.size());
				for (int j = 0; j < zqInfoList.size(); j++) {
					BewinBO bewinBO = new BewinBO();
					JSONObject obj = zqInfoList.getJSONObject(j);
//					System.out.println(obj);
					String hteam = obj.getString("hteam");
					String gteam = obj.getString("gteam");
					String startTime = obj.getString("startTime");
					String a_concede_num = "0";// 让球
					String a_concede_h = "0";// 主水
					String a_concede_g = "0";// 客水
					String a_dq_num = "0";// 打球
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
					System.out.println(hteam + "|" + gteam + "|" + startTime + "|" + a_concede_num 
							+ "|" + a_concede_h + "|" + a_concede_g + "|" + a_dq_num + "|" + a_d_num
							+ "|" + a_xq_num + "|" + a_x_num);
					bewinBO.setShijian(DateUtil.dateToStrDate(new Date()));// 日期时间
					bewinBO.setQiuduiMain(hteam);// 主队名称
					bewinBO.setQiuduiClient(gteam);// 客队名称
					bewinBO.setWholeRangqiu(a_concede_num);// 全场让球数
					bewinBO.setWholeRangqiu1(new Double(a_concede_h));// 主水
					bewinBO.setWholeRangqiu2(new Double(a_concede_g));// 客水
					bewinBO.setStatus("0");// 状态
					
					bewinBOList.add(bewinBO);
				}
				Thread.sleep(2000);
			}
			for (BewinBO bewinBO : bewinBOList) {
				System.out.println(bewinBO.getQiuduiMain() 
						+ "|" + bewinBO.getQiuduiClient()
						+ "|" + bewinBO.getWholeRangqiu()
						+ "|" + bewinBO.getWholeRangqiu1()
						+ "|" + bewinBO.getWholeRangqiu2());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			driver.close();
		}
		System.out.println("采集pujing数据完成!!");
	}
	
	public static void test2() throws MalformedURLException {
		String remoteUrl = "http://22207.com/MatchInfoServlet?task=matches&Type=3010000&pageNo=1&Lsids=&special=";
		FirefoxProfile ffpf = new FirefoxProfile();
		ProfilesIni allProfiles = new ProfilesIni();
		ffpf = allProfiles.getProfile("Selenium");
//		ffpf.setPreference("intl.accept_languages", "en-GB"); 
//		driver = new FirefoxDriver(fp);
		
//		DesiredCapabilities ffcaps = DesiredCapabilities.firefox();  
//		ffcaps.setCapability(FirefoxDriver.PROFILE, ffpf.toString());  
//		  
//		driver = new RemoteWebDriver(new URL(remoteUrl), ffcaps);
//		driver.get(url);//等待页面动态加载完毕
//		driver.quit();
		
//		FirefoxProfile ffpf = new FirefoxProfile();  
		ffpf.setPreference("intl.accept_languages", "zh-CN");  
		// for FirefoxDriver  
//		driver = new FirefoxDriver(ffpf);  
		  
		// for remote driver  
		DesiredCapabilities ffcaps = DesiredCapabilities.firefox();  
		ffcaps.setCapability(FirefoxDriver.PROFILE, ffpf.toString());  
		  
		driver = new FirefoxDriver(ffcaps);
		driver.get(remoteUrl);//等待页面动态加载完毕
	}
	
	public static void testJinshaTiyu(List<JinshaBO> jinshaBOList) {
		try {
			String url = "http://mkt.2999m.com/onebook?lang=cs&act=&webskintype=2";
			System.setProperty("phantomjs.binary.path", "E:/phantomjs.exe");  
			driver = new PhantomJSDriver();
			driver.get(url);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("match-container")));
			WebElement divMatch = driver.findElement(By.className("match-container"));
			List<WebElement> rowList = divMatch.findElements(By.className("row"));
//			System.out.println(">>>>>>>>>> rowList.size() <<<<<<<<<< " + rowList.size());
			for (WebElement row : rowList) {
				System.out.println("====================分割线====================");
				JinshaBO jinshaBO = new JinshaBO();
				System.out.println(">>>>>>>>>> row.class <<<<<<<<<< " + row.getAttribute("class"));
				List<WebElement> divList = row.findElements(By.tagName("div"));
				System.out.println(">>>>>>>>>> divList.size() <<<<<<<<<< " + divList.size());
				wait.until(ExpectedConditions.presenceOfElementLocated(By.className("cell")));
				WebElement div0 = divList.get(0);// 比赛时间
				WebElement div1 = divList.get(1);// 主客名称
				WebElement div3 = divList.get(3);// 主客水（包括大小）
				if (divList.size() == 4) {
					div3 = divList.get(2);
				}
				String startTimeStr = "";
				if (!exsitElememt(div0, By.tagName("ul"))) {// 不存在
					startTimeStr = "";
				} else {
					WebElement li0 = div0.findElement(By.tagName("ul")).findElement(By.tagName("li"));
					startTimeStr = li0.findElements(By.tagName("span")).get(1).getText();
				}
				System.out.println(">>>>>>>>>> startTimeStr <<<<<<<<<< " + startTimeStr);
				
				List<WebElement> liList1 = div1.findElement(By.tagName("ul")).findElements(By.tagName("li"));
				String qiuduiMain = liList1.get(0).findElement(By.tagName("span")).getText();
				String qiuduiClient = liList1.get(1).findElement(By.tagName("span")).getText();
				System.out.println(">>>>>>>>>> qiuduiMain <<<<<<<<<< " + qiuduiMain);
				System.out.println(">>>>>>>>>> qiuduiClient <<<<<<<<<< " + qiuduiClient);
				
				List<WebElement> ulList = div3.findElements(By.tagName("ul"));
				System.out.println(">>>>>>>>>> div3.class <<<<<<<<<< " + div3.getAttribute("class"));
				System.out.println(">>>>>>>>>> ulList.size() <<<<<<<<<< " + ulList.size());
				
				String wholeRangqiu = "0";
				if (exsitElememt(div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(0), By.tagName("em"))) {
					wholeRangqiu = div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(0).findElement(By.tagName("em")).getText();
				}
				String wholeRangqiu1 = "0";
				if (exsitElememt(div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(0), By.tagName("b"))) {
					wholeRangqiu1 = div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(0).findElement(By.tagName("b")).getText();
				}
				
				String wholeRangqiuTemp = "0";
				String wholeRangqiu2 = "0";
				if (div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).size() == 2) {
					if (exsitElememt(div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(1), By.tagName("em"))) {
						wholeRangqiuTemp = div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(1).findElement(By.tagName("em")).getText();
					}
					if (exsitElememt(div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(1), By.tagName("b"))) {
						wholeRangqiu2 = div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(1).findElement(By.tagName("b")).getText();
					}
				}
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
				
				System.out.println(">>>>>>>>>> wholeRangqiu <<<<<<<<<< " + wholeRangqiu);
				System.out.println(">>>>>>>>>> wholeRangqiu1 <<<<<<<<<< " + wholeRangqiu1);
				System.out.println(">>>>>>>>>> wholeRangqiuTemp <<<<<<<<<< " + wholeRangqiuTemp);
				System.out.println(">>>>>>>>>> wholeRangqiu2 <<<<<<<<<< " + wholeRangqiu2);
				
				// 大小
				String wholeDaxiao1 = "0";
				if (exsitElememt(div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(0), By.tagName("b"))) {
					wholeDaxiao1 = div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(0).findElement(By.tagName("b")).getText();// 大
				}
				String wholeDaxiao2 = "0";
				if (div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).size() == 2) {
					if (exsitElememt(div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(1), By.tagName("b"))) {
						wholeDaxiao2 = div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(1).findElement(By.tagName("b")).getText();// 小
					}
				}
				System.out.println(">>>>>>>>>> wholeDaxiao1 <<<<<<<<<< " + wholeDaxiao1);
				System.out.println(">>>>>>>>>> wholeDaxiao2 <<<<<<<<<< " + wholeDaxiao2);
				
				jinshaBO.setShijian(DateUtil.dateToStrDate(new Date()));
				jinshaBO.setStatus("0");
				jinshaBO.setStartTimeStr(startTimeStr);
				jinshaBO.setUpdateTime(new Date());
				
				jinshaBOList.add(jinshaBO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
			driver.quit();
		}
	}
	
	private static boolean exsitElememt(WebElement webElement,By locator) {
		try {
			webElement.findElement(locator);
            return true;
        } catch (Exception e) {
//        	e.printStackTrace();
            return false;
        }
	}
	
	public static void testSelect(List<JinshaBO> jinshaBOList) {
		System.setProperty("phantomjs.binary.path", "E:/phantomjs.exe");  
		WebDriver newDriver = new PhantomJSDriver();
		String url = "http://mkt.2999m.com/onebook?lang=cs&act=&webskintype=2";
		try {
			newDriver.get(url);
			WebDriverWait wait = new WebDriverWait(newDriver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("match-container")));
			
			WebElement divPage = newDriver.findElement(By.className("page"));
			Integer pageNo = new Integer(divPage.findElements(By.tagName("span")).get(2).getText().substring(0,1));
			System.out.println(" >>>>>>>>>> pageNo <<<<<<<<<< " + pageNo);
			for (int i = 0; i < pageNo; i++) {
				testJinshaTiyuSingle(i, jinshaBOList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("jinshaBOList.size() >>>>>>>>> " + jinshaBOList.size());
			newDriver.close();
			newDriver.quit();
		}
	}
	
	// 单页
	private static void testJinshaTiyuSingle(Integer pageIndex, List<JinshaBO> jinshaBOList) throws Exception {
		try {
			String url = "http://mkt.2999m.com/onebook?lang=cs&act=&webskintype=2";
			System.setProperty("phantomjs.binary.path", "E:/phantomjs.exe");
			driver = new PhantomJSDriver();
			driver.get(url);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("match-container")));
			WebElement divDropdown = driver.findElement(By.className("dropdown"));
			divDropdown.findElement(By.tagName("a")).click();
			if (exsitElememt(divDropdown, By.tagName("ul"))) {
				WebElement ul = divDropdown.findElement(By.tagName("ul"));
				List<WebElement> liList = ul.findElements(By.tagName("li"));
				liList.get(pageIndex).click();

				WebElement divMatch = driver.findElement(By.className("match-container"));
				List<WebElement> rowList = divMatch.findElements(By.className("row"));
//				System.out.println(">>>>>>>>>> rowList.size() <<<<<<<<<< " + rowList.size());
				for (WebElement row : rowList) {
					JinshaBO jinshaBO = new JinshaBO();
//					System.out.println(">>>>>>>>>> row.class <<<<<<<<<< " + row.getAttribute("class"));
					List<WebElement> divList = row.findElements(By.tagName("div"));
//					System.out.println(">>>>>>>>>> divList.size() <<<<<<<<<< " + divList.size());
					wait.until(ExpectedConditions.presenceOfElementLocated(By.className("cell")));
					WebElement div0 = divList.get(0);// 比赛时间
					WebElement div1 = divList.get(1);// 主客名称
					WebElement div3 = divList.get(3);// 主客水（包括大小）
					if (divList.size() == 4) {
						div3 = divList.get(2);
					}
					String startTimeStr = "";
					if (!exsitElememt(div0, By.tagName("ul"))) {// 不存在
						startTimeStr = "";
					} else {
						WebElement li0 = div0.findElement(By.tagName("ul")).findElement(By.tagName("li"));
						startTimeStr = li0.findElements(By.tagName("span")).get(1).getText();
					}
//					System.out.println(">>>>>>>>>> startTimeStr <<<<<<<<<< " + startTimeStr);
					
					List<WebElement> liList1 = div1.findElement(By.tagName("ul")).findElements(By.tagName("li"));
					String qiuduiMain = liList1.get(0).findElement(By.tagName("span")).getText();
					String qiuduiClient = liList1.get(1).findElement(By.tagName("span")).getText();
//					System.out.println(">>>>>>>>>> qiuduiMain <<<<<<<<<< " + qiuduiMain);
//					System.out.println(">>>>>>>>>> qiuduiClient <<<<<<<<<< " + qiuduiClient);
					
					List<WebElement> ulList = div3.findElements(By.tagName("ul"));
//					System.out.println(">>>>>>>>>> div3.class <<<<<<<<<< " + div3.getAttribute("class"));
//					System.out.println(">>>>>>>>>> ulList.size() <<<<<<<<<< " + ulList.size());
					
					String wholeRangqiu = "0";
					if (exsitElememt(div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(0), By.tagName("em"))) {
						wholeRangqiu = div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(0).findElement(By.tagName("em")).getText();
					}
					String wholeRangqiu1 = "0";
					if (exsitElememt(div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(0), By.tagName("b"))) {
						wholeRangqiu1 = div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(0).findElement(By.tagName("b")).getText();
					}
					
					String wholeRangqiuTemp = "0";
					String wholeRangqiu2 = "0";
					if (div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).size() == 2) {
						if (exsitElememt(div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(1), By.tagName("em"))) {
							wholeRangqiuTemp = div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(1).findElement(By.tagName("em")).getText();
						}
						if (exsitElememt(div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(1), By.tagName("b"))) {
							wholeRangqiu2 = div3.findElements(By.tagName("ul")).get(1).findElements(By.tagName("li")).get(1).findElement(By.tagName("b")).getText();
						}
					}
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
					
//					System.out.println(">>>>>>>>>> wholeRangqiu <<<<<<<<<< " + wholeRangqiu);
//					System.out.println(">>>>>>>>>> wholeRangqiu1 <<<<<<<<<< " + wholeRangqiu1);
//					System.out.println(">>>>>>>>>> wholeRangqiuTemp <<<<<<<<<< " + wholeRangqiuTemp);
//					System.out.println(">>>>>>>>>> wholeRangqiu2 <<<<<<<<<< " + wholeRangqiu2);
					
					// 大小
					String wholeDaxiao = "";
					if (exsitElememt(div3.findElements(By.tagName("ul")).get(2).findElements(By.tagName("li")).get(0), By.tagName("em"))) {
						wholeDaxiao = div3.findElements(By.tagName("ul")).get(2).findElements(By.tagName("li")).get(0).findElement(By.tagName("em")).getText();// 大
					}
					String wholeDaxiao1 = "0";
					if (exsitElememt(div3.findElements(By.tagName("ul")).get(2).findElements(By.tagName("li")).get(0), By.tagName("b"))) {
						wholeDaxiao1 = div3.findElements(By.tagName("ul")).get(2).findElements(By.tagName("li")).get(0).findElement(By.tagName("b")).getText();// 大
					}
					String wholeDaxiao2 = "0";
					if (div3.findElements(By.tagName("ul")).get(2).findElements(By.tagName("li")).size() == 2) {
						if (exsitElememt(div3.findElements(By.tagName("ul")).get(2).findElements(By.tagName("li")).get(1), By.tagName("b"))) {
							wholeDaxiao2 = div3.findElements(By.tagName("ul")).get(2).findElements(By.tagName("li")).get(1).findElement(By.tagName("b")).getText();// 小
						}
					}
//					System.out.println(">>>>>>>>>> wholeDaxiao1 <<<<<<<<<< " + wholeDaxiao1);
//					System.out.println(">>>>>>>>>> wholeDaxiao2 <<<<<<<<<< " + wholeDaxiao2);
					
					System.out.println(">>>>>>>>>>> wholeDaxiao <<<<<<<<<< " + wholeDaxiao);
//					System.out.println(">>>>>>>>>>> wholeRangqiuTemp <<<<<<<<<< " + wholeRangqiuTemp);
					
					jinshaBO.setShijian(DateUtil.dateToStrDate(new Date()));
					jinshaBO.setStatus("0");
					jinshaBO.setStartTimeStr(startTimeStr);
					jinshaBO.setUpdateTime(new Date());
					
					jinshaBOList.add(jinshaBO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
			driver.quit();
		}
	}
	
}
