package com.orangeHRM.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.orangeHRM.base.BaseClass;

public class DBConnection {

	private static final String DB_URL = "jdbc:Mysql://localhost:3306/orangehrm";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	public static final Logger logger = BaseClass.logger;
	
	public static Connection getDBConnection() {

		try {
			logger.info("starting DB connection..");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			logger.info("Db connection successfuly");
			return conn;
		} catch (SQLException e) {
			logger.error("error wihle stablishing the DB connection");
			e.printStackTrace();
			return null;
		}

	}

	public static Map<String, String> getDBEmployeeDetails(String employee_id) {
		String query = "SELECT emp_lastname,emp_firstname,emp_middle_name FROM `hs_hr_employee`  WHERE employee_id=" + employee_id;
		Map<String, String> employeeDetail = new HashMap<>();
		try (Connection conn = getDBConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			System.out.println("Executing query:" + query);
			if (rs.next()) {
				
				String firstName = rs.getString("emp_firstname");
				String middleName = rs.getString("emp_middle_name");
				String lastname = rs.getString("emp_lastname");
				
				// store in a map
				employeeDetail.put("firstName", firstName);
				employeeDetail.put("middleName", middleName!=null? middleName:"");
				employeeDetail.put("lastname", lastname);
				
				logger.info("query  Executed successfuly");
				logger.info("employee data fetched:"+employeeDetail);
			}else {
				logger.error("employee not found");
			}
		}
		catch(Exception e){
			logger.error("error wihle executing query");
			e.printStackTrace();
		}

		return employeeDetail;
	}
}
