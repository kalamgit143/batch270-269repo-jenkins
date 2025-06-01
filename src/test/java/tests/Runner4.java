package tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//Regression testing
//add "@regression" tag to required scenarios or scenario outlines in feature files manually, 
//which are passed previously but related to failed scenarios. So need to run again.

@CucumberOptions(
		features={"src\\test\\resources\\features"},
		glue={"gluecode"},
		tags="@regression",
		monochrome=true,
		plugin={"pretty","html:target/regressiontestreport.html",
				         "rerun:target/failedRegressionTestScenarios.txt"}
		)
public class Runner4 extends AbstractTestNGCucumberTests
{
}











