package tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//Smoke testing(Run few basic positive scenarios to check stability of AUT)
//Use "tags" in this class to run only "@smoke" tagged scenarios in all features
@CucumberOptions(
		features={"src/test/resources/features"},
		tags="@smoke",
		glue={"gluecode"},
		monochrome=true,
		plugin={"pretty","html:target/smoketestreport.html",
				"json:target/smoketestreport.json",
				"junit:target/smoketestreport.xml",
				"rerun:target/failedSmokeTestScenarios.txt"}
		) 
public class Runner1 extends AbstractTestNGCucumberTests
{ 
	// This class will be empty  
} 
