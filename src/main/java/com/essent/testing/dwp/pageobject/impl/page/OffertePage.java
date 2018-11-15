package com.essent.testing.dwp.pageobject.impl.page;


import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class OffertePage extends Component {

    public OffertePage(SeleniumDriver seleniumDriver) {

        super(seleniumDriver);
    }

    public String getOfferteNumber() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//list-link-bold-top-two-liner-cell//a/h5")).getText();
    }

    public void resetFilter() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("(//div[@class=\"form__footer\"])[1]//button ")));
    }

    public void clickOnLabel(String label, String value) {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//validation-wrapper[@label='" + label + "']//option[@label = '" + value + "']")));
    }
     public void clickOnType(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("quote-type-c-default-value-field")));
     }

     public void clickOnFilter(){
         seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//top-actions/div/a[2]")));
     }

     public void offerteNumberFieldSendKeys(String input){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("number-default-value-field")),input);
     }

    public void markAsDoneOplossingSendKeys(String input){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("task-resolution-c-field")),input);
    }

    public String getStatus(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id("stage-field")).getText();
    }

    public void clickOnBevestigen(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("primaryButton")));
    }
}
