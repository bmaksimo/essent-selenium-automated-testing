package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.service;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CaseDetailsPage extends Component {
  public String getComplaintText() {
    return seleniumDriver.findElementWhenVisible(By.id("description")).getText();
  }

  private WebElement priorityField() {

    return seleniumDriver.findElementWhenVisible(By.id("priority-field"));
  }

  public boolean checkIfPriorityIsHigh() {
    return priorityField().getText().equalsIgnoreCase("Hoog");
  }

  public String getSolutionFieldText() {
    return seleniumDriver.findElementWhenVisible(By.id("resolution-field")).getText();
  }
}
