package com.essent.testing.dwp.pageobject.select;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class SelectElement extends Component {

  private static final String REPLACEMENT_KEY = "replacement_key";
  private static final String SELECT_ELEMENT =
      "//validation-wrapper[@label='${" + REPLACEMENT_KEY + "}']//select";

  public void selectByText(String label, String text) throws Exception {
    String selectPath = createQuery(SELECT_ELEMENT, REPLACEMENT_KEY, label);
    Select selectElement = new Select(findElementWithRetries(By.xpath(selectPath), 10));
    selectElement.selectByVisibleText(text);
    seleniumDriver.waitForRequestsToFinish();
    String currentlySelectedElement = selectElement.getFirstSelectedOption().getText();
    Assert.assertTrue(
        "Currently selected element is not " + text + " but " + currentlySelectedElement,
        text.equalsIgnoreCase(currentlySelectedElement));
  }
}
