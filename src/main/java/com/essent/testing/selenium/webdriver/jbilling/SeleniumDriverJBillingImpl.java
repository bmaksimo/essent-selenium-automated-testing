package com.essent.testing.selenium.webdriver.jbilling;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.selenium.webdriver.AbstractSeleniumDriver;
import com.essent.testing.selenium.webdriver.SeleniumDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

/**
 * This class is a wrapper around the selenium webdriver.
 *
 * @author Sonja Novakovic
 * @author Dmitry Che
 */
public class SeleniumDriverJBillingImpl extends AbstractSeleniumDriver implements SeleniumDriver {


    @Override
    public void waitForRequestsToFinish() {
  
    }

}
