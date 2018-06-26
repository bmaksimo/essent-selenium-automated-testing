package com.essent.testing.selenium;

import com.billinghouse.javascript.JavascriptTestRunner;
import com.billinghouse.javascript.JsTestRegistry;
import com.billinghouse.javascript.impl.SeleniumJsTestExpanderService;
import com.billinghouse.javascript.testrunner.dwp.system.Queries;
import com.essent.automation.core.WebDriverWait;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.util.ResourceUtils;
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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * This class is a wrapper around the selenium webdriver.
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
    // new: class based
    private static final String PATH_TO_INLINE_CLASSES = "/js/runner/tests/";
    private static final String TEST_RUNNER_CLASS = "TestRunnerBase.js";

    private interface WebDriverInitializingStrategy {

        default WebDriver createWebDriver() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("chrome.switches", "--disable-extensions");
            String headless = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_HEADLESS);
            if (StringUtils.isNotEmpty(headless)) {
                options.setHeadless(true);
                String windowSize = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_HEADLESS_WINDOW_SIZE);
                if (StringUtils.isNotEmpty(windowSize)) {
                    options.addArguments("window-size=" + windowSize);
                }
            }
            String userDataPath = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_USER_DATA_PATH);
            if (StringUtils.isNotEmpty(userDataPath)) {
                options.addArguments("user-data-dir=" + userDataPath);
            }
            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            options.addArguments("--no-sandbox"); // Bypass OS security model
            logger.info(" - OPTIONS: " + options.toString());
            return new ChromeDriver(options);
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

    public void setUp() throws Exception {
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
        String path = ResourceUtils.toPath(PATH + "DwpInjectScript.js.template");
        File injectionFile = new File(path);
        try {
            String injection = FileUtils.readFileToString(injectionFile, Charset.defaultCharset());
            String function = FileUtils.readFileToString(functionFile, Charset.defaultCharset());
            Map values = new HashMap();
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
       String testRunnerClassPath = ResourceUtils.toPath(PATH + TEST_RUNNER_CLASS);
        File testRunnerClassFile = new File(testRunnerClassPath);
        injectJavaScriptInline(testRunnerClassFile);
        String pathToClasses = ResourceUtils.toPath(PATH_TO_INLINE_CLASSES);
        File dirClasses = new File(pathToClasses);
        FileFilter fileFilterClasses = new WildcardFileFilter("*.js");
        for (File file : Objects.requireNonNull(dirClasses.listFiles(fileFilterClasses))) {
            injectJavaScriptInline(file);
            JsTestRegistry.get().add(FilenameUtils.getBaseName(file.getName()));
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

    /**
     *
     * @param registeredJsClass
     * @param options
     * @return
     */
    public Map executeJavascriptMethod(String registeredJsClass, Object options) {
        waitUntilAngularPageIsLoaded();
        String jsTestCall = SeleniumJsTestExpanderService.get().expandToJavascript(registeredJsClass, options);
        logger.info("STEP:");
        logger.info(" - ACTION: EVALUATE_JAVASCRIPT_METHOD");
        logger.info(" - TEST: " + jsTestCall);
        Map result = (Map) ((JavascriptExecutor) driver).executeAsyncScript(jsTestCall);
        String status = ((String) result.get("status"));
        if(StringUtils.isEmpty(status)) {
            status = "UNDEFINED";
        }
        logger.info(" - RESULT: " + status);
        if (StringUtils.equals("FAILED", status)) {
            String reason = ((String) result.get("reason"));
            logger.info(" - REASON: " + reason);
        }
        return result;
    }

    /**
     *
     * @param registeredJsClass
     * @param options
     * @return
     */
    public boolean executeJavascriptTest(String registeredJsClass, Object options) {
        waitUntilAngularPageIsLoaded();
        String executeTest = SeleniumJsTestExpanderService.get().expandToJavascript(registeredJsClass, options);
        logger.info("STEP:");
        logger.info(" - ACTION: EXEC_JAVASCRIPT_TEST");
        logger.info(" - TEST: " + executeTest);
        Map result = (Map) ((JavascriptExecutor) driver).executeAsyncScript(executeTest);
        String status = ((String) result.get("status"));
        boolean success = StringUtils.equals("PASSED", status);
        logger.info(" - RESULT: " + status);
        if (StringUtils.equals("FAILED", status)) {
            String reason = ((String) result.get("reason"));
            logger.info(" - REASON: " + reason);
        }
        return success;
    }

    private void awaitJqueryNotActive(long milliseconds) {
        new WebDriverWait(driver, milliseconds).until(webDriver -> {
            final JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js
                .executeScript(Queries.JQUERY_IS_NOT_ACTIVE.getTest());
        });
    }

    public void waitUntilAngularPageIsLoaded() {
        awaitJqueryNotActive(200);
        logger.info("STEP:");
        logger.info(" - WAIT: waiting for all angular requests to finish on page at url: " + getDriver().getCurrentUrl());
        ngWebDriver.waitForAngularRequestsToFinish();
        logger.info(" - RESULT: all angular requests finished! " + getDriver().getCurrentUrl());
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

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public WebElement findElementOrNull(By by) {
        WebDriverWait waiter = new WebDriverWait(driver, 5).withoutException();
        WebElement result = waiter.until(driver -> driver.findElement(by));
        return result;
    }
}
