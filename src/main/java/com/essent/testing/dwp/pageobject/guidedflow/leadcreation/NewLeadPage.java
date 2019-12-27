package com.essent.testing.dwp.pageobject.guidedflow.leadcreation;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class NewLeadPage extends BaseObjectPage {

  private static final String COMPANY_NAME = "company-name-c-field";
  private static final String FIRST_NAME = "first-name-field";
  private static final String LAST_NAME = "last-name-field";

  public void createLead(List<Map<String, String>> table) {
    seleniumDriver.waitForRequestsToFinish();

    String companyNumber = table.get(0).get("companyName");
    String contactPersonFirstName = table.get(0).get("firstName");
    String contactPersonLastName = table.get(0).get("secondName");

    fillInCompanyName(companyNumber);
    seleniumDriver.waitForRequestsToFinish();
    fillInContactPerson(contactPersonFirstName, contactPersonLastName);
    seleniumDriver.waitForRequestsToFinish();
  }

  private void fillInCompanyName(String companyNumber) {
    WebElement companyNameField = seleniumDriver.findElement(By.id(COMPANY_NAME));
    companyNameField.sendKeys(companyNumber);
    seleniumDriver.waitForRequestsToFinish();
  }

  private void fillInContactPerson(String contactPersonName, String contactPersonLastName) {
    seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id(FIRST_NAME)), contactPersonName);
    Sleeper.sleepTightInSeconds(2);
    seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id(FIRST_NAME)), contactPersonName);
    Sleeper.sleepTightInSeconds(2);
    seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id(LAST_NAME)), contactPersonLastName);
  }
}
