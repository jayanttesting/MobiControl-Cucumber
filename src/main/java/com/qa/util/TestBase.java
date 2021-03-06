package com.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBase {

	private static TestBase test;
	private static Logger Log = Logger.getLogger(Logger.class.getName());

	public synchronized static TestBase getinstance() throws FileNotFoundException {
		if (test == null)
			test = new TestBase();

		return test;

	}

	public static WebDriver driver;
	public static Properties prop;

	public TestBase() throws FileNotFoundException {
		prop = new Properties();
		FileInputStream fi = new FileInputStream(
				"src/main/java/com/qa/config/Config.properties");
		try {
			prop.load(fi);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initialize() {
		String browsername = prop.getProperty("browser");
		if (browsername.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "//Resources//chromedriver.exe");
			driver = new ChromeDriver();
		}
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		// driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public void takescreenshot(WebDriver webdriver, String fileWithPath) throws Exception {

		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);
	}
}
