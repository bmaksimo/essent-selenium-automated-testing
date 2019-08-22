package com.essent.testing.dwp.pageobject.guided_flow.lead_create;


import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import org.openqa.selenium.By;


import java.util.List;
import java.util.Map;

public class NewLeadPage extends BaseObjectPage {

    private final static String COMPANYNAME="company-name-c-field";
//    private final static String COMPANYNAMEBUTTON=".//*[@id='company_name_c']/div/autocomplete/ul";
//    private final static String COMPANYNAMEBUTTON=".//*[@id='company_name_c']/div/autocomplete/ul/li[4]";
    private final static String FIRSTNAME="first-name-field";
    private final static String LASTNAME="last-name-field";

  //TODO Remove locale-specific hardcode.
  // The project must support official Belgian languages.
  // Locale-specific elements of web element locators must be parameterized.
  // This is basic rule!

    public void createLead(List<Map<String,String>> table) {
        ToggleImpl tg = new ToggleImpl();
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
//        Sleeper.sleepTightInSeconds(2);
        fillInContactPerson(contactPersonFirstName, contactPersonLastName);
    }

    private void fillInCompanyName(String companyNumber) {
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id(COMPANYNAME)), companyNumber);
//        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath(COMPANYNAMEBUTTON)));
        seleniumDriver.waitForRequestsToFinish();
    }


    private void fillInContactPerson(String contactPersonName, String contactPersonLastName) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id(FIRSTNAME)), contactPersonName);
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id(LASTNAME)), contactPersonLastName);
    }

}
