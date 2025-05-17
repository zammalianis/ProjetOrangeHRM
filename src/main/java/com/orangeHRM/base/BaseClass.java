package com.orangeHRM.base;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.orangeHRM.actiondriver.ActionDriver;
import com.orangeHRM.utilities.ExtentManager;
import com.orangeHRM.utilities.LoggerManager;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseClass {

	protected static Properties prop;
	// protected static WebDriver driver;
	// private static ActionDriver actionDriver;
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
	protected ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);
	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);
	
	public SoftAssert getSoftAssert() {
		return softAssert.get();
	}

	@BeforeSuite
	public void LoadConfig() throws IOException {
		prop = new Properties();
		FileInputStream Fils = new FileInputStream("src/main/resources/config.properties");
		prop.load(Fils);
		// logger.info("config.prperties file loaded");
		System.out.println("config.prperties file loaded");
		// start the extent report
		//ExtentManager.getReporter();  --this has been implemented in TestListener
	}

	@BeforeMethod
	public synchronized void setup() throws IOException {
		System.out.println("Setting Browser" + this.getClass().getSimpleName());
		launchBrowser();
		configBrowser();
		staticWait(2);

		/*
		 * logger.info("webDriver Initialized and Browser Maximized");
		 * logger.trace("this is a trace message");
		 * logger.error("this is a error message");
		 * logger.debug("this is a debug message");
		 * logger.fatal("this is a fatal message");
		 * logger.warn("this is a warn message");
		 */
		System.out.println("webDriver Initialized and Browser Maximized");
		System.out.println("this is a trace message");
		System.out.println("this is a error message");
		System.out.println("this is a debug message");
		System.out.println("this is a fatal message");
		System.out.println("this is a warn message");
		// initializer instance

		/*
		 * if(actionDriver==null) { actionDriver =new ActionDriver(driver);
		 * System.out.println("ActionDriver instance created."+Thread.activeCount());
		 * 
		 * }
		 */
		actionDriver.set(new ActionDriver(getDriver()));
		System.out.println("ActionDriver inisialised for webDriver Thread created." + Thread.activeCount());
	}

	private synchronized void launchBrowser() {
		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
		
			//create chromeOptions
			ChromeOptions options= new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("--disable-gpu");
			//options.addArguments("--window-size=1920,1080");
			options.addArguments("--disable-notifications");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			// driver = new ChromeDriver();
			// logger.info("ChromeDriver instance is created.");
			driver.set(new ChromeDriver());
			ExtentManager.registerDriver(getDriver());
			System.out.println("ChromeDriver instance is created.");
		} else if (browser.equalsIgnoreCase("firefox")) {
			
			//create firefoxOptions
			FirefoxOptions options= new FirefoxOptions();
			options.addArguments("--headless");
			options.addArguments("--disable-gpu");
			//options.addArguments("--window-size=1920,1080");
			options.addArguments("--disable-notifications");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			// driver = new FirefoxDriver();
			// logger.info("FirefoxDriver instance is created.");
			driver.set(new FirefoxDriver());
			ExtentManager.registerDriver(getDriver());
			System.out.println("FirefoxDriver instance is created.");

		} else {
			throw new IllegalArgumentException("Browser Not supported:" + browser);
		}
	}

	private void configBrowser() {
		getDriver().manage().window().maximize();
		try {
			getDriver().get(prop.getProperty("Url"));
		} catch (Exception e) {
			System.out.println("failed to navigate to the URL" + e.getMessage());
		}
	}

	@AfterMethod
	public synchronized void TeamDown() {
		if (getDriver() != null) {
			try {
				getDriver().quit();
			} catch (Exception e) {
				System.out.println("Unable to quit the driver" + e.getMessage());
			}
		}
		// logger.info("webDriver instance is closed.");
		System.out.println("webDriver instance is closed.");
		driver.remove();
		actionDriver.remove();
		// driver=null;
		// actionDriver=null;
		//ExtentManager.endTest(); --this has been implemented in TestListener
	}

	public static Properties getProp() {
		return prop;
	}

	/*
	 * public WebDriver getDriver() { return driver; }
	 */
	public static WebDriver getDriver() {
		if (driver.get() == null) {
			// logger.info("WebDriver is not initialized");
			System.out.println("WebDriver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
		}
		return driver.get();
	}

	public static ActionDriver getActionDrive() {
		if (actionDriver.get() == null) {
			// logger.info("ActionDriver is not initialized");
			System.out.println("ActionDriver is not initialized");
			throw new IllegalStateException("ActionDriver is not initialized");
		}
		return actionDriver.get();
	}

	public void setDriver(ThreadLocal<WebDriver> driver) {
		this.driver = driver;
	}

	public void staticWait(int second) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(second));
	}

}
