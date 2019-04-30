package com.essent.testing.selenium;

import com.essent.automation.core.WebDriverWait;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.function.Function;

public class JBillingSeleniumDriver extends SeleniumDriver {
  private static final Logger logger = Logger.getLogger(JBillingSeleniumDriver.class);

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

  public <V> void waitForExpectedCondition(
      final ExpectedCondition<?> expectedCondition,
      final long timeoutInSeconds,
      final long sleepInMillis) {
    final WebDriverWait driverWait = new WebDriverWait(driver, timeoutInSeconds, sleepInMillis);
    driverWait.until((Function<? super WebDriver, V>) expectedCondition);
  }

  private void waitForElement(WebElement element) {
    waitForExpectedCondition(ExpectedConditions.visibilityOf(element), 30, 5);
    waitForExpectedCondition(ExpectedConditions.elementToBeClickable(element), 30, 5);
  }

  public void waitForRequestsToFinish() {
    awaitJqueryNotActive(500);
  }
}
