package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

import java.util.List;

public class LeadPage extends BaseObject {

    public LeadPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public void plusAddLead() {
        findElementWhenVisible(By.xpath("//span[@class='icon-plus']")).click();
    }

    public void createLead(List<List<String>> table) {
        setCompanyName(table.get(1).get(0));
        waitForRequestsToFinish();
        setContactPerson(table.get(1).get(1), table.get(1).get(2));
        setTelephone(table.get(1).get(3));
        setMobile(table.get(1).get(4));
        setEmail(table.get(1).get(5));
        waitForRequestsToFinish();
        saveLead();
        waitForRequestsToFinish();
    }

    private void setCompanyName(String companyNumber) {
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("company-name-c-field")), companyNumber);
        findElementWhenVisible(By.xpath(".//*[@id='company_name_c']/div/autocomplete/ul/li[2]")).click();
        waitForRequestsToFinish();
    }

    private void saveLead() {
        findElementWhenVisible(By.id("primaryButton")).click();
    }

    private void setContactPerson(String contactPersonName, String contactPersonLastName) {
        waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("first-name-field")), contactPersonName);
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("last-name-field")), contactPersonLastName);
    }

    private void setTelephone(String telephone) {
        waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field")),
            telephone);
    }

    private void setMobile(String mobile) {
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-mobile-phone-contact-details-type-phone-contact-details-value-field")),
            mobile);
    }

    private void setEmail(String email) {
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("leads-contact-details-contact-details-type-email-contact-details-value-field")),
            email);
    }

    public void validateCreatingLead(String name) {
        waitForRequestsToFinish();
        seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[.='" + name + "'][1]")).isDisplayed();
    }
}
