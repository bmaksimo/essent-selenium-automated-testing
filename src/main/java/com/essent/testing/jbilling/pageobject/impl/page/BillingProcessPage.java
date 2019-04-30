package com.essent.testing.jbilling.pageobject.impl.page;

import com.essent.testing.jbilling.pageobject.impl.Component;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BillingProcessPage extends Component {

  private String pattern = "MM/dd/yyyy";
  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
  private String date = simpleDateFormat.format(new Date());

  public void onTimeBillingProcess() {
    seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("701")));
  }

  public void editButton() {
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(
            By.xpath("//a[@class='submit edit']/span[contains(text(),'Edit')]")));
  }

  public WebElement invoiceDate() {
    return seleniumDriver.findElementWhenVisible(By.id("invoiceDate"));
  }

  public String getInvoiceDate() {
    return invoiceDate().getText();
  }

  public void invoiceDateInPast(Integer days) {
    DateTime dateAgo = new DateTime().minusDays(days);
    String ago = simpleDateFormat.format(dateAgo.toDate());
    seleniumDriver.waitAndSendKeys(invoiceDate(), ago);
  }

  public void invoiceDateDatapicker() {
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(
            By.xpath("(//img[@class='ui-datepicker-trigger'])[2]")));
  }

  public void saveProccessBilling() {
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(By.xpath("//a[@class='submit save']")));
  }

  public void cancelProccessBilling() {
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(By.xpath("//a[@class='submit cancel']")));
  }

  public String errorMsg() {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver.findElement(By.xpath("//*[@id=\"messages\"]/div/ul/li")).getText();
  }
}
