package com.essent.testing.odoo.pageobject.impl.page;

import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;

public class InvoiceBlockPage extends Component {
    private static String INVOICE_BLOCK_REASON = "//td[@data-field='reason']";
    private static String INVOICE_BLOCK_START_DATE = "//td[@data-field='date_start']";
    private static String INVOICE_BLOCK_END_DATE = "//td[@data-field='date_end']";

    public String getInvoiceBlockReason() {
        return seleniumDriver.findElementWhenVisible(By.xpath(INVOICE_BLOCK_REASON)).getText();
    }

    public String getAccountBlockStartDate() {
        return seleniumDriver.findElementWhenVisible(By.xpath(INVOICE_BLOCK_START_DATE)).getText();
    }

    public String getAccountBlockEndDate() {
        return seleniumDriver.findElementWhenVisible(By.xpath(INVOICE_BLOCK_END_DATE)).getText();
    }

    public String getAccountBlockEndDateIsEmpty() {
        return seleniumDriver.findElement(By.xpath(INVOICE_BLOCK_END_DATE)).getAttribute("value");
    }
}
