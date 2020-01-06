package com.essent.testing.dwp.pageobject.select;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.Select;

public class SelectElement extends Component {

  private static final String REPLACEMENT_KEY = "replacement_key";
  private static final String SELECT_ELEMENT =
      "//validation-wrapper[@label='${" + REPLACEMENT_KEY + "}']//select";

  public void selectByText(String label, String text) throws Exception {
    try {
      validateSelection(label, text);
    } catch (StaleElementReferenceException e) {
      validateSelection(label, text);
    }
  }

  private void validateSelection(String label, String text) throws Exception {
    String selectPath = createQuery(SELECT_ELEMENT, REPLACEMENT_KEY, label);
    Select selectElement = new Select(findElementWithRetries(By.xpath(selectPath), 10));
    seleniumDriver.waitForRequestsToFinish();
    Sleeper.sleepTightInSeconds(2);
    selectElement.selectByVisibleText(text);
    seleniumDriver.waitForRequestsToFinish();
    Sleeper.sleepTightInSeconds(2);
    assertSelectedElement(selectPath, text);
  }

  private void assertSelectedElement(String selectPath, String text) throws Exception {
    Select currentSelect = new Select(findElementWithRetries(By.xpath(selectPath), 10));
    String currentlySelectedElement = currentSelect.getFirstSelectedOption().getText();
    Assert.assertTrue(
        "Currently selected element is not " + text + " but " + currentlySelectedElement,
        text.equalsIgnoreCase(currentlySelectedElement));
  }
}
