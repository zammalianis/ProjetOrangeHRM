package com.orangeHRM.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.utilities.ExtentManager;

public class ActionDriver {

	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger = BaseClass.logger;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int explicitwait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitwait));
		//logger.info("instance cree");
		System.out.println("instance cree");
	}

	public void click(By by) {
		String elementDescription = getElementDescription(by);
		try {                       
			waitForElementToBeClickable(by);
			applyBorder(by,"green");
			driver.findElement(by).click();
			ExtentManager.logStep("clicked an element:"+elementDescription);
			//logger.info("click element---->" + elementDescription);
			System.out.println("click element---->" + elementDescription);
		} catch (Exception e) {
			applyBorder(by,"red");
			System.out.println("unible to click element:" +e.getMessage());
			
			ExtentManager.logFailure(BaseClass.getDriver(), "unible to click element:", elementDescription+"_unable to click");
			//logger.error("unible to click element");
			System.out.println("unible to click element");
		}
	}

	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by,"green");
			// driver.findElement(by).clear();
			// driver.findElement(by).sendKeys(value);
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(value);
			//logger.info(" Entered text:" +getElementDescription(by) +"---->" +value);
			System.out.println(" Entered text:" +getElementDescription(by) +"---->" +value);
		} catch (Exception e) {
			applyBorder(by,"red");
			//logger.error("unable to enter value in input box:" +e.getMessage());
			System.out.println("unable to enter value in input box:" +e.getMessage());
		}
	}

	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by,"green");
			return driver.findElement(by).getText();
		} catch (Exception e) {
			applyBorder(by,"red");
			//logger.error("unable to get the text:" +e.getMessage());
			System.out.println("unable to get the text:" +e.getMessage());
			return null;
		}
	}

	public boolean compareText(By by, String expectedText) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by,"green");
			String actualText = driver.findElement(by).getText();

			if (expectedText.equals(actualText)) {
				applyBorder(by,"green");
				//logger.info("text are Matching" +actualText +"equal" +expectedText);
				System.out.println("text are Matching"+" " +actualText+" " +"equal"+" " +expectedText);
				ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "compare text", "text verified successfully!"+actualText+" equal" +expectedText);
				return true;
			} else {
				applyBorder(by,"red");
				//logger.error("text are'nt Matching" +actualText +"not equal" +expectedText);
				System.out.println("text are'nt Matching"+" " +actualText +" "+"not equal"+" " +expectedText);
				ExtentManager.logFailure(BaseClass.getDriver(), "compare text", "text compared is failed"+actualText+" not equal" +expectedText);

				return false;
			}
		} catch (Exception e) {
			//logger.error("unable to compare texts:" +e.getMessage());
			System.out.println("unable to compare texts:" +e.getMessage());
		}
		return false;
	}

	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by,"green");
			//logger.info("element is display:" +getElementDescription(by));
			System.out.println("element is display:" +getElementDescription(by));
			ExtentManager.logStep("element is display:" +getElementDescription(by));
			ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "element is display:" ,"element is display:" +getElementDescription(by));
			return driver.findElement(by).isDisplayed();

			/*
			 * boolean isDisplayed = driver.findElement(by).isDisplayed(); if (isDisplayed)
			 * { System.out.println("element is visible"); return isDisplayed; } else {
			 * return isDisplayed; }
			 */
		} catch (Exception e) {
			applyBorder(by,"red");
			//logger.error("unable is not visible:" +e.getMessage());
			System.out.println("unable is not visible:" +e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(),"element is not displayed:" ,"element is not  displayed:"+getElementDescription(by));
			return false;
		}

	}

	public void waitForPageload(int timeOutInsc) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInsc)).until(WebDriver -> ((JavascriptExecutor) WebDriver)
					.executeScript("return document.readyState").equals("complete"));

			//logger.info("page loaded successfly");
			System.out.println("page loaded successfly");
		} catch (Exception e) {
			//logger.error("page did not load with" +timeOutInsc +"seconds.Exception:" +e.getMessage());
			System.out.println("page did not load with" +timeOutInsc +"seconds.Exception:" +e.getMessage());
		}
	}

	public void scrollToElement(By by) {
		try {
			applyBorder(by,"green");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("arguments[0].scrollIntoview(true);", element);
		} catch (Exception e) {
			applyBorder(by,"red");
			//logger.error("unable to locate element:" +e.getMessage());
			System.out.println("unable to locate element:" +e.getMessage());
		}
	}

	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			//logger.error("element is not clickable:" +e.getMessage());
			System.out.println("element is not clickable:" +e.getMessage());
		}
	}

	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			applyBorder(by,"green");
		} catch (Exception e) {
			applyBorder(by,"red");
			//logger.error("element not visible:" +e.getMessage());
			System.out.println("element not visible:" +e.getMessage());
		}
	}

	public String getElementDescription(By locator) {

		if (driver == null)
			return "driver is null ";
		if (locator == null)
			return "locator is null";
		try {
			WebElement element = driver.findElement(locator);

			String name = element.getDomAttribute("name");
			String id = element.getDomAttribute("id");
			String Text = element.getText();
			String ClassName = element.getDomAttribute("calss");
			String placeHolder = element.getDomAttribute("placeHolder");

			if (isNotEmpty(name)) {
				return "element with name:" +name;
			} else if (isNotEmpty(id)) {
				return "element with id:" +id;
			} else if (isNotEmpty(Text)) {
				return "element with text:" +truncate(Text, 50);
			} else if (isNotEmpty(ClassName)) {
				return "element with placeHolder:" +ClassName;
			} else if (isNotEmpty(placeHolder)) {
				return "element with placeHolder:" +placeHolder;
			}
		} catch (Exception e) {
			//logger.error("unable to describe the  element:" +e.getMessage());
			System.out.println("unable to describe the  element:" +e.getMessage());
		}
		return "unable to describe the element";

	}

	private boolean isNotEmpty(String value) {
		return value != null && value.isEmpty();
	}

	private String truncate(String value, int maxlenght) {
		if (value == null || value.length() <= maxlenght) {
			return value;
		}
		return value.substring(0, maxlenght) +"...";
	}
	
	public void applyBorder(By by , String color) {
		try {
			//locate the element
			WebElement element = driver.findElement(by);
			//Apply the border
			String script = "arguments[0].style.border='3px solid "+color+"'";
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript(script,element);
			logger.info("Applied the border with color "+color+"to element:"+getElementDescription(by));
		} catch (Exception e) {
			logger.warn("failed  to apply the border to an element:"+getElementDescription(by),e);
		}
		
	}
	
}
