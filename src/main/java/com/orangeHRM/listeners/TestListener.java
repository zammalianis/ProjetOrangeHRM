package com.orangeHRM.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.utilities.ExtentManager;
import com.orangeHRM.utilities.RetryAnalyzer;

public class TestListener implements ITestListener, IAnnotationTransformer {

	
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}
	// Trigger when a test starts
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		// start logging in Extent Reports
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Test Started:" + testName);

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		if(!result.getTestClass().getName().toLowerCase().contains("api")) {
			ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test passed successfully!","Test End" + testName + "- Test Passed");
		}
		else {
			ExtentManager.logStepValidationForAPI("Test End" + testName + "- Test Passed");
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String failedMessage=result.getThrowable().getMessage();
		ExtentManager.logStep(failedMessage);
		if(!result.getTestClass().getName().toLowerCase().contains("api")) {
			ExtentManager.logFailure(BaseClass.getDriver(), "Test Failed!", "Test End" + testName + "- Test Failed");
		}
		else {
			ExtentManager.logFailureAPI( "Test End" + testName + "- Test Failed");
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		ExtentManager.logSkip("Test skipped" +testName);
	}

//Triggered when a suite Starts
	@Override
	public void onStart(ITestContext context) {
		// Initialize the Extent Reports
		ExtentManager.getReporter();
	}

//Triggered when the suite ends
	@Override
	public void onFinish(ITestContext context) {
		// Flush the Extent Reports
		ExtentManager.endTest();
	}

	

}
