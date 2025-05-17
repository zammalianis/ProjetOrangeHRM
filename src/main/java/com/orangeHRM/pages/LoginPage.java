package com.orangeHRM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangeHRM.actiondriver.ActionDriver;
import com.orangeHRM.base.BaseClass;

public class LoginPage {

	private ActionDriver actionDriver;

	private By usernameField = By.name("username");
	private By passwordField = By.name("password");
	private By loginBoutton = By.xpath("//button[@type='submit']");
	private By errorsMessage = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");

	/*
	 * public LoginPage(WebDriver driver) { this.actionDriver= new
	 * ActionDriver(driver);
	 * 
	 * }
	 */

	public LoginPage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDrive();
	}

	public void login(String username, String password) {
		actionDriver.enterText(usernameField, username);
		actionDriver.enterText(passwordField, password);
		actionDriver.click(loginBoutton);

	}

	public boolean iserrorsMessage() {
		return actionDriver.isDisplayed(errorsMessage);
	}

	public String getMessage() {
		return actionDriver.getText(errorsMessage);
	}

	public boolean verrifiererrorMessage(String expectedMessage) {
		return actionDriver.compareText(errorsMessage, expectedMessage);
	}
}
