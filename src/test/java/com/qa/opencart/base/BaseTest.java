package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

@Listeners(ChainTestListener.class)
public class BaseTest {

	protected WebDriver driver;
	DriverFactory df;
	protected LoginPage loginPage;
	protected Properties prop;
	protected AccountPage accPage;
	protected SearchResultsPage searchResultPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;

	protected SoftAssert softAssert;

	@Parameters({ "browser" })
	@BeforeTest
	public void setUp(@Optional("chrome") String browserName) {
		
		// Adding these lines for chaintest system customized data
		ChainPluginService.getInstance().addSystemInfo("builds", "1.0");
		ChainPluginService.getInstance().addSystemInfo("Owner Name", "Sabawoon Yuonus");
		ChainPluginService.getInstance().addSystemInfo("Sr. Tester", "Sabawoon Yuonus");
		ChainPluginService.getInstance().addSystemInfo("Manager", "Sabawoon Yuonus");
		
		df = new DriverFactory();
		prop = df.initProp();

		// If a browser parameter is provided from the TestNG XML, it will override the
		// browser value from config.properties.
		// This ensures the XML-specified browser takes precedence over the
		// default/configured browser.
		if (browserName != null) {
			prop.setProperty("browser", browserName);
		}

		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();
	}

	@AfterTest
	public void tearDown() {
		driver.close();
		DriverFactory.tlDriver.remove(); 
	}

}
