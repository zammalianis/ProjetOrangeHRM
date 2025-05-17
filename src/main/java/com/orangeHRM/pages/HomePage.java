package com.orangeHRM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangeHRM.actiondriver.ActionDriver;
import com.orangeHRM.base.BaseClass;

public class HomePage {

	private ActionDriver actionDriver;
	
	private By adminTab = By.xpath("//li[1]//a[1]//span[1]");
	private By userIdBoutton = By.xpath("//p[@class='oxd-userdropdown-name']");
	private By logoutBoutton = By.xpath("//a[normalize-space()='Logout']");
	private By orangeHMLLogo = By.xpath("//img[@alt='client brand banner']");
	
	private By pimtTab = By.xpath("//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name'][normalize-space()='PIM']");
	private By employeeSearch= By.xpath("//div[@class='oxd-grid-4 orangehrm-full-width-grid']//div[1]//div[1]//div[2]//div[1]//div[1]//input[1]");
	private By searchBoutton= By.xpath("//button[@type='submit']");
	private By emplFirstAndMiddleName=By.xpath("//div[@class='oxd-table-card']/div/div[3]");
	private By emplLastName= By.xpath("//div[@class='oxd-table-card']/div/div[4]");
	
	/*public HomePage(WebDriver driver) {
		this.actionDriver = new ActionDriver(driver);
	}*/
	
	public HomePage(WebDriver driver) {
		this.actionDriver= BaseClass.getActionDrive();
	}
	
	public boolean verifieradminTab() {
	return	actionDriver.isDisplayed(adminTab);
	}
	
	public boolean veriferOrangeHMLLogo() {
		return actionDriver.isDisplayed(orangeHMLLogo);
	}
	//employee to navigate
	public void clickOnPimTab() {
		actionDriver.click(pimtTab);
	}
	// employee search
	public void employeeSearch(String value) {
		actionDriver.enterText(employeeSearch, value);
		actionDriver.click(searchBoutton);
		actionDriver.scrollToElement(emplFirstAndMiddleName);
	}
	//verify employee last first name
	public boolean verifyEmployeeFirstAndMiddleNameEmployee(String FirstNameFromDB) {
		return actionDriver.compareText(emplFirstAndMiddleName, FirstNameFromDB);
	}
	public boolean verifyLastNameEmployee(String LastNameFromDB) {
		return actionDriver.compareText(emplLastName, LastNameFromDB);
	}
	public void logout() {
		actionDriver.click(userIdBoutton);
		actionDriver.click(logoutBoutton);
	}
}
