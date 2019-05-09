package com.essent.testing.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;


public class SeleniumPage
{
  protected WebDriver driver;

  public SeleniumPage(WebDriver driver)
  {
    super();
    this.driver = driver;
  }

  public WebDriver getDriver()
  {
    return driver;
  }

  public void setDriver(WebDriver driver)
  {
    this.driver = driver;
  }

  protected void waitForReady() {
    waitForReady(200);
  }

  /**
   * For some steps we must use a hardcoded delay.
   * It should be avoided and should be the last resort.
   * This method will allow you to provide user
   *  defined wait time.
   */
  private void waitForReady(long milliseconds)
  {
    try {
      Thread.sleep(milliseconds);
    }
    catch (final InterruptedException e) {
      e.printStackTrace();
    }
    new WebDriverWait(driver, 180).until(new ExpectedCondition<Boolean>()
    {
              @Override
      public Boolean apply(WebDriver driver)
      {
                final JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Boolean) js
                .executeScript("return window.jQuery != undefined && jQuery.active === 0");
              }
            });
  }


  protected void dealWithElement(String id, String value) {

    WebElement element = waitAndPollUntilElementIsFound(id, 30, 5);
    if (value.compareToIgnoreCase("click") == 0) {
      try {
        element.click();
      }catch(WebDriverException e){element.sendKeys(Keys.ENTER);}
    } else {
      // Checking if text field
      try {
        element.sendKeys(value);
      } catch (WebDriverException e) {
        try {
          // Checking if drop down
          new Select(element).selectByVisibleText(value);
        } catch (UnexpectedTagNameException ue) {
          // dropdown element select is one level below
          WebElement selectElement = element.findElement(By.xpath(".//select"));
          try {
            new Select(selectElement).selectByVisibleText(value);
          } catch (WebDriverException nse) {
            new Select(selectElement).selectByValue(value);
          }
        }
      }
    }
  }

  private WebElement waitAndPollUntilElementIsFound(String id, int totalTimeoutInSeconds, int pollTimeoutInSeconds){
    waitForReady();
    WebElement element = createStubbornWait(totalTimeoutInSeconds,pollTimeoutInSeconds).until(driver1 -> {
      List <WebElement> elements = driver1.findElements(By.xpath("//*[contains(@id, '" + id + "')]"));
                  return elements.get(elements.size() - 1);
                });
    return element;
  }

  private Wait<WebDriver> createStubbornWait(int totalTimeoutInSeconds, int pollTimeoutInSeconds) {
    return new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(totalTimeoutInSeconds))
        .pollingEvery(Duration.ofSeconds(pollTimeoutInSeconds))
        .ignoring(org.openqa.selenium.NoSuchElementException.class)
        .ignoring(StaleElementReferenceException.class);
  }

}
