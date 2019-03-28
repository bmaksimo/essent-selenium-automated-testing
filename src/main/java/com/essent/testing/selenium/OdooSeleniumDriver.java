package com.essent.testing.selenium;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.util.resource.ResourceUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class OdooSeleniumDriver extends SeleniumDriver {
    private static final Logger logger = Logger.getLogger(OdooSeleniumDriver.class);
    private static final String DEFAULT_DOWNLOAD_LOCATION = ResourceUtil.toPath(File.separator + "data" + File.separator + "odoo" + File.separator);

    public void initOdooWebDriver() {
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
        setUpDefaultFileDownloadLocation(options);
        enableFileDownloadInHeadlessMode(driverService, (ChromeDriver) driver);
    }

    private void setUpDefaultFileDownloadLocation(ChromeOptions options) {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", DEFAULT_DOWNLOAD_LOCATION);
        logger.info("-Default download directory: " + DEFAULT_DOWNLOAD_LOCATION);
        options.setExperimentalOption("prefs", chromePrefs);
    }

    private void enableFileDownloadInHeadlessMode(ChromeDriverService driverService, ChromeDriver chromeDriver) {
        Map<String, Object> commandParams = new HashMap<>();
        commandParams.put("cmd", "Page.setDownloadBehavior");
        Map<String, String> params = new HashMap<>();
        params.put("behavior", "allow");
        params.put("downloadPath", DEFAULT_DOWNLOAD_LOCATION);
        commandParams.put("params", params);
        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient httpClient = HttpClientBuilder.create().build();
        String command = null;
        try {
            command = objectMapper.writeValueAsString(commandParams);
        } catch (JsonProcessingException e) {
            logger.error("Object serialization has failed. Reason: " + e.getMessage());
        }
        String remoteBrowserUrl = driverService.getUrl().toString() + "/session/" + chromeDriver.getSessionId() + "/chromium/send_command";
        HttpPost request = new HttpPost(remoteBrowserUrl);
        request.addHeader("content-type", "application/json");
        try {
            request.setEntity(new StringEntity(command));
        } catch (UnsupportedEncodingException e) {
            logger.error("Error on HttpPost request creation. Reason: " + e.getMessage());
        }
        try {
            httpClient.execute(request);
        } catch (IOException e2) {
            logger.error(" - ERROR_CONFIGURE_HEADLESS_DOWNLOAD: request" + request.toString() + "comand: " + command);
        }
    }

    public WebElement findElementWhenVisible(By selector) {
        FluentWait<WebDriver> waiter = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(100))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(ElementNotVisibleException.class)
            .ignoring(NoSuchElementException.class);
        return waiter.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    private void driverWaitFor(final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds, final long sleepInMillis) {
        waitForExpectedCondition(expectedCondition, timeoutInSeconds, sleepInMillis);
    }

    private void waitForElementToBeVisible(final WebElement element, final long timeoutInSeconds, final long sleepInMillis) {
        driverWaitFor(ExpectedConditions.visibilityOf(element), timeoutInSeconds, sleepInMillis);
    }

    private void waitForElementToBeClickable(final WebElement element, final long timeoutInSeconds, final long sleepInMillis) {
        driverWaitFor(ExpectedConditions.elementToBeClickable(element), timeoutInSeconds, sleepInMillis);
    }

    private void waitForElement(final WebElement element) {
        waitForElementToBeVisible(element, 100, 5);
        waitForElementToBeClickable(element, 100, 5);
    }

    public void waitAndClick(final WebElement element) {
        waitForElement(element);
        element.click();
    }

    public void waitAndSendKeys(final WebElement element, final String keysToSend) {
        waitForElement(element);
        element.clear();
        waitForElement(element);
        element.sendKeys(keysToSend);
    }
}
