package gluecode;

import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.Reporter;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static utilities.BrowserWindowUtility.*;
import static utilities.PropertiesFileUtility.*;
import static utilities.WebSiteUtility.*;

public class StepDef1
{
	Shared sh;
	public StepDef1(Shared sh)//PicoContainer creates and injects the "Shared" class object
	{
		this.sh=sh;
	}
	@Given("I open browser")
	public void i_open_browser() throws Exception
	{
		String bn=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
				                             "browser");
		String temp1=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
                "maxwait");
		int mw=Integer.parseInt(temp1);
		String temp2=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
                "interval");
		int it=Integer.parseInt(temp2);
		sh.driver = openBrowser(bn);
		sh.wait = defineExplicitWait(sh.driver, mw, it);
	}
	
	@When("I launch cricbuz site for {long} and {string}")
	public void i_launch_cricbuz_site_for_and(long matchid, String series) throws Exception
	{
		//1. update qaurl in properties file with given matchid and series
		String[] pnames=new String[]{"qaurl"};
		String[] pvalues=
			new String[]{"https://www.cricbuzz.com/live-cricket-scorecard"+"/"+matchid+"/"+series};
		updatePropertiesFile("src/test/resources/propertiesfile/config.properties", pnames, pvalues);
		//2. take updated url from properties file
		String url=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
				"qaurl");
		//3. launch site
		launchSite(sh.driver, url);
		browserMaximize(sh.driver);
	}
	
	@Then("I should see {string} in the title of scorecard page")
	public void i_should_see_in_the_title_of_scorecard_page(String expectedtitle) throws Exception
	{
		String actualtitle=sh.driver.getTitle();
		if(actualtitle.contains(expectedtitle))
		{
			sh.se.log("Test case passed");
			Reporter.log("Test case passed");
		}
		else
		{
			//For Cucumber reports
			sh.se.log("Test case failed");			
			byte[] b=sh.driver.getScreenshotAs(OutputType.BYTES);
			sh.se.attach(b, "image/png", "Screenshot of wrong title");
			//For TestNG reports
			Reporter.log("Test case failed");
			String ssfpath=captureFullPageScreenshotAsFile(sh.driver);
			Reporter.log("Please see attached screenshot:");
			Reporter.log(
			"<a href=\""+ssfpath+"\"><img src=\""+ssfpath+"\" height=\"100\" width=\"100\"/></a>");
			//Fail test(Hard Assertion)
			Assert.fail();
		}
	}
}