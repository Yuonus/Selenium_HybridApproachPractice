package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtils;

public class ProductResultsPageTest extends BaseTest {

	@DataProvider(name="data")
	public Object[][] getData() {
	        return new Object[][] {
	               {"user1", "pass1", 25},
	                {"user2", "pass2", 30}
	         };
	}

	
	@BeforeClass
	public void productResultSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

//	@DataProvider
//	public Object[][] getSearchData() {
//		 return new Object[][] {
//			 {"MacBook", "MacBook Pro", 4},
//			 {"MacBook", "MacBook Air", 4},
//			 {"iMac", "iMac", 3},
//			 {"Samsung", "Samsung SyncMaster 941BW", 1}
//		 };
//	}
//
//	
//	@Test (dataProvider = "getSearchData")
//	public void productImagesTest(String searchKey, String productName, int imageCount) {
//		searchResultPage = accPage.doSearch(searchKey);
//		productInfoPage = searchResultPage.selectProduct(productName);
//		Assert.assertEquals(productInfoPage.getProductImagesCount(), imageCount); // TDD -- Method Creation
//	}
	
	// Feeding the data from excel file
	@DataProvider
	public Object[][] getSearchExcelTestData() {
		Object prodData[][] =ExcelUtils.getTestData(AppConstants.PRODUCT_DATA_SHEET_NAME);
		return prodData;
	}


	// Using this given below code will throw (Data provider mismatch) error because the excel util is returning
	// String and we have an [int imageCount] in the productImageTest() method. so we will have to change the int
	// int String and also use String.valueOf() method to convert the int into string value int the Assertion.
//	@Test (dataProvider = "getSearchExcelTestData")
//	public void productImagesTest(String searchKey, String productName, int imageCount) {
//		searchResultPage = accPage.doSearch(searchKey);
//		productInfoPage = searchResultPage.selectProduct(productName);
//		Assert.assertEquals(productInfoPage.getProductImagesCount(), imageCount); // TDD -- Method Creation
//	}
	
	// The correct Test method
	@Test (dataProvider = "getSearchExcelTestData")
	public void productImagesTest(String searchKey, String productName, String imageCount) {
		searchResultPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(productName);
		Assert.assertEquals(String.valueOf(productInfoPage.getProductImagesCount()), imageCount); // TDD -- Method Creation
	}

	@Test
	public void productInfoTest() {
		searchResultPage = accPage.doSearch("MacBook");
		productInfoPage = searchResultPage.selectProduct("MacBook Pro");
		Map<String, String> productDetailsMap = productInfoPage.getProductDetails();
		softAssert.assertEquals(productDetailsMap.get("Brand"), "Apple");
		softAssert.assertEquals(productDetailsMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productDetailsMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(productDetailsMap.get("Reward Points"), "800");

		softAssert.assertEquals(productDetailsMap.get("price"), "$2,000.00");
		softAssert.assertEquals(productDetailsMap.get("ExternalTaxprice"), "$2,000.00");

		softAssert.assertAll();
	}

}


