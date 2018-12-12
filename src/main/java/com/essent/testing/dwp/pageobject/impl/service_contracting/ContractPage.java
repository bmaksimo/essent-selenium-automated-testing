package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;


public class ContractPage extends BaseObject {

    public ContractPage(SeleniumDriver seleniumDriver) { super(seleniumDriver); }


    public void openFirstContractFromList() {
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//div[@class = 'col-1-1']/div[@class = 'row-']/list[@list-key = 'ContractedEansOnAccount']//tbody[@id = 'rows']/tr[1]/td[4]")));
        waitForRequestsToFinish();
    }

    public void contractPlus() {
        waitForRequestsToFinish();
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[1]/td[12]")));
    }

    public void changeAmount(String value) {
        findElementWhenVisible(By.id("dwp-recurring-amount-field")).clear();
        findElementWhenVisible(By.id("dwp-recurring-amount-field")).sendKeys(value);
        waitForRequestsToFinish();
    }

    public boolean getAmountOfACustomer(String amount) {
        String amountValue = findElementWhenVisible(By.id("advance-amount-field")).getText();
        String amountParameter = amount + ",00";
        String[] value = amountValue.split(" ", 2);
        for (String i : value) {
        }

        return amountParameter.equals(value[1]);
    }
}
