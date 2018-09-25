package com.essent.testing.selenium;

import com.billinghouse.test_automation.javascript.testrunner.JavascriptTestRunner;
import com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry;
import com.billinghouse.test_automation.javascript.testrunner.dwp.system.Queries;
import com.billinghouse.test_automation.javascript.testrunner.impl.SeleniumJsTestExpanderService;
import com.essent.automation.core.WebDriverWait;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.util.resource.ResourceUtil;
import com.paulhammant.ngwebdriver.NgWebDriver;
import cucumber.runtime.CucumberException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    public class ExecuteJavascriptTest {

        private final SeleniumDriver seleniumDriver;
        private boolean withException;

        public ExecuteJavascriptTest(SeleniumDriver seleniumDriver) {
            this.seleniumDriver = seleniumDriver;
        }

        public ExecuteJavascriptTest withException(boolean withException) {
            this.withException = withException;
            return this;
        }

        /**
         * @param registeredJsClass
         * @param options
         * @return
         */
        public boolean executeJavascriptTest(String registeredJsClass, Object options) {
            seleniumDriver.waitForRequestsToFinish();
            String executeTest = SeleniumJsTestExpanderService.get().expandToJavascript(registeredJsClass, options);
            SeleniumDriver.logger.info("STEP:");
            SeleniumDriver.logger.info(" - ACTION: EXEC_JAVASCRIPT_TEST");
            SeleniumDriver.logger.info(" - TEST: " + executeTest);
            Map result = (Map) ((JavascriptExecutor) seleniumDriver.getDriver()).executeAsyncScript(executeTest);
            String status = ((String) result.get("status"));
            boolean success = StringUtils.equals("PASSED", status);
            SeleniumDriver.logger.info(" - RESULT: " + status);
            if (StringUtils.equals("FAILED", status)) {
                String reason = ((String) result.get("reason"));
                SeleniumDriver.logger.info(" - REASON: " + reason);
                if (withException) {
                    fail(reason);
                }
                File scrFile = ((TakesScreenshot) seleniumDriver.getDriver()).getScreenshotAs(OutputType.FILE);
                SeleniumDriver.logger.info(" - ACTION: CAPTURE_SCREENSHOT: " + scrFile.getPath());
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
            chromeDriver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS).setScriptTimeout(1, TimeUnit.MINUTES);
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
        driver.close();
        driver.quit();
    }

    private void injectJavaScriptInline(File functionFile) {
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        String path = ResourceUtil.toPath(PATH + "DwpInjectScript.js.template");
        File injectionFile = new File(path);
        try {
            String injection = FileUtils.readFileToString(injectionFile, Charset.defaultCharset());
            String function = FileUtils.readFileToString(functionFile, Charset.defaultCharset());
            Map<String, String> values = new HashMap<>();
            values.put("function", StringEscapeUtils.escapeEcmaScript(function));
            StrSubstitutor sub = new StrSubstitutor(values);
            injection = sub.replace(injection);
            logger.info("STEP:");
            logger.info(" - ACTION: INJECT_JAVASCRIPT");
            jsExec.executeScript(injection);
            logger.info(" - SCRIPT: " + function);
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
        return executeJavascriptMethod(registeredJsClass, options, null);
    }

    public Map executeJavascriptMethod(String registeredJsClass, Object options, Object address) {
        waitForRequestsToFinish();
        String jsTestCall = SeleniumJsTestExpanderService.get().expandToJavascript(registeredJsClass, options);
        logger.info("STEP:");
        logger.info(" - ACTION: EVALUATE_JAVASCRIPT_METHOD");
        logger.info(" - TEST: " + jsTestCall);
        Map result = (Map) ((JavascriptExecutor) driver).executeAsyncScript(jsTestCall);
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
     * @param registeredJsClass
     * @param options
     * @return
     */
    public boolean executeJavascriptTest(String registeredJsClass, Object options) {
        return new ExecuteJavascriptTest(this).executeJavascriptTest(registeredJsClass, options);
    }

    /**
     * @param registeredJsClass
     * @param options
     * @return
     */
    public boolean executeJavascriptTest(String registeredJsClass, Object options, boolean withException) {
        return new ExecuteJavascriptTest(this).withException(withException).executeJavascriptTest(registeredJsClass, options);
    }

    private void awaitJqueryNotActive(long milliseconds) {
        new WebDriverWait(driver, milliseconds).until(webDriver -> {
            final JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js
                .executeScript(Queries.JQUERY_IS_NOT_ACTIVE.getTest());
        });
    }

    public void waitForRequestsToFinish() {
        awaitJqueryNotActive(200);
        logger.info("STEP:");
        logger.info(" - WAIT: waiting for all angular requests to finish on page at url: " + getDriver().getCurrentUrl());
        ngWebDriver.waitForAngularRequestsToFinish();
        logger.info(" - RESULT: all angular requests finished! " + getDriver().getCurrentUrl());
    }

    public void takeScreenshot(boolean success) {
        if (success) {
            return;
        }
        File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
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
        FluentWait<WebDriver> waiter = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(NoSuchElementException.class);
        List<WebElement> elements = waiter.until(driver -> driver.findElements(selector));
        if(elements.isEmpty()) {
            return null;
        } else  return elements.get(0);
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
        return element;
    }

}
