package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.BaseObject;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LeadPage extends BaseObject {
    //TODO: Change Hard Coded data with Test Data
    private final static String contactPerson = "Test";
    private final static String companyNumber = "BE0531816752";
    private final static String telephone = "+32 78 15 79 79";
    private final static String mobile = "+32 498 12 34 56";
    private final static String email = "test@test.com";
    private final static String street = "Testeltsesteenweg";
    private final static String number = "1";
    private final static String postalCode = "3201";
    private final static String city = "LANGDORP";

    public LeadPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public LeadPage(WebElement element, SeleniumDriver seleniumDriver) {
        super(element, seleniumDriver);
    }

    public void plusAddLead() {
        findElementWhenVisible(By.xpath("//span[@class='icon-plus']")).click();
    }

    public void createLead(String companyName) {
        setCompanyName(companyName);
        waitForRequestsToFinish();
        setContactPerson(contactPerson);
        waitForRequestsToFinish();
        setTelephone(telephone);
        waitForRequestsToFinish();
        setMobile(mobile);
        waitForRequestsToFinish();
        setEmail(email);
        waitForRequestsToFinish();
        saveLead();
        waitForRequestsToFinish();
    }

    private void setCompanyName(String companyNumber) {
        findElementWhenVisible(By.id("company-name-c-field")).sendKeys(companyNumber);
        findElementWhenVisible(By.xpath("//b[.='" + companyNumber + " - BE0476243769 - Veldkant 7 Kontich']")).click();
        waitForRequestsToFinish();
    }

    private void saveLead() {
        findElementWhenVisible(By.id("primaryButton")).click();
    }

    private void setContactPerson(String contactPerson) {
        findElementWhenVisible(By.id("first-name-field")).sendKeys(contactPerson);
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("last-name-field")).sendKeys(contactPerson);
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("first-name-field")).sendKeys(contactPerson);
        waitForRequestsToFinish();
    }

    private void setTelephone(String telephone) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field")).click();
        findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field")).sendKeys(telephone);
        System.out.println("JEBEM LI GA");
    }

    private void setMobile(String mobile) {
        findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-mobile-phone-contact-details-type-phone-contact-details-value-field")).sendKeys(mobile);
    }

    private void setEmail(String email) {
        findElementWhenVisible(By.id("leads-contact-details-contact-details-type-email-contact-details-value-field")).sendKeys(email);
    }
}
