package com.essent.testing.selenium.scenario;


import com.essent.automation.util.Sleeper;
import com.essent.testing.scenario.RegisteredScenario;
import com.essent.testing.selenium.webdriver.SeleniumDriver;
import com.essent.testing.selenium.webdriver.dwp.SeleniumDriverDwpImpl;
import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Map;


public abstract class AbstractSeleniumScenario extends RegisteredScenario {

    static protected SeleniumDriver webDriver;

    public void tidyUp() {
        if (webDriver != null) {
            webDriver.tearDown();
            webDriver = null;
        }
    }

    public abstract  void setUpWebDriver() throws Exception;

    protected void moveToElementAndClick(WebElement element, WebDriver driver) {
        Actions actions = new Actions(driver);
        Actions elementMovedTo = actions.moveToElement(element);
        elementMovedTo.perform();
        Sleeper.sleepTightInSeconds(3);
        elementMovedTo.click().perform();
    }

    @AfterClass
    public void tearDown()  {
        if (webDriver != null) {
            tidyUp();
        }
    }
}
