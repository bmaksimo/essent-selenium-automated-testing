package com.essent.testing.dwp.pageobject.DashboardPages.MarktberishtenPages;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MarktBerichtenPage extends Component {

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
        return seleniumDriver.findElementWhenVisible(By.xpath("//list-link-bold-top-two-liner-cell[@line-1='INITIATE STOP ACCESS']/div/a/h5")).getText();
    }

    public String getEanFromMarketbericht(String num)  {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//h5)['"+num+"']")).getText();
    }
    public String getModulFromMarketbericht(String num) {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//h5)['"+num+"']")).getText();
    }
    public String marketberichtStatusMarketbericht(String num){
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell//span[1])['"+num+"']")).getText();
    }

    public String getMarketberichtEndDateElement(String num){
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell//span[1])['"+num+"']")).getText();
    }

    public String getEanCode() {
        return findElementWhenVisible(By.id("aos-products-quotes-ean-c-field")).getText();
    }

    public boolean validateRejectionHeader(String input) {
        return findElementWhenVisible(By.xpath("(//h5)[.='" + input + "'][1]")).isDisplayed();
    }

    public void setEanCodeInFilter(String eanCode) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.xpath("//validation-wrapper[@label='EAN-code']/div[@class='input label-inline']//input-form-element//input")), eanCode);
    }
}

