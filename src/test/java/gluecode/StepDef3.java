package gluecode;

import org.openqa.selenium.By;
import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;

import static utilities.BrowserWindowUtility.*;
import static utilities.PropertiesFileUtility.*;
import static utilities.WebSiteUtility.*;

import java.util.List;
import java.util.Map;

public class StepDef3
{
	Shared sh;
	public StepDef3(Shared sh)
	{
		this.sh=sh;
	}
	@When("get runs done by {string} in {int} innings from JSON response body")
	public void get_runs_done_by_in_innings_from_json_response_body(String batsman, int innings) throws Exception
	{
		//Get data from API
		try
		{
			sh.apiruns=sh.res.jsonPath().getInt(
			 "scoreCard["+(innings-1)+"].batTeamDetails.batsmenData.find{it.value.batName=='"+batsman+"'}.value.runs");
			System.out.println("Runs in API is "+sh.apiruns);
		}
		catch(Exception e)
		{
			RuntimeException ex2=new RuntimeException("Invalid batsman name or innings number");
			throw(ex2);
		}
	}
	
	@Then("I compare the runs in the response body and in the scorecard page for equality")
	public void i_compare_the_runs_for_equality()
	{
		if(sh.uiruns==sh.apiruns)
		{
			System.out.println("Test case passed");
		}
		else
		{
			System.out.println("Test case failed");
			Assert.fail();
		}
	}
	
	@When("get runs scored from scorecard page related to")
	public void get_runs_scored_from_scorecard_page_related_to(String ds) throws Exception
	{
		//Divide DocString into required pieces of data
		String[] pieces=ds.split(",");
		String batsman=pieces[0];
		//System.out.println(batsman);
		String[] subpieces=pieces[1].trim().split(" ");
		int innings=Integer.parseInt(subpieces[0]);
		//System.out.println(innings);
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
			RuntimeException ex1=new RuntimeException("Invalid batsman name");
			throw(ex1);
		}
	}
	
	@When("I launch cricbuz site and check equality of total runs with corresponding API given runs")
	public void i_launch_cricbuz_site_and_connect_to_corresponding_api(DataTable dt) throws Exception
	{
		// divide DataTable into required pieces of data
		// asList() or asLists() or asMaps() are available methods
		// Use asLists() method when there is no column headings in data table
		// Use asMaps() method when there are column headings in data table
		// Do not prefer asList() method 
		// Here, no column headings in DataTable. So use asLists() method
		List<List<String>> testdata=dt.asLists();
		//Data driven
		for(int i=0;i<testdata.size();i++)
		{
			String matchid=testdata.get(i).get(0);
			String series=testdata.get(i).get(1);
			int innings=Integer.parseInt(testdata.get(i).get(2));
			//Get data from UI
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
			//4. Get total runs from scorecard page
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
			//1. update basepath in properties file with given matchid
			pnames=new String[]{"basepath"};
			pvalues=new String[]{"mcenter/v1/"+matchid+"/hscard"};
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
			//4. Get total runs from API JSON response
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
			// Compare UI and API given total runs
			if(sh.uiscore==sh.apiscore)
			{
				System.out.println("Test case passed");
			}
			else
			{
				System.out.println("Test case failed");
				Assert.fail();
			}
		}
	}
	
	@When("I launch cricbuz site and connect to corresponding API to check equality of total runs")
	public void i_launch_cricbuz_site_and_connect_to_corresponding_api2(DataTable dt) throws Exception
	{
		// divide DataTable into required pieces of data
		// asList() or asLists() or asMaps() are available methods
		// Use asLists() method when there is no column headings in data table
		// Use asMaps() method when there are column headings in data table
		// Do not prefer asList() method 
		// Here, column headings are in DataTable. So use asMaps() method
		// If we want to use asLists() method, we have to start looping from 1
		List<Map<String, String>> testdata=dt.asMaps();
		//Data driven
		for(int i=0;i<testdata.size();i++)
		{
			String matchid=testdata.get(i).get("matchid");
			String series=testdata.get(i).get("matchdetails");
			int innings=Integer.parseInt(testdata.get(i).get("innings_number"));
			//Get data from UI
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
			//4. Get total runs from scorecard page
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
			//1. update basepath in properties file with given matchid
			pnames=new String[]{"basepath"};
			pvalues=new String[]{"mcenter/v1/"+matchid+"/hscard"};
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
			//4. Get total runs from API JSON response
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
			// Compare UI and API given total runs
			if(sh.uiscore==sh.apiscore)
			{
				System.out.println("Test case passed");
			}
			else
			{
				System.out.println("Test case failed");
				Assert.fail();
			}
		}
	}
}