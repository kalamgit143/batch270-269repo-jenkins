package tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//features parallel
@CucumberOptions(
        features = "src/test/resources/features/feature2.feature",
        glue = "gluecode",                        
        plugin = {
                    "pretty",
                    "html:target/parallel-feature2-cucumber-reports.html",
                    "json:target/parallel-feature2-cucumber-reports.json"
        }
)
public class Runner6ForFeature2 extends AbstractTestNGCucumberTests
{
}
