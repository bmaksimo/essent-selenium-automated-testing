package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.BaseObject;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LeadPage extends BaseObject {
    //TODO: Look at Dmitry Quote implementation
    private final static String contactPersonName = "Strahinja";
    private final static String contactPersonLastName = "Vuckovic";
    private final static String telephone = "+32 78 15 79 79";
    private final static String mobile = "+32 498 12 34 56";
    private final static String email = "test@test.com";

    public LeadPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public void plusAddLead() {
        findElementWhenVisible(By.xpath("//span[@class='icon-plus']")).click();
    }

    public void createLead(String companyName) {
        setCompanyName(companyName);
        waitForRequestsToFinish();
        setContactPerson(contactPersonName, contactPersonLastName);
        setTelephone(telephone);
        setMobile(mobile);
        setEmail(email);
        waitForRequestsToFinish();
        saveLead();
        waitForRequestsToFinish();
    }

    private void setCompanyName(String companyNumber) {
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("company-name-c-field")), companyNumber);
        findElementWhenVisible(By.xpath("//b[.='" + companyNumber + " - BE0476243769 - Veldkant 7 Kontich']")).click();
        waitForRequestsToFinish();
    }

    private void saveLead() {
        findElementWhenVisible(By.id("primaryButton")).click();
    }

    private void setContactPerson(String contactPersonName, String contactPersonLastName) {
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("first-name-field")), contactPersonName);
        waitForRequestsToFinish();
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
}
