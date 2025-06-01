package utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class JavaScriptUtility 
{
	//1. find elements using JavaScript "document" object methods
	public WebElement findElementByJSById(WebDriver driver,String id)
	{
		WebElement e=(WebElement) ((JavascriptExecutor) driver).executeScript(
				"return(document.getElementById('"+id+"'));");
		return e;
	}
	
	@SuppressWarnings("unchecked")
	public List<WebElement> findElementsByJSByName(WebDriver driver,String name)
	{
		List<WebElement> l=(List<WebElement>) ((JavascriptExecutor) driver).executeScript(
				"return(document.getElementsByName('"+name+"'));");
		return l;
	}
	
	public WebElement findElementByJSByName(WebDriver driver,String name, int index)
	{
		WebElement e=(WebElement) ((JavascriptExecutor) driver).executeScript(
				"return(document.getElementsByName('"+name+"'))["+index+"];");
		return(e);
	}
	
	public List<WebElement> findElementsByJSByClassName(WebDriver driver,String className)
	{	
		@SuppressWarnings("unchecked")
		List<WebElement> l=(List<WebElement>) ((JavascriptExecutor) driver).executeScript(
				"return(document.getElementsByClassName('"+className+"'));");
		return l;
	}
	
	public WebElement findElementByJSByClassName(WebDriver driver,String className,int index)
	{	
		WebElement e=(WebElement) ((JavascriptExecutor) driver).executeScript(
				"return(document.getElementsByClassName('"+className+"'))["+index+"];");
		return e;
	}
	
	public List<WebElement> findElementsByJSByTagName(WebDriver driver,String tagName)
	{
		@SuppressWarnings("unchecked")
		List<WebElement> l=(List<WebElement>) ((JavascriptExecutor) driver).executeScript(
				"return(document.getElementsByTagName('"+tagName+"'));");
		return l;
	}
	
	public WebElement findElementByJSByTagName(WebDriver driver,String tagName,int index)
	{
		WebElement e=(WebElement) ((JavascriptExecutor) driver).executeScript(
				"return(document.getElementsByTagName('"+tagName+"'))["+index+"];");
		return e;
	}
	
	@SuppressWarnings("unchecked")
	public List<WebElement> findElementsByJSByCssSelector(WebDriver driver,String cssSelector)
	{
		List<WebElement> l=(List<WebElement>) ((JavascriptExecutor) driver).executeScript(
				"return(document.querySelectorAll('"+cssSelector+"'));");
		return l;
	}
	
	public WebElement findElementByJSByCssSelector(WebDriver driver,String cssSelector,int index)
	{
		WebElement e=(WebElement) ((JavascriptExecutor) driver).executeScript(
				"return document.querySelectorAll('"+cssSelector+"')["+index+"];");
		return e;
	}
	
	public WebElement findElementByJSByXpath(WebDriver driver,String xpath)
	{
		
		WebElement e=(WebElement) ((JavascriptExecutor) driver).executeScript(
				"return document.evaluate('"+xpath+"',document,null,XPathResult.FIRST_ORDERED_NODE_TYPE,null).singleNodeValue;");
		return e;
	}
	
	public WebElement findElementByJSByLinkText(WebDriver driver,String linkText)
	{
		WebElement e=(WebElement) ((JavascriptExecutor) driver).executeScript(
				"return(document.evaluate('"+linkText+"',document,null,XPathResult.FIRST_ORDERED_NODE_TYPE,null).singleNodeValue);");
		return e;
	}
	
	public WebElement findElementByJSByPartialLinkText(WebDriver driver,String partialLinkText)
	{
		WebElement e=(WebElement) ((JavascriptExecutor) driver).executeScript(
			"return document.evaluate('"+partialLinkText+"',document,null,XPathResult.FIRST_ORDERED_NODE_TYPE,null).singleNodeValue;");
		return e;
	}
	
	//2. click element using JavaScript
	public void clickElementByJS(WebDriver driver,WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}
	
	//3. sendKeys using JavaScript
	public void sendKeysByJS(WebDriver driver,WebElement element,String value)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1];",element,value);
	}
	public void sendKeysInColorByJS(WebDriver driver,WebElement element,String value,String color)
	{
		((JavascriptExecutor) driver).executeScript(
				                "arguments[0].style.color=arguments[1];", element, color);
		((JavascriptExecutor) driver).executeScript(
				                "arguments[0].value='"+value+"';", element);
	}
	
	//4. flash element or highlight element using JavaScript
	public void flashElementByJS(WebDriver driver,WebElement element) throws Exception
	{
		
		String bgColor=element.getCssValue("backgroundColor"); //get existing bgcolor
		for(int i=0;i<10;i++)
		{
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].style.backgroundColor='#000000'", element);
			Thread.sleep(100);
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].style.backgroundColor='"+bgColor+"'", element);
			Thread.sleep(100);
		}
	}
	public void highlightElementByJS(WebDriver driver,WebElement element, String color)
	{
		((JavascriptExecutor) driver).executeScript(
				                "arguments[0].style.border='3px solid "+color+"'", element);
	}
	
	//5. edit element source in HTML using JavaScript
	public void disableElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].disabled=true;", element);
    }
	public void enableElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].disabled=false;", element);
    }
    public void removeElement(WebDriver driver, WebElement element) 
    {
        ((JavascriptExecutor) driver).executeScript("arguments[0].remove();", element);
    }
    public void addPlainTextElement(WebDriver driver, WebElement parentElement, String text) 
    {
        ((JavascriptExecutor) driver).executeScript(
                "var para=document.createElement('p');" +
                "var tn=document.createTextNode(arguments[1]);" +
                "para.appendChild(tn); " +
                "arguments[0].appendChild(para);", parentElement, text);
    }
	public void removeAttributeByJS(WebDriver driver,WebElement element,String attribute)
	{
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].removeAttribute('"+attribute+"')", element);
	}
	public void addAttributeByJS(WebDriver driver,WebElement element,String attribute, String value)
	{
		//add new attribute or change attribute value
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].setAttribute('"+attribute+"','"+value+"');", element);
	}
	
	//6. password testing using JavaScript
	public boolean isPasswordMaskedCorrectByJS(WebDriver driver,WebElement element,String password)
	                                                                               throws Exception
	{
		//Step-1:Enter password into password box
		element.sendKeys(password);
		Thread.sleep(5000);
		//Step-2: change password box type to "text" to get visibility of given value
		((JavascriptExecutor) driver).executeScript(
								"arguments[0].setAttribute('type','text');",element);
		//Step-3: get that visible value
		String actualPassword=element.getAttribute("value");
		//Step-4: compare that visible value with given value
		if(actualPassword.equals(password))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//7. get text of hidden element using JavaScript
	public String getTextOfElementIncludeChilds(WebDriver driver, WebElement e)
	{
		if(e.isDisplayed()){
			return(e.getText());
		}
		else{
			//hidden element
			String x=(String) ((RemoteWebDriver)driver).executeScript(
					                                      "return(arguments[0].textContent);",e);
			return(x);
		}
	}
	//8. get text of an element with child nodes text
	public String getTextOfChildElement(WebDriver driver, WebElement parent, int childindex)
	{
		List<WebElement> childs=parent.findElements(By.xpath("descenadant::*"));
		if(childs.get(childindex).isDisplayed()){
			return(childs.get(childindex).getText());
		}
		else{
			//hidden element
			String x=(String) ((RemoteWebDriver)driver).executeScript(
					                     "return(arguments[0].textContent);",childs.get(childindex));
			return(x);
		}
	}
	//9. get text of an element without child nodes text
	public String getTextOfElementExcludeChilds(WebDriver driver, WebElement parent)
	{
		String text="";
		if(parent.isDisplayed()){
			text=parent.getText();
		}
		else{
			//hidden element
			text=(String) ((RemoteWebDriver)driver).executeScript(
					                                  "return(arguments[0].textContent);",parent);
		}
		List<WebElement> childs=parent.findElements(By.xpath("descendant::*"));
		for(WebElement child:childs)
		{
			//remove each child element's text from total text
		    text=text.replace(child.getText(),""); 
		}
		return(text);
	}
	//10. get text of a line in multi lined text element
	public String getTextOfLineInElementBody(WebDriver driver, WebElement parent, int lineindex)
	{
		String text=(String) ((RemoteWebDriver) driver).executeScript(
                              "return(arguments[0].childNodes["+lineindex+"].textContent);",parent);
		return(text);
	}
	
	//11. get page innertext using JavaScript
	public String getPageInnerTextByJS(WebDriver driver)
	{
		return (String) ((JavascriptExecutor) driver).executeScript(
				                      "return(document.documentElement.innerText);");
	}
	
	//12. page scroll using JavaScript
	public void scrollToElementByJS(WebDriver driver,WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	public void scrollPageDownByJS(WebDriver driver)
	{
		
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
	public void scrollPageUpByJS(WebDriver driver)
	{
		
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,-document.body.scrollHeight)");
	}
	public void scrollPageLeftByJS(WebDriver driver)
	{
		((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollWidth,0)");
	}
	public void scrollPageRightByJS(WebDriver driver)
	{
		((JavascriptExecutor) driver).executeScript("window.scrollTo(-document.body.scrollWidth,0)");
	}
	
	//13. scroll in an element using JavaScript
	public void scrollUpInElementByJS(WebDriver driver,WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop=0;",element);		
	}
	
	public void scrollDownInElementByJS(WebDriver driver,WebElement element)
	{
		((JavascriptExecutor) driver).executeScript(
				"var x=arguments[0].scrollHeight; arguments[0].scrollTop=x;",element);
				
	}
	public void scrollLeftInElementByJS(WebDriver driver,WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft=0;",element);	
	}
	public void scrollRightInElementByJS(WebDriver driver,WebElement element)
	{
		((JavascriptExecutor) driver).executeScript(
				"var x=arguments[0].scrollWidth; arguments[0].scrollLeft=x;",element);		
	}
	
	//14. page zoom using JavaScript
	public void zoomPageByJS(WebDriver driver, int scale)
	{
		((JavascriptExecutor) driver).executeScript("document.body.style.zoom='"+scale+"%'");
	}
	
	//15. generate alert using JavaScript
	public void generateAlertByJS(WebDriver driver,String message)
	{
		
		((JavascriptExecutor) driver).executeScript("window.alert('"+message+"')");
	}
	
	//16: Clean history of browser
	public void cleanBrowserHistoryByJS(WebDriver driver)
	{
		((JavascriptExecutor) driver).executeScript("history.go(0)"); //0 means forcibly clean
	}
	
	//17: Clean cookies of browser using JavaScript
	public void cleanBrowserCookiesByJS(WebDriver driver)
	{
		((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");
		((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
	}
	
	//18: Back, Forward, Refresh and Navigation in browser using JavaScript
	public void refreshBrowserByJS(WebDriver driver)
	{
		((JavascriptExecutor) driver).executeScript("history.go(0)");
	}
	public void backBrowserByJS(WebDriver driver)
	{
		((JavascriptExecutor) driver).executeScript("history.go(-1)");
	}
	public void forwardBrowserByJS(WebDriver driver)
	{
		((JavascriptExecutor) driver).executeScript("history.go(1)");
	}
	public void navigateToURLByJS(WebDriver driver,String url)
	{
		((JavascriptExecutor) driver).executeScript("window.location='"+url+"'");
	}
	
	//19: get page title using JavaScript
	public String getPageTitleByJS(WebDriver driver)
	{
		return (String) ((JavascriptExecutor) driver).executeScript("return document.title;");
	}
	
	//20: get page source using JavaScript
	public String getPageSourceByJS(WebDriver driver)
	{
		return (String) ((JavascriptExecutor) driver).executeScript(
				                                  "return document.documentElement.outerHTML;");
	}
	
	//21: close browser using JavaScript
	public void closeBrowserByJS(WebDriver driver)
	{
		((JavascriptExecutor) driver).executeScript("window.close();");
	}
	
	//22: Total Frames in Browser using JavaScript
	public long getTotalFramesInBrowserByJS(WebDriver driver)
	{
		long n=(long) ((JavascriptExecutor) driver).executeScript("return(window.frames.length);");
		return n;
	}
	//23: get details of pseudo element
	public String getDetailsOfPseudoElementPropertyByJS(WebDriver driver, 
			              String containerElementCssSelector, String pseudoElement, String property)
	{
		return (String) ((JavascriptExecutor) driver).executeScript(
				"var ne=document.querySelector(arguments[0]);"
				+ "var pe=window.getComputedStyle(ne,arguments[1]); "
				+ "var y=pe.getPropertyValue(arguments[2]);"
				+ "return(y);",containerElementCssSelector,pseudoElement,property);
	}
	//24: set a value to a range slider
	public void setValueToRangeSliderByJS(WebDriver driver,WebElement element,int value)	
	{
		((JavascriptExecutor) driver).executeScript("$(arguments[0]).val(arguments[1]).change();",
				                                                                   element,value);
	}
}
