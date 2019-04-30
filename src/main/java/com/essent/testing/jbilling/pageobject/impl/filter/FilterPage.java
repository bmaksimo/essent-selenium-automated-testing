package com.essent.testing.jbilling.pageobject.impl.filter;

import com.essent.testing.jbilling.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FilterPage extends Component {

  private static final String XPATH_CONTAINS_TEXT_TEMPLATE =
      "//div[span[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'${text}')]]//input";

  public boolean filterBy(String label, String value) {
    String query = createQuery(XPATH_CONTAINS_TEXT_TEMPLATE, "text", label);
    WebElement we = seleniumDriver.findElementWhenVisible(By.xpath(query));
    if (we != null) {
      seleniumDriver.waitAndSendKeys(we, value);
      return true;
    }
    return false;
  }

  public boolean clickFilterButton(String label) {
    WebElement we =
        seleniumDriver.findElementWhenVisible(
            By.xpath(
                "//a[span[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"
                    + label
                    + "')]]"));
    if (we != null) {
      seleniumDriver.waitAndClick(we);
      return true;
    }
    return false;
  }
}
