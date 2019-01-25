package com.essent.testing.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import java.time.Duration;
import java.util.*;

/**
 * This class is a wrapper around the selenium webdriver.
 *
 * @author Peter Wessels
 * @author Dmitry Che
 */
public interface SeleniumDriver { //implements JavascriptExecutor, JavascriptTestRunner {

    void setUp();
    WebDriver createWebDriver();
    void tearDown();
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
    <V> void waitForExpectedCondition(final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds, final long sleepInMillis);
    Set<Cookie> getCookies();

    //    //Odoo
//    private static final String DEFAULT_DOWNLOAD_LOCATION = ResourceUtil.toPath(File.separator + "data" + File.separator + "odoo" + File.separator);
//    private interface WebDriverInitializingStrategy {
//
//        default WebDriver createWebDriver() {
//            ChromeOptions options = new ChromeOptions();
//            options.addArguments("chrome.switches", "--disable-extensions");
//            options.addArguments("window-size=1920,1080");
//            options.addArguments("--incognito");
//
//
//            //Odoo
//            String userDataPath = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_USER_DATA_PATH);
//            if (StringUtils.isNotEmpty(userDataPath)) {
//                options.addArguments("user-data-dir=" + userDataPath);
//                try {
//                    FileUtils.cleanDirectory(new File((userDataPath)));
//                    logger.info(" - CLEAN_DIR: " + userDataPath);
//                } catch (IOException e) {
//                    logger.error(" - CLEAN_DIR: " + userDataPath);
//                }
//            }
//            //
//            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
//            options.addArguments("--no-sandbox"); // Bypass OS security model
//            logger.info(" - OPTIONS: " + options.toString());
//
//            //Odoo
//            setUpDefaultFileDownloadLocation(options);
//
//            ChromeDriver chromeDriver;
//
//            String headless = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_HEADLESS);
//            if (StringUtils.isNotEmpty(headless)) {
//                options.setHeadless(true);
//                String windowSize = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_HEADLESS_WINDOW_SIZE);
//                if (StringUtils.isNotEmpty(windowSize)) {
//                    options.addArguments("window-size=" + windowSize);
//                } else {
//                    options.addArguments("--start-maximized");
//                }
//                ChromeDriverService driverService = ChromeDriverService.createDefaultService();
//                chromeDriver = new ChromeDriver(driverService, options);
//                //DWP
//                enableFileDownloadInHeadlessMode(driverService, chromeDriver);
//            } else {
//                options.addArguments("--start-maximized");
//                chromeDriver = new ChromeDriver(options);
//
//            }
//            chromeDriver.manage().timeouts().implicitlyWait(3, TimeUnit.MINUTES).setScriptTimeout(5, TimeUnit.MINUTES);
//            return chromeDriver;
//        }
//
//        //Odoo
//        default void setUpDefaultFileDownloadLocation(ChromeOptions options) {
//            HashMap<String, Object> chromePrefs = new HashMap<>();
//            chromePrefs.put("profile.default_content_settings.popups", 0);
//            chromePrefs.put("download.default_directory", DEFAULT_DOWNLOAD_LOCATION);
//            options.setExperimentalOption("prefs", chromePrefs);
//        }
//
//        //Odoo
//        default void enableFileDownloadInHeadlessMode(ChromeDriverService driverService, ChromeDriver chromeDriver) {
//            Map<String, Object> commandParams = new HashMap<>();
//            commandParams.put("cmd", "Page.setDownloadBehavior");
//            Map<String, String> params = new HashMap<>();
//            params.put("behavior", "allow");
//            params.put("downloadPath", DEFAULT_DOWNLOAD_LOCATION);
//            commandParams.put("params", params);
//            ObjectMapper objectMapper = new ObjectMapper();
//            HttpClient httpClient = HttpClientBuilder.create().build();
//            String command = null;
//            try {
//                command = objectMapper.writeValueAsString(commandParams);
//            } catch (JsonProcessingException e) {
//                logger.error("Object serialization has failed. Reason: " + e.getMessage());
//            }
//            String remoteBrowserUrl = driverService.getUrl().toString() + "/session/" + chromeDriver.getSessionId() + "/chromium/send_command";
//            HttpPost request = new HttpPost(remoteBrowserUrl);
//            request.addHeader("content-type", "application/json");
//            try {
//                request.setEntity(new StringEntity(command));
//            } catch (UnsupportedEncodingException e) {
//                logger.error("Error on HttpPost request creation. Reason: " + e.getMessage());
//            }
//            try {
//                httpClient.execute(request);
//            } catch (IOException e2) {
//                logger.error(" - ERROR_CONFIGURE_HEADLESS_DOWNLOAD: request" + request.toString() + "comand: " + command);
//            }
//        }
//
//        class FirefoxWebdriverInitialingStrategy implements WebDriverInitializingStrategy {
//            @Override
//            public WebDriver createWebDriver() {
//                FirefoxOptions options = new FirefoxOptions();
//                //Marionette capability should be set to false in CentOS
//                options.setCapability("marionette", true);
//                String firefoxProfile = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_FIREFOX_PROFILE);
//                if (StringUtils.isNotEmpty(firefoxProfile)) {
//                    options.addArguments("-profile", firefoxProfile);
//                }
//                return new FirefoxDriver(options);
//            }
//        }
//    }


//    private WebDriver createWebDriver() {
//        String location = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_CHROME_DRIVER);
//        WebDriver driver;
//        logger.info("STEP:");
//        logger.info(" - ACTION: INIT_SELENIUM_DRIVER");
//        if (StringUtils.isNotEmpty(location)) {
//            driver = new WebDriverInitializingStrategy() {
//            }.createWebDriver();
//        } else {
//            location = ConfigProvider.getProperty(ConfigKey.WEBDRIVER_GECKO_DRIVER);
//            if (StringUtils.isNotEmpty(location)) {
//                driver = new WebDriverInitializingStrategy.FirefoxWebdriverInitialingStrategy().createWebDriver();
//            } else {
//                logger.info(" - RESULT: ERROR, Selenium Webdriver location undefined.");
//                throw new IllegalArgumentException("Error: undefined Selenium Webdriver location.");
//            }
//        }
//        logger.info(" - RESULT: Selenium Webdriver location: " + location);
//        return driver;
//    }
}
