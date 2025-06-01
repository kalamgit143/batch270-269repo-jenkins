package gluecode;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;

import static utilities.ExcelFileUtility.*;
import static utilities.TextFileUtility.*;
import static utilities.BrowserWindowUtility.*;
import static utilities.PropertiesFileUtility.*;
import static utilities.WebSiteUtility.*;

public class StepDef4
{
	Shared sh;
	public StepDef4(Shared sh)
	{
		this.sh=sh;
	}
	@Given("I open {string} browser")
	public void i_open_browser(String bn) throws Exception
	{
		String temp1=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
				"maxwait");
		int mw=Integer.parseInt(temp1);
		String temp2=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
				"interval");
		int it=Integer.parseInt(temp2);
		sh.driver = openBrowser(bn);
		sh.wait = defineExplicitWait(sh.driver, mw, it);
	}
	
	@When("I launch cricbuz site and connect to API to check equality of runs scored by a batsman using data in a CSV or Text file")
	public void i_launch_cricbuz_site_and_connect_to_api() throws Exception
	{
		//Data driven
		//1. open CSV or txt file and take data line by line
		int nol=getCountOfLines("src\\test\\resources\\csvfiles\\Matchesdata.txt");
		for(int ln=2;ln<=nol;ln++) //1st line is column headings, so start from 2nd line
		{
			//1. take a line of data from CSV file and split that line into words by comma separator
			String[] data=getValuesOfLine(
					"src\\test\\resources\\csvfiles\\Matchesdata.txt", ln);
			String matchid=data[0];
			String series=data[1];
			String innings_number=data[2];
			String batsman_name=data[3];
			//2. update properties file environment data related to UI with test cases data
			String[] pnames=new String[]{"qaurl"};
			String[] pvalues=
					new String[]{"https://www.cricbuzz.com/live-cricket-scorecard"+"/"+matchid+"/"+series};
			updatePropertiesFile("src/test/resources/propertiesfile/config.properties", pnames, pvalues);
			//3. take updated url from properties file
			String url=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
					"qaurl");
			//4. launch site
			launchSite(sh.driver, url);
			browserMaximize(sh.driver);
			//5. Get data from UI
			try
			{
				//Use xpath expression to get runs in UI(HTML page)
				String temp1=sh.driver.findElement(By.xpath(
				  "(//div[@id='innings_"+innings_number+"']/descendant::a[contains(text(),'"+batsman_name+"')])[1]/following::div[2]"))
					.getText().trim();
				sh.uiruns=Integer.parseInt(temp1);
				System.out.println("Runs in UI is "+sh.uiruns);
			}
			catch(Exception e)
			{
				RuntimeException ex1=new RuntimeException("Invalid batsman name");
				throw(ex1);
			}
			//6. update properties file environment data related to API with test cases data
			pnames=new String[]{"basepath"};
			pvalues=new String[]{"mcenter/v1/"+matchid+"/hscard"};
			updatePropertiesFile("src/test/resources/propertiesfile/config.properties", pnames, pvalues);
			//7. take updated baseuri and basepath from properties file
			String baseuri=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
					"baseuri");
			String basepath=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
					"basepath");
			String apikey=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
					"X-RapidAPI-Key");
			String host=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
					"X-RapidAPI-Host");
			//8. connect to API
			sh.req=RestAssured.given();
			sh.req.baseUri(baseuri);
			sh.req.basePath(basepath);
			sh.req.header("X-RapidAPI-Key",apikey);
			sh.req.header("X-RapidAPI-Host",host);
			sh.res=sh.req.get();
			//9. Get data from API
			try
			{
				//Use GPath expression to get batsman runs from JSON response
				int temp=Integer.parseInt(innings_number);
				sh.apiruns=sh.res.jsonPath().getInt(
				 "scoreCard["+(temp-1)+"].batTeamDetails.batsmenData.find{it.value.batName=='"+batsman_name+"'}.value.runs");
				System.out.println("Runs in API is "+sh.apiruns);
			}
			catch(Exception e)
			{
				RuntimeException ex2=new RuntimeException("Invalid batsman name or innings number");
				throw(ex2);
			}
			//8. Compare UI and API given total runs
			if(sh.uiruns==sh.apiruns)
			{
				System.out.println("Test case passed");
			}
			else
			{
				System.out.println("Test case failed");
				Assert.fail();
			}
		} //for loop ending
	}
	
	@When("I launch cricbuz site in {string} and corresponding API to check equality of total runs by using data in a XLSX file")
	public void when_i_launch_cricbuz_site_and_corresponding_api(String bn) throws Exception
	{
		//Data driven
		//1. open excel file 
		SoftAssert sa=new SoftAssert();
		Workbook wb=openExcelFile("src\\test\\resources\\excelfiles\\MatchDetails.xlsx");
		Sheet sht=openSheet(wb, "Sheet1");
		//2. Create result column to store results of test cases and get test cases data 
		int nour=getRowsCount(sht);
		int cellno=getCellscount(sht,0);
		createResultColumn(sht, cellno);
		for(int i=1;i<nour;i++) //1st row(index=0) is column headings, so start from 2nd row(index=1)
		{
			String matchid=getCellValue(sht, i, 0);
			String series=getCellValue(sht, i, 1);
			String innings_number=getCellValue(sht, i, 2);
			String batsman_name=getCellValue(sht, i, 3);
			//3. update properties file with data from excel file
			String[] pnames=new String[]{"qaurl"};
			String[] pvalues=
					new String[]{"https://www.cricbuzz.com/live-cricket-scorecard"+"/"+matchid+"/"+series};
			updatePropertiesFile("src/test/resources/propertiesfile/config.properties", pnames, pvalues);
			//4. take updated url from properties file
			String url=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
					"qaurl");
			//5. launch site
			launchSite(sh.driver, url);
			browserMaximize(sh.driver);
			Thread.sleep(5000);
			//6. Get data from UI
			try
			{
				String temp1=sh.driver.findElement(By.xpath(
				  "(//div[@id='innings_"+innings_number+"']/descendant::a[contains(text(),'"+batsman_name+"')])[1]/following::div[2]"))
					.getText().trim();
				sh.uiruns=Integer.parseInt(temp1);
				System.out.println("Runs in UI is "+sh.uiruns);
			}
			catch(Exception e)
			{
				RuntimeException ex1=new RuntimeException("Invalid batsman name");
				throw(ex1);
			}
			//7. Connect to API
			pnames=new String[]{"basepath"};
			pvalues=new String[]{"mcenter/v1/"+matchid+"/hscard"};
			updatePropertiesFile("src/test/resources/propertiesfile/config.properties", pnames, pvalues);
			String baseuri=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
					"baseuri");
			String basepath=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
					"basepath");
			String apikey=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
					"X-RapidAPI-Key");
			String host=getValueFromPropertiesFile("src/test/resources/propertiesfile/config.properties",
					"X-RapidAPI-Host");
			sh.req=RestAssured.given();
			sh.req.baseUri(baseuri);
			sh.req.basePath(basepath);
			sh.req.header("X-RapidAPI-Key",apikey);
			sh.req.header("X-RapidAPI-Host",host);
			sh.res=sh.req.get();
			//8. Get data from API
			try
			{
				int temp=Integer.parseInt(innings_number);
				sh.apiruns=sh.res.jsonPath().getInt(
				 "scoreCard["+(temp-1)+"].batTeamDetails.batsmenData.find{it.value.batName=='"+batsman_name+"'}.value.runs");
				System.out.println("Runs in API is "+sh.apiruns);
			}
			catch(Exception e)
			{
				RuntimeException ex2=new RuntimeException("Invalid batsman name or innings number");
				throw(ex2);
			}
			//9. Compare UI and API given total runs
			if(sh.uiruns==sh.apiruns)
			{
				setCellValue(sht, i, cellno, "Test case passed in "+bn, 15, "Arial Black", true, true, 
						IndexedColors.GREEN.getIndex(), IndexedColors.BLUE.getIndex(), "CENTER");
			}
			else
			{
				setCellValue(sht, i, cellno, "Test case failed in "+bn, 15, "Arial Black", true, true, 
						IndexedColors.RED.getIndex(), IndexedColors.BLUE.getIndex(), "center");
				sa.assertTrue(false);
			}
		} //for loop ending
		saveAndCloseExcel(wb,"src\\test\\resources\\excelfiles\\MatchDetails.xlsx");
		sa.assertAll();
	}
}