package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DetailsPage extends Component {

    private static final String NUMBER_BILLING_CUSTOMER = "//list[@list-key='BillingCustomerOnaccount']//tr[@class='list__row']";
    private static final String IBAN = "//span[.='${iban}']";
    private static final String ADDRESS = "//div[@class='card__content__inner-wrapper']/p[1]";
    private static final String PHONE = "//div[@class='card__content__inner-wrapper']/p[2]/span[2]";
    private static final String EMAIL = "//div[@class='card__content__inner-wrapper']/p[2]/span[3]";
    private static final String CUSTOMER_NAME = "//div[@class='card__header']/h1";
    private static final String TYPE = "account-type-field";
    private static final String BANK_ACCOUNT = "//list[@list-key='BillingCustomerOnaccount']//tbody//td[2]//span[1]";
    private static final String PAYMENT_METHOD = "//list[@list-key='BillingCustomerOnaccount']//tbody//td[3]//span[2]";
    private static final String ACCOUNT_BLOCK_START_DATE = "//*[@id=\"rows\"]/tr[1]/td[2]/list-simple-two-liner-cell/p/span[1]";
    private static final String ACCOUNT_BLOCK_END_DATE = "//list[@list-key='accountBlockReasonsForAccountList']//tr[1]/td[3]/list-simple-two-liner-cell/p";
    private static final String PREFERENCES_EMAIL_ADDRESS_LEGAL = "paym-details-accounts-contacts-primary-contact-c-1-contacts-contact-details-contact-details-type-email-contact-details-value-field";
    private static final String PREFERENCES_EMAIL_ADDRESS_GENERAL = "contacts-contacts-contact-details-contact-details-type-email-contact-details-value-field";
    private static final String COMMUNICATION_PREFERENCES_UPDATED_SUCCESS_MESSAGE = "//div/focus-mode/focus-mode-content/div/div/div[2]/flash-message-renderer/flash-message";
    private static final String INVOICE_BLOCK_END_DATE = "//list[@list-key='invoiceBlockReasonsForInvoice']//tr[1]/td[3]/list-simple-two-liner-cell/p";

    public void findIban(String iban) {
        seleniumDriver.waitForRequestsToFinish();
        String xpathFindIban = createQuery(IBAN, "iban", iban);
        seleniumDriver.findElementWhenVisible(By.xpath(xpathFindIban)).isDisplayed();
    }

    public int getNumberOfBillingCustomers() {
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(5);
        return seleniumDriver.findElements(By.xpath(NUMBER_BILLING_CUSTOMER)).size();
    }

    public String getAddress() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath(ADDRESS)).getText();
    }

    public String getPhone() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath(PHONE)).getText();
    }

    public String getEmail() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath(EMAIL)).getText();
    }

    public String getCustomerName() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath(CUSTOMER_NAME)).getText();
    }

    public String getCustomerType() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id(TYPE)).getText();
    }

    public String getIban() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath(BANK_ACCOUNT)).getText();
    }

    public String getPaymentMethod() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath(PAYMENT_METHOD)).getText();
    }

    public String getAccountBlockStartDate() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath(ACCOUNT_BLOCK_START_DATE)).getText();
    }

    public String getAccountBlockEndDate() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath(ACCOUNT_BLOCK_END_DATE)).getText();
    }

    public String getInvoiceBlockEndDate() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath(INVOICE_BLOCK_END_DATE)).getText();
    }

    public String getAccountBlockEndDateNotExist() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElement(By.xpath(ACCOUNT_BLOCK_END_DATE)).getAttribute("value");
    }

    public void emailAddressInputIsCleared(String preference) {
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(5);
        String id = preference.equalsIgnoreCase("Algemeen") ? PREFERENCES_EMAIL_ADDRESS_GENERAL : PREFERENCES_EMAIL_ADDRESS_LEGAL;

        seleniumDriver.findElement(By.id(id)).clear();
    }

    public String getCommunicationPreferencesUpdatedMessage() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenPresent(By.xpath(COMMUNICATION_PREFERENCES_UPDATED_SUCCESS_MESSAGE)).getText();
    }

    public void clickOnPlusMenuInRow(WebElement plusMenu) {
        int attempts = 20;
        int currentAttempt = 0;
        boolean isDisplayed = false;
        while (!isDisplayed && currentAttempt <= attempts) {
            currentAttempt++;
            isDisplayed = plusMenu.isDisplayed();
            Sleeper.sleepTightInSeconds(2);
            if (isDisplayed) plusMenu.findElement(By.tagName("a")).click();
        }
    }

    public WebElement getModalName(String modal){
        return seleniumDriver.findElementWhenVisible(By.xpath("//h5[normalize-space()= '"+modal+"']"));
    }

    public WebElement getEmcIdElemet(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='CommunicationonInteraction']//td[4]//list-simple-two-liner-cell//span[1]"));
    }
}
