package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.webdriver.dwp.SeleniumDriverDwpImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;



public class DwpHomePage extends Component {

    public DwpHomePage(SeleniumDriverDwpImpl seleniumDriver) {
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

    public void enterAccountId(String accountNumber)  {
        searchByAccountNumberField().sendKeys(accountNumber);
    }

    public WebElement accountWithAppropriateId(String accountId)  {
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[contains(text(),'" + accountId + "')]"));

    }

    public void clickOnAccountWithAppropriateId(String accountId)  {
        accountWithAppropriateId(accountId).click();
    }

    public WebElement filterButton() {

        return seleniumDriver.findElementWhenVisible(By.className("icon-filters"));
    }

    public void clickOnFilterButton()  {
        filterButton().click();
    }

    public WebElement searchField(String search ) {
        return seleniumDriver.findElementWhenVisible(By.xpath("//input[@type='"+search+"']"));
    }

    public void searchForAppropriateUser(String newNameOfDuplicatedUser, String search )  {
        searchField(search).clear();
        searchField(search).click();
        searchField(search).sendKeys(newNameOfDuplicatedUser);
        searchField(search).sendKeys(Keys.ENTER);

    }

    public WebElement firstResultOfTheSearch() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//h5"));
    }

    public void clickOnFirstResultOfTheSearch()  {
        firstResultOfTheSearch().click();
    }

    public String getTextOfTheFirstResult() {
        return firstResultOfTheSearch().getText();
    }

    public WebElement checkIfAccountTypeIsB2bProspect(String key) {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//*[@text='"+key+"'])[1]")); //key = B2B Prospect
    }

    public void ClickOnContractsLink()  {
        seleniumDriver.findElementWhenVisible(By.id("contract-list-link")).click();
    }


    public WebElement newCase() {
        return seleniumDriver.findElementWhenVisible(By.name("CASE TOEVOEGEN"));
    }

    public void clickOnNewCase(){
        seleniumDriver.waitAndClick(newCase());
    }
}
