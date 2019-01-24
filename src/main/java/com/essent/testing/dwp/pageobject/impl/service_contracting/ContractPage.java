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

    public void selectEAN(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("ean-c-aos-products-quotes-5-d-9-d-5-d-43-41-d-0-8957-6581-5-c-498-d-3-a-302-d-field")));
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenClickable(By.xpath("(//*[@id='ean_c']//b)[1]")));
    }

}
