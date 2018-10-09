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
        return seleniumDriver.findElementWhenClickable(By.xpath("//select-with-search-modal/section[@class='view__modal']//a[@href='']"));
    }

    public void clickOnSubmitButton() throws InterruptedException {
        seleniumDriver.waitAndClick(submitButton());
    }

}

