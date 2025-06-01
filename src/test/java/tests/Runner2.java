package tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//Real testing or complete testing or comprehensive testing(Run all features one after other)
//Don't use "tags" in this class to run all scenarios in all features
@CucumberOptions(
		features={"src/test/resources/features"},
		glue={"gluecode"},
		//no "tags" option will run all scenarios in all features
		monochrome=true,
		plugin={"pretty","html:target/realtestreport.html",
				"rerun:target/failedRealTestScenarios.txt"}
		) 
public class Runner2 extends AbstractTestNGCucumberTests
{ 
	// This class will be empty  
} 
