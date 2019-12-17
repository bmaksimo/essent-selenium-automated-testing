package com.essent.testing.odoo.pageobject.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Model;
import com.essent.automation.core.WebDriverWait;
import com.essent.testing.context.ContextService;
import com.essent.testing.odoo.table.OdooTableFilter;
import com.essent.testing.selenium.OdooSeleniumDriver;
import com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter;
import com.essent.testing.table.Filter;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Component {

    protected WebElement element;
    protected OdooSeleniumDriver seleniumDriver;
    private final Logger logger = Logger.getLogger(Component.class);

    protected Logger logger() {
        return logger;
    }

    public Component() {
        this.seleniumDriver = (OdooSeleniumDriver) ContextService.getContext().getBean("odooSeleniumDriver");
    }

    public Component(By selector) {
        this();
        logger.debug("STEP:");
        logger.debug(" - ACTION: LOAD_PAGE_OBJECT");
        try {
            element = seleniumDriver.findElementWhenPresent(selector);
        } catch (TimeoutException te) {
            logger.error(" - RESULT: FAILED");
            logger.error(" - REASON: " + getClass() + "{null}: Web element was not found. ");
            throw new CucumberException(getClass() + ": Web element was not found.");
        }
        logger.debug(String.format(" - TARGET: %s -> %s", selector, element.getAttribute("innerHTML")));
    }

    public Component(WebElement element) {
        this();
        logger.debug("STEP:");
        logger.debug(" - ACTION: LOAD_PAGE_OBJECT");

        if (element == null) {
            logger.error(" - RESULT: FAILED");
            logger.error(" - REASON: " + getClass() + "{null}: Web element was not found. ");
            throw new CucumberException(getClass() + ": Web element was not found.");
        }

        logger.debug(" - RESULT: " + element);
        this.element = element;
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
            new WebDriverWait(seleniumDriver.getDriver(), seconds).withoutException()
                .until(webDriver -> webDriver.findElements(By.cssSelector(".oe_wait")).isEmpty());
    }


    public List<WebElement> selectRowOnTable(String tableName, List<Filter> filters, String contextParameters) throws Exception {
        return new OdooTableFilter(contextParameters)
            .getTable(tableName)
            .findBy(filters)
            .getRow();
    }
}
