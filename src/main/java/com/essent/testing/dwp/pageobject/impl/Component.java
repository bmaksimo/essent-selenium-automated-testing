package com.essent.testing.dwp.pageobject.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Model;
import com.essent.testing.context.ContextService;
import com.essent.testing.selenium.DWPSeleniumDriver;
import com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter;
import cucumber.runtime.CucumberException;
import org.apache.commons.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Component {

    protected WebElement element;
    protected DWPSeleniumDriver seleniumDriver;
    private final Logger logger = Logger.getLogger(Component.class);
    protected Logger logger() {
        return logger;
    }

    public Component() {
        this.seleniumDriver = (DWPSeleniumDriver) ContextService.getContext().getBean("dwpSeleniumDriver");
    }

    public Component(By selector) {
        this();
        logger().info("STEP:");
        logger().info(" - ACTION: LOAD_PAGE_OBJECT");
        element = seleniumDriver.findElementOrNull(selector);
        if(element == null) {
            logger().fatal(" - RESULT: FAILED");
            logger().fatal(" - REASON: " + getClass() + "{null}: Web element was not found. ");
            throw new CucumberException(getClass() + ": Web element was not found.");
        }
        logger.debug(String.format(" - TARGET: %s -> %s", selector, element.getAttribute("innerHTML")));
    }

    public Component(WebElement element) {
        this();
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

    public boolean executeJavascriptTest(String registeredJsClass, Object options) {
        return seleniumDriver.executeJavascriptTest(registeredJsClass, options);
    }

    protected WebElement findElementWhenVisible(By selector) {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(selector);
    }

    public WebElement findElementWhenClickable(By selector) {
        return seleniumDriver.findElementWhenClickable(selector);
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
//        seleniumDriver.waitForRequestsToFinish();
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

    protected Model.Callback hideIconOverlays() {
        return new Model.Callback() {
            @Override
            public void onAccess(Autocrat.ExecutionContext context, Model.Step step, WebElement element) {
                JavascriptExecutor jsExec = (JavascriptExecutor) context.driver;
                List<WebElement> elements = element.findElements(By.xpath("../span[contains(@class, 'icon')]"));
                elements.forEach(siblingIcon -> {
                    String setProperty = "style = 'display:none'";
                    logger().info("Executing javascript " + setProperty + " on target element");
                    jsExec.executeScript("arguments[0]." + setProperty, siblingIcon);
                });
            }
        };
    }
}
