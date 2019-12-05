package com.essent.testing.odoo.pageobject.impl.pageObject;

import com.essent.automation.util.Sleeper;
import com.essent.testing.odoo.pageobject.impl.Component;
import com.essent.testing.odoo.pageobject.impl.elements.ButtonImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CustomerPage extends Component {

    private static final String LABEL_PAYMENT_METHOD = "//tr[7]/td[2]/span";
    private static final String LABEL_BANK_ACCOUNT = "//td[@data-field=\"acc_number\"]";
    private static final String NAME_TAB = "name_tab";
    private static final String TAB_MENU_XPATH = "//li[@class='ui-state-default ui-corner-top']/a[contains(text(),'${"+NAME_TAB+"}')] ";
    private static final String JOURNAL_ENTRY_ROW = "//table[@class='oe_list_content'][1]//tr[1]//td[@data-field='move_id'][1]//a";
    private static final String ACTIVE_CHECKBOX = "//div[@class='oe_form_nosheet']//tbody/tr[3]/td[2]//input";
    private static final String SENT_TO_CUSTOMER_CHECKBOX = "//td[@data-field='sent_to_customer']/input";
    private static final String FORMAT = "//td[@data-field='format']";
    private static final String SENT_DATE = "//td[@data-field='date_sent']";
    private static final String BUTTON_LABEL = "//div[@class='modal in']//span[contains(text(),'${"+NAME_TAB+"}')]";


    public boolean clickOnTabMenu(String tab){
       awaitOdooRequestToFinish(20);
       String xpath = createQuery(TAB_MENU_XPATH, NAME_TAB, tab);
       seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(xpath)));

       return true;
    }

    private WebElement getBankAccountElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath(LABEL_BANK_ACCOUNT));
    }

    public void clickOnBankAccountElement(){
        seleniumDriver.waitAndClick(getBankAccountElement());
    }

    public String getBankAccountAsString(){
        return seleniumDriver.findElementWhenVisible(By.xpath(LABEL_BANK_ACCOUNT)).getText();
    }

    public String getPaymentMethodAsString(){
        return seleniumDriver.findElementWhenVisible(By.xpath(LABEL_PAYMENT_METHOD)).getText();
    }

    public void openJournalEntry() {
        awaitOdooRequestToFinish(45);
        Sleeper.sleepTightInSeconds(5);
        WebElement journal = seleniumDriver.findElementWhenVisible(By.xpath(JOURNAL_ENTRY_ROW));
        journal.click();
    }

    public void buttonJournalItemsClicked(String label) {
        awaitOdooRequestToFinish(120);
        WebElement webElement = seleniumDriver.findElementWhenVisible(By.xpath("//button//span[contains(., '" + label + "')]"));
        new ButtonImpl(webElement).click();
        awaitOdooRequestToFinish(120);
    }

    public void modalReverseClickButton(String buttonLabel) {
        awaitOdooRequestToFinish(60);
        String xpath = createQuery(BUTTON_LABEL, NAME_TAB, buttonLabel);
        WebElement button = seleniumDriver.findElementWhenVisible(By.xpath(xpath));
        button.click();
        awaitOdooRequestToFinish(60);
    }

    private WebElement getActiveCheckboxElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath(ACTIVE_CHECKBOX));
    }

    public boolean isActiveCheckboxElementChecked(){
        return getActiveCheckboxElement().isSelected();
    }

    public String getDirectDebitFormat(){
        return seleniumDriver.findElementWhenVisible(By.xpath(FORMAT)).getText();
    }

    public boolean isSentToCustomerChecked(){
        return seleniumDriver.findElementWhenVisible(By.xpath(SENT_TO_CUSTOMER_CHECKBOX)).isSelected();
    }

    public String getDirectDebitSentDate(){
        return seleniumDriver.findElementWhenVisible(By.xpath(SENT_DATE)).getText();
    }

    public void rightBoxButtonClicked(String button) {
        awaitOdooRequestToFinish(120);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='oe_right oe_button_box']//div[contains(.,'"+button+"')]")));
    }

    public void chooseLeftMenuCustomers(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//a[@class='oe_menu_leaf']/span[normalize-space()='Customers']")));

    }

}
