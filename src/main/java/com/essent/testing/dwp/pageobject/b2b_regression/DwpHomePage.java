package com.essent.testing.dwp.pageobject.b2b_regression;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import com.essent.testing.selenium.SeleniumDriver;



public class DwpHomePage {

private SeleniumDriver seleniumDriver;

public DwpHomePage(SeleniumDriver seleniumDriver) {
    this.seleniumDriver = seleniumDriver;
    }

    public WebElement salesMarketingLink() throws InterruptedException {
        //Thread.sleep(2000);
        return seleniumDriver.findElementWhenVisible(By.id("sales-marketing-link"));

    }

    public void clickOnsalesMarketingLink() throws InterruptedException {
        //Thread.sleep(3000);
        salesMarketingLink().click();
    }

    public WebElement accountsListLink() {
        return seleniumDriver.findElementWhenVisible(By.id("accounts-list-link"));

    }

    public void clickOnAccountsListLink() throws InterruptedException {
        //Thread.sleep(3000);
        accountsListLink().click();
    }

    public WebElement searchByAccountNumberField() {
        return seleniumDriver.findElementWhenVisible(By.id("account-number-c-default-value-field"));
    }

    public void searchByAccountNumberFieldClearAndClick() throws Throwable {
      //Thread.sleep(2000);
        seleniumDriver.findElementWhenVisible(By.id("account-number-c-default-value-field")).clear();
        seleniumDriver.findElementWhenVisible(By.id("account-number-c-default-value-field")).click();

    }

    public WebElement searchByAccountIdResultArea() {
        return seleniumDriver.findElementWhenVisible(By.className("list__row"));
    }

    public void enterAccountId(String accountNumber) throws InterruptedException {
        //Thread.sleep(500);
        searchByAccountNumberField().sendKeys(accountNumber);
    }

    public WebElement accountWithAppropriateId(String accountId) throws InterruptedException {
        ////Thread.sleep(5000);
        System.out.println("------------------ffffff accountId:"+accountId);
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[contains(text(),'" + accountId + "')]"));

    }

    public void clickOnAccountWithAppropriateId(String accountId) throws InterruptedException {
       // //Thread.sleep(5000);
        accountWithAppropriateId(accountId).click();
    }

    public WebElement filterButton() {
        return seleniumDriver.findElementWhenVisible(By.className("icon-filters"));
    }

    public void clickOnFilterButton() throws InterruptedException {
      //Thread.sleep(2000);
        filterButton().click();
    }

    public WebElement searchField() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//input[@type='search']"));
    }

    public void searchForAppropriateUser(String newNameOfDuplicatedUser) throws InterruptedException {
        //Thread.sleep(1000);
        searchField().clear();
        searchField().click();
        //Thread.sleep(300);
        searchField().sendKeys(newNameOfDuplicatedUser);
        //Thread.sleep(200);
        searchField().sendKeys(Keys.ENTER);
        //Thread.sleep(4000);

    }

    public WebElement firstResultOfTheSearch() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//h5"));
    }

    public void clickOnFirstResultOfTheSearch() throws InterruptedException {
        //Thread.sleep(3000);
        firstResultOfTheSearch().click();
    }

    public String getTextOfTheFirstResult() {
        return firstResultOfTheSearch().getText();
    }

    public WebElement checkIfAccountTypeIsB2bProspect() {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//*[@text='B2B Prospect'])[1]"));
    }

    public void ClickOnContractsLink() throws InterruptedException {
        //Thread.sleep(3000);
        seleniumDriver.findElementWhenVisible(By.id("contract-list-link")).click();
    }
}
