package com.essent.testing.dwp.pageobject.impl.Page;

import com.essent.testing.dwp.pageobject.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import com.essent.testing.selenium.SeleniumDriver;



public class DwpHomePage extends Component {

    public DwpHomePage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
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
        //accountWithAppropriateId(accountId).click();
        seleniumDriver.waitAndClick(accountWithAppropriateId(accountId));
    }

    public WebElement filterButton() {

    return seleniumDriver.findElementWhenVisible(By.className("icon-filters"));
    }

    public void clickOnFilterButton() throws InterruptedException {
        //filterButton().click();
        seleniumDriver.waitAndClick(filterButton());
    }

    public WebElement searchField(String search ) {
        return seleniumDriver.findElementWhenVisible(By.xpath("//input[@type='"+search+"']"));
    }

    public void searchForAppropriateUser(String newNameOfDuplicatedUser, String search ) throws InterruptedException {
        searchField(search).clear();
        //searchField(search).click();
        seleniumDriver.waitAndClick(searchField(search));
        searchField(search).sendKeys(newNameOfDuplicatedUser);
        searchField(search).sendKeys(Keys.ENTER);

    }

    public WebElement firstResultOfTheSearch() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//h5"));
    }

    public void clickOnFirstResultOfTheSearch() throws InterruptedException {
        //firstResultOfTheSearch().click();
        seleniumDriver.waitAndClick(firstResultOfTheSearch());
    }

    public String getTextOfTheFirstResult() {
        return firstResultOfTheSearch().getText();
    }

    public WebElement checkIfAccountTypeIsB2bProspect(String key) {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//*[@text='"+key+"'])[1]")); //key = B2B Prospect
    }

    public void ClickOnContractsLink() throws InterruptedException {
        //seleniumDriver.findElementWhenVisible(By.id("contract-list-link")).click();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("contract-list-link")));
    }
}
