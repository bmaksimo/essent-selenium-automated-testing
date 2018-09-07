package com.essent.testing.dwp.pageobject.impl.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import com.essent.testing.selenium.SeleniumDriver;



public class DwpHomePage {

    private SeleniumDriver seleniumDriver;

    public DwpHomePage(SeleniumDriver seleniumDriver) {
        this.seleniumDriver = seleniumDriver;
    }

    public WebElement searchByAccountNumberField() {
        return seleniumDriver.findElementWhenVisible(By.id("account-number-c-default-value-field"));
    }

    public void searchByAccountNumberFieldClearAndClick() throws Throwable {
        seleniumDriver.findElementWhenVisible(By.id("account-number-c-default-value-field")).clear();
        seleniumDriver.findElementWhenVisible(By.id("account-number-c-default-value-field")).click();
    }

    public WebElement searchByAccountIdResultArea() {
        return seleniumDriver.findElementWhenVisible(By.className("list__row"));
    }

    public void enterAccountId(String accountNumber) throws InterruptedException {
        searchByAccountNumberField().sendKeys(accountNumber);
    }

    public WebElement accountWithAppropriateId(String accountId) throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[contains(text(),'" + accountId + "')]"));

    }

    public void clickOnAccountWithAppropriateId(String accountId) throws InterruptedException {
        accountWithAppropriateId(accountId).click();
    }

    public WebElement filterButton() {

    return seleniumDriver.findElementWhenVisible(By.className("icon-filters"));
    }

    public void clickOnFilterButton() throws InterruptedException {
        filterButton().click();
    }

    public WebElement searchField() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//input[@type='search']"));
    }

    public void searchForAppropriateUser(String newNameOfDuplicatedUser) throws InterruptedException {
        searchField().clear();
        searchField().click();
        searchField().sendKeys(newNameOfDuplicatedUser);
        searchField().sendKeys(Keys.ENTER);

    }

    public WebElement firstResultOfTheSearch() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//h5"));
    }

    public void clickOnFirstResultOfTheSearch() throws InterruptedException {
        firstResultOfTheSearch().click();
    }

    public String getTextOfTheFirstResult() {
        return firstResultOfTheSearch().getText();
    }

    public WebElement checkIfAccountTypeIsB2bProspect() {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//*[@text='B2B Prospect'])[1]"));
    }

    public void ClickOnContractsLink() throws InterruptedException {
        seleniumDriver.findElementWhenVisible(By.id("contract-list-link")).click();
    }
}
