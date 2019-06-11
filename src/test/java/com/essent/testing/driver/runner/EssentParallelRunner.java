package com.essent.testing.driver.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


/**
 * @author Peter This class is the test runner class. JUnit will pickup this
 *         class
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = { "src/test/resources" },
    glue = { "stepdefinitions"},
    junit = "--step-notifications",
    plugin = { "pretty",
        "html:target/cucumber-html-report",
        "junit:target/cucumber-junit-report/allcukes.xml",
        "json:target/cucumber.json"}
)
public class EssentParallelRunner
{

}
