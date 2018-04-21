package com.essent.testing.driver.chrome;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

public class ChromeDriverTest {


    private static final String GECKO_DRIVER_PROPERTY_KEY = "webdriver.gecko.driver";
    private static final String FIREFOX_PROFILE_PROPERTY_KEY = "firefox.profile.path";

    private static final String PATH_TO_GECKO_DRIVER = "C:/Users/dmitr/scoop/apps/geckodriver/0.19.1/geckodriver.exe";
    private static final String PATH_TO_FIREFOX_PROFILE = "C:/Users/dmitr/AppData/Local/Mozilla/Firefox/Profiles/DWP Testing";

    public static void main(String[] args) throws MalformedURLException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("chrome.switches", "--disable-extensions");
        options.addArguments("no-sandbox");
        String userDataPath = System.getProperty("chrome.user.data.path");
        if(StringUtils.isNotEmpty(userDataPath)) {
            options.addArguments("user-data-dir=" + userDataPath);
        }
        WebDriver driver = new ChromeDriver(options);
        Wait<WebDriver> wait = new WebDriverWait(driver, 3000);
        final String url = "https://www.google.com/";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            driver.navigate().to(url);
        } finally {
            driver.close();
        }
    }
}
