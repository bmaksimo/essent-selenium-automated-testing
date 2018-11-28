package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.Form;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import stepdefinitions.dwp.tables.LeadInfo;

import java.util.List;

public class LeadPage extends BaseObject implements Form {

    private LeadInfo leadInfo;
    private static final String box = "Bel me niet";
    public LeadPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public void plusAddLead() {
        findElementWhenVisible(By.xpath("//span[@class='icon-plus']")).click();
    }

    public void createLead(List<List<String>> table) {
        ToggleImpl tg= new ToggleImpl(seleniumDriver);
        setCompanyName(table.get(1).get(0));
        waitForRequestsToFinish();
        setContactPerson(table.get(1).get(1), table.get(1).get(2));
        setGender(table.get(1).get(6));
        setTelephone(table.get(1).get(3));
        setMobile(table.get(1).get(4));
        setEmail(table.get(1).get(5));
        waitForRequestsToFinish();
        tg.clickCheckbox(box);
        waitForRequestsToFinish();
        saveLead();
        waitForRequestsToFinish();
    }

    private void setGender(String gender) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.xpath("//select[@id='gender-c-field']/option[@label='" + gender + "']")).click();
        waitForRequestsToFinish();
    }

    private boolean createLead2() {
        setCompanyName(leadInfo.getCompanyName());
        waitForRequestsToFinish();
        setContactPerson(leadInfo.getFirstName(), leadInfo.getSecondName());
        setTelephone(leadInfo.getTelephone());
        setMobile(leadInfo.getMobile());
        setEmail(leadInfo.getEmail());
        waitForRequestsToFinish();
        //optional checks can be here
        return true;
    }

    private void setCompanyName(String companyNumber) {
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("company-name-c-field")), companyNumber);
        findElementWhenVisible(By.xpath(".//*[@id='company_name_c']/div/autocomplete/ul/li[4]")).click();
        waitForRequestsToFinish();
    }

    public void saveLead() {
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("primaryButton")).click();
    }

    private void setContactPerson(String contactPersonName, String contactPersonLastName) {
        waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("first-name-field")), contactPersonName);
        waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("last-name-field")), contactPersonLastName);
    }

    private void setTelephone(String telephone) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field")).clear();
        waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field")),
            telephone);
    }

    private void setMobile(String mobile) {
        waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-mobile-phone-contact-details-type-phone-contact-details-value-field")),
            mobile);
    }

    private void setEmail(String email) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("leads-contact-details-contact-details-type-email-contact-details-value-field")).clear();
        waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("leads-contact-details-contact-details-type-email-contact-details-value-field")),
            email);
    }

    public void validateCreatingLead(String name) {
        waitForRequestsToFinish();
        seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[.='" + name + "'][1]")).isDisplayed();
    }

    public void setLead(LeadInfo leadInfo) {
        this.leadInfo = leadInfo;
    }

    @Override
    public boolean fillInFormData() {
        return createLead2();
    }
}
