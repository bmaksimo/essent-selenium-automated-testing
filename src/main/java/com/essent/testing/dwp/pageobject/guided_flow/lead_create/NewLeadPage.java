package com.essent.testing.dwp.pageobject.guided_flow.lead_create;

import com.essent.testing.dwp.pageobject.Form;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import org.openqa.selenium.By;
import stepdefinitions.dwp.tables.LeadInfo;

import java.util.List;

public class NewLeadPage extends BaseObjectPage implements Form {

    private LeadInfo leadInfo;
    private static final String box = "Bel me niet";



    public void createLead(List<List<String>> table) {
        ToggleImpl tg = new ToggleImpl();
        setCompanyName(table.get(1).get(0));
        seleniumDriver.waitForRequestsToFinish();
        setContactPerson(table.get(1).get(1), table.get(1).get(2));
        setGender(table.get(1).get(6));
        setTelephone(table.get(1).get(3));
        setMobile(table.get(1).get(4));
        setEmail(table.get(1).get(5));
        seleniumDriver.waitForRequestsToFinish();
        tg.clickCheckbox(box);
        seleniumDriver.waitForRequestsToFinish();
        saveLead();
        seleniumDriver.waitForRequestsToFinish();
    }

    private void setGender(String gender) {
        seleniumDriver.waitForRequestsToFinish();
        findElementWhenVisible(By.xpath("//select[@id='gender-c-field']/option[@label='" + gender + "']")).click();
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
        findElementWhenVisible(By.xpath(".//*[@id='company_name_c']/div/autocomplete/ul/li[4]")).click();
        seleniumDriver.waitForRequestsToFinish();
    }

    public void saveLead() {
        seleniumDriver.waitForRequestsToFinish();
        findElementWhenVisible(By.id("primaryButton")).click();
    }

    private void setContactPerson(String contactPersonName, String contactPersonLastName) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("first-name-field")), contactPersonName);
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("last-name-field")), contactPersonLastName);
    }

    private void setTelephone(String telephone) {
        seleniumDriver.waitForRequestsToFinish();
        findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field")).clear();
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field")),
            telephone);
    }

    private void setMobile(String mobile) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("leads-contact-details-contact-details-phone-type-mobile-phone-contact-details-type-phone-contact-details-value-field")),
            mobile);
    }

    private void setEmail(String email) {
        seleniumDriver.waitForRequestsToFinish();
        findElementWhenVisible(By.id("leads-contact-details-contact-details-type-email-contact-details-value-field")).clear();
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("leads-contact-details-contact-details-type-email-contact-details-value-field")),
            email);
    }

    public void setLead(LeadInfo leadInfo) {
        this.leadInfo = leadInfo;
    }

    @Override
    public boolean fillInFormData() {
        return createLead2();
    }
}
