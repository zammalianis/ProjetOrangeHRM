package com.orangeHRM.test;


import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import com.orangeHRM.utilities.ApiUtilitie;
import com.orangeHRM.utilities.ExtentManager;
import com.orangeHRM.utilities.RetryAnalyzer;

import io.restassured.response.Response;

public class ApiTest {

	@Test()
	
	public void verifyGetUserAPI() {
		// Initialization softAssert
		SoftAssert softAssert = new SoftAssert();
		// step1: Define EndPoint
		String endPoint = "https://jsonplaceholder.typicode.com/users/1";
		ExtentManager.logStep("API EndPoint:" + endPoint);

		// step2: send Get Request
		ExtentManager.logStep("Sending Get Request to the API");
		Response response = ApiUtilitie.sendGetRequest(endPoint);

		// step3: validate status code
		ExtentManager.logStep("validating API Response status code ");
		boolean isStatusCodeValid = ApiUtilitie.validateStatuscode(response, 200);
		softAssert.assertTrue(isStatusCodeValid,"status  code is not expected");
		
		if(isStatusCodeValid) {
			ExtentManager.logStepValidationForAPI("status code validation Passed!");
		}else {
			ExtentManager.logFailureAPI("status code validation Failed");
		}
		//validate user name
		ExtentManager.logStep("validating response body for username");
		String username=ApiUtilitie.getJsonValue(response, "username");
		boolean isUserNameValid = "Bret".equals(username);
		softAssert.assertTrue(isUserNameValid,"username is not valid");
		if(isUserNameValid) {
			ExtentManager.logStepValidationForAPI("username validation Passed!");
		}else {
			ExtentManager.logFailureAPI("username validation Failed");
		}
		//Step4: validate email
		ExtentManager.logStep("validating response body for email");
		String email=ApiUtilitie.getJsonValue(response, "email");
		boolean isEmailValid = "Sincere@april.biz".equals(email);
		softAssert.assertTrue(isEmailValid,"email is not valid");
		if(isEmailValid) {
			ExtentManager.logStepValidationForAPI("email validation Passed!");
		}else {
			ExtentManager.logFailureAPI("email validation Failed");
		}
		softAssert.assertAll();
	}
}
