package com.essent.testing.dwp.pageobject.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Model;
import com.essent.automation.util.Sleeper;
import com.essent.testing.context.ContextService;
import com.essent.testing.dwp.pageobject.table.Filter;
import com.essent.testing.dwp.pageobject.table.TableFilter;
import com.essent.testing.selenium.DWPSeleniumDriver;
import com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.time.Duration;
import java.util.*;

public abstract class Component {

    private static final By MANDATORY_INPUT_EXCLAMATION_CSS = By.cssSelector(".is-error");
    private static final String CLOSE_MODAL_BUTTON = "//guidance-modal//div[@class = 'modal__header']/a";

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

    public boolean executeJavascriptTest(String registeredJsClass, Object options) {
        return seleniumDriver.executeJavascriptTest(registeredJsClass, options);
    }

    protected Map executeJavascriptMethod(String registeredJsClass, Object options) {
        return seleniumDriver.executeJavascriptMethod(registeredJsClass, options);
    }

    public boolean executeJavascriptTest(String registeredJsClass, Object options, boolean withException) {
        return seleniumDriver.executeJavascriptTest(registeredJsClass, options, withException);
    }

    protected Map executeJavascriptMethodImmediately(String registeredJsClass, Object options) {
        return seleniumDriver.executeJavascriptMethodWithImmediateFlag(registeredJsClass, options, true);
    }

    protected WebElement findElementWhenVisible(By selector) {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(selector);
    }

    protected WebElement findElementWhenClickable(By selector) {
        return seleniumDriver.findElementWhenClickable(selector);
    }

    public WebElement findElementWhenPresent(By selector, Duration timeout, Duration pollingEvery) {
        return seleniumDriver.findElementWhenPresent(selector, timeout, pollingEvery);
    }

    protected Model.Execution createExecution() {
        return AutocratExecutionAdapter.newExecution();
    }

    protected Model.Step createStep(Action action) {
        return new Model.Step().action(action);
    }

    protected Model.Element createElement(String searchType, String query) {
        return new Model.Element().search(searchType).query(query);
    }

    protected boolean execute(final Model.Execution execution) {
        seleniumDriver.waitForRequestsToFinish();
        boolean result = AutocratExecutionAdapter.execute(seleniumDriver.getDriver(), execution);
        seleniumDriver.waitForRequestsToFinish();
        handleAlert();
        seleniumDriver.waitForRequestsToFinish();
        return result;
    }

    protected boolean executeNow(final Model.Execution execution) {
        return AutocratExecutionAdapter.execute(seleniumDriver.getDriver(), execution);
    }

    protected String createQuery(String template, String key, String value) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put(key, value);
        return new StrSubstitutor(valuesMap).replace(template);
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
                    logger.debug("Executing javascript " + setProperty + " on target element");
                    jsExec.executeScript("arguments[0]." + setProperty, siblingIcon);
                });
            }
        };
    }

    protected void handleAlert() {
        if (isAlertPresent()) {
            seleniumDriver.getDriver().switchTo().alert().accept();
        }
    }

    private boolean isAlertPresent() {
        try {
            seleniumDriver.getDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException ex) {
            return false;
        }
    }

    protected void validateForm(String scenarioInfo) {
        try {
            List<WebElement> elements = seleniumDriver.findElements(MANDATORY_INPUT_EXCLAMATION_CSS,
                java.time.Duration.ofSeconds(1),
                java.time.Duration.ofMillis(200));
            String location = elements.stream().map(WebElement::getText).reduce("", (partialString, element) -> partialString + (" " + element + System.lineSeparator()));
            if (StringUtils.isNotEmpty(location)) {
                logger().error(scenarioInfo + " - WARNING: Mandatory input failure in: " + location);
            }
        } catch (UnhandledAlertException uae) {
            handleAlert();
        }
    }

    protected void closeGuidanceModalIfPresent() {
        Optional<WebElement> guidanceModal = seleniumDriver.findElementOptional(By.xpath(CLOSE_MODAL_BUTTON));
        guidanceModal.ifPresent(m -> closeModal());
        seleniumDriver.waitForRequestsToFinish();
    }

    public void closeModal() {
        seleniumDriver.waitForRequestsToFinish();
        WebElement xElement = seleniumDriver.findElementWhenVisible(By.xpath(CLOSE_MODAL_BUTTON));
        seleniumDriver.waitAndClick(xElement);
    }

    public List<WebElement> selectRowOnTable(String tableName, List<Filter> filters, String contextParameters) throws Exception {
        return new TableFilter(contextParameters)
            .getTable(tableName)
            .findBy(filters)
            .get();
    }

    protected void clickWithRetries(WebElement element, int attempts) {
        int currentAttempt = 0;
        boolean isDisplayed = false;
        while (!isDisplayed && currentAttempt <= attempts) {
            currentAttempt++;
            isDisplayed = element.isDisplayed() && element.isEnabled();
            Sleeper.sleepTightInSeconds(2);
            if (isDisplayed) element.click();
        }
    }
}
