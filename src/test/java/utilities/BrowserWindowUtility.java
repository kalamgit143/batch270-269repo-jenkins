package utilities;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

//don't run, just use in other runner classes
public final class BrowserWindowUtility
{
	private BrowserWindowUtility() 
	{
	}
	
	public static void browserMaximize(WebDriver driver)
	{
		driver.manage().window().maximize();
	}
	public static void browserMinimize(WebDriver driver)
	{
		driver.manage().window().minimize();
	}
	public static void browserFullScreen(WebDriver driver)
	{
		driver.manage().window().fullscreen();
	}
	public static void browserRelocate(WebDriver driver, int x, int y)
	{
		driver.manage().window().setPosition(new Point(x,y));
	}
	public static void browserResize(WebDriver driver, int w, int h)
	{
		driver.manage().window().setSize(new Dimension(w,h));
	}
	public static void getBrowserNormalFromFullScreen(WebDriver driver) throws Exception
	{
		Robot r=new Robot();
		r.keyPress(KeyEvent.VK_F11);
		r.keyRelease(KeyEvent.VK_F11);
	}
	public static void browserRefresh(WebDriver driver)
	{
		driver.navigate().refresh();
	}
	public static void browserForward(WebDriver driver) 
	{
		driver.navigate().forward();
	}
	public static void browserBack(WebDriver driver) 
	{
		driver.navigate().back();
	}
	public static void getNewWindoworTab(WebDriver driver, String type)
	{
		if(type.equalsIgnoreCase("window"))
		{
			driver.switchTo().newWindow(WindowType.WINDOW);
		}
		else if(type.equalsIgnoreCase("tab"))
		{
			driver.switchTo().newWindow(WindowType.TAB);
		}
		else
		{
			throw new RuntimeException("Invalid type");
		}
		
	}
	public static void switchToDesiredBrowser(WebDriver driver, String handle)
	{
		driver.switchTo().window(handle);
	}
	public static List<String> getAllBrowserWindowHandlesList(WebDriver driver)
	{
		Set<String> s=driver.getWindowHandles();
		List<String> l=new ArrayList<String>(s);
		return(l);
	}
}
