package com.orangeHRM.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.pages.HomePage;
import com.orangeHRM.pages.LoginPage;
import com.orangeHRM.utilities.ExtentManager;

public class HomePageTest extends BaseClass {
	
	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPage() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	@Test
	public void verifierHRMOrangeLogo() {
		//ExtentManager.startTest("home page verify logo  test");--this has been implemented in TestListener
		ExtentManager.logStep("Navigating to login page entering login and password");
		loginPage.login("admin", "admin123");
		ExtentManager.logStep("verifying admin logo is visible or not");
		Assert.assertTrue(homePage.veriferOrangeHMLLogo(),"logo is not visible");
		ExtentManager.logStep("validation successfully");
		ExtentManager.logStep("Logged out successfully!");
	}
}
