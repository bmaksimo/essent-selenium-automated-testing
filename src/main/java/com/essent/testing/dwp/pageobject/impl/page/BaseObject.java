package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseObject extends Component {

    public String getTaskId() {
        final String taskId;
        taskId = seleniumDriver.findElementWhenVisible(By.xpath("(//h6)[2]")).getText();
        return taskId;
    }

    public void insertEANcode(String eanCode) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("sql-i-aos-contracts-i-aos-products-quotes-i-ean-c-default-value-field")),eanCode);
    }

    public void clickOnPlus() {

        seleniumDriver.waitAndClick(seleniumDriver.findElementOrNull(By.xpath("(//list-plus-cell//a)[1]")));
    }

    public void plusSubaction(String action) {
        try {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-row-action[normalize-space(@label)='" + action + "']/a")));

        }
        catch(org.openqa.selenium.StaleElementReferenceException ex) {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-row-action[normalize-space(@label)='" + action + "']/a")));
        }
    }

    public void clickOnToggle (String label) throws InterruptedException{
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(2);
        ToggleImpl toggle = new ToggleImpl();
        if (!toggle.checkIfCheckboxIsChecked(label)) {
            seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//validation-wrapper[@label='" + label + "?']//toggle-form-element/label")));
        }
    }

    public String documentText(){
        return findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell//span[1])[2]")).getText();
    }

    private String pattern = "dd/MM/yyyy";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    private String date = simpleDateFormat.format(new Date());

    public void dateIsNow(String label) throws InterruptedException {
        seleniumDriver.waitForRequestsToFinish();
        Thread.sleep(2000);
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.xpath("//validation-wrapper[@label='"+label+"']//input")),date);
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//span[@class='icon-kalender']")));
    }

}
