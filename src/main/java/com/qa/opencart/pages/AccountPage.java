package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountPage {

	// Private driver instance
	private WebDriver driver;
	private ElementUtil eleUtil;

	// By locators
	private By logoutLink = By.linkText("Logout");
	private By search = By.xpath("//input[@name='search']");
	private By searchIcon = By.cssSelector("div#search button");
	private By accHeaders = By.cssSelector("div#content > h2"); 

	// Page Constructor
	public AccountPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// Page methods

	public String getAccountPageTitle() {
		String pageTitle = eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("The Account page title is: " + pageTitle);
		return pageTitle;
	}

	public String getAccountPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.ACCOUNTS_PAGE_URL_FRACTION, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Account Page current URL is: " + url);
		return url;
	}

	public boolean isLogoutLinkExist() {
		return eleUtil.waitForVisibilityOfElement(accHeaders, AppConstants.SHORT_DEFAULT_WAIT).isDisplayed();
	}

	public boolean isSearchFieldExist() {
		return eleUtil.waitForVisibilityOfElement(search, AppConstants.MEDIUM_DEFAULT_WAIT).isDisplayed();
	}

	public List<String> getAccountHeaders() {
		List<WebElement> headersList = eleUtil.waitForVisibilityOfElements(accHeaders, AppConstants.SHORT_DEFAULT_WAIT);
		List<String> headersValueList = new ArrayList<String>();
		for (WebElement e : headersList) {
			String text = e.getText();
			headersValueList.add(text);
		}
		return headersValueList;
	}

	public void logout() {
		eleUtil.doClick(logoutLink);
	}
	
	public SearchResultsPage doSearch(String searchKey) {
		eleUtil.waitForVisibilityOfElement(search, AppConstants.MEDIUM_DEFAULT_WAIT).clear();
		eleUtil.waitForVisibilityOfElement(search, AppConstants.MEDIUM_DEFAULT_WAIT).sendKeys(searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver); // TDD (Test Driven Development)
		
		/*
		 * TDD 
		 * When I start writing my test cases or methods then on the fly I will create classes, methods or 
		 * whatever I need based on my test requirements and this called Test Driven development approach
		 */
	}
}
