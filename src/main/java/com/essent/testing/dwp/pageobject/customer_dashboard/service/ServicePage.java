package com.essent.testing.dwp.pageobject.customer_dashboard.service;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class ServicePage extends Component {

    public void validateCreatedTask(String input) {
        seleniumDriver.waitForRequestsToFinish();
        Assert.assertTrue(findElementWhenVisible(By.xpath("(//span[.='" + input + "'])[1]")).isDisplayed());
    }

    public WebElement newCase() {
        return seleniumDriver.findElementWhenVisible(By.name("CASE TOEVOEGEN"));
    }

    public void clickOnNewCase(){
        seleniumDriver.waitAndClick(newCase());
    }


    public void findRejectionReason(String input) {
        seleniumDriver.waitForRequestsToFinish();
        Assert.assertTrue(seleniumDriver.findElementWhenVisible(By.xpath("(//span[.='" + input + "'])[1]")).isDisplayed());
    }

    public String getCaseOnderwerp(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key=\"InteractionsOnAccount\"]//td[@class=\"list__cell cell__text\"][6]/list-link-bold-top-two-liner-cell/div/h6")).getText();
    }
    public String getCaseNumber(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key=\"InteractionsOnAccount\"]//td[7]//div//h5[1]")).getText();
    }

    public String getInteractionType(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//list-simple-two-liner-cell[@line-1='Document']/p/span[1]")).getText();
    }

    public String getInteractionOnderwerp() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//list-simple-two-liner-cell[@line-1='Document']/p/span[2]")).getText();
    }

    public String getInteractionVerwanteCase() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id='rows']/tr[1]/td[7]/list-link-bold-top-two-liner-cell/div/a")).getText();
    }

}
