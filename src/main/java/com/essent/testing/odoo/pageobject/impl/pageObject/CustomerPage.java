package com.essent.testing.odoo.pageobject.impl.pageObject;

import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CustomerPage extends Component {

    private static final String labelPaymentMethod = "//tr[7]/td[2]/span";
    private static final String labelBankAccount = "//td[@data-field=\"acc_number\"]";
    private static final String nameTab = "name_tab";
    private static final String tabMenuXpath = "//li[@class='ui-state-default ui-corner-top']/a[contains(text(),'${"+nameTab+"}')] ";
    private static final String REVERSE_BUTTON_1 = "//div[@class='oe_view_manager oe_view_manager_current'][4]//div[@class='oe_form_nosheet']//button[4]//span";
    private static final String REVERSE_BUTTON_2 ="//div[@class='modal-footer']//button[1]//span";
    private static final String JOURNAL_ENTRY_BUTTON = "//tbody/tr[9]//span/a";
    private static final String JOURNAL_ENTRY_ROW = "//table[@class='oe_list_content'][1]//tbody//tr[1]//td[@data-field='move_id'][1]//a";
    private static final String ACITVE_CHECKBOX = "//div[@class='oe_form_nosheet']//tbody/tr[3]/td[2]//input";
    private static final String SENT_TO_CUSTOMER_CHECKBOX = "//td[@class='oe_list_field_cell oe_list_field_boolean   oe_readonly ']/input";
    private static final String FORMAT = "//td[@class='oe_list_field_cell oe_list_field_selection   oe_readonly oe_required']";
    private static final String SENT_DATE = "(//td[@class='oe_list_field_cell oe_list_field_date   oe_readonly '])[3]";



    public void clickOnTabMenu(String tab){
       awaitOdooRequestToFinish(20);
       String xpath = createQuery(tabMenuXpath, nameTab, tab);
       seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(xpath)));

    }

    public WebElement bankAccountElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath(labelBankAccount));
    }

    public void clikcOnBankAccoutElement(){
        seleniumDriver.waitAndClick(bankAccountElement());
    }

    public String getBankAccountAsString(){
        return seleniumDriver.findElementWhenVisible(By.xpath(labelBankAccount)).getText();
    }

    public String getPaymentMethodAsString(){
        return seleniumDriver.findElementWhenVisible(By.xpath(labelPaymentMethod)).getText();
    }

    public void reversePaymentPlan() {
        awaitOdooRequestToFinish(20);
        seleniumDriver.findElementWhenPresent(By.xpath(REVERSE_BUTTON_1)).click();

        awaitOdooRequestToFinish(20);
        seleniumDriver.findElementWhenPresent(By.xpath(REVERSE_BUTTON_2)).click();
        awaitOdooRequestToFinish(20);
    }

    public void openJournalEntry() {
        awaitOdooRequestToFinish(45);
        WebElement journal = seleniumDriver.findElement(By.xpath(JOURNAL_ENTRY_ROW));
        journal.click();
        awaitOdooRequestToFinish(45);
        seleniumDriver.findElement(By.xpath(JOURNAL_ENTRY_BUTTON)).click();
    }

    public WebElement activeCheckboxElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath(ACITVE_CHECKBOX));
    }

    public boolean activeCheckboxElementIsChecked(){
        return activeCheckboxElement().isSelected();
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
}
