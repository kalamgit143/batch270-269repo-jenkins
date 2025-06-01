package utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class CookiesUtility 
{
	public int getCookiesCount(WebDriver driver)
	{
		int x=driver.manage().getCookies().size();
		return(x);
	}
	
	public List<Cookie> getCookiesList(WebDriver driver)
	{
		Set<Cookie> cs=driver.manage().getCookies();
		List<Cookie> cl=new ArrayList<Cookie>(cs);
		return(cl);
	}
	
	public List<String> getCookiesDetailsList(WebDriver driver)
	{
		Set<Cookie> cs=driver.manage().getCookies();
		List<Cookie> cl=new ArrayList<Cookie>(cs);
		List<String> cdetails=new ArrayList<String>();
		for(Cookie c:cl)
		{
			cdetails.add("Name="+c.getName()+", Value="+c.getValue()+", Domain="+c.getDomain()+", and expiry="+c.getExpiry());
		}
		return(cdetails);
	}
	
	//use null for "superdomain" argument when no super domain does not exist
	public List<String> getCookiesTypes(
			WebDriver driver, String samedomain, String superdomain)
	{
		Set<Cookie> cs=driver.manage().getCookies();
		List<Cookie> cl=new ArrayList<Cookie>(cs);
		List<String> ctypes=new ArrayList<String>();
		for(Cookie c:cl) //to work with each cookie
		{
			String res=c.getName()+" is";
			//TestCase-1
			if(c.isHttpOnly())
			{
				res=res+" HTTP-only,";
			}
			//TestCase-2
			if(c.isSecure())
			{
				res=res+" Secured,";
			}
			//TestCase-3
			if(c.getExpiry()!=null)
			{
				res=res+" Persistent,";
			}
			else
			{
				res=res+" Session,";
			}
			//TestCase-4
			if(c.getDomain().contains(samedomain))
			{
				res=res+" Same-site Cookie";
			}
			else if(superdomain!=null && c.getDomain().contains(superdomain)) 
			{
				res=res+" Super Cookie";
			}
			else
			{
				res=res+" third-party Cookie and it came from "+c.getDomain();
			}
			ctypes.add(res);
		}
		return(ctypes);
	}
	
	public void addNewCookie(WebDriver driver, String name, String value, String domain, 
		   String path, Date expiry, boolean isSecure, boolean isHttpOnly, String sameSite)
	{
		Cookie c=new Cookie(name, value, domain, path, expiry, isSecure, isHttpOnly, sameSite);
        driver.manage().addCookie(c);
	}
	
	public void addNewCookieViaBuilder(WebDriver driver, String name, String value, 
	   String domain,String path,Date expiry,boolean isSecure,boolean isHttpOnly,String sameSite)
	{
		Cookie.Builder b=new Cookie.Builder(name,value); 
	    Cookie c=b.domain(domain).path(path).expiresOn(expiry).isSecure(isSecure)
	    		.isHttpOnly(isHttpOnly).sameSite(sameSite).build();
		driver.manage().addCookie(c);
	}

	public String getCookieByName(WebDriver driver, String name)
	{
		Cookie c=driver.manage().getCookieNamed(name);
		return(c.getName()+"-"+c.getValue()+"-"+c.getDomain()+"-"+c.getPath()+"-"+c.getExpiry());
	}
	
	public void deleteCookieByIndex(WebDriver driver, int index)
	{
		Set<Cookie> s=driver.manage().getCookies();
		List<Cookie> l=new ArrayList<Cookie>(s);
		driver.manage().deleteCookie(l.get(index)); 
	}
	
	public void deleteCookieByName(WebDriver driver, String name)
	{
		driver.manage().deleteCookieNamed(name);
	}
	
	public void deleteCookies(WebDriver driver)
	{
		driver.manage().deleteAllCookies();
	}
}
