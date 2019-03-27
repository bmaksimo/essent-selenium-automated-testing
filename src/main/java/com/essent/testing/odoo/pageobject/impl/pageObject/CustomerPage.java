package com.essent.testing.odoo.pageobject.impl.pageObject;

import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;

public class CustomerPage extends Component {

    private static final String labelPaymentMethod = "//tr[7]/td[2]/span";
    private static final String labelBankAccount = "//td[@data-field=\"acc_number\"]";
    private static final String nameTab = "name_tab";
    private static final String tabMenuXpath = "//li[@class='ui-state-default ui-corner-top']/a[contains(text(),'${"+nameTab+"}')] ";

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
}
