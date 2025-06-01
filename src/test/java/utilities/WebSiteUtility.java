package utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.FluentWait;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

//don't run these utility classes, just use in other runner classes(tests)
public final class WebSiteUtility 
{
	private WebSiteUtility()
	{	
	}
    
	public static RemoteWebDriver openBrowser(String browsername)
    {
    	 RemoteWebDriver driver;
         if(browsername.equalsIgnoreCase("chrome"))
         {
              driver=new ChromeDriver(); 
         }
         else if(browsername.equalsIgnoreCase("firefox"))
         {
             driver=new FirefoxDriver();
         }
         else if(browsername.equalsIgnoreCase("edge"))
         {
             driver=new EdgeDriver();
         }
         else if(browsername.equalsIgnoreCase("safari"))
         {
             driver=new SafariDriver();
         }
         else
         {
             RuntimeException e=new 
                            RuntimeException("Please check the given browser name");
             throw(e); //to raise our own exception to stop execution
         }
         return(driver);
    }
    public static FluentWait<RemoteWebDriver> defineExplicitWait(RemoteWebDriver driver,
    		                                               int timeoutsec,
    		                                               int intervalmillis)
    {
    	FluentWait<RemoteWebDriver> wait=new FluentWait<RemoteWebDriver>(driver);
    	wait.withTimeout(Duration.ofSeconds(timeoutsec)); //Maximum waiting time
    	wait.pollingEvery(Duration.ofMillis(intervalmillis)); //Interval time to check wait condition
		return(wait);
    }
    public static void launchSite(RemoteWebDriver driver, String url)
    {
        driver.get(url);
    } 
    public static void closeSite(RemoteWebDriver driver)
    {
    	driver.quit();
    }
    public static By getByFromWebElement(WebElement e)
	{
	    //convert "WebElement" to "By"
	    String p[]=e.toString().split("->");
	    String q[]=p[1].split(":");
	    String locatortype=q[0].trim();
	    String locatorvalue=q[1].substring(0,q[1].length()-1).trim();
	    By b=null;
	    if(locatortype.equals("name"))
	    {
	    	b=By.name(locatorvalue);
	    }
	    else if(locatortype.equals("id"))
	    {
	        b=By.id(locatorvalue);
	    } 
	    else if(locatortype.equalsIgnoreCase("class name"))
	    {
	    	b=By.className(locatorvalue);
	    } 
	    else if(locatortype.equalsIgnoreCase("tag name"))
	    {
	        b=By.tagName(locatorvalue);
	    } 
	    else if(locatortype.equalsIgnoreCase("link text"))
	    {
	        b=By.linkText(locatorvalue);
	    } 
	    else if(locatortype.equalsIgnoreCase("partial link text"))
	    {
	        b=By.partialLinkText(locatorvalue);
	    } 
	    else if(locatortype.equals("xpath"))
	    {
	        b=By.xpath(locatorvalue);
	    } 
		else
		{
	        b=By.cssSelector(locatorvalue);
		}
		return(b);
	}
    public static String capturePageScreenshotAsFile(RemoteWebDriver driver) throws Exception
	{
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss-SSS");
		Date dt=new Date();
		String fn=System.getProperty("user.dir")+"\\target\\"+sf.format(dt)+".png";
		File dest=new File(fn); //create a new file in HDD
		File src=((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src,dest);
		return(dest.getAbsolutePath());
	}
    public static String captureElementScreenshotAsFile(WebElement e) throws Exception
	{
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss-SSS");
		Date dt=new Date();
		String fn=System.getProperty("user.dir")+"\\target\\"+sf.format(dt)+".png";
		File dest=new File(fn); //create a new file in HDD
		File src=e.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src,dest);
		return(dest.getAbsolutePath());
	}
    public static String captureFullPageScreenshotAsFile(RemoteWebDriver driver) throws Exception
    {
          SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss-SSS");
          Date dt=new Date();
          File dest=new File("target/"+sf.format(dt)+".png"); 
          AShot as=new AShot();
          ShootingStrategy shs=ShootingStrategies.viewportPasting(1000); //1 second delay
          Screenshot ss=as.shootingStrategy(shs).takeScreenshot(driver);
          ImageIO.write(ss.getImage(),"PNG",dest); 
          return(dest.getAbsolutePath());
    }
}
