package tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//features parallel
@CucumberOptions(
        features = "src/test/resources/features/feature3.feature",
        glue = "gluecode",                        
        plugin = {
                    "pretty",
                    "html:target/parallel-feature3-cucumber-reports.html",
                    "json:target/parallel-feature3-cucumber-reports.json"
        }
)
public class Runner6ForFeature3 extends AbstractTestNGCucumberTests
{
}
