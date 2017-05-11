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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.newwing.entity.BewinBO;
import com.newwing.entity.JinshaBO;
import com.newwing.entity.RelativeBO;
import com.newwing.service.IJinshaService;
import com.newwing.util.DateUtil;
import com.newwing.util.ExcelUtil;
import com.newwing.util.Logger;

@SuppressWarnings("all")
@Service("jinshaService")
public class JinshaServiceImpl extends BaseServiceImpl implements IJinshaService {

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
			
//			System.setProperty("phantomjs.binary.path", "E:/phantomjs.exe");// windows环境
			System.setProperty("phantomjs.binary.path", "/mydata/phantomjs-2.0.0/bin/phantomjs");// linux环境
	        driver = new PhantomJSDriver();
	        driver.get(url);
			Thread.sleep(3000);
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
//						logger.info("qiuduiMain : " + qiuduiMain);
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
//						list<webelement> div4list = tdlist.get(4).findelements(by.tagname("div"));
//						list<webelement> subdiv4list0 = div4list.get(0).findelements(by.tagname("div"));
//						string wholedaxiao = subdiv4list0.get(0).gettext();
//						string wholedaxiaotemp = subdiv4list0.get(1).gettext();
//						if (wholedaxiao != null && !"".equals(wholedaxiao)) {
//							jinshabo.setwholedaxiao(wholedaxiao);
//						}
//						if (wholedaxiaotemp != null && !"".equals(wholedaxiaotemp)) {
//							jinshabo.setwholedaxiao(wholedaxiaotemp);
//						}
//						
//						logger.info("wholedaxiao : " + wholedaxiao);
//						list<webelement> subdiv4list1 = div4list.get(3).findelements(by.tagname("div"));
//						string wholedaxiao1 = subdiv4list1.get(0).findelement(by.tagname("a")).gettext();
//						string wholedaxiao2 = subdiv4list1.get(1).findelement(by.tagname("a")).gettext();

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
						
//						jinshaBO.setWholeDaxiao(wholeDaxiao);
//						if (wholeDaxiao1 != null && !"".equals(wholeDaxiao1)) {
//							jinshaBO.setWholeDaxiao1(new Double(wholeDaxiao1));
//						}
//						if (wholeDaxiao2 != null && !"".equals(wholeDaxiao2)) {
//							jinshaBO.setWholeDaxiao2(new Double(wholeDaxiao2));
//						}
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
	
	public void autoReptileTiyu(List<JinshaBO> jinshaBOList) throws Exception {
		logger.info(">>>>>>>>>>>>>>>>>>>> 开始采集金沙体育投注数据 <<<<<<<<<<<<<<<<<<<<");
//		System.setProperty("phantomjs.binary.path", "E:/phantomjs.exe");// windows环境
		System.setProperty("phantomjs.binary.path", "/mydata/phantomjs-2.0.0/bin/phantomjs");// linux环境
		WebDriver newDriver = null;
		String url = "http://mkt.2999m.com/onebook?lang=cs&act=&webskintype=2";
		try {
			newDriver = new PhantomJSDriver();
			newDriver.get(url);
			WebDriverWait wait = new WebDriverWait(newDriver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("match-container")));
			
			WebElement divPage = newDriver.findElement(By.className("page"));
			Integer pageNo = new Integer(divPage.findElements(By.tagName("span")).get(2).getText().substring(0,1));
			System.out.println(" >>>>>>>>>> pageNo <<<<<<<<<< " + pageNo);
			for (int i = 0; i < pageNo; i++) {
				autoReptileJinshaTiyuSingle(i, jinshaBOList);
			}
			this.save(jinshaBOList);
		} catch (Exception e) {
			logger.error("采集金沙数据出现异常 >>>>>>>>>> " + e.getMessage());
			throw e;
		} finally {
			newDriver.close();
			newDriver.quit();
		}
		logger.info(">>>>>>>>>>>>>>>>>>>> 金沙体育投注数据采集结束 <<<<<<<<<<<<<<<<<<<< [ " + jinshaBOList.size() + " ] 条记录");
	}
	
	private void autoReptileJinshaTiyuSingle(Integer pageIndex, List<JinshaBO> jinshaBOList) throws Exception {
		WebDriver driver = null;
		try {
			String url = "http://mkt.2999m.com/onebook?lang=cs&act=&webskintype=2";
//			System.setProperty("phantomjs.binary.path", "E:/phantomjs.exe");// windows环境
			System.setProperty("phantomjs.binary.path", "/mydata/phantomjs-2.0.0/bin/phantomjs");// linux环境
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
					String wholeDaxiao = "";// 大小让球
					String wholeDaxiao1 = "0";// 主水
					if (exsitElememt(div3.findElements(By.tagName("ul")).get(2).findElements(By.tagName("li")).get(0), By.tagName("b"))) {
						wholeDaxiao = div3.findElements(By.tagName("ul")).get(2).findElements(By.tagName("li")).get(0).findElement(By.tagName("em")).getText();// 大
						wholeDaxiao1 = div3.findElements(By.tagName("ul")).get(2).findElements(By.tagName("li")).get(0).findElement(By.tagName("b")).getText();// 大
					}
					String wholeDaxiao2 = "0";// 客水
					if (div3.findElements(By.tagName("ul")).get(2).findElements(By.tagName("li")).size() == 2) {
						if (exsitElememt(div3.findElements(By.tagName("ul")).get(2).findElements(By.tagName("li")).get(1), By.tagName("b"))) {
							wholeDaxiao2 = div3.findElements(By.tagName("ul")).get(2).findElements(By.tagName("li")).get(1).findElement(By.tagName("b")).getText();// 小
						}
					}
//					System.out.println(">>>>>>>>>> wholeDaxiao1 <<<<<<<<<< " + wholeDaxiao1);
//					System.out.println(">>>>>>>>>> wholeDaxiao2 <<<<<<<<<< " + wholeDaxiao2);
					
					jinshaBO.setShijian(DateUtil.dateToStrDate(new Date()));
					jinshaBO.setStatus("0");
					jinshaBO.setStartTimeStr(startTimeStr);
					jinshaBO.setUpdateTime(new Date());
					jinshaBO.setQiuduiMain(qiuduiMain.replace(" ", ""));
					jinshaBO.setQiuduiClient(qiuduiClient.replace(" ", ""));
					jinshaBO.setWholeDaxiao(wholeDaxiao);
					jinshaBO.setWholeDaxiao1(new Double(wholeDaxiao1));
					jinshaBO.setWholeDaxiao2(new Double(wholeDaxiao1));
					jinshaBO.setWholeRangqiu(wholeRangqiu);
					
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
					
					if (!"".equals(relativeBO.getQiuduiJinsha()) && !relativeBO.getQiuduiJinsha().contains("入球")
							&& !relativeBO.getQiuduiJinsha().contains("开球") && !relativeBO.getQiuduiJinsha().contains("角球")) {
						this.baseDAO.saveOrUpdate(relativeBO);
					}
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
					if (!"".equals(relativeBO.getQiuduiJinsha()) && !relativeBO.getQiuduiJinsha().contains("入球")
							&& !relativeBO.getQiuduiJinsha().contains("开球") && !relativeBO.getQiuduiJinsha().contains("角球")) {
						this.baseDAO.saveOrUpdate(relativeBO);
					}
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
	
	private static boolean exsitElememt(WebElement webElement,By locator) {
		try {
			webElement.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
	}

}