package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.service;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class ServicePage extends Component {

    private static final String CASE_TOPIC = "//list[@list-key='CasesOnAccount']//td[@class='list__cell cell__text'][3]/list-simple-two-liner-cell/p";
    private static final String REPLACEMENT_KEY = "replacement_key";
    private static final String INTERACTION_TYPE = "//list-simple-two-liner-cell[@line-1='${" + REPLACEMENT_KEY + "}']/p/span[1]";
    private static final String INTERACTION_ONDERWERP = "//list-simple-two-liner-cell[@line-1='${" + REPLACEMENT_KEY + "}']/p/span[2]";
    private static final String CASE_NUMBER = "//list[@list-key=\"InteractionsOnAccount\"]//td[7]//div//h5[1]";
    private static final String INTERACTION_VERWANTE_CASE = "//*[@id='rows']/tr[1]/td[7]/list-link-bold-top-two-liner-cell/div/a";

    private WebElement newCase() {
        //TODO Remove locale-specific hard code.
        // The project must support official Belgian languages.
        // Locale-specific elements of web element locators must be parameterized.
        // This is basic rule!
        return seleniumDriver.findElementWhenVisible(By.name("CASE TOEVOEGEN"));
    }

    public void clickOnNewCase(){
        seleniumDriver.waitAndClick(newCase());
    }


    public void findRejectionReason(String input) {
        seleniumDriver.waitForRequestsToFinish();
        Assert.assertTrue(seleniumDriver.findElementWhenVisible(By.xpath("(//span[.='" + input + "'])[1]")).isDisplayed());
    }

    public String getCaseOnderwerp() {
        return seleniumDriver.findElementWhenVisible(By.xpath(CASE_TOPIC)).getText();
    }
    public String getCaseNumber(){
        return seleniumDriver.findElementWhenVisible(By.xpath(CASE_NUMBER)).getText();
    }

    public String getInteractionType(String type){
        String interactionType = createQuery(INTERACTION_TYPE, REPLACEMENT_KEY, type);
        return seleniumDriver.findElementWhenVisible(By.xpath(interactionType)).getText();

    }

    public String getInteractionOnderwerp(String type) {
        String interactionOnderwerp = createQuery(INTERACTION_ONDERWERP, REPLACEMENT_KEY, type);
        return seleniumDriver.findElementWhenVisible(By.xpath(interactionOnderwerp)).getText();

    }

    public String getInteractionVerwanteCase() {
        return seleniumDriver.findElementWhenVisible(By.xpath(INTERACTION_VERWANTE_CASE)).getText();

    }

    public void goToProspect(){
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.linkText("Go to prospect")));
        Sleeper.sleepTightInSeconds(10);
    }

    public void  gotoGLNAccount (){
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.linkText("Go to GLN Account")));
        Sleeper.sleepTightInSeconds(10);
    }

    public String getSecondInteractionOnderwerp(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='InteractionsOnAccount']//tr[3]//td[3]//list-simple-two-liner-cell//span[2]")).getText();
    }
}
