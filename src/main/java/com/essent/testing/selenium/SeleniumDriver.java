package com.essent.testing.selenium;

import com.billinghouse.test_automation.javascript.testrunner.JavascriptTestRunner;
import com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry;
import com.billinghouse.test_automation.javascript.testrunner.impl.SeleniumJsTestExpanderService;
import com.essent.automation.core.WebDriverWait;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.util.resource.ResourceUtil;
import com.paulhammant.ngwebdriver.NgWebDriver;
import cucumber.api.Scenario;
import cucumber.runtime.CucumberException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.billinghouse.test_automation.util.gherkin.DateTimeFormatUtil.printPeriod;
import static org.junit.Assert.fail;

/**
 * This class is a wrapper around the selenium webdriver.
 *
 * @author Peter Wessels
 * @author Dmitry Che
 */
public class SeleniumDriver implements JavascriptExecutor, JavascriptTestRunner {

    private NgWebDriver ngWebDriver;
    private static final Logger logger = Logger.getLogger(SeleniumDriver.class);
    private WebDriver driver;
    private String baseUrl;
    private String browserName;
    private String browserVersion;
    private static final String PATH = "/js/runner/";

    private static final String PATH_TO_INLINE_CLASSES = "/js/runner/tests/";
    private static final String TEST_RUNNER_CLASS = "TestRunnerBase.js";

    private static final String JQUERY_IS_NOT_ACTIVE = "return window.jQuery != undefined && jQuery.active === 0";

    public class ExecuteJavascriptTest {

        private final SeleniumDriver seleniumDriver;
        private boolean withException;

        ExecuteJavascriptTest(SeleniumDriver seleniumDriver) {
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
            SeleniumDriver.logger.info("STEP:");
            SeleniumDriver.logger.info(" - ACTION: EXEC_JAVASCRIPT_TEST");
            DateTime startOfMeasurement = DateTime.now();
            seleniumDriver.waitForRequestsToFinish();
            String executeTest = SeleniumJsTestExpanderService.get().expandToJavascript(registeredJsClass, options);
            SeleniumDriver.logger.info(" - TEST: " + executeTest);
            Map result = (Map) ((JavascriptExecutor) seleniumDriver.getDriver()).executeAsyncScript(executeTest);
            Period periodOfMeasurement = new Period(startOfMeasurement, DateTime.now());
            logger.info(" - MEASURED_TIME: " + printPeriod(periodOfMeasurement));
            String status = ((String) result.get("status"));
            boolean success = StringUtils.equals("PASSED", status);
            SeleniumDriver.logger.info(" - RESULT: " + status);
            if (StringUtils.equals("FAILED", status)) {
                String reason = ((String) result.get("reason"));
                SeleniumDriver.logger.info(" - REASON: " + reason);
                if (withException) {
                    fail(reason);
                }
                takeScreenshot(false);
            }
            return success;
        }
    }

    private interface WebDriverInitializingStrategy {

