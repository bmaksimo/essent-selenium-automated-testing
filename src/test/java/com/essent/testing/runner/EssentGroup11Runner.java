package com.essent.testing.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = { "src/test/resources/features/groups/11" },
    glue = { "stepdefinitions"},
    junit = "--step-notifications",
    plugin = { "pretty",
        "html:target/cucumber-html-report",
        "junit:target/cucumber-junit-report/allcukes.xml",
        "json:target/cucumber-reports/11.json"
    }
)
public class EssentGroup11Runner {}
