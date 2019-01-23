package com.essent.testing.selenium.webdriver.dwp;

import com.billinghouse.test_automation.javascript.testrunner.JavascriptTestRunner;
import com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry;
import com.billinghouse.test_automation.javascript.testrunner.impl.SeleniumJsTestExpanderService;
import com.essent.automation.core.WebDriverWait;
import com.essent.testing.selenium.webdriver.AbstractSeleniumDriver;
import com.essent.testing.selenium.webdriver.SeleniumDriver;
import com.essent.testing.util.resource.ResourceUtil;
import com.paulhammant.ngwebdriver.NgWebDriver;
import cucumber.runtime.CucumberException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.billinghouse.test_automation.util.gherkin.DateTimeFormatUtil.printPeriod;
import static org.junit.Assert.fail;

/**
 * This class is a wrapper around the selenium webdriver.
 *
 * @author Peter Wessels
 * @author Dmitry Che
 */
public class SeleniumDriverDwpImpl extends AbstractSeleniumDriver implements SeleniumDriver, JavascriptTestRunner {

    private NgWebDriver ngWebDriver;
    private static final String PATH = "/js/runner/";

    private static final String PATH_TO_INLINE_CLASSES = "/js/runner/tests/";
    private static final String TEST_RUNNER_CLASS = "TestRunnerBase.js";


    public class ExecuteJavascriptTest {

        private final SeleniumDriverDwpImpl seleniumDriver;
        private boolean withException;

        ExecuteJavascriptTest(SeleniumDriverDwpImpl seleniumDriver) {
            this.seleniumDriver = seleniumDriver;
        }

        ExecuteJavascriptTest withException(boolean withException) {
            this.withException = withException;
            return this;
        }

        /**
         * @param registeredJsClass JavascriptTestRunner class name
         * @param options           Arguments to pass to Javascript
         * @return <code>true</code> when executed successfully. <code>false</code> otherwise.
         */
        public boolean executeJavascriptTest(String registeredJsClass, Object options) {
            logger().info("STEP:");
            logger().info(" - ACTION: EXEC_JAVASCRIPT_TEST");
            DateTime startOfMeasurement = DateTime.now();
            seleniumDriver.waitForRequestsToFinish();
            String executeTest = SeleniumJsTestExpanderService.get().expandToJavascript(registeredJsClass, options);
            logger().info(" - TEST: " + executeTest);
            Map result = (Map) ((JavascriptExecutor) seleniumDriver.getDriver()).executeAsyncScript(executeTest);
            Period periodOfMeasurement = new Period(startOfMeasurement, DateTime.now());
            logger().info(" - MEASURED_TIME: " + printPeriod(periodOfMeasurement));
            String status = ((String) result.get("status"));
            boolean success = StringUtils.equals("PASSED", status);
            logger().info(" - RESULT: " + status);
            if (StringUtils.equals("FAILED", status)) {
                String reason = ((String) result.get("reason"));
                logger().info(" - REASON: " + reason);
                if (withException) {
                    fail(reason);
                }
                takeScreenshot(false);
            }
            return success;
        }
    }

    @Override
    public void setUp() {
        super.setUp();
        ngWebDriver = new NgWebDriver((JavascriptExecutor) getDriver());
    }

    private void injectJavaScriptInline(File functionFile) {
        logger().debug("STEP:");
        logger().debug(" - ACTION: INJECT_JAVASCRIPT");
        JavascriptExecutor jsExec = (JavascriptExecutor)getDriver();
        DateTime startOfMeasurement = DateTime.now();
        String path = ResourceUtil.toPath(PATH + "DwpInjectScript.js.template");
        File injectionFile = new File(path);
        try {
            String injection = FileUtils.readFileToString(injectionFile, Charset.defaultCharset());
            String function = FileUtils.readFileToString(functionFile, Charset.defaultCharset());
            Map<String, String> values = new HashMap<>();
            values.put("function", StringEscapeUtils.escapeEcmaScript(function));
            StrSubstitutor sub = new StrSubstitutor(values);
            injection = sub.replace(injection);
            jsExec.executeScript(injection);
            Period periodOfMeasurement = new Period(startOfMeasurement, DateTime.now());
            logger().debug(" - MEASURED_TIME: " + printPeriod(periodOfMeasurement));
            logger().debug(" - SCRIPT: " + function);

        } catch (IOException e) {
            throw new CucumberException(e);
        }
    }

