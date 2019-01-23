package com.essent.testing.selenium.webdriver.odoo;

import com.essent.automation.util.Sleeper;
import com.essent.testing.selenium.webdriver.AbstractSeleniumDriver;
import com.essent.testing.selenium.webdriver.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * This class is a wrapper around the selenium webdriver.
 *
 * @author Sonja Novakovic
 * @author Dmitry Che
 */
public class SeleniumDriverOdooImpl extends AbstractSeleniumDriver implements SeleniumDriver {


    @Override
    public void waitForRequestsToFinish() {
  
    }

    @Override
    public void waitAndClick(WebElement element) {
        Sleeper.sleepTightInSeconds(1.5);
        super.waitAndClick(element);
    }

    @Override
    public void waitAndSendKeys(WebElement element, String keysToSend) {
        Sleeper.sleepTightInSeconds(1.5);
        super.waitAndSendKeys(element, keysToSend);
    }

}
