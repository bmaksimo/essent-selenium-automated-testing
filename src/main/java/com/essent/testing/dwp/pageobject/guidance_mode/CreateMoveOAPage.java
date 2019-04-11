package com.essent.testing.dwp.pageobject.guidance_mode;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CreateMoveOAPage extends Component {

    public WebElement getCommunicationChannelElement(){
        return seleniumDriver.findElementWhenVisible(By.id("dwp-move-interaction-channel-field"));
    }

    public void chooseCommunicationChannel(String channel){
        seleniumDriver.waitAndClick(getCommunicationChannelElement());
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id='dwp-move-interaction-channel-field']/option[@label='"+channel+"']")));
    }

    public WebElement getReasonOfMoveElement(){
        return seleniumDriver.findElementWhenVisible(By.id("dwp-move-reason-field"));
    }

    public void chooseReasonOfMove(String reason){
        seleniumDriver.waitAndClick(getReasonOfMoveElement());
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id='dwp-move-reason-field']/option[@label='"+reason+"']")));
    }

    public void chooseMoveDate(String date){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("dwp-move-date-field")),date);
    }

    public String getPreviousMeterReading(int rate){
        return seleniumDriver.findElementWhenVisible(By.xpath("(//*[@id='dwp|previousIndex']/div/strong)["+rate+"]")).getText();
    }

    public void chooseMeterReadingDate(String date){
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("(//validation-wrapper[@label='Datum meteropname']//datepicker-form-element//div[@class='input-holder']/input)[2]")),date);
    }

    public void setMeterReading(String mr){
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("(//validation-wrapper[@label='Meterstand']//div[@class='auto-complete']/input)[2]")),mr);
    }

}
