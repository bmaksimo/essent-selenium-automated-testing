package com.essent.testing.dwp.pageobject.impl.filter;

import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class DwpFilter extends Component {

    public DwpFilter(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public WebElement searchByAccountNumberField() {
        return seleniumDriver.findElementWhenVisible(By.id("account-number-c-default-value-field"));
    }

    public void searchByAccountNumberFieldClearAndClick() throws Throwable {
        seleniumDriver.findElementWhenVisible(By.id("account-number-c-default-value-field")).clear();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("account-number-c-default-value-field")));
    }

    public WebElement searchByAccountIdResultArea() {
        return seleniumDriver.findElementWhenVisible(By.className("list__row"));
    }

    public void enterAccountId(String accountNumber)  {
        seleniumDriver.waitAndSendKeys(searchByAccountNumberField(), accountNumber);
    }

    public WebElement accountWithAppropriateId(String accountId)  {
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[contains(text(),'" + accountId + "')]"));
    }

    public void clickOnAccountWithAppropriateId(String accountId)  {
        seleniumDriver.waitAndClick(accountWithAppropriateId(accountId));
    }

    public WebElement filterButton() {
        return seleniumDriver.findElementWhenVisible(By.className("icon-filters"));
    }

    public void clickOnFilterButton()  {
        seleniumDriver.waitAndClick(filterButton());
    }

    public WebElement searchField() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//input[@type='search']"));
    }

    public void searchForAppropriateUser(String newNameOfDuplicatedUser)  {
        seleniumDriver.waitAndSendKeys(searchField(), newNameOfDuplicatedUser);
        searchField().sendKeys(Keys.ENTER);
    }

    public WebElement firstResultOfTheSearch() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//h5"));
    }

    public void clickOnFirstResultOfTheSearch()  {
        seleniumDriver.waitAndClick(firstResultOfTheSearch());
    }

    public String getTextOfTheFirstResult() {
        return firstResultOfTheSearch().getText();
    }

    public WebElement checkIfAccountTypeIsB2bProspect(String key) {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//*[@text='"+key+"'])[1]")); //key=B2B Prospect
    }

    public void ClickOnContractsLink()  {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("contract-list-link")));
    }
}
