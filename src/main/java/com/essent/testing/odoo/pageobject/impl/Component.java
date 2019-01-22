package com.essent.testing.odoo.pageobject.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Model;
import com.essent.automation.core.WebDriverWait;
import com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter;
import com.essent.testing.selenium.webdriver.odoo.SeleniumDriverOdooImpl;
import cucumber.runtime.CucumberException;
import org.apache.commons.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;


public abstract class Component {

    protected WebElement element;


    protected SeleniumDriverOdooImpl seleniumDriver;

    private final Logger logger = Logger.getLogger(Component.class);

    protected Logger logger() {
        return logger;
    }

    public Component(SeleniumDriverOdooImpl seleniumDriver) {
        this.seleniumDriver = seleniumDriver;
    }

    public Component(By selector, SeleniumDriverOdooImpl seleniumDriver) {
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

    public Component(WebElement element, SeleniumDriverOdooImpl seleniumDriver) {
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

    protected Model.Execution createExecution() {
        return AutocratExecutionAdapter.newExecution();
    }

    protected Model.Step createStep(Action action) {
        return new Model.Step().action(action);
    }

    protected Model.Element createElement(String searchType, String query) {
        return new Model.Element()
            .search(searchType)
            .query(query);
    }

    protected boolean execute(final Model.Execution execution) {
        return AutocratExecutionAdapter.execute(seleniumDriver.getDriver(), execution);
    }

    protected String createQuery(String template, String key, String value) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put(key, value);
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(template);
    }
    protected String createQuery(String template, Map<String, String> valuesMapper) {
        StrSubstitutor sub = new StrSubstitutor(valuesMapper);
        return sub.replace(template);
    }

    protected Model.Callback scrollToView() {
        return new Model.Callback() {
            @Override
            public void onAccess(Autocrat.ExecutionContext context, Model.Step step, WebElement element) {
                JavascriptExecutor jsExec = (JavascriptExecutor) context.driver;
                jsExec.executeScript("arguments[0].scrollIntoView()", element);
            }
        };
    }

    public String getTitle() {
        return null;
    }

    public void awaitOdooRequestToFinish(int seconds) {
        new WebDriverWait(seleniumDriver.getDriver(), seconds).until(webDriver -> webDriver.findElements(By.cssSelector(".oe_wait")).isEmpty());
    }
}
