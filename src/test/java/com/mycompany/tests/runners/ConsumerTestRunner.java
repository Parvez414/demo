package com.mycompany.tests.runners;

import com.automation.config.ConfigReader;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.mycompany.tests.stepdefinitions", "com.automation.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty.html"
        },
        monochrome = true
)
public class ConsumerTestRunner extends AbstractTestNGCucumberTests {

    @BeforeTest(alwaysRun = true)
    public void setupSuite(ITestContext context) {
        int threads = ConfigReader.getInt("thread.count", 2);
        context.getSuite().getXmlSuite().setDataProviderThreadCount(threads);
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
