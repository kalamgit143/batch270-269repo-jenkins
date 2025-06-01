package gluecode;

import java.util.Collection;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.asserts.SoftAssert;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Shared
{
	//declare common variables and objects required in all StepDef classes
	public RemoteWebDriver driver; //to contact web pages in browser
	public FluentWait<RemoteWebDriver> wait; //to define explicit wait
	public RequestSpecification req; //to define request in RestAssured-Java
	public Response res; //to define response in RestAssured-Java
	public int apiruns;
	public int apiscore;
	public int uiruns;
	public int uiscore;
	public String pfpath="src\\test\\resources\\propertiesfile\\config.properties";
	public Scenario se; //to log results into cucumber reports(.html/.json/.xml/.txt)
	public int stepno;
	public static int scenariono=1;	
	
	@Before //before every scenario by taking that scenario information
	public void method1(Scenario se) //picocontainer creates and supplies scenario object
	{
		this.se=se;
		if(se.getName()!="")
		{
			se.log("Scenario:"+scenariono+"-"+se.getName()+" is started");
			if(se.getSourceTagNames().size()!=0)
			{
				Collection<String> tags=se.getSourceTagNames();
				se.log("Tags are:");
				for(String tag:tags)
				{
					se.log("Scenario:"+scenariono+"-"+se.getName()+" is started with tag:"+tag);
				}
			}
		}
		else
		{
			se.log("Scenario:"+scenariono+" is started");
		}
		stepno=1; //set to 1 before going into steps of each scenario
	}
	
	@BeforeStep("@smoketest and not @regressiontest")
	public void method2(Scenario s) //before every step
	{
		se.log("Step-"+stepno+" is started");
	}
	
	@AfterStep("@smoketest")
	public void method3(Scenario s) //after every step
	{
		se.log("Step-"+stepno+" is completed");
		stepno++;
	}
	
	@After
	public void method4(Scenario s) //after every scenario
	{
		if(se.getName()!="")
		{
			se.log(s.getName()+" is completed with status:"+s.getStatus().name());
		}
		else
		{
			se.log("Scenario:"+scenariono+" is completed with status:"+s.getStatus().name());
		}
		scenariono++; //for next scenario
	}
}
