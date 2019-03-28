package com.essent.testing.dwp.pageobject.contracting_switching;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class QuotesListPage extends Component {

    public String getOfferteNumberAsString() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//list-link-bold-top-two-liner-cell//a/h5")).getText();
    }

    public void offerteNumberFieldSendKeys(String input){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("number-default-value-field")),input);
    }

    public void markAsDoneOplossingSendKeys(String input){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("task-resolution-c-field")),input);
    }

    public String getOfferteStatus(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("stage-field")).getText();
    }

    public WebElement calendar(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"signature_received_date_c\"]//div[@class='input__with-button']/span"));
    }

    public void setSignatureReceivedDate(String date){
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//validation-wrapper[@label='Datum handtekening ontvangen']//input[@id='signature-received-date-c-field']")),date);
        seleniumDriver.waitAndClick(calendar());
    }


}
