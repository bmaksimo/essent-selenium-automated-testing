package com.essent.testing.jbilling.pageobject.impl;

import com.essent.testing.context.ContextService;
import com.essent.testing.selenium.JBillingSeleniumDriver;
import cucumber.runtime.CucumberException;
import org.apache.commons.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public abstract class Component {

    protected WebElement element;
    protected JBillingSeleniumDriver seleniumDriver;

    private final Logger logger = Logger.getLogger(Component.class);

    protected Logger logger() {
        return logger;
    }

    public Component() {
        this.seleniumDriver = (JBillingSeleniumDriver) ContextService.getContext().getBean("jBillingSeleniumDriver");
    }

    public Component(By selector) {
        this();
        logger().info("STEP:");
        logger().info(" - ACTION: LOAD_PAGE_OBJECT");
        try {
            element = seleniumDriver.findElementWhenPresent(selector);
        } catch (TimeoutException te) {
            logger().fatal(" - RESULT: FAILED");
            logger().fatal(" - REASON: " + getClass() + "{null}: Web element was not found. ");
            throw new CucumberException(getClass() + ": Web element was not found.");
        }
        logger.debug(String.format(" - TARGET: %s -> %s", selector, element.getAttribute("innerHTML")));
    }

    public Component(WebElement element) {
        logger.info("STEP:");
        logger.info(" - ACTION: LOAD_PAGE_OBJECT");

        if (element == null) {
            logger.error(" - RESULT: FAILED");
            logger.error(" - REASON: " + getClass() + "{null}: Web element was not found. ");
            throw new CucumberException(getClass() + ": Web element was not found.");
        }

        logger.info(" - RESULT: " + element);
        this.element = element;
    }

    public WebElement findElementWhenVisible(By selector) {
        return seleniumDriver.findElementWhenVisible(selector);
    }

    protected String createQuery(String template, String key, String value) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put(key, value);
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(template);
    }

    public String getTitle() {
        return null;
    }
}
