package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class UpdateCustomerDetailsPage extends Component {

  private WebElement saveButtonForFinanceAndLegalSection() {
    return seleniumDriver.findElementWhenVisible(
        By.xpath("//*[contains(text(),' Finance & legal ')]/preceding-sibling::*[1]"));
  }

  public void clickOnSaveButtonForFinanceAndLegalSection() {
    seleniumDriver.waitAndClick(saveButtonForFinanceAndLegalSection());
  }
}
