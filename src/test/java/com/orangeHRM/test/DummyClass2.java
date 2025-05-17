package com.orangeHRM.test;


import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.utilities.ExtentManager;

public class DummyClass2 extends BaseClass {

	@Test
	public void DummyTest() {
		//ExtentManager.startTest("DummyTest2  test");--this has been implemented in TestListener
		//String Title= driver.getTitle();
		String Title=getDriver().getTitle();
		ExtentManager.logStep("verify the title");
		assert Title.equals("OrangeHRM") : "Test failed - Title not Matching";
		System.out.println("Test Passed - Title is Matching");
		ExtentManager.logStep("validation successfully");
	}
}
