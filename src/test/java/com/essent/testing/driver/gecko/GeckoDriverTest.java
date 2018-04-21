package com.essent.testing.driver.gecko;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

public class GeckoDriverTest {


    private static final String GECKO_DRIVER_PROPERTY_KEY = "webdriver.gecko.driver";
    private static final String FIREFOX_PROFILE_PROPERTY_KEY = "firefox.profile.path";

    private static final String PATH_TO_GECKO_DRIVER = "C:/Users/dmitr/scoop/apps/geckodriver/0.19.1/geckodriver.exe";
    private static final String PATH_TO_FIREFOX_PROFILE = "C:/Users/dmitr/AppData/Local/Mozilla/Firefox/Profiles/DWP Testing";

    public static void main(String[] args) throws MalformedURLException {

        if (StringUtils.isEmpty(System.getProperty(GECKO_DRIVER_PROPERTY_KEY))) {
            System.setProperty("webdriver.gecko.driver", "C:/Users/dmitr/scoop/apps/geckodriver/0.19.1/geckodriver.exe");
        }
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        String firefoxProfile = System.getProperty(FIREFOX_PROFILE_PROPERTY_KEY);
        //firefoxOptions.setCapability("marionette", false);
        //setupFirefoxProfile(firefoxOptions, firefoxProfile);
        WebDriver driver = new FirefoxDriver(firefoxOptions);

        Wait<WebDriver> wait = new WebDriverWait(driver, 3000);
        final String url = "https://www.google.com/";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            driver.navigate().to(url);
        } finally {
            driver.close();
        }
    }

    private static void setupFirefoxProfile(FirefoxOptions firefoxOptions, String firefoxProfile) {
        firefoxProfile = StringUtils.isEmpty(firefoxProfile) ? PATH_TO_FIREFOX_PROFILE : firefoxProfile;
        ProfilesIni profiles = new ProfilesIni();
        firefoxOptions.addArguments("-profile", firefoxProfile);
        FirefoxProfile dwp_testing = profiles.getProfile("DWP Testing");
        dwp_testing.setAcceptUntrustedCertificates(true);
        firefoxOptions.setProfile(dwp_testing);
    }
}
