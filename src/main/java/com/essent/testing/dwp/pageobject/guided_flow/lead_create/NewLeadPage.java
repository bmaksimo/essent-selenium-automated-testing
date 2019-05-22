package com.essent.testing.dwp.pageobject.guided_flow.lead_create;


import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import org.openqa.selenium.By;


import java.util.List;

public class NewLeadPage extends BaseObjectPage {

    private final static String COMPANYNAME="company-name-c-field";
    private final static String COMPANYNAMEBUTTON=".//*[@id='company_name_c']/div/autocomplete/ul/li[4]";
    private final static String FIRSTNAME="first-name-field";
    private final static String LASTNAME="last-name-field";

  //TODO Remove locale-specific hardcode.
  // The project must support official Belgian languages.
  // Locale-specific elements of web element locators must be parameterized.
  // This is basic rule!

    public void createLead(List<List<String>> table) {
        ToggleImpl tg = new ToggleImpl();
        fillInCompanyName(table.get(1).get(0));
        Sleeper.sleepTightInSeconds(2);
        fillInContactPerson(table.get(1).get(1), table.get(1).get(2));
    }

    private void fillInCompanyName(String companyNumber) {
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id(COMPANYNAME)), companyNumber);
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath(COMPANYNAMEBUTTON)));
        seleniumDriver.waitForRequestsToFinish();
    }


    private void fillInContactPerson(String contactPersonName, String contactPersonLastName) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id(FIRSTNAME)), contactPersonName);
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id(LASTNAME)), contactPersonLastName);
    }

}
