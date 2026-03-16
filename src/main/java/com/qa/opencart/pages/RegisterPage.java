package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {

	// Private driver instance
	private WebDriver driver;
	private ElementUtil eleUtil;

	// Locators/object repositories
	private By firstName = By.cssSelector("#input-firstname");
	private By lastName = By.cssSelector("#input-lastname");
	private By email = By.cssSelector("#input-email");
	private By telephone = By.cssSelector("#input-telephone");
	private By password = By.cssSelector("#input-password");
	private By confirmPassword = By.cssSelector("#input-confirm");
	private By subscribeYes = By.xpath("(//input[@type='radio'])[2]");
	private By subscribeNo = By.xpath("(//input[@type='radio'])[3]");
	private By agreeCheckBox = By.cssSelector("[name='agree']");
	private By continueButton = By.cssSelector("[value='Continue']");
	private By successMessage = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	// Page Constructor
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}

	// Page methods
	public boolean userRegistration(String firstName, String lastName, String email, String telephone, String password, String confirmPassword, String subscribe) {
		eleUtil.waitForVisibilityOfElement(this.firstName, AppConstants.MEDIUM_DEFAULT_WAIT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, confirmPassword);
		
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		}else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton);
		
		String successMesg = eleUtil.waitForVisibilityOfElement(successMessage, AppConstants.SHORT_DEFAULT_WAIT).getText();
		System.out.println(successMesg);
		
		if(successMesg.contains(AppConstants.USER_REGISTER_SUCCESS_MESSAGE)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			
			return true;
		} else {
			return false;
		}
		
	}
	

}
