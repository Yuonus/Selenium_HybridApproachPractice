package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("SWP-12 Public Orders")
@Story("User story name")
@Feature("F50") // If you have feature created in Jira
public class LoginPageTest extends BaseTest{

	@Description("Login Page Title Test")
	@Severity(SeverityLevel.MINOR) // Say this represent a bug or feature severity level
	@Test (priority = 1) // Selenium With Sabawoon Yuonus @ S_Tech
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		ChainTestListener.log("actual title : ===>" + actTitle);
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
	}
	
	
	@Test (priority = 2)
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		ChainTestListener.log("actual url : ===>" + actURL);
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));
	}
	
	@Test (priority = 3)
	public void forgotPwdLinkExistTest() {
		ChainTestListener.log("Checking if the forgot password link exist");
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Test (priority = 4)
	public void appLogExistTest() {
		ChainTestListener.log("Checking if the logo is visible");
		Assert.assertTrue(loginPage.isLogoExist());
	}
	
	@Test (priority = 5)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
}
