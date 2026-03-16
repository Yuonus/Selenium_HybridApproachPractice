package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exception.FrameworkException;

import io.qameta.allure.Step;

public class ElementUtil {

	private WebDriver driver;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
	}

	public By getBy(String locatorType, String locatorValue) {
		By by = null;
		switch (locatorType.toLowerCase().trim()) {
		case "id":
			by = By.id(locatorValue);
			break;
		case "name":
			by = By.name(locatorValue);
			break;
		case "class":
			by = By.className(locatorValue);
			break;
		case "xpath":
			by = By.xpath(locatorValue);
			break;
		case "css":
			by = By.cssSelector(locatorValue);
			break;
		case "linktext":
			by = By.linkText(locatorValue);
			break;
		case "partiallink":
			by = By.partialLinkText(locatorValue);
			break;
		case "tag":
			by = By.tagName(locatorValue);
			break;
			
		default:
			System.out.println("Wrong locator type is passed --- " + locatorType);
			throw new FrameworkException("WRONG LOCATOR TYPE");
		}
		return by;
	}

	public void doSendKeys(String locatorType, String locatorValue, String value) {
		getElement(locatorType, locatorValue).sendKeys(value);
	}

	@Step("Entering value {1} to element {0}")
	public void doSendKeys(By locator, String value) {
		getElement(locator).sendKeys(value);
	}

	@Step("Clicking on element {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public void doClick(String locatorType, String locatorValue) {
		getElement(locatorType, locatorValue).click();
	}

	public String doElementGetText(By locator) {
		return getElement(locator).getText();
//		If we don't want to use the "return" we can directly print the value of the header by storing it
//		in a separate variable. and changing the method type to "void"
//		String header = getElement(locator).getText();
//		System.out.println(header);
	}
	
	public String doElementGetText(String locatorType, String locatorValue) {
		return getElement(locatorType, locatorValue).getText();
	}
	
	public String doGetElementAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public WebElement getElement(By locator) {
		return driver.findElement(locator);
	}
	
	public WebElement getElement(String locatorType, String locatorValue) {
		return  driver.findElement(getBy(locatorType, locatorValue));
	}
	
	// WAF: Capture the text of all page links and return List<String>
	
		/**
		 * This function capture text of all links in a page and return List<String>
		 * @param locator
		 * @return
		 */
		public List<String> getElementsTextList(By locator) {
			List<WebElement> eleList = getElements(locator);
			//Selenium does not have a class to return list of Strings, we will instantiate our own custom method
//			that will return a list of strings and it is physical capacity will be 0.
			List<String> eleTextList = new ArrayList<String>();
			for(WebElement e : eleList) {
				String text = e.getText();
					if(text.length()!=0) {
						eleTextList.add(text);
					}
			}
			return eleTextList;
		}
		
		// WAF to capture the value of a specific attribute. getAttribute();
		
		/**
		 * This Method will help to capture the attribute value of any attribute
		 * @param locator
		 * @param attrName
		 * @return List<String>
		 */
		public List<String> getElementsAttributeList(By locator, String attrName) {
			List<WebElement> eleList = getElements(locator);
			List<String> eleAttrList = new ArrayList<String>(); // PC = 0
			
			for (WebElement e : eleList) {
				String attrValue = e.getAttribute(attrName);
				eleAttrList.add(attrValue);
			}
			return eleAttrList;
		}
		
		/**
		 * This function/method returns the number of available web elements in a page.
		 * @param locator
		 * @return
		 */
		public int getElementsCount(By locator) {
			return getElements(locator).size();
		}
		
		/**
		 * This Function returns List of WebElements.
		 * @param locator
		 * @return
		 */
		public List<WebElement> getElements(By locator) {
			return driver.findElements(locator);
		}
		
		/**
		 * This method checks if a specific (single) element is available on the 
		 * page or not.
		 * @param locator
		 * @return
		 */
		public boolean checkSingleElementPresent(By locator) {
			return driver.findElements(locator).size() == 1 ? true : false;
		}
		
		/**
		 * This method checks if multiple elements are available on the 
		 * page or not.
		 * @param locator
		 * @return
		 */
		public boolean checkElementPresent(By locator) {
			return driver.findElements(locator).size() >=1 ? true : false;
		}
		
		/**
		 * This method checks if multiple elements with a specific numbers are available on the 
		 * page or not.
		 * @param locator
		 * @return
		 */
		public boolean checkElementPresent(By locator, int totalElements) {
			return driver.findElements(locator).size()==totalElements ? true : false;
		}

		public void search(By searchField, By suggestions, String searchKey, String suggName ) throws InterruptedException {
//			driver.findElement(searchField).sendKeys(searchKey); --> We can use doSendKeys method instead of this line of code
			doSendKeys(searchField, searchKey);
			Thread.sleep(3000);
			
//			List<WebElement> suggList = driver.findElements(suggestions); --> we can use getElements() method instead of this line of code
			List<WebElement> suggList = getElements(suggestions);
			System.out.println("Number of suggestion available at Search keys are :" + suggList.size());
			
			for (WebElement e : suggList) {
				String text = e.getText();
				System.out.println(text);
				if(text.contains(suggName)) {
					e.click();
					break;
				}
			}
		}
		
		public  void clikcOnElement(By locator, String eleText) {
			List<WebElement> eleList = getElements(locator);
			System.out.println(eleList.size());		
			for(WebElement e : eleList) {
				String text = e.getText();
				System.out.println(text);
				//store in the list<string>
					if(text.contains(eleText)) {
						e.click();
						break;
					}
			}
		}

		//******************* Select Drop Down Utils ***************//
		
		private Select createSelect(By locator) {
			Select select = new Select(getElement(locator));
			return select;
		}
		
		public void doSelectDropDownByIndex(By locator, int index) {
//			Select select = new Select(getElement(locator));
//			select.selectByIndex(index);
			createSelect(locator).selectByIndex(index); // new code
		}
		
		public void doSelectDropDownByVisibleText(By locator, String visibleText) {
			createSelect(locator).selectByVisibleText(visibleText);
		}
		
		public void doSelectDropDownByValue(By locator, String value) {
			createSelect(locator).selectByVisibleText(value);
			
//	Note: We cannot overload doSelectDropDownByVisibleText() & doSelectDropDownByValue() because number of 
//	parameters and their type are the same. but we can overload doSelectDropDownByVisibleText() & 
//	doSelectDropDownByIndex(). But we will let them three independent methods.
		}
		
		public void selectDropDownOptions(By locator, String dropDownValue) {
			List<WebElement> countryList = createSelect(locator).getOptions();
			System.out.println("There are totally: " + countryList.size() + " options in COUNTRY dropdwon.");
			for(WebElement e : countryList) {
				String text = e.getText();
				System.out.println(text);
				
				if(text.contains(dropDownValue)) {
					e.click();
					break;
				// u can also use text.equals("Afghanistan") method
				}
			}
		}
		
	
		public List<String> getDropDownOptions(By locator) {
			List<WebElement> countryList = createSelect(locator).getOptions();
			List<String> optionsTextList = new ArrayList<String>();
			for(WebElement e : countryList) {
				String text = e.getText();
				optionsTextList.add(text);
			}
			return optionsTextList;
		}
		
		public int getDropDownOptionsCount(By locator) {
			return createSelect(locator).getOptions().size();
		}
		
		public void selectDropDownValue(By locator, String value) {
			List<WebElement> optionsList = getElements(locator);
			for(WebElement e : optionsList) {
				String text = e.getText();
				if(text.equals(value)) {
					doClick(locator);
					break;
				}
			}
		}
		
		public boolean isDropDownMultiple(By locator) {
			return createSelect(locator).isMultiple() ? true : false;  // ternary operator
		}
		
		/**
		 * This method is used to select the values from the drop down. It can select:
		 * 	1: Single selection
		 * 	2: Multiple selection
		 * 	3: All selection. Please pass "identifier" as a value to select all values
		 * @param locator
		 * @param values
		 */
		
		public void selectDropDownMultipleValues(By locator, By optionLocator, String ... values) {
			if(isDropDownMultiple(locator)) {
				if(values[0].equalsIgnoreCase("all")) {
					List<WebElement> optionsList = getElements(optionLocator);
						for(WebElement e : optionsList) {
							e.click();
						}
					}else {
						for(String value : values) {
							createSelect(locator).selectByVisibleText(value);
						}
					}
				}
			}
		
		// *********************** Actions Utilities ****************//
		
		public void twoLevelMenuHandle(By parentMenuLocator, By childMenuLocator) throws InterruptedException {
			Actions act = new Actions(driver);
			act.moveToElement(getElement(parentMenuLocator)).build().perform();
			Thread.sleep(2000);
			doClick(childMenuLocator);
		}
		
		public void fourLevelMenuHandle(By parentMenuLocator, By firstChildMenuLocator, 
				By secondChildMenuLocator, By thirdChildMenuLocator) throws InterruptedException {
			
			Actions act = new Actions(driver);
			doClick(parentMenuLocator);
			Thread.sleep(1000);
			
			act.moveToElement(getElement(firstChildMenuLocator)).build().perform();
			Thread.sleep(1000);
			
			act.moveToElement(getElement(secondChildMenuLocator)).build().perform();
			Thread.sleep(1000);
			
			doClick(thirdChildMenuLocator);
		}
		
		public void doActionsSendKeys(By locator, String value) {
			Actions act = new Actions(driver);
			act.sendKeys(getElement(locator), value).perform();
		}
		
		public void doActionsClick(By locator) {
			Actions act = new Actions(driver);
			act.click(getElement(locator)).perform();
		}
		
		public void doActionsSendKeysWithPause(By locator, String value) {
			Actions act = new Actions(driver);
			char val[] = value.toCharArray();
			for(char c : val) {
				act.sendKeys(getElement(locator), String.valueOf(c)).pause(500).build().perform();
			}
		}
		
		//************************ Wait Utils ************************//
		
		/**
		 * An expectation for checking that an element is present on the DOM of a page. This does not necessarily mean that 
		 * the element is visible.
		 * @param locator
		 * @param timeOut
		 * @return web element
		 */
		public WebElement waitForPresenceOfElement(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		
		/**
		 * An expectation for checking that an element is present on the DOM of a page. This does not necessarily mean that 
		 * the element is visible.
		 * @param locator
		 * @param timeOut
		 * @param intervalTime
		 * @return web element
		 */
		public WebElement waitForPresenceOfElement(By locator, int timeOut, int intervalTime) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		
		
		/**
		 * An expectation for checking that an element is present on the DOM of a page and visible.
		 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
		@Step("Waiting for element: {0} with timeOut {1}")
		public WebElement waitForVisibilityOfElement(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		
		/**
		 * An expectation for checking that an element is present on the DOM of a page and visible.
		 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
		 * @param locator
		 * @param timeOut
		 * @Param intervalTime
		 * @return
		 */
		public WebElement waitForVisibilityOfElement(By locator, int timeOut, int intervalTime) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
	
		public void doClickWithWait(By locator, int timeOut) {
			waitForVisibilityOfElement(locator, timeOut).click();
		}
		
		public void doSendKeysWithWait(By locator, String value, int timeOut) {
			waitForVisibilityOfElement(locator, timeOut).sendKeys(value);
		}
		
		/**
		 * An expectation for checking that all elements present on the web page that match the locator are visible. 
		 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
		 * @param locator
		 * @param timeOut
		 * @return web elements
		 */
		public List<WebElement> waitForVisibilityOfElements(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		
		
		/**
		 * An expectation for checking that there is at least one element present on a web page.
		 * @param locator
		 * @param timeOut
		 * @return web element
		 */
		public List<WebElement> waitForPresenceOfElements(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}
		
		public String waitForTitleContains(String titleFractionValue, int timeOut ) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
			if(wait.until(ExpectedConditions.titleContains(titleFractionValue))) {
				return driver.getTitle();
				}
			}catch(TimeoutException e) {
				System.out.println(titleFractionValue + "Title fraction value is not present");
				e.printStackTrace();
			}
			return null;
		}
		
		@Step("Waiting for the page title: {0} with timeOut: {1}")
		public String waitForTitleIs(String titleCompleteValue, int timeOut ) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
			if(wait.until(ExpectedConditions.titleContains(titleCompleteValue))) {
				return driver.getTitle();
				}
			}catch(TimeoutException e) {
				System.out.println(titleCompleteValue + "Title complete value is not present");
				e.printStackTrace();
			}
			return null;
		}
		
		@Step("Waiting for URL: {0} and timeOut {1}")
		public String waitForURLContains(String urlFractionValue, int timeOut ) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
			if(wait.until(ExpectedConditions.urlContains(urlFractionValue))) {
				return driver.getCurrentUrl();
				}
			}catch(TimeoutException e) {
				System.out.println(urlFractionValue + " URL fraction value is not present");
				e.printStackTrace();
			}
			return null;
		}
		
		public String waitForURLToBe(String urlCompleteValue, int timeOut ) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
			if(wait.until(ExpectedConditions.urlContains(urlCompleteValue))) {
				return driver.getCurrentUrl();
				}
			}catch(TimeoutException e) {
				System.out.println(urlCompleteValue + "URL complete value is not present");
				e.printStackTrace();
			}
			return null;
		}
		
		
		public Alert waitForJSAlert(int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.alertIsPresent());
		}
		
		public void acceptJSAlert(int timeOut) {
			waitForJSAlert(timeOut).accept();
		}
		
		public void dismissJSAlert(int timeOut) {
			waitForJSAlert(timeOut).dismiss();
		}
		
		public String getJsAlertText(int timeOut) {
			return waitForJSAlert(timeOut).getText();
		}
		
		public void enterValueOnJsAlert(int timeOut, String value) {
			waitForJSAlert(timeOut).sendKeys(value);
		}
		
		public void waitForFrameByLocator(By frameLocator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		}
		
		public void waitForFrameByIndex(int frameIndex, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		}
		
		public void waitForFrameByIDOrName(String IDOrName, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IDOrName));
		}
		
		public void waitForFrameByElement(WebElement frameElement, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
		}
		
		public boolean checkNewWwindowExist(int timeOut, int expectedNumberOfWindows) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
				if (wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows))) {
					return true;
				}
			} catch (TimeoutException e) {
				System.out.println("Number of windows are not the same ...");
			}
			return false;
		}
		
		/**
		 * An expectation for checking an element is visible and enabled such that you can click it.
		 * @param locator
		 * @param timeOut
		 */
		public void clickElementWhenReady(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
				wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
			} catch (TimeoutException e) {
				System.out.println("Element is not clickable/enabled ...");
			}
		}
	
		public WebElement waitForElementWithFluentWait(By locator, int timeOut, int intervalTime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(intervalTime))
					.withMessage("-- Timeout is done ... Element is not found ...")
					.ignoring(NoSuchElementException.class)
					.ignoring(ElementNotInteractableException.class);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		
		public void waitForFrameWithFluentWait(String frameIDOrName, int timeOut, int intervalTime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(intervalTime))
					.withMessage("-- Timeout is done ... Frame is not found ...")
					.ignoring(NoSuchFrameException.class);
			 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));
		}
		
		public void waitForJSAlertWithFluentWait(int timeOut, int intervalTime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(intervalTime))
					.withMessage("-- Timeout is done ... JS Alert is not appearing ...")
					.ignoring(NoAlertPresentException.class);
			 wait.until(ExpectedConditions.alertIsPresent());
		}
		
		// ******************* Custom Wait ***********************//
		public WebElement retryingElement(By locator, int timeOut) {
			
			WebElement element = null;
			int attempts = 0;
			
			while(attempts < timeOut) {
				try {
					element = getElement(locator);
					System.out.println("Element is found ... " + locator + " in attempt " + attempts);
					break;
				}
				catch(NoSuchElementException e) {
					System.out.println("Element is not found ... " + locator + " in attempt " + attempts);
					try {
						Thread.sleep(500); // Default Polling time after each attempts
					} catch (InterruptedException e1) {
						e1.printStackTrace();// to show where the exception come from
					}
				}
				attempts++;
			}
			
			if(element == null) {
				System.out.println("Element is not found ... tried for " + timeOut + " times " + 
						" with the interval of " + 500 + " milli seconds ");
				throw new FrameworkException("No Such Element");
			}
			return element;
		}
		
		// If the user want to supply his/her own polling/sleep time, then we can overload the above method.
//		as the above method is having default polling frequency/time of 500 milli seconds.
		
		public WebElement retryingElement(By locator, int timeOut, int intervalTime) {
			
			WebElement element = null;
			int attempts = 0;
			
			while(attempts < timeOut) {
				try {
					element = getElement(locator);
					System.out.println("Element is found ... " + locator + " in attempt " + attempts);
					break;
				}
				catch(NoSuchElementException e) {
					System.out.println("Element is not found ... " + locator + " in attempt " + attempts);
					try {
						Thread.sleep(intervalTime); // Custom Polling time after each attempts
					} catch (InterruptedException e1) {
						e1.printStackTrace();// to show where the exception come from
					}
				}
				attempts++;
			}
			
			if(element == null) {
				System.out.println("Element is not found ... tried for " + timeOut + " times " + 
						" with the interval of " + intervalTime + " milli seconds ");
				throw new FrameworkException("No Such Element");
			}
			return element;
		}

		public boolean isPageLoaded(int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'")).toString();
			return Boolean.parseBoolean(flag);
		}

	
		public void doClear(By locator) {
			  getElement(locator).clear();
		}
}
