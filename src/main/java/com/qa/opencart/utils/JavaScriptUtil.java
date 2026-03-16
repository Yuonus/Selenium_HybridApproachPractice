package com.qa.opencart.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
	
	private WebDriver driver;
	private JavascriptExecutor js;
	
	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor)this.driver;
	}
	
	
	public String getTitleByJs() {
		return js.executeScript("return document.title").toString();
	}
	
	public String getURLByJs() {
		return js.executeScript("return document.URL").toString();
	}
	
	public void generateJsAlert(String mesg) {
		js.executeScript("alert ('"+mesg+"')");
		// After this "js.executeScript("alert ('"+mesg+"')");" the page/app will be frozen. So, to prevent freezing the page
		// We will add the below code to accept the alert. 
		// Note: if you want to dismiss or cancel the alert you can create a separate utility for cancellation
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
	}
	
	public void generateJsConfirm(String mesg) {
		js.executeScript("confirm ('"+mesg+"')");
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
	}
	
	public void generateJsPrompt(String mesg, String value) {
		js.executeScript("prompt ('"+mesg+"')");
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(value);
		alert.accept();
	}
	
	public void goBackWithJS() {
		js.executeScript("history.go(-1)");
	}
	
	public void goForwardWithJS() {
		js.executeScript("history.go(1)");
	}
	
	public void pageRefreshWithJS() {
		js.executeScript("history.go(0)");
	}
	
	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText").toString();
	}

	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0, '"+height+"');");
	}
	
	public void scrollDownToTheMiddleOfPage() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight/2);");
	}
	
	public void scrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0);");
	}
	
	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true)", element);
	}
	
	/**
	 * Script:"document.body.style.zoom = 200 %"
	 * @param zoomPercentage
	 */
	public void zoomChromeEdgeSagari(String zoomPercentage) {
		String zoom = "document.body.style.zoom ='"+zoomPercentage+"%'";
		js.executeScript(zoom);
	}
	
	/**
	 * Script:"document.body.style.MozTransform = 'scale(0.5)'";
	 * @param zoomPercentage
	 */
	public void zoomFirefox(String zoomPercentage) {
		String zoom = "document.body.style.MozTransform = 'scale("+zoomPercentage+")'";
		js.executeScript(zoom);
	}
	
	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	
	public void falsh(WebElement element) {
		String bgColor = element.getCssValue("backgroundColor");
		for(int i = 0; i<30; i++) {
			changeColor("rgb(200,0,0)", element); // The color we are passing to flash/high light the element
			changeColor(bgColor, element); // Element orginal color
		}
	}
	
	private void changeColor(String color, WebElement element) {
//		JavascriptExecutor js = (JavascriptExecutor)driver;// We can remove this line as we have removed from each and every
//		utility methods above as we have supplied it to the starting constructor 
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
		
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void clickElementByJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", element);
	}
	
	public void sendKeysUsingWIthId(String id, String value) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("document.getElementById('"+id+"').value='"+value+"'");
						// document.getElementById("input-email").value = 'sabawoon@gmail.com'
	}
	
	
}
