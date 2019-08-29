package com.essent.testing.dwp.pageobject.sales_marketing.focuse_mode;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PricingToolDashboardPage extends Component {
    private final static String PURCHASE_PRICE_HEADER = "//tariff-calculation-form-element//table//th[1]";
    private final static String PURCHASE_PRICE_FIRST_ROW = "//tariff-calculation-form-element//tbody//div[1]";
    private final static String INDEXATIEPARAMETER = "dwp-indexation-param-field";
    private final static String HUIDIGE_WAARDE= "dwp-floating-base-price-field";


    public WebElement getPurchasePriceHeader(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath(PURCHASE_PRICE_HEADER));
    }

    public WebElement getPurchasePriceFirstRow(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath(PURCHASE_PRICE_FIRST_ROW));
    }

    public WebElement getIndexatieParameter(){
        return seleniumDriver.findElementWhenVisible(By.id(INDEXATIEPARAMETER));
    }

    public WebElement getHuidigeWaarde(){
        return seleniumDriver.findElementWhenVisible(By.id(HUIDIGE_WAARDE));
    }

}
