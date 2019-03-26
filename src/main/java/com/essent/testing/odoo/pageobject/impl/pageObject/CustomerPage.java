package com.essent.testing.odoo.pageobject.impl.pageObject;

import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;

public class CustomerPage extends Component {

    private static final String labelPaymentMethod = "//tr[7]/td[2]/span";

    public void clickOnTabMenu(String tab){
        awaitOdooRequestToFinish(20);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//li[@class='ui-state-default ui-corner-top']/a[contains(text(),'"+tab+"')] ")));
    }

    public String getBankAccountAsString(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//td[@data-field=\"acc_number\"]")).getText();
    }

    public String getPaymentMethodAsString(){
        return seleniumDriver.findElementWhenVisible(By.xpath(labelPaymentMethod)).getText();
    }
}
