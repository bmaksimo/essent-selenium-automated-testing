package com.essent.testing.jbilling.pageobject.impl;

import com.essent.testing.selenium.SeleniumDriver;
import cucumber.runtime.CucumberException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public abstract class Component {

    protected WebElement element;


    protected SeleniumDriver seleniumDriver;

    private final Logger logger = Logger.getLogger(Component.class);

    protected Logger logger() {
        return logger;
    }

    public Component(SeleniumDriver seleniumDriver) {
        this.seleniumDriver = seleniumDriver;
    }

    public Component(By selector, SeleniumDriver seleniumDriver) {
        logger().info("STEP:");
        logger().info(" - ACTION: LOAD_PAGE_OBJECT");
        element = seleniumDriver.findElementOrNull(selector);
        if(element == null) {
            logger().fatal(" - RESULT: FAILED");
            logger().fatal(" - REASON: " + getClass() + "{null}: Web element was not found. ");
            throw new CucumberException(getClass() + ": Web element was not found.");
        }
        logger.debug(String.format(" - TARGET: %s -> %s", selector, element.getAttribute("innerHTML")));
        this.seleniumDriver = seleniumDriver;
    }

    public Component(WebElement element, SeleniumDriver seleniumDriver) {
        logger.info("STEP:");
        logger.info(" - ACTION: LOAD_PAGE_OBJECT");

        if (element == null) {
            logger.error(" - RESULT: FAILED");
            logger.error(" - REASON: " + getClass() + "{null}: Web element was not found. ");
            throw new CucumberException(getClass() + ": Web element was not found.");
        }

        logger.info(" - RESULT: " + element);
        this.element = element;
        this.seleniumDriver = seleniumDriver;
    }

    public WebElement findElementWhenVisible(By selector) {
        return seleniumDriver.findElementWhenVisible(selector);
    }

    protected void waitForRequestsToFinish() {
        seleniumDriver.awaitJqueryNotActive(500);
    }

    public String getTitle() {
        return null;
    }
}
