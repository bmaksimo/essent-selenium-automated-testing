package com.essent.testing.dwp.pageobject.DashboardPages.ContractenPages;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;

public class NewQuotePage extends Component {
    public void clickOnX() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//guidance-modal//div[@class = 'modal__header']/a")));

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

    public void clickNaceCode() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"nace-code-c-field\"]")));
    }

    public void clickOnSearch() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//div[@class = 'input__with-button']/input")));
    }

    public void checkNaceCode() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//select-with-search-modal/section//span")));
    }

    public void saveSelectedItem() {
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

    public void setName(String fname, String lname) throws Throwable {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"first-name-field\"]")), fname);
        Thread.sleep(2000);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"last-name-field\"]")), lname);

    }

    public void setCompanyName(String cname) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"company-name-c-field\"]")), cname);

    }

    public void setEanCode(String eanCode) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"ean-c-accounts-aos-quotes-aos-products-quotes-7-cc-91145-f-43-f-4800-d-705-58-ffa-7899-fbd-field\"]")), eanCode);

    }

    public void saveInitialQuote() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"primaryButton\"]/span[2]")));

    }
}
