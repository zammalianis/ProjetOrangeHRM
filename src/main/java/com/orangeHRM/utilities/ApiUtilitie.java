package com.orangeHRM.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtilitie {

	// Method send the Get Request
	public static Response sendGetRequest(String endPoint) {
		return RestAssured.get(endPoint);
	}
	//method send the POST Request
	public static Response sendPostRequest(String endPoint,  String payLoad) {
		 return	RestAssured.given().header("content-type","application/json")
							.body(payLoad)
							.post();
	}
	//method to validation the response status
	public static boolean validateStatuscode(Response response,int statusCode) {
		return response.getStatusCode()== statusCode;
	}
	//method to extract value from JSON response
	public static String getJsonValue(Response response, String value) {
		return response.jsonPath().getString(value);
	}
}
