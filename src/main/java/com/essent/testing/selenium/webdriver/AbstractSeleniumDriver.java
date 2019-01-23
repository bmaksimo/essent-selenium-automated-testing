package com.essent.testing.selenium.webdriver;

import com.essent.automation.core.WebDriverWait;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.billinghouse.test_automation.util.gherkin.DateTimeFormatUtil.printPeriod;

public abstract class AbstractSeleniumDriver implements SeleniumDriver {
    private static final Logger logger = Logger.getLogger(AbstractSeleniumDriver.class);
    protected WebDriver driver;
    protected String baseUrl;
    protected String browserName;
    protected String browserVersion;

    protected static Logger logger() {
        return logger;
    }

    @Override
    public void setUp() {
        driver = createWebDriver();
        baseUrl = ConfigProvider.getProperty(ConfigKey.TESTING_BASE_URL);
        getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Capabilities caps = ((RemoteWebDriver) getDriver()).getCapabilities();
        browserName = caps.getBrowserName();
        browserVersion = caps.getVersion();
        logger().info(" - RESULT: Running tests on  " + getBrowserName() + " " + getBrowserVersion());
    }

    @Override
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

    @Override
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public void takeScreenshot(String name) {
        File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        logger.debug(" - ACTION: CAPTURE_SCREENSHOT: " + screenshot.getPath());
        Path currentRelativePath = Paths.get("").resolveSibling("target");
        String currentAbsolutePath = currentRelativePath.toAbsolutePath().toString();
        try {
            FileUtils.copyFile(screenshot, new File(FilenameUtils.concat(currentAbsolutePath, name + "_" + screenshot.getName())));
        } catch (IOException e) {
            logger.warn(String.format("- ACTION: failed copying screenshot to %s", currentAbsolutePath));
        }
    }

