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
    public void clickOnTabMenu(String tab){
       awaitOdooRequestToFinish(20);
       String xpath = createQuery(tabMenuXpath, nameTab, tab);
       seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(xpath)));

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
}
