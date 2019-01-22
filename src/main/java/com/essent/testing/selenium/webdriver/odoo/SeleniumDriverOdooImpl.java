package com.essent.testing.selenium.webdriver.odoo;

import com.essent.automation.core.WebDriverWait;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.selenium.webdriver.AbstractSeleniumDriver;
import com.essent.testing.selenium.webdriver.SeleniumDriver;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
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

/**
 * This class is a wrapper around the selenium webdriver.
 *
 * @author Sonja Novakovic
 * @author Dmitry Che
 */
public class SeleniumDriverOdooImpl extends AbstractSeleniumDriver implements SeleniumDriver {


    @Override
    public void waitForRequestsToFinish() {
  
    }

}
