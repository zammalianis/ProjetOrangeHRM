package com.orangeHRM.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	private int retryCont=0; //number of retries
	private static final int maxRetryCount=2;//maximum no of retries
	
	
	@Override
	public boolean retry(ITestResult result) {
		if(retryCont<maxRetryCount) {
			retryCont++;
			return true;//Retry the test
		}
		return false;
	}
}
