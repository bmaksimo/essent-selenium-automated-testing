package com.essent.testing.selenium;

import com.essent.automation.util.Sleeper;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertNotNull;


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

  public void waitForReady()  {
    waitForReady(200);
  }

  /*For some steps we must use a hardcoded delay. It should be avoided and
    should be the last resort. This method will allow you to provide user
    defined wait time.
   */
  public void waitForReady(long milliseconds)
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

  public void SetTextInField(String id, String Text)
  {

    final WebElement element = driver.findElement(By.id(id));

    Assert.assertNotNull(element);
    element.clear();

    element.sendKeys(Text);
  }

  public void clickSave(WebDriver driver)

  {
    final WebElement submitElement = driver.findElement(By.id("SAVE_FOOTER"));
    assertNotNull(submitElement);
    Sleeper.sleepTight(500);
    submitElement.click();
    waitForReady();

  }

  public void findByNameAndClick(
          WebElement webElement, String name, By byFirst, By bySecond)
  {
    final WebElement we = findByName(webElement, name, byFirst, bySecond);
    if (we == null)
      throw new NoSuchElementException(name);

    we.click();
  }

  public WebElement findByName(
          WebElement webElement, String name, By byFirst, By bySecond)
  {
    final List<WebElement> we_collection = webElement.findElements(byFirst);
    for (final WebElement we : we_collection) {
      final WebElement result = findByName(we, name, bySecond);
      if (result != null)
        return result;
    }

    return null;
  }

  public WebElement findByName(WebElement webElement, String name, By by)
  {
    final List<WebElement> we_collection = webElement.findElements(by);
    for (final WebElement we : we_collection)
      if (name == null || we.getText().startsWith(name))
        return we;

    return null;
  }

  /*
    This method is written to handle the cases where id is available
    and no other information about the element exists.
    For ex- temp.put("company_name_c", "Tuincentrum Janssens");
    Ids have changed and there is no way to determine what kind of element it is.

    This method will still find out which element type is most relevant and
    will perform the actions like- setting text, selecting a value from dropdown
    , setting a checkbox or doing a click.

    Some examples-
    temp.put("company_name_c", "Tuincentrum Janssens");
    temp.put("legal_form_c", "string:bvba");
    temp.put("primaryButton", "click");
   */

  public void dealWithElement(String id, String value){

    WebElement element = waitAndPollUntilElementIsFound(id,30,5);
    if (value.compareToIgnoreCase("click") == 0){
      try {
        element.click();
      }catch(WebDriverException e){element.sendKeys(Keys.ENTER);}
    }else{
      //Checking if text field
      try {
        element.sendKeys(value);
      }catch(WebDriverException e) {
        try{
          //Checking if drop down
          new Select(element).selectByVisibleText(value);
        }catch(UnexpectedTagNameException ue){
          //dropdown element select is one level below
          WebElement selectElement = element.findElement(By.xpath(".//select"));
          try {
            new Select(selectElement).selectByVisibleText(value);
          }catch(WebDriverException nse){
            new Select(selectElement).selectByValue(value);
          }
        }
      }
    }
  }

  public WebElement waitAndPollUntilElementIsFound(String id, int totalTimeoutInSeconds, int pollTimeoutInSeconds){
    waitForReady();
    WebElement element = createStubbornWait(totalTimeoutInSeconds,pollTimeoutInSeconds).until(driver1 -> {
      List <WebElement> elements = driver1.findElements(By.xpath("//*[contains(@id, '" + id + "')]"));
      return elements.get(elements.size()-1);
    });
    return element;
  }

  public Wait<WebDriver> createStubbornWait(int totalTimeoutInSeconds, int pollTimeoutInSeconds){
    return new FluentWait<WebDriver>(driver)
            .withTimeout(totalTimeoutInSeconds, SECONDS)
            .pollingEvery(pollTimeoutInSeconds, SECONDS)
            .ignoring(org.openqa.selenium.NoSuchElementException.class)
            .ignoring(StaleElementReferenceException.class);
  }


}