    public void injectJavaScriptTestRunner() {
        String testRunnerClassPath = ResourceUtil.toPath(PATH + TEST_RUNNER_CLASS);
        File testRunnerClassFile = new File(testRunnerClassPath);
        injectJavaScriptInline(testRunnerClassFile);
        String pathToClasses = ResourceUtil.toPath(PATH_TO_INLINE_CLASSES);
        File dirClasses = new File(pathToClasses);
        FileFilter fileFilterClasses = new WildcardFileFilter("*.js");
        for (File file : Objects.requireNonNull(dirClasses.listFiles(fileFilterClasses))) {
            injectJavaScriptInline(file);
            JsTestRegistry.get().register(FilenameUtils.getBaseName(file.getName()));
        }
    }

    @Override
    public Map executeJavascriptMethod(String registeredJsClass, Object options) {
        logger().info("STEP:");
        logger().info(" - ACTION: EVALUATE_JAVASCRIPT_METHOD");
        DateTime startOfMeasurement = DateTime.now();
        waitForRequestsToFinish();
        String jsTestCall = SeleniumJsTestExpanderService.get().expandToJavascript(registeredJsClass, options);
        logger().info(" - TEST: " + jsTestCall);
        Map result = (Map) ((JavascriptExecutor) driver).executeAsyncScript(jsTestCall);
        Period periodOfMeasurement = new Period(startOfMeasurement, DateTime.now());
        logger().info(" - MEASURED_TIME: " + printPeriod(periodOfMeasurement));
        String status = ((String) result.get("status"));
        if (StringUtils.isEmpty(status)) {
            status = "UNDEFINED";
        }
        logger().info(" - RESULT: " + status);
        if (StringUtils.equals("FAILED", status)) {
            String reason = ((String) result.get("reason"));
            logger().info(" - REASON: " + reason);
            takeScreenshot(false);
        }
        return result;
    }

    /**
     * @param registeredJsClass Name of registered Javascript test
     * @param options           arguments given to registered Javascript test
     * @return <code>true</code> when test was successfulyexecuted by JavascriptTestRunnr, <code>false</code> otherwise.
     */
    @Override
    public boolean executeJavascriptTest(String registeredJsClass, Object options) {
        return new ExecuteJavascriptTest(this).executeJavascriptTest(registeredJsClass, options);
    }

    /**
     * @param registeredJsClass Name of registered Javascript test
     * @param options           arguments given to registered Javascript test
     * @param withException     <code>true</code> to generate Cucumner exception on test failure, <code>false</code> to proceed without exception.
     * @return <code>true</code> when test was successfulyexecuted by JavascriptTestRunnr, <code>false</code> otherwise.
     */

    public boolean executeJavascriptTest(String registeredJsClass, Object options, boolean withException) {
        return new ExecuteJavascriptTest(this).withException(withException).executeJavascriptTest(registeredJsClass, options);
    }

    public void awaitJqueryNotActive(long milliseconds) {
        new WebDriverWait(driver, milliseconds).until(webDriver -> {
            final JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js
                .executeScript(SeleniumDriver.JQUERY_IS_NOT_ACTIVE);
        });
    }

    @Override
    public void waitForRequestsToFinish() {
        awaitJqueryNotActive(200);
        logger().info("STEP:");
        logger().debug(" - WAIT: waiting for all angular requests to finish on page at url: " + getDriver().getCurrentUrl());
        ngWebDriver.waitForAngularRequestsToFinish();
        logger().info(" - RESULT: all angular requests are finished on page at url: " + getDriver().getCurrentUrl());
    }

    public NgWebDriver getAngularDriver() {
        return ngWebDriver;
    }

    @Override
    public WebElement findElementWhenClickable(By selector) {
        ngWebDriver.waitForAngularRequestsToFinish();
        FluentWait<WebDriver> waiter = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofMillis(200))
            .ignoring(ElementNotVisibleException.class)
            .ignoring(NoSuchElementException.class);
        WebElement element = waiter.until(ExpectedConditions.elementToBeClickable(selector));
        ngWebDriver.waitForAngularRequestsToFinish();
        return element;
    }


    @Override
    protected void driverWaitFor(final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds, final long sleepInMillis) {
        ngWebDriver.waitForAngularRequestsToFinish();
        waitForExpectedCondition(expectedCondition, timeoutInSeconds, sleepInMillis);
    }

    @Override
    protected void waitForElement(final WebElement element) {
        ngWebDriver.waitForAngularRequestsToFinish();
        waitForElementToBeVisible(element, 30, 5);
        waitForElementToBeClickable(element, 30, 5);
    }

    @Override
    public void waitAndClick(final WebElement element) {
        waitForElement(element);
        element.click();
        ngWebDriver.waitForAngularRequestsToFinish();
    }

    @Override
    public void waitAndSendKeys(final WebElement element, final String keysToSend) {
        waitForElement(element);
        element.clear();
        waitForElement(element);
        element.sendKeys(keysToSend);
        ngWebDriver.waitForAngularRequestsToFinish();
    }

    @Override
    public void tearDown() {
        super.tearDown();
        ngWebDriver = null;
    }

}
