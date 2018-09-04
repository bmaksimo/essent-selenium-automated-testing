package com.essent.testing.dwp.pageobject.b2b_regression;

import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseUtil extends Component {

    protected final SeleniumDriver seleniumDriver;
    private static Properties propertiesFiles;
    private static final String xPath = "//";

    public BaseUtil(SeleniumDriver seleniumDriver, SeleniumDriver seleniumDriver1) {
        super(seleniumDriver);
        this.seleniumDriver = seleniumDriver1;
    }

    public SeleniumDriver getSeleniumDriver() {
        return seleniumDriver;
    }

    private static Properties loadProperties(String propertiesLocation) {
        InputStream propertiesFile;
        Properties properties = new Properties();
        try {
            propertiesFile = new FileInputStream(propertiesLocation);
            properties.load(propertiesFile);
            propertiesFile.close();
        } catch (IOException e) {
            final String message = "Something went wrong while trying to load properties from: " + propertiesLocation;
            throw new RuntimeException(message, e);
        }

        return properties;
    }

    protected Properties findLocators(String locator) {

        propertiesFiles = loadProperties("src\\test\\resources\\environmentspecific\\LOCATORS.properties");
        return propertiesFiles;
    }

    public void click(String locator) {
        locateElement(locator).click();
    }

    public WebElement locateElement(String locator) {
        WebElement sDriver;

        Properties loc = findLocators(locator);
        final String value = loc.getProperty(locator);

        if (value.startsWith(xPath)) {
            sDriver = getSeleniumDriver().findElementOrNull(By.xpath(value));
        } else {
            sDriver = getSeleniumDriver().findElementOrNull(By.id(value));
        }

        return sDriver;
    }
}