        default WebDriver createWebDriver() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("chrome.switches", "--disable-extensions");
            String headless = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_HEADLESS);
            if (StringUtils.isNotEmpty(headless)) {
                options.setHeadless(true);
                options.addArguments("--headless");
                String windowSize = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_HEADLESS_WINDOW_SIZE);
                if (StringUtils.isNotEmpty(windowSize)) {
                    options.addArguments("window-size=" + windowSize);
                }
            }
            String userDataPath = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_USER_DATA_PATH);
            if (StringUtils.isNotEmpty(userDataPath)) {
                options.addArguments("user-data-dir=" + userDataPath);
                try {
                    FileUtils.cleanDirectory(new File((userDataPath)));
                    logger.info(" - CLEAN_DIR: " + userDataPath);
                } catch (IOException e) {
                    logger.error(" - CLEAN_DIR: " + userDataPath);
                }
            }

            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            options.addArguments("--no-sandbox"); // Bypass OS security model
            logger.info(" - OPTIONS: " + options.toString());
            ChromeDriver chromeDriver = new ChromeDriver(options);
            chromeDriver.manage().timeouts().implicitlyWait(3, TimeUnit.MINUTES).setScriptTimeout(5, TimeUnit.MINUTES);
            return chromeDriver;
        }

        class FirefoxWebdriverInitialingStrategy implements WebDriverInitializingStrategy {
            @Override
            public WebDriver createWebDriver() {
                FirefoxOptions options = new FirefoxOptions();
                //Marionette capability should be set to false in CentOS
                options.setCapability("marionette", true);
                String firefoxProfile = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_FIREFOX_PROFILE);
                if (StringUtils.isNotEmpty(firefoxProfile)) {
                    options.addArguments("-profile", firefoxProfile);
                }
                return new FirefoxDriver(options);
            }
        }
    }


    public void setUp() {
        driver = createWebDriver();
        ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
        baseUrl = ConfigProvider.getProperty(ConfigKey.TESTING_BASE_URL);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
        browserName = caps.getBrowserName();
        browserVersion = caps.getVersion();
        logger.info(" - RESULT: Running tests on  " + browserName + " " + browserVersion);
    }

    private WebDriver createWebDriver() {
        String location = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_DRIVER);
        WebDriver driver;
        logger.info("STEP:");
        logger.info(" - ACTION: INIT_SELENIUM_DRIVER");
        if (StringUtils.isNotEmpty(location)) {
            driver = new WebDriverInitializingStrategy() {
            }.createWebDriver();
        } else {
            location = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_GECKO_DRIVER);
            if (StringUtils.isNotEmpty(location)) {
                driver = new WebDriverInitializingStrategy.FirefoxWebdriverInitialingStrategy().createWebDriver();
            } else {
                logger.info(" - RESULT: ERROR, Selenium Webdriver location undefined.");
                throw new IllegalArgumentException("Error: undefined Selenium Webdriver location.");
            }
        }
        logger.info(" - RESULT: Selenium Webdriver location: " + location);
        return driver;
    }

    public void tearDown() {
        if (driver == null)
            return;
//        driver.close();
//        driver.quit();
    }

    private void injectJavaScriptInline(File functionFile) {
        logger.debug("STEP:");
        logger.debug(" - ACTION: INJECT_JAVASCRIPT");
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
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
            logger.debug(" - MEASURED_TIME: " + printPeriod(periodOfMeasurement));
            logger.debug(" - SCRIPT: " + function);

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
    public Object executeScript(String function, Object... objects) {
        return ((JavascriptExecutor) driver).executeScript(function, objects);
    }

    @Override
    public Object executeAsyncScript(String function, Object... objects) {
        return ((JavascriptExecutor) driver).executeAsyncScript(function, objects);
    }

    public Map executeJavascriptMethod(String registeredJsClass, Object options) {
        logger.info("STEP:");
        logger.info(" - ACTION: EVALUATE_JAVASCRIPT_METHOD");
        DateTime startOfMeasurement = DateTime.now();
        waitForRequestsToFinish();
        String jsTestCall = SeleniumJsTestExpanderService.get().expandToJavascript(registeredJsClass, options);
        logger.info(" - TEST: " + jsTestCall);
        Map result = (Map) ((JavascriptExecutor) driver).executeAsyncScript(jsTestCall);
        Period periodOfMeasurement = new Period(startOfMeasurement, DateTime.now());
        logger.info(" - MEASURED_TIME: " + printPeriod(periodOfMeasurement));
        String status = ((String) result.get("status"));
        if (StringUtils.isEmpty(status)) {
            status = "UNDEFINED";
        }
        logger.info(" - RESULT: " + status);
        if (StringUtils.equals("FAILED", status)) {
            String reason = ((String) result.get("reason"));
            logger.info(" - REASON: " + reason);
            takeScreenshot(false);
        }
        return result;
    }

    /**
     * @param registeredJsClass Name of registered Javascript test
     * @param options           arguments given to registered Javascript test
     * @return <code>true</code> when test was successfulyexecuted by JavascriptTestRunnr, <code>false</code> otherwise.
     */
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
                .executeScript(JQUERY_IS_NOT_ACTIVE);
        });
    }

    public void waitForRequestsToFinish() {
        awaitJqueryNotActive(200);
        logger.info("STEP:");
        logger.debug(" - WAIT: waiting for all angular requests to finish on page at url: " + getDriver().getCurrentUrl());
        ngWebDriver.waitForAngularRequestsToFinish();
        logger.info(" - RESULT: all angular requests are finished on page at url: " + getDriver().getCurrentUrl());
    }

    public void takeScreenshot(boolean success) {
        if (success) {
            return;
        }
        File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        logger.debug(" - ACTION: CAPTURE_SCREENSHOT: " + screenshot.getPath());
        Path currentRelativePath = Paths.get("").resolveSibling("target");
        String currentAbsolutePath = currentRelativePath.toAbsolutePath().toString();
        try {
            FileUtils.copyFile(screenshot, new File(FilenameUtils.concat(currentAbsolutePath, screenshot.getName())));
        } catch (IOException e) {
            logger.warn(String.format("- ACTION: failed copying screenshot to %s", currentAbsolutePath));
        }
    }

    public void goToHomePage() {
        driver.get(baseUrl);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBrowserName() {
        return browserName;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public NgWebDriver getAngularDriver() {
        return ngWebDriver;
    }

    public List<WebElement> findElements(By selector) {
        return driver.findElements(selector);
    }

    public WebElement findElementOrNull(By selector) {
        logger.debug("STEP:");
        DateTime startOfMeasurement = DateTime.now();
        FluentWait<WebDriver> waiter = new FluentWait<>(driver)
            .withTimeout(Duration.ofMinutes(1))
            .pollingEvery(Duration.ofSeconds(10))
            .ignoreAll(
                Arrays.asList(
                    NoSuchElementException.class,
                    StaleElementReferenceException.class)
            );
        List<WebElement> elements = waiter.until(driver -> {
            logger.debug(" - WAIT: polling findElementOrNull()");
            return driver.findElements(selector);
        });
        Period periodOfMeasurement = new Period(startOfMeasurement, DateTime.now());
        logger.debug(" - MEASURED_TIME: " + printPeriod(periodOfMeasurement));
        if (elements.isEmpty()) {
            logger.warn(" - RESULT: empty");
            return null;
        } else {
            WebElement webElement = elements.get(0);
            logger.debug(String.format(" - RESULT: %s -> %s", selector, webElement.getAttribute("innerHTML")));
            return webElement;
        }
    }

    public WebElement findElement(By selector) {
        FluentWait<WebDriver> waiter = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(NoSuchElementException.class);
        WebElement element = waiter.until(driver -> driver.findElement(selector));
        return element;
    }

    public WebElement findElementWhenVisible(By selector) {
        FluentWait<WebDriver> waiter = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(ElementNotVisibleException.class);
        WebElement element = waiter.until(ExpectedConditions.visibilityOfElementLocated(selector));
        return element;
    }

    public WebElement findElementWhenClickable(By selector) {
        FluentWait<WebDriver> waiter = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(ElementNotVisibleException.class);
        WebElement element = waiter.until(ExpectedConditions.elementToBeClickable(selector));
        ngWebDriver.waitForAngularRequestsToFinish();
        return element;
    }

    private <V> void waitForExpectedCondition(final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds, final long sleepInMillis) {
        final WebDriverWait driverWait = new WebDriverWait(driver, timeoutInSeconds, sleepInMillis);
        driverWait.until((Function<? super WebDriver, V>) expectedCondition);
    }

    private void driverWaitFor(final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds, final long sleepInMillis) {
        ngWebDriver.waitForAngularRequestsToFinish();
        waitForExpectedCondition(expectedCondition, timeoutInSeconds, sleepInMillis);
    }

    public void waitForElementToBeVisibleBy(final By by, final long timeoutInSeconds, final long sleepInMillis) {
        driverWaitFor(ExpectedConditions.visibilityOfElementLocated(by), timeoutInSeconds, sleepInMillis);
    }

    private void waitForElementToBeVisible(final WebElement element, final long timeoutInSeconds, final long sleepInMillis) {
        driverWaitFor(ExpectedConditions.visibilityOf(element), timeoutInSeconds, sleepInMillis);
    }

    private void waitForElementToBeClickable(final WebElement element, final long timeoutInSeconds, final long sleepInMillis) {
        driverWaitFor(ExpectedConditions.elementToBeClickable(element), timeoutInSeconds, sleepInMillis);
    }

    public void waitForElementNotToBeDisplayed(final WebElement element, final long timeoutInSeconds, final long sleepInMillis) {
        driverWaitFor(ExpectedConditions.stalenessOf(element), timeoutInSeconds, sleepInMillis);
    }

    private void waitForElement(final WebElement element) {
        ngWebDriver.waitForAngularRequestsToFinish();
        waitForElementToBeVisible(element, 30, 5);
        waitForElementToBeClickable(element, 30, 5);
    }

    public void waitAndClick(final WebElement element) {
        waitForElement(element);
        element.click();
        ngWebDriver.waitForAngularRequestsToFinish();
    }

    public void waitAndSendKeys(final WebElement element, final String keysToSend) {
        waitForElement(element);
        element.click();
        element.clear();
        element.click();
        element.sendKeys(keysToSend);
        ngWebDriver.waitForAngularRequestsToFinish();
    }

}
