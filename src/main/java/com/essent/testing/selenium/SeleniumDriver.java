package com.essent.testing.selenium;

import com.essent.automation.core.WebDriverWait;
import com.essent.automation.util.Sleeper;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.billinghouse.test_automation.util.gherkin.DateTimeFormatUtil.printPeriod;

public abstract class SeleniumDriver {
    private static final Logger logger = Logger.getLogger(SeleniumDriver.class);
    private static final String JQUERY_IS_NOT_ACTIVE = "return window.jQuery != undefined && jQuery.active === 0";
    protected WebDriver driver;
    private String baseUrl;
    protected String browserName;
    private String browserVersion;
    protected ChromeOptions options;
    protected ChromeDriverService driverService;

    public void setUp() {
        baseUrl = ConfigProvider.getProperty(ConfigKey.TESTING_BASE_URL);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
        browserName = caps.getBrowserName();
        browserVersion = caps.getVersion();
        logger.info(" - RESULT: Running tests on  " + browserName + " " + browserVersion);
    }

    public void createWebDriver() {
        options = new ChromeOptions();
        options.addArguments("chrome.switches", "--disable-extensions");
        options.addArguments("window-size=1920,1080");
        options.addArguments("--incognito");
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        logger.info(" - OPTIONS: " + options.toString());
        setChromeDriverBinary(options);
        ChromeDriver chromeDriver;

        String headless = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_HEADLESS);
        if (StringUtils.isNotEmpty(headless)) {
            options.setHeadless(true);
            String windowSize = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_HEADLESS_WINDOW_SIZE);
            if (StringUtils.isNotEmpty(windowSize)) {
                options.addArguments("window-size=" + windowSize);
            } else {
                options.addArguments("--start-maximized");
            }
        } else {
            options.addArguments("--start-maximized");
        }
        driverService = ChromeDriverService.createDefaultService();
        chromeDriver = new ChromeDriver(driverService, options);
        chromeDriver.manage().timeouts().implicitlyWait(3, TimeUnit.MINUTES).setScriptTimeout(5, TimeUnit.MINUTES);
        driver = chromeDriver;
    }

    private void setChromeDriverBinary(ChromeOptions options) {
        String binary = ConfigProvider.getProperty(ConfigKey.GOOGLE_CHROME_BINARY);
        if(StringUtils.isNotEmpty(binary)) {
            options.setBinary(binary);
        }
    }

    public void tearDown() {
        if (driver == null)
            return;
        driver.close();
        driver.quit();
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

    public List<WebElement> findElements(By selector) {
        return driver.findElements(selector);
    }

    public WebElement findElementWhenPresent(By selector) {
        return findElementWhenPresent(selector, Duration.ofMinutes(1), Duration.ofSeconds(10));
    }

    public Optional<WebElement> findElementOptional(By selector) {
        FluentWait<WebDriver> waiter = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(3))
            .pollingEvery(Duration.ofSeconds(1))
            .ignoring(NoSuchElementException.class);
        List<WebElement> element  = waiter.until(driver -> {
            logger.debug(" - WAIT: polling findElementWhenPresent()");
            return driver.findElements(selector);
        });
        if(element.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(element.get(0));
    }

    public WebElement findElementWhenPresent(By selector, Duration timeout, Duration pollingEvery) {
        logger.debug("STEP:");
        DateTime startOfMeasurement = DateTime.now();
        FluentWait<WebDriver> waiter = new FluentWait<>(driver)
            .withTimeout(timeout)
            .pollingEvery(pollingEvery)
            .ignoring(NoSuchElementException.class);
        WebElement element  = waiter.until(driver -> {
            logger.debug(" - WAIT: polling findElementWhenPresent()");
            return ExpectedConditions.presenceOfElementLocated(selector).apply(driver);
        });
        Period periodOfMeasurement = new Period(startOfMeasurement, DateTime.now());
        logger.debug(" - MEASURED_TIME: " + printPeriod(periodOfMeasurement));
        return element;
    }


    public List<WebElement> findElements(By selector, Duration timeout, Duration pollingEvery) {
        logger.debug("STEP:");
        logger.debug(" - ELEMENT QUERY: " + selector.toString());
        DateTime startOfMeasurement = DateTime.now();
        FluentWait<WebDriver> waiter = new FluentWait<>(driver)
            .withTimeout(timeout)
            .pollingEvery(pollingEvery)
            .ignoring(
                    NoSuchElementException.class);
        List<WebElement> elements = waiter.until(driver -> {
            logger.debug(" - WAIT: polling findElementWhenPresent()");
            return driver.findElements(selector);
        });
        Period periodOfMeasurement = new Period(startOfMeasurement, DateTime.now());
        logger.debug(" - MEASURED_TIME: " + printPeriod(periodOfMeasurement));
        if (elements.isEmpty()) {
            logger.warn(" - RESULT: empty");
        }
        return elements;
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
            .withTimeout(Duration.ofSeconds(50))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(ElementNotVisibleException.class)
            .ignoring(NoSuchElementException.class);
        WebElement element = waiter.until(ExpectedConditions.visibilityOfElementLocated(selector));
        return element;
    }

    public <V> void waitForExpectedCondition(final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds, final long sleepInMillis) {
        final WebDriverWait driverWait = new WebDriverWait(driver, timeoutInSeconds, sleepInMillis);
        driverWait.until((Function<? super WebDriver, V>) expectedCondition);
    }

    public void moveToElementAndClick(WebElement element) {
        Actions actions = new Actions(driver);
        Actions elementMovedTo = actions.moveToElement(element);
        elementMovedTo.perform();
        Sleeper.sleepTightInSeconds(3);
        elementMovedTo.click().perform();
    }

    public Set<Cookie> getCookies() {
        return driver.manage().getCookies();
    }

    public void awaitJqueryNotActive(long milliseconds) {
        new org.openqa.selenium.support.ui.WebDriverWait(driver, milliseconds).until(webDriver -> {
            final JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js
                .executeScript(JQUERY_IS_NOT_ACTIVE);
        });
    }

}
