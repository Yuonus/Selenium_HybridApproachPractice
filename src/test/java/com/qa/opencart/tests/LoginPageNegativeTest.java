package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class LoginPageNegativeTest extends BaseTest{
	
	@DataProvider
	public Object[][] incorrectLoginTestData(){
		return new Object[][] {
			{"Automation12@@#@#$il.mo", "LoveSDET"},
			{"AutomatJohn@%$$$il.", "QAisLove"},
			{"Java@34ail.cm", "LoveTesting"},
			{"Autosf2@#@mail.cmm", "LoveJava"},
			{"Automatest@!!ail.cmm", "LoveSelenium"},
			{"AutomationTEST@!!il.cmm", "LoveCypress"}
		};
	}
	
	@Test (dataProvider = "incorrectLoginTestData")
	public void loginWithWrongCredentialsTest(String username, String pwd) {
		
		// Using static method is not allowed in automation framework and also no Selenium and Java code are allowed in test classes
		// it is only for demo
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		Assert.assertTrue(loginPage.doLoginWithWrongCredentials(username, pwd));
	}

}
