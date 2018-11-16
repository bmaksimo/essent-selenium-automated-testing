package com.essent.testing.dwp.pageobject.impl.page;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import gherkin.lexer.Da;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ContractPage extends Component {

    public ContractPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public WebElement startData(){
        return seleniumDriver.findElementWhenVisible(By.id("contract-start-date-field"));
    }
    String pattern = "dd/MM/yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    String date = simpleDateFormat.format(new Date());

    public void startDateIsToday()throws InterruptedException {
        seleniumDriver.waitAndSendKeys(startData(),"date");
    }

    public void saveButtton()throws InterruptedException {
        waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("primaryButton")));
    }

    public String getClientNumber()throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.xpath("//blue-sidebar//h4")).getText();
    }

    public void selectAccount(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"account-id-field\"]//span[2]")));
    }

    public void searchByClientNuiber(String nubmer)throws InterruptedException{
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("search-input")),nubmer);
    }

    public void clickOnPlusMeniInTable(String row, String table) {
        waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementOrNull(By.xpath("(//list[@list-key='"+table+"']//tbody[@id='rows']//list-plus-cell//a[@class='show-actions icon-plus'])["+row+"]")));
    }
}
