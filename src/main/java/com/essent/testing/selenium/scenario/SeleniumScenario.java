package com.essent.testing.selenium.scenario;


import com.essent.testing.scenario.RegisteredScenario;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.api.Scenario;
import org.junit.AfterClass;

import java.util.Map;


public class SeleniumScenario extends RegisteredScenario {

    static protected SeleniumDriver webDriver;

    public void tidyUp(Scenario scenario) throws Exception {
        if(webDriver != null) {
            webDriver.tearDown(scenario);
            webDriver = null;
        }
    }

    public void setUpWebDriver(Scenario scenario) throws Exception {
        if (webDriver == null) {
            webDriver = new SeleniumDriver();
            webDriver.setUp();
        } else {
            tidyUp(scenario);
            webDriver = new SeleniumDriver();
            webDriver.setUp();
        }
        webDriver.getDriver().manage().window().maximize();
    }


    protected void injectJavaScriptTestRunner() {
        webDriver.injectJavaScriptTestRunner();
    }

    protected boolean executeJavascriptTest(String registeredJsClass, Object options)  {
        return webDriver.executeJavascriptTest(registeredJsClass, options);
    }

    public static boolean executeJavascriptTest(String registeredJsClass, Object options, boolean withException) {
        return webDriver.executeJavascriptTest(registeredJsClass, options, withException);
    }

    protected Map executeJavascriptMethod(String registeredJsClass, Object options)  {
        Map map = webDriver.executeJavascriptMethod(registeredJsClass, options);
        return map;
    }

    protected void takeScreenshot(boolean success)  {
        webDriver.takeScreenshot(success);
    }

    @AfterClass
    public void tearDown(Scenario scenario) throws Exception {
        if (webDriver != null) {
            tidyUp(scenario);
        }
    }
}
