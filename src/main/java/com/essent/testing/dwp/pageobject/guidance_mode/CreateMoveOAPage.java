package com.essent.testing.dwp.pageobject.guidance_mode;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CreateMoveOAPage extends Component {

    private static final String COMMUNICATION_CHANNEL_ELEMENT = "dwp-move-interaction-channel-field";
    private static final String REPLACEMENT_KEY ="replacement_key";
    private static final String COMMUNICATION_CHANNEL = "//*[@id='dwp-move-interaction-channel-field']/option[@label='${"+REPLACEMENT_KEY+"}']";
    private static final String REASON_OF_MOVE_ELEMENT_ID = "dwp-move-reason-field";
    private static final String REASON_OF_MOVE = "//*[@id='dwp-move-reason-field']/option[@label='${"+REPLACEMENT_KEY+"}']";
    private static final String MOVE_DATE_ID = "dwp-move-date-field";
    private static final String PREVIOUS_METER = "(//*[@id='dwp|previousIndex']/div/strong)[${"+REPLACEMENT_KEY+"}]";
    private static final String METER_READING_DATE = "(//validation-wrapper[@label='Datum meteropname']//datepicker-form-element//div[@class='input-holder']/input)[2]";
    private static final String METER_READING = "(//validation-wrapper[@label='Meterstand']//div[@class='auto-complete']/input)[2]";

    private WebElement getCommunicationChannelElement(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id(COMMUNICATION_CHANNEL_ELEMENT));
    }

    public void chooseCommunicationChannel(String channel){
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(getCommunicationChannelElement());
        String communicationChannel = createQuery(COMMUNICATION_CHANNEL, REPLACEMENT_KEY, channel);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(communicationChannel)));
        seleniumDriver.waitForRequestsToFinish();
    }

    private WebElement getReasonOfMoveElement(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id(REASON_OF_MOVE_ELEMENT_ID));
    }

    public void chooseReasonOfMove(String reason){
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(getReasonOfMoveElement());
        seleniumDriver.waitForRequestsToFinish();
        String reasonOfMove = createQuery(REASON_OF_MOVE, REPLACEMENT_KEY, reason);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(reasonOfMove)));
        seleniumDriver.waitForRequestsToFinish();
    }

    public void chooseMoveDate(String date){
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(5);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id(MOVE_DATE_ID)),date);
        seleniumDriver.waitForRequestsToFinish();
    }

    public String getPreviousMeterReading(String rate){
        seleniumDriver.waitForRequestsToFinish();
        String previousMeterReading = createQuery(PREVIOUS_METER, REPLACEMENT_KEY, rate);
        return seleniumDriver.findElementWhenVisible(By.xpath(previousMeterReading)).getText();
    }

    public void chooseMeterReadingDate(String date){
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath(METER_READING_DATE)),date);
    }

    public void setMeterReading(String mr){
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath(METER_READING)),mr);
    }

}
