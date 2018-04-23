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
        final String url = "https://www.verbix.com/";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            driver.navigate().to(url);
        } finally {
            driver.close();
        }
    }
}
