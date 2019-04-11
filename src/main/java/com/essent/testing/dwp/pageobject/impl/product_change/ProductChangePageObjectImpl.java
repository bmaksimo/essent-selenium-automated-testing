package com.essent.testing.dwp.pageobject.impl.product_change;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.product_change.ProductChangePageObject;
import org.openqa.selenium.By;

public class ProductChangePageObjectImpl extends Component implements ProductChangePageObject {

    private static final String TARIFF_ID = "aos-products-quotes-tariffsheet-id-field";
    private static final String TARIFF_FIRST_LIST_ITEM = "//select[@id='aos-products-quotes-tariffsheet-id-field']/option[1]";


    @Override
    public void selectFirstItemFromList() {
        seleniumDriver.waitAndClick(seleniumDriver.findElement(By.id(TARIFF_ID)));
        seleniumDriver.waitAndClick(seleniumDriver.findElement(By.xpath(TARIFF_FIRST_LIST_ITEM)));
     }
}
