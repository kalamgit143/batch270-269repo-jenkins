package tests;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//Scenarios parallel
@CucumberOptions(
        features = "src/test/resources/features",  
        glue = "gluecode",                        
        plugin = {
                    "pretty",
                    "html:target/parallel-cucumber-reports.html",
                    "json:target/parallel-cucumber-reports.json"
        }
)
public class Runner5 extends AbstractTestNGCucumberTests
{
      @DataProvider(parallel=true)  //Enable parallel execution
      public Object[][] scenarios() //Override this scenarios() method of parent in child(Runner5)
      {
           return(super.scenarios());  // Cucumber will pick up all scenarios from all feature files
      }
}
