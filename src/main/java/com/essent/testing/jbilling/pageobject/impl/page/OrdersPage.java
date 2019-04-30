package com.essent.testing.jbilling.pageobject.impl.page;

import com.essent.testing.jbilling.pageobject.impl.Component;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OrdersPage extends Component {

  public boolean checkOrderTableNotEmpty() {
    seleniumDriver.waitForRequestsToFinish();
    List<WebElement> rows = seleniumDriver.findElements(By.xpath("//table[@id='orders']/tbody"));

    return CollectionUtils.isNotEmpty(rows);
  }

  public String checkValueNextToLabel(String label) {
    return seleniumDriver
        .findElementWhenVisible(
            By.xpath(
                "//table[@class='innerTable']//tr[td[contains(text(),'" + label + "')]]/td[2]"))
        .getText();
  }
}
