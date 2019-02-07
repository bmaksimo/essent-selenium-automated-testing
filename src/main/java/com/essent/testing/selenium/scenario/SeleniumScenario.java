<<<<<<< HEAD
//package com.essent.testing.selenium.scenario;
//
//
//import com.essent.automation.util.Sleeper;
//import com.essent.testing.scenario.RegisteredScenario;
//import com.essent.testing.selenium.SeleniumDriver;
//import org.junit.AfterClass;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//
//public class SeleniumScenario extends RegisteredScenario {
//
//
//}
=======
package com.essent.testing.selenium.scenario;


import com.essent.automation.util.Sleeper;
import com.essent.testing.scenario.RegisteredScenario;
import com.essent.testing.selenium.SeleniumDriver;
import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.Map;


public class SeleniumScenario extends RegisteredScenario {

    static protected SeleniumDriver webDriver;

    public void tidyUp() {
        if (webDriver != null) {
            webDriver.tearDown();
            webDriver = null;
        }
    }

    public void setUpWebDriver() throws Exception {
        tidyUp();
        webDriver = new SeleniumDriver();
        webDriver.setUp();
    }


    protected void injectJavaScriptTestRunner() {
        webDriver.injectJavaScriptTestRunner();
    }

    protected boolean executeJavascriptTest(String registeredJsClass, Object options) {
        return webDriver.executeJavascriptTest(registeredJsClass, options);
    }

    public static boolean executeJavascriptTest(String registeredJsClass, Object options, boolean withException) {
        return webDriver.executeJavascriptTest(registeredJsClass, options, withException);
    }

    protected Map executeJavascriptMethod(String registeredJsClass, Object options) {
        Map map = webDriver.executeJavascriptMethod(registeredJsClass, options);
        return map;
    }

    protected void takeScreenshot(boolean success) {
        webDriver.takeScreenshot(success);
    }

    protected void moveToElementAndClick(WebElement element, WebDriver driver) {
        Actions actions = new Actions(driver);
        Actions elementMovedTo = actions.moveToElement(element);
        elementMovedTo.perform();
        Sleeper.sleepTightInSeconds(3);
        elementMovedTo.click().perform();
    }

    protected <T> FluentWait<T> waiter(T testObject, long secondsTimeout, long secondsPollingEvery) {
        return new FluentWait<>(testObject)
            .withTimeout(java.time.Duration.ofSeconds(secondsTimeout))
            .pollingEvery(java.time.Duration.ofSeconds(secondsPollingEvery));
    }

    @AfterClass
    public void tearDown()  {
        if (webDriver != null) {
            tidyUp();
        }
    }
}
>>>>>>> develop
