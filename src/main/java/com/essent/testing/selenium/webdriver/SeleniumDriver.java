package com.essent.testing.selenium.webdriver;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.selenium.webdriver.dwp.SeleniumDriverDwpImpl;
import com.essent.testing.util.resource.ResourceUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface SeleniumDriver extends JavascriptExecutor {
    String DEFAULT_DOWNLOAD_LOCATION = ResourceUtil.toPath(File.separator + "data" + File.separator + "odoo" + File.separator);
    String JQUERY_IS_NOT_ACTIVE = "return window.jQuery != undefined && jQuery.active === 0";

    void setUp();

    void tearDown();

    @Override
    Object executeScript(String function, Object... objects);

    @Override
    Object executeAsyncScript(String function, Object... objects);


    void waitForRequestsToFinish();

    void takeScreenshot(boolean success);

    void takeScreenshot(String name);

    void goToHomePage();

    String getBaseUrl();

    void setBaseUrl(String baseUrl);

    String getBrowserName();

    String getBrowserVersion();

    WebDriver getDriver();

    List<WebElement> findElements(By selector);

    WebElement findElementOrNull(By selector);

    WebElement findElementOrNull(By selector, Duration timeout, Duration pollingEvery);

    List<WebElement> findElements(By selector, Duration timeout, Duration pollingEvery);

    WebElement findElement(By selector);

    WebElement findElementWhenVisible(By selector);

    WebElement findElementWhenClickable(By selector);

    void waitForElementToBeVisibleBy(By by, long timeoutInSeconds, long sleepInMillis);

    void waitForElementNotToBeDisplayed(WebElement element, long timeoutInSeconds, long sleepInMillis);

    void waitAndClick(WebElement element);

    void waitAndSendKeys(WebElement element, String keysToSend);

    Set<Cookie> getCookies();

    public interface WebDriverInitializingStrategy {

        default WebDriver createWebDriver() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("chrome.switches", "--disable-extensions");
            options.addArguments("window-size=1920,1080");
            options.addArguments("--incognito");
            final Logger logger = Logger.getLogger(SeleniumDriverDwpImpl.class);
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

            setUpDefaultFileDownloadLocation(options);

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
                ChromeDriverService driverService = ChromeDriverService.createDefaultService();
                chromeDriver = new ChromeDriver(driverService, options);
                enableFileDownloadInHeadlessMode(driverService, chromeDriver);
            } else {
                options.addArguments("--start-maximized");
                chromeDriver = new ChromeDriver(options);

            }
            chromeDriver.manage().timeouts().implicitlyWait(3, TimeUnit.MINUTES).setScriptTimeout(5, TimeUnit.MINUTES);
            return chromeDriver;
        }

        default void setUpDefaultFileDownloadLocation(ChromeOptions options) {
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", DEFAULT_DOWNLOAD_LOCATION);
            options.setExperimentalOption("prefs", chromePrefs);
        }

        default void enableFileDownloadInHeadlessMode(ChromeDriverService driverService, ChromeDriver chromeDriver) {
            final Logger logger = Logger.getLogger(SeleniumDriverDwpImpl.class);
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
}
