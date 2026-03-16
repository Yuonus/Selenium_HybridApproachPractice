package com.qa.opencart.tests;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtils;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}

	// This method will return random email IDs
	public String getRandomEmailID(String userFirstName) {
		return userFirstName + "testAutomation" + System.currentTimeMillis() + "@gmail.com";
		// Or we can also use this given approach
//		return "testAutomation" + UUID.randomUUID() + "@gmail.com";
	}

//	@DataProvider
//	public Object[][] getUserRegData() {
//		return new Object[][] {
//			{"Rajat", "Sharma",  "123456789", "abc123", "abc123", "yes"},
//			{"naveen", "kunteta",  "123456789", "abc123", "abc123", "yes"},
//			{"Sonali", "Bandari",  "123456789", "abc123", "abc123", "yes"},
//		};
//	}

//	@Test(dataProvider = "getUserRegData")
//	public void userRegTest(String firstName, String lastName, String telephone, String password,
//			String confirmPassword, String subscribe) {
//		boolean isRegDone = registerPage.userRegistration(firstName, lastName, getRandomEmailID(firstName), telephone,
//				password, confirmPassword, subscribe);
//		Assert.assertTrue(isRegDone);
//	}

	@DataProvider
	public Object[][] getUserRegTestExcelData() {
		Object regData[][] = ExcelUtils.getTestData(AppConstants.REGISTER_DATA_SHEET_NAME);
		return regData;
	}
	
	@Test (dataProvider = "getUserRegTestExcelData")
	public void userRegTest(String firstName, String lastName, String telephone, String password, String confirmPassword, String subscribe) {
		boolean isRegDone = registerPage.userRegistration(firstName, lastName, getRandomEmailID(firstName), telephone, password, confirmPassword, subscribe);
		Assert.assertTrue(isRegDone);
	}

}
