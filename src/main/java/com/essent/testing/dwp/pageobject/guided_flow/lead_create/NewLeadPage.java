package com.essent.testing.dwp.pageobject.guided_flow.lead_create;

import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import org.openqa.selenium.By;
import java.util.List;
import java.util.Map;

public class NewLeadPage extends BaseObjectPage {

    private final static String COMPANYNAME="company-name-c-field";
    private final static String FIRSTNAME="first-name-field";
    private final static String LASTNAME="last-name-field";

    public void createLead(List<Map<String,String>> table) {
        seleniumDriver.waitForRequestsToFinish();
        String companyNumber = null;
        String contactPersonFirstName = null;
        String contactPersonLastName = null;

        for (int i = 0; i < table.size(); i++) {
            companyNumber = table.get(i).get("companyName");
            contactPersonFirstName = table.get(i).get("firstName");
            contactPersonLastName = table.get(i).get("secondName");
        }

        fillInCompanyName(companyNumber);
        seleniumDriver.waitForRequestsToFinish();
        fillInContactPerson(contactPersonFirstName, contactPersonLastName);
    }

    private void fillInCompanyName(String companyNumber) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id(COMPANYNAME)), companyNumber);
    }


    private void fillInContactPerson(String contactPersonName, String contactPersonLastName) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id(FIRSTNAME)), contactPersonName);
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id(LASTNAME)), contactPersonLastName);
    }

}
