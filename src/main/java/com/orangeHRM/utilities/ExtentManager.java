package com.orangeHRM.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap = new HashMap<>();

	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			String reportPath = System.getProperty("user.dir") + "/src/test/resources/ExtentReport/ExtentReport.html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			spark.config().setReportName("Automatisation test report");
			spark.config().setDocumentTitle("OrangeHRM report");
			spark.config().setTheme(Theme.DARK);
			extent = new ExtentReports();
            extent.attachReporter(spark);
			// add system information
			extent.setSystemInfo("operating System", System.getProperty("os.name"));
			extent.setSystemInfo("java version", System.getProperty("java.version"));
			extent.setSystemInfo("User Name", System.getProperty("user.name"));

		}
		return extent;

	}

	// start the test
	public synchronized static ExtentTest startTest(String TestName) {
		ExtentTest extentTest = getReporter().createTest(TestName);
		test.set(extentTest);
		return extentTest;

	}

	// end a test
	public synchronized static void endTest() {
		getReporter().flush();
	}

	// get current thread's test
	public synchronized static ExtentTest getTest() {
		return test.get();
	}

	public static String getTestName() {
		ExtentTest currentTest = getTest();
		if (currentTest != null) {
			return currentTest.getModel().getName();
		} else {
			return "No test is currently active for this thread";
		}

	}

	// log step
	public static void logStep(String logMessage) {
		getTest().info(logMessage);
	}

	// log a step validation with screenshot
	public static void logStepWithScreenshot(WebDriver driver, String logMessage, String screenshotMessage) {
		getTest().pass(logMessage);
		// screenshot Method
		attachScreenshot(driver,screenshotMessage);
	}
	// log a step validation for API
		public static void logStepValidationForAPI(String logMessage) {
			getTest().pass(logMessage);
			
		}

	// Log a failure
	public static void logFailure(WebDriver driver, String logMessage, String screenshotMessage) {
		String colorMessage= String.format("<span style='color:red;' >%s</span>",logMessage);
		getTest().fail(colorMessage);
		// screenshot Method
		attachScreenshot(driver,screenshotMessage);

	}
	// Log a failure for API
		public static void logFailureAPI(String logMessage) {
			String colorMessage= String.format("<span style='color:red;' >%s</span>",logMessage);
			getTest().fail(colorMessage);
		
		}
	//Log a skip
	public static void logSkip(String logMessage) {
		String colorMessage= String.format("<span style='color:orange;' >%s</span>",logMessage);
		getTest().skip(colorMessage);
	}
	//take a screenshot with date and time in the file
	public synchronized static String takeScreenshot(WebDriver driver, String screenshotName)  {
		TakesScreenshot ts =(TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		//Format date and  Time for file  name
	String timeStamp=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
	// Saving the screenshot to a file
	String destPath = System.getProperty("user.dir") + "/src/test/resources/screenshots/"+screenshotName+"_"+timeStamp+".png";
	File finalPath = new File(destPath);
	try {
		FileUtils.copyFile(src, finalPath);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String base64Format = convertToBase64(src);
	return base64Format;
	}
	//convert screenshot to  base64 format
	public static String convertToBase64(File screenshotFile) {
		String base64Format="";
		//read the file content into a byte array
		
		try {
			byte[] fileContent = FileUtils.readFileToByteArray(screenshotFile);
			base64Format = Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base64Format;
		
	}
	//Attach screenshot to report using Base64
	public synchronized static void attachScreenshot(WebDriver driver,String message) {
		try {
			String screenShotBase64=takeScreenshot(driver,getTestName());
			getTest().info(message,com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(screenShotBase64).build());
		} catch (Exception e) {
			getTest().fail("failed to attach screenshot:"+message);
			e.printStackTrace();
		} 
		
	}

	// register webDriver for current Thread
	public static void registerDriver(WebDriver driver) {
		driverMap.put(Thread.currentThread().getId(), driver);
	}

}
