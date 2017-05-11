package com.test;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

public class QhtTest {

	public static WebDriver driver;
	
	public static void main(String[] args) {
		testSelect();
	}
	
	public static void testSelect() {
		String url = "http://a88.sx699.com/search.aspx";
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		try {
			WebElement iframe = null;
			while (true) {
				try {
					driver.get(url);
					WebElement suBtn = driver.findElement(By.id("su"));
					WebElement wdinput = driver.findElement(By.name("wd"));
					wdinput.sendKeys("88999");
					suBtn.click();
					iframe = driver.findElement(By.tagName("iframe"));
					break;
				} catch (Exception e) {
					continue;
				}
			}
			driver.switchTo().frame(iframe);
			WebElement txt_U_name = driver.findElement(By.id("txt_U_name"));
			WebElement txt_U_Password = driver.findElement(By.id("txt_U_Password"));
			WebElement txt_validate = driver.findElement(By.id("txt_validate"));
			WebElement longinbtn = driver.findElement(By.id("longinbtn"));
			
			// 获取验证码文字
			while (true) {
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				String code = "";
				WebElement imgCheckNum = driver.findElement(By.id("imgCheckNum"));
		        try {
		            Point p = imgCheckNum.getLocation();
		            int width = imgCheckNum.getSize().getWidth();
		            int higth = imgCheckNum.getSize().getHeight();
		            Rectangle rect = new Rectangle(width, higth);
		            BufferedImage img = ImageIO.read(scrFile);
		            BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width, higth);
		            ImageIO.write(dest, "png", scrFile);
		            Thread.sleep(1000);
		            File fng = new File("D:/yzm.png");
		            if(fng.exists()){
		                fng.delete();
		            }
		            FileUtils.copyFile(scrFile, fng);
		            
		            Runtime rt = Runtime.getRuntime();
		            rt.exec("cmd.exe /C tesseract.exe D:\\yzm.png  D:\\yzm -1 ");
		            Thread.sleep(1000);
		            File file = new File("D:\\yzm.txt");
		            if(file.exists()) {
		                FileHandler fh = new FileHandler();
		                code = fh.readAsString(file).trim();
		                System.out.println(code);
		                if (code == null || "".equals(code.trim())
		                		|| code.length() != 4 || !code.trim().matches("^[0-9]*$")) {
		                	imgCheckNum.click();
		                	Thread.sleep(500);
		                	continue;
		                }
		            } else {
		                System.out.print("yzm.txt不存在");
		                continue;
		            }
		            txt_U_name.sendKeys("mw147258");
					txt_U_Password.sendKeys("as147258");
					txt_validate.sendKeys(code);
					longinbtn.click();
		        } catch(UnhandledAlertException e) {
					WebElement dialog = driver.findElement(By.tagName("dialog"));
					System.out.println(dialog.getText());
		        	e.printStackTrace();
		        	imgCheckNum.click();
		        	continue;
		        }
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			driver.close();
//			driver.quit();
		}
	}
	
}
