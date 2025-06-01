package tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//Re-testing(Run previously failed scenarios only on modified software)
//Here, failed scenarios info is available in a text file
@CucumberOptions(
		features={"@target/failedRealTestScenarios.txt"}, //Use "@" symbol in front of text file path
		glue={"gluecode"},
		monochrome=true,
		plugin={"pretty","html:target/retestreport.html",
				"rerun:target/failedReTestScenarios.txt"}
		) 
public class Runner3 extends AbstractTestNGCucumberTests
{
}
