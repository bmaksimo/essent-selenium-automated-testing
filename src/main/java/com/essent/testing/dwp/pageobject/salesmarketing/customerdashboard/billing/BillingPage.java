package com.essent.testing.dwp.pageobject.salesmarketing.customerdashboard.billing;

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

    public String getInvoiceAmount(){
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list[@list-key='TransactionsOnAccount']//list-simple-two-liner-cell//span[@ng-bind-html='listSimpleTwoLinerCellController.line1'])[1]")).getText();
    }
}
