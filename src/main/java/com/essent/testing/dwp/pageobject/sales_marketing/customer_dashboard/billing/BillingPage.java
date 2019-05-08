package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.billing;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;

public class BillingPage extends Component {

    private static final String TRANSACTION_TYPE= "(//list-link-bold-top-two-liner-cell/div/h6)[1]";
    private static final String SEND = "//a[contains(text(),'Verzenden')]";

    public String selectProductCode() {
       return seleniumDriver.findElementWhenClickable(By.xpath(TRANSACTION_TYPE)).getText();
    }

    public void clickOnSendButton(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(SEND)));
    }

}
