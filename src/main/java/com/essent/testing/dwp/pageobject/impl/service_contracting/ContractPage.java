package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.BaseObject;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ContractPage extends BaseObject {

    public ContractPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public ContractPage(WebElement element, SeleniumDriver seleniumDriver) {
        super(element, seleniumDriver);
    }

    public void openFirstContractFromList() {
        findElementWhenVisible(By.xpath("//tr[@class='list__row']/td[4]")).click();
        waitForRequestsToFinish();
    }

    public void contractPlus() {
        waitForRequestsToFinish();
        findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[1]/td[12]")).click();
    }

    public void changeAmount(String value) {
        findElementWhenVisible(By.id("dwp-recurring-amount-field")).clear();
        findElementWhenVisible(By.id("dwp-recurring-amount-field")).sendKeys(value);
        waitForRequestsToFinish();
    }

    public boolean getAmountOfACustomer(String amount) {
        String amountValue = findElementWhenVisible(By.id("advance-amount-field")).getText();
        String amountParameter = "€ " + amount + ",00";
        System.out.println("AMOUNT VALUE : " + amountValue);
        System.out.println("AMOUNT PARAMETER : " + amount);
        System.out.println("AMOUNT PARAMETER2 : " + amountParameter);
        return amountParameter.equals(amountValue);
    }
}
