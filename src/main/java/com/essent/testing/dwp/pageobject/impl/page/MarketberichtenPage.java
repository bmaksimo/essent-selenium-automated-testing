package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MarketberichtenPage  extends Component {

    public MarketberichtenPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }
    public WebElement listActionsElemet(String element) throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.name(element));
    }

    public void clickOnListActionsElemet(String element) throws InterruptedException {

        seleniumDriver.waitAndClick(listActionsElemet(element));
    }

    public String getEanFromTheFirstTransaction() throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[1]")).getText();
    }

    public WebElement selectNewContractlineButton() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//button[contains(.,'Select Contractline')]"));
        //return seleniumDriver.findElementWhenVisible(By.xpath("//button[class='button-placeholder'][1]"));
    }

    public void clickOnSelectNewContractlineButton() throws InterruptedException {
        seleniumDriver.waitAndClick(selectNewContractlineButton());
    }

    public WebElement searchForContractField() {
        return seleniumDriver.findElementWhenVisible(By.id("search-input"));
    }

    public void enterContractNumber(String transactionEan) throws InterruptedException {
        seleniumDriver.waitAndSendKeys(searchForContractField(), transactionEan);
    }

    public WebElement searchButton() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//input[@value='Search']"));
    }

    public void clickOnSearchButton() throws InterruptedException {
        seleniumDriver.waitAndClick(searchButton());

    }

    public WebElement FirstContractInTheList() {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//label[@class='input__checkbox'])[2]"));
    }

    public void clickOnTheFirstContract() throws InterruptedException {
        seleniumDriver.waitAndClick(FirstContractInTheList());
    }

    public WebElement submitButton() {
        return seleniumDriver.findElementWhenClickable(By.xpath("//a[contains(.,'Verzenden')]"));
    }

    public void clickOnSubmitButton() throws InterruptedException {
        seleniumDriver.waitAndClick(submitButton());
    }

    public WebElement selectButton() {
        return seleniumDriver.findElementWhenVisible(By.id("confirm-button"));
    }

    public void clickOnSelectButton() throws InterruptedException {
        seleniumDriver.waitAndClick(selectButton());
    }

    public WebElement moduleDropdownMenu() throws InterruptedException {
        //return seleniumDriver.findElementWhenVisible(By.id("dwp-mig-module-c-field"));  //label[class='input__checkbox'][2] //*[@id='dwp-mig-module-c-field']/option[1]"
        //return seleniumDriver.findElementWhenVisible(By.xpath("//label[class='input__checkbox']")).findElement(By.id("dwp-mig-module-c-field"));
        return seleniumDriver.findElementWhenVisible(By.xpath("//select[@id='dwp-mig-module-c-field']"));
    }

    public void clickOnModuleDropdownMenu() throws InterruptedException {
        seleniumDriver.waitAndClick(moduleDropdownMenu());
    }

    public void clickOnInitiateStopAccessOption() throws InterruptedException {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//option[@label=//option[@label='INITIATE STOP ACCESS']]")));
     }

    public WebElement labelDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.id("dwp-mig-label-c-field"));
    }

    public void clickOnLabelDropdownMenu() throws InterruptedException {
        seleniumDriver.waitAndClick(labelDropdownMenu());
    }

    public void clickOnNonResidentialEndOfContractOption() throws InterruptedException {

        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("(//option[@label='Non-Residential End-of-Contract'])[1]")));
    }

    public void clickOnDropBudgetMeterOption() throws InterruptedException {

        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='Drop/Request Budget Meter']")));
    }

    public void clickOnConfirmButton() throws InterruptedException {

        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//a[@class='button']")));

    }


}

