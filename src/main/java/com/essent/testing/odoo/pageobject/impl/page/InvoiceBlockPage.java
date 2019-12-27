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

  public String getInvoiceBlockStartDate() {
    return seleniumDriver.findElementWhenVisible(By.xpath(INVOICE_BLOCK_START_DATE)).getText();
  }

  public String getInvoiceBlockEndDate() {
    return seleniumDriver.findElementWhenVisible(By.xpath(INVOICE_BLOCK_END_DATE)).getText();
  }

  public String getInvoiceBlockEndDateIsEmpty() {
    return seleniumDriver.findElement(By.xpath(INVOICE_BLOCK_END_DATE)).getAttribute("value");
  }

  public void expand(String text) {
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(By.xpath("//th[contains(text(), '" + text + "')]")));
  }

  public void clickManageInvoiceBlock() {
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(By.xpath("//img[@alt='Manage invoice blocks']")));
  }

  public void clickCreateNewInvoiceBlockButton() {
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(
            By.xpath("(//button[@class='oe_button oe_list_add oe_highlight'])[3]")));
  }

  public void selectInvoiceBlockReason(String reason) {
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(
            By.xpath(
                "//span[@data-fieldname='reason']/div/span[@class='oe_m2o_drop_down_button']")));
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(By.xpath("//a[contains(text(), '" + reason + "')]")));
  }

  public void selectEndDateInInvoiceBlock(String endDate) {
    seleniumDriver.waitAndSendKeys(
        seleniumDriver.findElementWhenVisible(By.xpath("//input[@name='date_end']")), endDate);
  }

  public void clickSaveInvoiceBlockButton() {
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(
            By.xpath(
                "//div[@class='oe_list_buttons oe_editing']//button[contains(text(), 'Save')] ")));
  }
}
