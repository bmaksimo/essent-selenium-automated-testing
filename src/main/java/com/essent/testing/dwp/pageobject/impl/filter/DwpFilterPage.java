package com.essent.testing.dwp.pageobject.impl.filter;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;

public class DwpFilterPage extends Component {

  public void resetFilter() {
    seleniumDriver.waitForRequestsToFinish();
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(
            By.xpath("(//div[@class=\"form__footer\"])[1]//button ")));
  }

  public void clickOnFilter() {
    seleniumDriver.waitForRequestsToFinish();
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(By.xpath("//top-actions/div/a[2]")));
  }
}
