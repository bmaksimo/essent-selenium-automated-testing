package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.BaseObject;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LeadPage extends BaseObject {
    //TODO: Change Hard Coded data with Test Data
    private final static String contactPerson = "Test";
    private final static String companyNumber = "BE0531816752";
    private final static String telephone = "+32 498 12 34 56";
    private final static String mobile = "+32 000 00 00 00";
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
        waitForRequestsToFinish();
        findElementWhenVisible(By.xpath("//span[@class='icon-plus']")).click();
    }

    public void createLead(String companyName) {
        setCompanyNumber(companyNumber);
        waitForRequestsToFinish();
        setCompanyName(companyName);
        waitForRequestsToFinish();
        setContactPerson(contactPerson);
        setAddress(street, number, number, number, postalCode, city);
        setTelephone(telephone);
        setMobile(mobile);
        setEmail(email);
    }

    private void setCompanyName(String companyNumber) {
        findElementWhenVisible(By.id("company-name-c-field")).click();
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("company-name-c-field")).clear();
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("company-name-c-field")).sendKeys(companyNumber);
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("company-name-c-field")).click();
        waitForRequestsToFinish();
    }

    private void setContactPerson(String contactPerson) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("first-name-field")).sendKeys(contactPerson);
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("last-name-field")).sendKeys(contactPerson);
        waitForRequestsToFinish();
    }

    private void setCompanyNumber(String contactPerson) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("company-number-c-field")).sendKeys(contactPerson);
        waitForRequestsToFinish();
    }

    private void setTelephone(String telephone) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field")).sendKeys(telephone);
        waitForRequestsToFinish();
    }

    private void setMobile(String mobile) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-mobile-phone-contact-details-type-phone-contact-details-value-field")).sendKeys(mobile);
        waitForRequestsToFinish();
    }

    private void setEmail(String email) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("leads-contact-details-contact-details-type-email-contact-details-value-field")).sendKeys(email);
        waitForRequestsToFinish();
    }

    private void setAddress(String street, String houseNumber, String addition, String boxNumber, String postalCode, String city) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("address-street-field")).sendKeys(street);
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("address-number-field")).sendKeys(houseNumber);
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("address-addition-field")).sendKeys(addition);
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("address-bus-field")).sendKeys(boxNumber);
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("address-postalcode-field")).sendKeys(postalCode);
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("address-city-field")).sendKeys(city);
        waitForRequestsToFinish();
    }

    private void naceCode() {

    }
}
