package com.orangeHRM.test;

import java.util.Map;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.pages.HomePage;
import com.orangeHRM.pages.LoginPage;
import com.orangeHRM.utilities.DBConnection;
import com.orangeHRM.utilities.ExtentManager;
import com.orangeHRM.utilities.RetryAnalyzer;

public class DBVerificationTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void verifyemployeeNameVerificationFromDB() {

		SoftAssert softAssert = getSoftAssert();

		ExtentManager.logStep("logging with Admin credentails ");
		loginPage.login(prop.getProperty("Username"), prop.getProperty("password"));

		ExtentManager.logStep("click on PIM Tab");
		homePage.clickOnPimTab();

		ExtentManager.logStep("search for Employee");
		homePage.employeeSearch("anis");

		ExtentManager.logStep("Get tthe employee Name from DB");
		String employee_id = "1";

		// fetch the data into a map

		Map<String, String> employeeDetails = DBConnection.getDBEmployeeDetails(employee_id);
		String emplFirstName = employeeDetails.get("firstName");
		String emplMiddleName = employeeDetails.get("middleName");
		String emplLastName = employeeDetails.get("lastname");

		String emplFirstAndMiddleName = (emplFirstName + "  " + emplMiddleName).trim();

		ExtentManager.logStep("verify the employee first and last name");
		softAssert.assertTrue(homePage.verifyEmployeeFirstAndMiddleNameEmployee(emplFirstAndMiddleName),
				"first and last are not matching");

		ExtentManager.logStep("verify the employee last name");
		softAssert.assertTrue(homePage.verifyLastNameEmployee(emplLastName), "last name are not matching");

		ExtentManager.logStep("DB validation Completed");

		softAssert.assertAll();
	}
}
