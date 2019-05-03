package com.essent.testing.dwp.pageobject.guided_flow.lead_create;

import com.essent.testing.dwp.pageobject.Form;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.tables.LeadInfo;

import java.util.List;

public class NewLeadPage extends BaseObjectPage implements Form {


    private LeadInfo leadInfo;
    private static final String box = "Bel me niet";

    public void createLead(List<List<String>> table) {
        ToggleImpl tg = new ToggleImpl();
        setCompanyName(table.get(1).get(0));
        setContactPerson(table.get(1).get(1), table.get(1).get(2));
        setGender(table.get(1).get(6));
        setTelephone(table.get(1).get(3));
        setMobile(table.get(1).get(4));
        setEmail(table.get(1).get(5));
        seleniumDriver.waitForRequestsToFinish();
        tg.switchOn(box);
        seleniumDriver.waitForRequestsToFinish();
        saveLead();
        seleniumDriver.waitForRequestsToFinish();
    }

    private void setGender(String gender) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//select[@id='gender-c-field']/option[@label='" + gender + "']")));
        seleniumDriver.waitForRequestsToFinish();
    }

    private boolean createLead2() {
        setCompanyName(leadInfo.getCompanyName());
        seleniumDriver.waitForRequestsToFinish();
        setContactPerson(leadInfo.getFirstName(), leadInfo.getSecondName());
        setTelephone(leadInfo.getTelephone());
        setMobile(leadInfo.getMobile());
        setEmail(leadInfo.getEmail());
        seleniumDriver.waitForRequestsToFinish();
        //optional checks can be here
        return true;
    }

    private void setCompanyName(String companyNumber) {
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("company-name-c-field")), companyNumber);
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath(".//*[@id='company_name_c']/div/autocomplete/ul/li[4]")));
        seleniumDriver.waitForRequestsToFinish();
    }

    public void saveLead() {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(findElementWhenVisible(By.id("primaryButton")));
    }

    private void setContactPerson(String contactPersonName, String contactPersonLastName) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("first-name-field")), contactPersonName);
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("last-name-field")), contactPersonLastName);
    }

    private WebElement getTelephoneElement() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field"));
    }

    private void setTelephone(String telephone) {
        seleniumDriver.waitForRequestsToFinish();
        getTelephoneElement().clear();
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(getTelephoneElement(), telephone);
    }

    private void setMobile(String mobile) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-mobile-phone-contact-details-type-phone-contact-details-value-field")),
            mobile);
    }

    private WebElement getEmailElement(){
        return seleniumDriver.findElementWhenVisible(By.id("leads-contact-details-contact-details-type-email-contact-details-value-field"));
    }

    private void setEmail(String email) {
        seleniumDriver.waitForRequestsToFinish();
        getEmailElement().clear();
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(getEmailElement(),email);
    }

    public void setLead(LeadInfo leadInfo) {
        this.leadInfo = leadInfo;
    }

    @Override
    public boolean fillInFormData() {
        return createLead2();
    }

}
