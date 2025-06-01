package utilities;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class ActionsUtility
{
	private Actions act;
	private FluentWait<WebDriver> wait;
	//Constructor
	public ActionsUtility(WebDriver driver, FluentWait<WebDriver> wait)
	{
		act=new Actions(driver);
		this.wait=wait;
	}
	//Operational methods
	public void clickOnElementCenter(WebElement e)
	{
		wait.until(ExpectedConditions.elementToBeClickable(e));
		act.click(e).perform(); //similar to click() in WebElement interface
	}
	
	public void rightClickOnElementCenter(WebElement e)
	{
		wait.until(ExpectedConditions.elementToBeClickable(e));
		act.contextClick(e).perform();
	}
	
	public void doubleClickOnElementCenter(WebElement e)
	{
		wait.until(ExpectedConditions.elementToBeClickable(e));
		act.doubleClick(e).perform();
	}
	
	public void clickOnElementAtLocation(WebElement e, int xoffset, int yoffset)
	{
		wait.until(ExpectedConditions.elementToBeClickable(e));
		act.moveToElement(e).pause(Duration.ofSeconds(3)).moveByOffset(xoffset, yoffset)
		                          .pause(Duration.ofSeconds(3)).click().build().perform();
	}
	
	public void clickOnPageAtLocation(int xCoord, int yCoord)
	{
		act.moveByOffset(xCoord, yCoord).pause(Duration.ofSeconds(3)).click().build().perform();
		//move from 0,0 to x,y
	}
	
	public void fillElement(WebElement e, String data)
	{
		wait.until(ExpectedConditions.elementToBeClickable(e));
		act.sendKeys(e,data).perform(); //similar to sendKeys() in WebElement interface
	}
	
	public void mouseClickAndHoldOnElement(WebElement e)
	{
		act.clickAndHold(e).pause(500).perform();
	}
	
	public void mouseReleaseOnElement(WebElement e)
	{
		act.release(e).perform();
	}
	
	public void keyBoardKeyStrokeOnElement(WebElement e, CharSequence k)
	{
		act.keyUp(k).pause(500).perform();
		act.keyDown(k).perform();
	}
	
	public void scrollFullPageDown()
	{
		act.sendKeys(Keys.END).perform();
	}
	
	public void scrollFullPageUp()
	{
		act.sendKeys(Keys.HOME).perform();
	}
	
	public void scrollToPageLocation(int xCoord, int yCoord)
	{
		act.scrollByAmount(xCoord, yCoord).perform();
	}
	
	public void scrollToGetElementVisible(WebElement e)
	{
		act.scrollToElement(e).perform(); //get into bottom of the viewport
	}
	
	public void scrollFromElement(WebElement e, int xoffset, int yoffset)
	{
		WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(e);
		act.scrollFromOrigin(scrollOrigin, xoffset, yoffset).perform();
	}
	
	public void scrollRightInElement(WebDriver driver, WebElement e)
	{
		//get element into top of the viewport
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",e);
		for(int i=1;i<=10;i++)
		{
			act.sendKeys(e,Keys.ARROW_RIGHT).perform();
		}
	}
	
	public void scrollLeftInElement(WebDriver driver, WebElement e)
	{
		//get element into top of the viewport
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",e);
		for(int i=1;i<=10;i++)
		{
			act.sendKeys(e,Keys.ARROW_LEFT).perform();
		}
	}
	
	public void scrollDownInElement(WebDriver driver, WebElement e)
	{
		//get element into top of the viewport
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",e);
		act.sendKeys(e,Keys.END).perform();
	}
	
	public void scrollUpInElement(WebDriver driver, WebElement e)
	{
		//get element into top of the viewport
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",e);
		act.sendKeys(e,Keys.HOME).perform();
	}
		
	public void scrollToElementInElement(WebDriver driver, WebElement outer, WebElement inner)
	{
		//get element into top of the viewport
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",outer);
		act.moveToElement(outer).pause(Duration.ofSeconds(3)).moveToElement(inner).perform();
	}
	
	public void focusOnElementCenter(WebElement e)
	{
		wait.until(ExpectedConditions.elementToBeClickable(e));
		act.moveToElement(e).perform();
	}
	
	public void focusOnElementLocation(WebElement e, int xoffset, int yoffset)
	{
		wait.until(ExpectedConditions.elementToBeClickable(e));
		act.moveToElement(e).moveByOffset(xoffset, yoffset).build().perform();
	}
	
	public void dragDrop(WebDriver driver, WebElement e1, WebElement e2)
	{
		wait.until(ExpectedConditions.visibilityOf(e1));
		wait.until(ExpectedConditions.visibilityOf(e2));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",e1);
		act.dragAndDrop(e1,e2).perform();
	}
	
	public void moveSlider(WebDriver driver, WebElement e, int xoffset, int yoffset)
	{
		wait.until(ExpectedConditions.visibilityOf(e));
		act.moveToElement(e).pause(500).dragAndDropBy(e,xoffset,yoffset).perform(); 
	}
	
	public String getStaticToolTipText(WebElement e)
	{
		//tool-tip is value of "title" attribute of an element
		wait.until(ExpectedConditions.visibilityOf(e));
		String temp=e.getAttribute("title");
		return(temp);
	}
	
	public String getDynamicToolTipText(WebElement e)
	{
		//tool-tip is child span of target element
		wait.until(ExpectedConditions.visibilityOf(e));
		act.moveToElement(e).pause(5000).clickAndHold(e).pause(5000).build().perform();
		WebElement tt=e.findElement(By.xpath("child::span"));
		String temp=tt.getText();
		act.release(e).perform();
		return(temp);
	}
}
