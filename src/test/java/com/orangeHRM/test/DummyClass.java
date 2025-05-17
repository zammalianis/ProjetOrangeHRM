package com.orangeHRM.test;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.utilities.ExtentManager;

public class DummyClass extends BaseClass {

	@Test
	public void DummyTest() {
		//ExtentManager.startTest("DummyTest1  test");--this has been implemented in TestListener
		//String Title= driver.getTitle();
		String Title=getDriver().getTitle();
		ExtentManager.logStep("verify the title");
		assert Title.equals("OrangeHRM") : "Test failed - Title not Matching";
		System.out.println("Test Passed - Title is Matching");
		//ExtentManager.logSkip("this case is skipped");
		throw new SkipException("skipping the test as part of Testing");
	}
}
