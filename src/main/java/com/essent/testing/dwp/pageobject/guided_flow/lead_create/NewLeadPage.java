package com.essent.testing.dwp.pageobject.guided_flow.lead_create;

import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NewLeadPage extends BaseObjectPage {

    private final static String COMPANY_NAME = "company-name-c-field";
    private final static String FIRST_NAME = "first-name-field";
    private final static String LAST_NAME = "last-name-field";

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
        seleniumDriver.waitForRequestsToFinish();
        Optional<WebElement> companyNameField = seleniumDriver.findElementOptional(By.id(COMPANY_NAME));
        companyNameField.ifPresent(WebElement::click);
        seleniumDriver.waitForRequestsToFinish();
        companyNameField.ifPresent(cnField -> seleniumDriver.waitAndSendKeys(cnField, companyNumber));
        seleniumDriver.waitForRequestsToFinish();
    }


    private void fillInContactPerson(String contactPersonName, String contactPersonLastName) {
        seleniumDriver.waitForRequestsToFinish();
        Optional<WebElement> firstNameField = seleniumDriver.findElementOptional(By.id(FIRST_NAME));
        firstNameField.ifPresent(WebElement::click);
        seleniumDriver.waitForRequestsToFinish();
        firstNameField.ifPresent(firstName -> seleniumDriver.waitAndSendKeys(firstName, contactPersonName));

        seleniumDriver.waitForRequestsToFinish();

        Optional<WebElement> lastNameField = seleniumDriver.findElementOptional(By.id(LAST_NAME));
        lastNameField.ifPresent(WebElement::click);
        seleniumDriver.waitForRequestsToFinish();
        lastNameField.ifPresent(lastName -> seleniumDriver.waitAndSendKeys(lastName, contactPersonLastName));
        seleniumDriver.waitForRequestsToFinish();
    }

}
