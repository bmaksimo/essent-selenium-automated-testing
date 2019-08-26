package com.essent.testing.dwp.pageobject.guided_flow.cupq;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.quote.QuoteCreationGuidedStep;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class NewQuotePage extends QuoteCreationGuidedStep {

    private static final String X_ELEMENT_SELECTOR = "//guidance-modal//div[@class = 'modal__header']/a";
    private static final String CONFIRM_SIGNIN_PLACE = "//*[@id=\"accounts|aos_quotes|sign_location_c\"]/div[1]/input";

    public void clickOnX() {
        seleniumDriver.waitForRequestsToFinish();
        WebElement xElement = seleniumDriver.findElementWhenVisible(By.xpath(X_ELEMENT_SELECTOR));
        seleniumDriver.waitAndClick(xElement);
    }

    public void selectItemLegalForm() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"legal-form-c-field\"]/option[2]")));
    }

    public void selectGender() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"gender-c-field\"]/option[2]")));
    }

    public void getEmail(String emailContract) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"leads-contact-details-contact-details-type-email-contact-details-value-field\"]")), emailContract);
    }

    public void clickNaceCodeButton() {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"nace-code-c-field\"]")));
    }

    public void clickOnSearch() {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//div[@class = 'input__with-button']/input")));
    }

    public void checkNaceCodeCheckBox() {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//select-with-search-modal/section//span")));
    }

    public void saveSelectedItem() {
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(10);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//select-with-search-modal/section//div[@class = 'modal__header']/a")));
    }

    public void setAddress(String address, String houseNumber, String postalCode, String City) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-street-field\"]")), address);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-number-field\"]")), houseNumber);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-postalcode-field\"]")), postalCode);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-city-field\"]")), City);

    }

    public void setTelephone(String telephone) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field\"]")), telephone);
    }

    public void setName(String fname, String lname) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"first-name-field\"]")), fname);
        Sleeper.sleepTightInSeconds(2);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"last-name-field\"]")), lname);

    }

    public void setCompanyName(String cname) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"company-name-c-field\"]")), cname);

    }

    public void setEanCode(String eanCode) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"ean-c-accounts-aos-quotes-aos-products-quotes-7-cc-91145-f-43-f-4800-d-705-58-ffa-7899-fbd-field\"]")), eanCode);

    }

    public void confirmTheSign(String place){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath(CONFIRM_SIGNIN_PLACE)),place);
    }

    @Override
    public boolean fillInFormData() {
        return false;
    }
}
