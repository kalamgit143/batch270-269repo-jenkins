package gluecode;

import org.openqa.selenium.By;
import org.testng.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;

import static utilities.PropertiesFileUtility.*;
import static utilities.WebSiteUtility.*;

public class StepDef2
{	
	Shared sh;
	public StepDef2(Shared sh)
	{
		this.sh=sh;
	}
	@When("I connect to cricbuzz API with {long} matchid")
	public void i_connect_to_cricbuzz_api_with_matchid(long matchid) throws Exception
	{
		//1. update basepath in properties file with given matchid
		String[] pnames=new String[]{"basepath"};
		String[] pvalues=
			new String[]{"mcenter/v1/"+matchid+"/hscard"};
		updatePropertiesFile("src/test/resources/propertiesfile/config.properties", pnames, pvalues);
		//2. take updated baseuri and basepath from properties file
		String baseuri=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
				"baseuri");
		String basepath=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
				"basepath");
		String apikey=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
				"X-RapidAPI-Key");
		String host=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
				"X-RapidAPI-Host");
		//3. connect to API
		sh.req=RestAssured.given();
		sh.req.baseUri(baseuri);
		sh.req.basePath(basepath);
		sh.req.header("X-RapidAPI-Key",apikey);
		sh.req.header("X-RapidAPI-Host",host);
		sh.res=sh.req.get();
		//System.out.println(res.asPrettyString());
	}
	
	@Then("I compare the total of {int} innings in the response body and in the scorecard page for equality")
	public void i_compare_the_total_for_equality(int innings) throws Exception
	{
		//Get data from UI
		try
		{
			String temp1=sh.driver.findElement(
				By.xpath("(//div[text()='Total'])["+innings+"]/following-sibling::div[1]"))
				.getText().trim();
			sh.uiscore=Integer.parseInt(temp1);
			System.out.println(sh.uiscore);
		}
		catch(Exception e)
		{
			RuntimeException ex1=new RuntimeException("Invalid innings number");
			throw(ex1);
		}
		//Get data from API
		try
		{
			sh.apiscore=sh.res.jsonPath().getInt("scoreCard["+(innings-1)+"].scoreDetails.runs");
			System.out.println(sh.apiscore);
		}
		catch(Exception e)
		{
			RuntimeException ex2=new RuntimeException("Invalid innings number");
			throw(ex2);
		}
		if(sh.uiscore==sh.apiscore)
		{
			System.out.println("Test case passed");
		}
		else
		{
			System.out.println("Test case failed");
			Assert.fail(); //Hard assertion
		}
	}
	
	@Then("I close browser")
	public void i_close_browser()
	{
		closeSite(sh.driver);
	}
	
	@When("get runs done by {string} in {int} innings from scorecard page")
	public void get_runs_done_by_in_innings_from_scorecard_page(String batsman, int innings) throws Exception
	{
		//Get data from UI
		try
		{
			String temp1=sh.driver.findElement(By.xpath(
			  "(//div[@id='innings_"+innings+"']/descendant::a[contains(text(),'"+batsman+"')])[1]/following::div[2]"))
				.getText().trim();
			sh.uiruns=Integer.parseInt(temp1);
			System.out.println("Runs in UI is "+sh.uiruns);
		}
		catch(Exception e)
		{
			RuntimeException ex1=new RuntimeException("Invalid batsman name or innings number");
			throw(ex1);
		}
	}
}