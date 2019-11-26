package com.essent.testing.dwp.pageobject.impl.productchange;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.productchange.ProductChangePageObject;
import org.openqa.selenium.By;

public class ProductChangePageObjectImpl extends Component implements ProductChangePageObject {

    private static final String TARIFF_ID = "aos-products-quotes-tariffsheet-id-field";
    private static final String TARIFF_FIRST_LIST_ITEM = "//select[@id='aos-products-quotes-tariffsheet-id-field']/option[1]";


    @Override
    public void selectFirstItemFromList() {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElement(By.id(TARIFF_ID)));
        Sleeper.sleepTightInSeconds(5);
        seleniumDriver.waitAndClick(seleniumDriver.findElement(By.xpath(TARIFF_FIRST_LIST_ITEM)));
     }
}