    @Override
    public void goToHomePage() {
        getDriver().get(baseUrl);
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public String getBrowserName() {
        return browserName;
    }

    @Override
    public String getBrowserVersion() {
        return browserVersion;
    }

    @Override
    public Set<Cookie> getCookies() {
        return getDriver().manage().getCookies();
    }

    @Override
    public Object executeScript(String function, Object... objects) {
        return ((JavascriptExecutor) getDriver()).executeScript(function, objects);
    }

    @Override
    public Object executeAsyncScript(String function, Object... objects) {
        return ((JavascriptExecutor) getDriver()).executeAsyncScript(function, objects);
    }

    @Override
    public List<WebElement> findElements(By selector) {
        return getDriver().findElements(selector);
    }

    @Override
    public WebElement findElementOrNull(By selector) {
        return findElementOrNull(selector, Duration.ofMinutes(1), Duration.ofSeconds(10));
    }

    @Override
    public WebElement findElementOrNull(By selector, Duration timeout, Duration pollingEvery) {
        logger().debug("STEP:");
        DateTime startOfMeasurement = DateTime.now();
        FluentWait<WebDriver> waiter = new FluentWait<>(getDriver())
            .withTimeout(timeout)
            .pollingEvery(pollingEvery)
            .ignoreAll(
                Arrays.asList(
                    NoSuchElementException.class,
                    StaleElementReferenceException.class)
            );
        List<WebElement> elements = waiter.until(driver -> {
            logger().debug(" - WAIT: polling findElementOrNull()");
            return getDriver().findElements(selector);
        });
        Period periodOfMeasurement = new Period(startOfMeasurement, DateTime.now());
        logger().debug(" - MEASURED_TIME: " + printPeriod(periodOfMeasurement));
        if (elements.isEmpty()) {
            logger().warn(" - RESULT: empty");
            return null;
        } else {
            return elements.get(0);
        }
    }

    @Override
    public List<WebElement> findElements(By selector, Duration timeout, Duration pollingEvery) {
        logger().debug("STEP:");
        logger().debug(" - ELEMENT QUERY: " + selector.toString());

        DateTime startOfMeasurement = DateTime.now();
        FluentWait<WebDriver> waiter = new FluentWait<>(driver)
            .withTimeout(timeout)
            .pollingEvery(pollingEvery)
            .ignoreAll(
                Arrays.asList(
                    NoSuchElementException.class,
                    StaleElementReferenceException.class)
            );
        List<WebElement> elements = waiter.until(driver -> {
            logger().debug(" - WAIT: polling findElementOrNull()");
            return getDriver().findElements(selector);
        });
        Period periodOfMeasurement = new Period(startOfMeasurement, DateTime.now());
        logger().debug(" - MEASURED_TIME: " + printPeriod(periodOfMeasurement));
        if (elements.isEmpty()) {
            logger().warn(" - RESULT: empty");
        }
        return elements;
    }

    @Override
    public WebElement findElement(By selector) {
        FluentWait<WebDriver> waiter = new FluentWait<>(getDriver())
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(NoSuchElementException.class);
        WebElement element = waiter.until(driver -> getDriver().findElement(selector));
        return element;
    }

    @Override
    public WebElement findElementWhenVisible(By selector) {
        FluentWait<WebDriver> waiter = new FluentWait<>(getDriver())
            .withTimeout(Duration.ofSeconds(50))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(ElementNotVisibleException.class)
            .ignoring(NoSuchElementException.class);
        WebElement element = waiter.until(ExpectedConditions.visibilityOfElementLocated(selector));
        return element;
    }

    protected WebDriver createWebDriver() {
        String location = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_DRIVER);
        WebDriver driver;
        logger().info("STEP:");
        logger().info(" - ACTION: INIT_SELENIUM_DRIVER");
        if (StringUtils.isNotEmpty(location)) {
            driver = new WebDriverInitializingStrategy() {
            }.createWebDriver();
        } else {
            location = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_GECKO_DRIVER);
            if (StringUtils.isNotEmpty(location)) {
                driver = new WebDriverInitializingStrategy.FirefoxWebdriverInitialingStrategy().createWebDriver();
            } else {
                logger().info(" - RESULT: ERROR, Selenium Webdriver location undefined.");
                throw new IllegalArgumentException("Error: undefined Selenium Webdriver location.");
            }
        }
        logger().info(" - RESULT: Selenium Webdriver location: " + location);
        return driver;
    }


    @Override
    public WebElement findElementWhenClickable(By selector) {
        FluentWait<WebDriver> waiter = new FluentWait<>(getDriver())
            .withTimeout(Duration.ofSeconds(300))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(ElementNotVisibleException.class)
            .ignoring(NoSuchElementException.class);
        WebElement element = waiter.until(ExpectedConditions.elementToBeClickable(selector));
        return element;
    }

    protected <V> void waitForExpectedCondition(final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds, final long sleepInMillis) {
        final WebDriverWait driverWait = new WebDriverWait(getDriver(), timeoutInSeconds, sleepInMillis);
        driverWait.until((Function<? super WebDriver, V>) expectedCondition);
    }

    protected void driverWaitFor(final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds, final long sleepInMillis) {
          waitForExpectedCondition(expectedCondition, timeoutInSeconds, sleepInMillis);
    }

    protected void waitForElementToBeVisible(final WebElement element, final long timeoutInSeconds, final long sleepInMillis) {
        driverWaitFor(ExpectedConditions.visibilityOf(element), timeoutInSeconds, sleepInMillis);
    }

    protected void waitForElementToBeClickable(final WebElement element, final long timeoutInSeconds, final long sleepInMillis) {
        driverWaitFor(ExpectedConditions.elementToBeClickable(element), timeoutInSeconds, sleepInMillis);
    }

    protected void waitForElement(final WebElement element) {
        waitForElementToBeVisible(element, 300, 5);
        waitForElementToBeClickable(element, 300, 5);
    }

    @Override
    public void waitAndClick(final WebElement element) {
        waitForElement(element);
        element.click();
    }

    @Override
    public void waitAndSendKeys(final WebElement element, final String keysToSend) {
        waitForElement(element);
        element.clear();
        waitForElement(element);
        element.sendKeys(keysToSend);
    }

    @Override
    public void waitForElementToBeVisibleBy(final By by, final long timeoutInSeconds, final long sleepInMillis) {
        driverWaitFor(ExpectedConditions.visibilityOfElementLocated(by), timeoutInSeconds, sleepInMillis);
    }
    @Override
    public void waitForElementNotToBeDisplayed(final WebElement element, final long timeoutInSeconds, final long sleepInMillis) {
        driverWaitFor(ExpectedConditions.stalenessOf(element), timeoutInSeconds, sleepInMillis);
    }

    @Override
    public void tearDown() {
        if (getDriver() == null)
            return;
        getDriver().close();
        getDriver().quit();
    }

}
