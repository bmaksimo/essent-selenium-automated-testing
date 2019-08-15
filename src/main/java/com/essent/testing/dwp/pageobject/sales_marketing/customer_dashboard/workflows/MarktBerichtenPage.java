package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.workflows;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MarktBerichtenPage extends Component {

    private static final String REPLACEMENT_KEY = "replacement_key";
    private static final String EAN_FROM_MARKET = "(//h5)[${" + REPLACEMENT_KEY + "}]";
    private static final String STATUS_FROM_MARKET_MSG = "(//list-simple-two-liner-cell//span[1])[${" + REPLACEMENT_KEY + "}]";
    private static final String END_DATE_ELEMENT = "(//list-simple-two-liner-cell//span[2])[${" + REPLACEMENT_KEY + "}]";
    private static final String EAN_CODE = "aos-products-quotes-ean-c-field";
    private static final String TASK_STATUS = "(//h6)[.='${" + REPLACEMENT_KEY + "}']";
    private static final String MARKET_LABEL = "(//list-link-bold-top-two-liner-cell/div/h6)[2]";


    public WebElement listActionsElemet(String element) {
        return seleniumDriver.findElementWhenVisible(By.name(element));
    }

    public void takenOver(String taken, String signed) {
        String line1 = seleniumDriver.findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell[@icon='null']//span)[1]")).getText();
        String line2 = seleniumDriver.findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell[@icon='null']//span)[2]")).getText();
        Assert.assertEquals(taken, line1);
        Assert.assertEquals(signed, line2);
    }

    public String marketberichtStatus(){
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell//span[1])[1]")).getText();
    }
    public String marketberichtCancelStatus(){
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell//span[1])[5]")).getText();
    }

    public void refreshByName(String name)  {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.linkText(name)));
    }

    public boolean isRefreshedByName(String name)  {
        refreshByName(name);
        return true;
    }

    public void createNewMarktBericht(String newMarktbericht){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.name(newMarktbericht)));
    }

    public void clickOnListActionsElemet(String element) {
        seleniumDriver.waitAndClick(listActionsElemet(element));
    }

    public String getEanFromTheFirstTransaction()  {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[1]")).getText();
    }

    public WebElement selectNewContractlineButton() {
        //TODO Remove locale-specific hard code.
        // The project must support official Belgian languages.
        // Locale-specific elements of web element locators must be parameterized.
        // This is basic rule!
        return seleniumDriver.findElementWhenVisible(By.xpath("//button[contains(.,'Select Contractline')]"));
    }

    public void clickOnSelectNewContractlineButton()  {
        seleniumDriver.waitAndClick(selectNewContractlineButton());
    }

    public WebElement searchForContractField() {
        return seleniumDriver.findElementWhenVisible(By.id("search-input"));
    }

    public void enterContractNumber(String transactionEan) {
        seleniumDriver.waitAndSendKeys(searchForContractField(), transactionEan);
    }

    public WebElement searchButton() {
        return findElementWhenVisible(By.xpath("//input[@value='Search']"));
    }

    public void clickOnSearchButton() {
        seleniumDriver.waitAndClick(searchButton());
    }

    public WebElement FirstContractInTheList() {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//label[@class='input__checkbox'])[2]"));
    }

    public void clickOnTheFirstContract() {
        seleniumDriver.waitAndClick(FirstContractInTheList());
    }

    public WebElement submitButton() {
        return findElementWhenClickable(By.xpath("//select-with-search-modal/section[@class='view__modal']//a[@href='']"));
    }

    public void clickOnSubmitButton() {
        seleniumDriver.waitAndClick(submitButton());
    }

    public String getModulFromTheFirstTransaction() {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[2]")).getText();
    }

    public String getModulFromCancelTransaction()  {
        //TODO Remove locale-specific hard code.
        // The project must support official Belgian languages.
        // Locale-specific elements of web element locators must be parameterized.
        // This is basic rule!
        return seleniumDriver.findElementWhenVisible(By.xpath("//list-link-bold-top-two-liner-cell[@line-1='INITIATE STOP ACCESS']/div/a/h5")).getText();
    }

    public WebElement getMarktBerichtH5Element(String num) {
        String eanMarket = createQuery(EAN_FROM_MARKET, REPLACEMENT_KEY, num);
        return seleniumDriver.findElementWhenVisible(By.xpath(eanMarket));
    }

    public String getEanFromMarketbericht(String num)  {
        return getMarktBerichtH5Element(num).getText();
    }

    public String getModulFromMarketbericht(String num) {
        return getMarktBerichtH5Element(num).getText();
    }

    public String marketberichtStatusMarketbericht(String num){
        String eanMarketStatus = createQuery(STATUS_FROM_MARKET_MSG, REPLACEMENT_KEY, num);
        return seleniumDriver.findElementWhenVisible(By.xpath(eanMarketStatus)).getText();
    }

    public String getMarketberichtEndDateElement(String num){
        String endDateElement = createQuery(END_DATE_ELEMENT, REPLACEMENT_KEY, num);
        return seleniumDriver.findElementWhenVisible(By.xpath(endDateElement)).getText();
    }

    public String getEanCodeNow() {
        return seleniumDriver.findElementWhenVisible(By.id(EAN_CODE)).getText();
    }

    public boolean validateRejectionHeader(String input) {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[.='" + input + "'][1]")).isDisplayed();
    }

    public WebElement getTaskStatus(String input) {
        seleniumDriver.waitForRequestsToFinish();
        String taskStatus = createQuery(TASK_STATUS, REPLACEMENT_KEY, input);
        return seleniumDriver.findElementWhenVisible(By.xpath(taskStatus));
    }

    public String getMarketberichtLabel(){
        return seleniumDriver.findElementWhenVisible(By.xpath(MARKET_LABEL)).getText();
    }
}

