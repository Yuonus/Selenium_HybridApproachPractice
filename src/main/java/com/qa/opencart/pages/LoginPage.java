package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	// Private driver instance 
	private WebDriver driver;
	private ElementUtil eleUtil;

	// Locators/object repositories
	private By userName = By.id("input-email");
	private By password = By.id("input-password");
	private By logo = By.xpath("//img[@title = 'naveenopencart']");
	private By loginBtn = By.xpath("//input[@type='submit']");
	private By forgotPassword = By.linkText("Forgotten Password");
	private By errorMessage = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	
	private By registerLink = By.linkText("Register");

	// Page Constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	
	// Page methods
	@Step("Getting Login Page Title ...")
	public String getLoginPageTitle() {
		String pageTitle = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("The page title is: " + pageTitle);
		return pageTitle;
	}
	
	@Step("Getting Login Page Current URL ...")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Login Page current URL is: " + url);
		return url;
	}
	
	public boolean isForgotPwdLinkExist() {
		return eleUtil.waitForVisibilityOfElement(forgotPassword, AppConstants.LONG_DEFAULT_WAIT).isDisplayed();
	}
	
	public boolean isLogoExist() {
		return eleUtil.waitForVisibilityOfElement(logo, AppConstants.LONG_DEFAULT_WAIT).isDisplayed();
	}
	
	@Step("username is: {0} and password {1}")
	public AccountPage doLogin(String username, String pwd) {
		System.out.println("Credentials are: " + username + " : " + pwd);
		eleUtil.waitForVisibilityOfElement(userName, AppConstants.MEDIUM_DEFAULT_WAIT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		
		return new AccountPage(driver);
	}
	
	public boolean doLoginWithWrongCredentials(String username, String pwd) {
		System.out.println("Incorrect credentials are: " + username + ", " + pwd);
		
//	    // static wait only for first email
//	    if(username.equals("Automation12@gmail.com")) {
//	        try {
//	            Thread.sleep(5000); // 3 seconds
//	        } catch (InterruptedException e) {
//	            e.printStackTrace();
//	        }
//	    }
		eleUtil.waitForVisibilityOfElement(userName, AppConstants.LONG_DEFAULT_WAIT);
		 // Clear previous values -- The code before adding these two methods were adding all the emails and passwords without clearing the previous values
	    eleUtil.doClear(userName);
	    eleUtil.doClear(password);
	    
		eleUtil.doSendKeys(userName, username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		eleUtil.waitForVisibilityOfElement(errorMessage, AppConstants.LONG_DEFAULT_WAIT);
		String errorMesg = eleUtil.doElementGetText(errorMessage);
		System.out.println(errorMesg);
		if(errorMesg.contains(AppConstants.LOGIN_ERROR_MESSAGE)) {
			return true;
		}
		return false;
	}
	
	
	public RegisterPage navigateToRegisterPage() {
		eleUtil.waitForVisibilityOfElement(registerLink, AppConstants.MEDIUM_DEFAULT_WAIT).click();
		return new RegisterPage(driver);
	}

	

}
