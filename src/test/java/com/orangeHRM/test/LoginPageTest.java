package com.orangeHRM.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.pages.HomePage;
import com.orangeHRM.pages.LoginPage;
import com.orangeHRM.utilities.ExtentManager;

public class LoginPageTest extends BaseClass  {

	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
	loginPage = new LoginPage(getDriver());
	homePage = new HomePage(getDriver());
	}

	@Test
	
	public void verifyValidLoginTest() {
		//ExtentManager.startTest("valid login test");--this has been implemented in TestListener
		System.out.println("on thread:"+Thread.activeCount());
		ExtentManager.logStep("Navigating to login page entering login and password");
		loginPage.login("admin", "admin123");
		ExtentManager.logStep("verifying admin tab is visible or not");
		Assert.assertTrue(homePage.verifieradminTab(),"admin tab should be visible after successfull login");
		ExtentManager.logStep("validation successfully");
		homePage.logout();
		ExtentManager.logStep("Logged out successfully!");
		staticWait(2);
	}
	
	@Test
	public void inValidLoginTest() {
		//ExtentManager.startTest("Invalid login test");--this has been implemented in TestListener
		System.out.println("on thread:"+Thread.activeCount());
		ExtentManager.logStep("Navigating to login page entering login and password");
		loginPage.login("admin", "frfrf");
		String ExpectederrorMessage = "Invalid credentials1";
		Assert.assertTrue(loginPage.verrifiererrorMessage(ExpectederrorMessage),"test failed: Invalid error message");
		ExtentManager.logStep("validation successfully");
		ExtentManager.logStep("Logged out successfully!");
	}
}
