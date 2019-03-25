package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.time.format.DateTimeFormatter;

public class ContractPage extends Component {

    public WebElement startData() {
        return seleniumDriver.findElementWhenVisible(By.id("contract-start-date-field"));
    }

    public String pattern = "dd/MM/yyyy";
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


    public String date = simpleDateFormat.format(new Date());
    String xpath = "//div[@class = 'non-editable-editor']";

    public void startDateIsToday() {
        seleniumDriver.waitAndSendKeys(startData(),"date");
    }

    public void saveButton(){
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("primaryButton")));
    }


    public String getClientNumber() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//blue-sidebar//h4")).getText();
    }

    public void selectAccount() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"account-id-field\"]//span[2]")));
    }

    private WebElement getSearchInputElemnt(){
        return seleniumDriver.findElementWhenVisible(By.id("search-input"));
    }

    public void searchByClientNumber(String number){
        seleniumDriver.waitAndSendKeys(getSearchInputElemnt(),number);
        seleniumDriver.waitAndSendKeys(getSearchInputElemnt(), number);
    }

    public void clickOnPlusMeniInTable(String row, String table) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementOrNull(By.xpath("(//list[@list-key='" + table + "']//tbody[@id='rows']//list-plus-cell//a[@class='show-actions icon-plus'])[" + row + "]")));
    }

    public String getActiveContractEAN() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"rows\"]//list-link-bold-top-two-liner-cell//a/h5")).getText();
    }

    public String contractStatus() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"rows\"]/tr[1]/td[2]/list-simple-two-liner-cell/p/span[1]")).getText();
    }

    public void checkInvoiceOpenBalance(String key) {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-checkbox-cell[@list-key='" + key + "']")));
    }

    public String getActiveContractEndDate() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"rows\"]/tr[1]/td[5]/list-simple-two-liner-cell/p/span[2]")).getText();
    }

    public String getActiveContractStartDate(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"rows\"]/tr[1]/td[5]/list-simple-two-liner-cell/p/span[1]")).getText();
    }

    public String getEanFromContract(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list-link-bold-top-two-liner-cell/div/a/h5)[1]")).getText();
    }

    public String getStatusFromContract(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list-link-bold-top-two-liner-cell/div/h6)[2]")).getText();
    }

    public String getContractType(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='ContractsOnAccount']//tr[1]/td[3]//span[1]")).getText();
    }

    public void clickOnContractenNummer(){
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='ContractsOnAccount']//tr[1]/td[4]//h5")));
    }

    public String getProductName(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list[@list-key='ContractlinesOnContract']//td[@class='list__cell cell__text']//p/span[2])[1]")).getText();
    }

    public String getKortingenOpContractKortingscode(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='DiscountsOnContract']//tr[1]/td[1]//span[1]")).getText();
    }

    public String getKortingenOpContractProducttype(){
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='DiscountsOnContract']//tr[1]/td[2]//span[1]")).getText();
    }

    public void chooseDiscounts(String discount){
        Sleeper.sleepTightInSeconds(6);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("dwp|discount_id")));
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id='dwp-discount-id-field']/option[@label='"+discount+"']")));
    }

    public String getCompanyNumber(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//*//*[@id=\"company-number-c-field\"]")).getText();
    }

    public String getContractNumber(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"account_number_c\"]/div")).getText();
    }

    public String getStartDate() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//td[@class='list__cell cell__text'][4]//p/span[1]")).getText();

    }

    public String getQuarterForChosenStartDate(String startDate, String attestDate) {
        String str[] = startDate.split("-");
        int monthStartDate = Integer.parseInt(str[1]);
        String yearStartDate = str[2];

        String str2[] = attestDate.split("/");
        String yearAttestDate = str2[2];

        String quarterEndMonth;


        if (startDate.compareTo(attestDate)>0) {
            if (monthStartDate <= 3) {
                quarterEndMonth = "03";
            }

            else if (monthStartDate <= 6) {
                quarterEndMonth = "06";
            }
            else if (monthStartDate <= 9) {
                quarterEndMonth = "09";
            }
            else{
                quarterEndMonth = "12";
            }
        }
        else if (startDate.compareTo(attestDate)<0)
        {
             if (yearStartDate.compareTo(yearAttestDate)<0) {
                 quarterEndMonth = "01";

             }
             else {
                 if (monthStartDate <= 3) {
                     quarterEndMonth = "03";

                 }

                 else if (monthStartDate <= 6) {
                     quarterEndMonth = "06";

                 }

                 else if (monthStartDate <= 9) {
                     quarterEndMonth = "09";

                 }

                 else{
                     quarterEndMonth = "12";

                 }

             }
            yearStartDate = yearAttestDate;
        }

        else {
            if (monthStartDate <= 3) {
                quarterEndMonth = "03";
            }

            else if (monthStartDate <= 6) {
                quarterEndMonth = "06";
            }

            else if (monthStartDate <= 9) {
                quarterEndMonth = "09";
            }

            else{
                quarterEndMonth = "12";
            }

        }

            StringBuilder builder = new StringBuilder();

            builder.append(date);
            builder.replace(0, builder.length(), "01/");
            builder.append(quarterEndMonth).append("/");

            builder.append(yearStartDate);

        return builder.toString();

    }

    public String getLastDayOfYear(String startDate, String attestDate) {

        String str[] = startDate.split("-");
        String str2[] = attestDate.split("/");

        String yearStartDate = str[2];
        String yearAttestDate = str2[2];

        if (startDate.compareTo(attestDate)<0)
        {
            yearStartDate = yearAttestDate;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(startDate);
        builder.replace(0,builder.length(),"31/12/");
        builder.append(yearStartDate);
        return builder.toString();
    }

    public static long rangeDates(String sd, String ed) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate sDate = LocalDate.parse(sd, format);
        LocalDate eDate = LocalDate.parse(ed, format);
        // Range = End date - Start date
        long range = ChronoUnit.DAYS.between(sDate, eDate);
        System.out.println("Number of days between the start date : " + sDate + " and end date : " + eDate
            + " is  ==> " + range);

        return range;
    }

    public String checkSuccessMessage () {
        seleniumDriver.waitForRequestsToFinish();
        String messageProductChange = seleniumDriver.findElementWhenVisible(By.xpath(xpath)).getText();
        String[] values = {"1 succeeded", "1 queuded", "1 failed"};
        String match = "";

        for (String value : values) {
            if (messageProductChange.contains(value)) {
                match = value;
                break;
            }
        }
        switch (match) {
            case "succeeded":
                break;
            case "queuded":
                break;
            case "failed":
                break;

        }
        return match;
    }
    public void openFirstContractFromList() {
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//div[@class = 'col-1-1']/div[@class = 'row-']/list[@list-key = 'ContractedEansOnAccount']//tbody[@id = 'rows']/tr[1]/td[4]")));
        seleniumDriver.waitForRequestsToFinish();
    }

    public void contractPlus() {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[1]/td[12]")));
    }

    public void changeAmount(String value) {
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("dwp-recurring-amount-field")),value);
        seleniumDriver.waitForRequestsToFinish();
    }

    public boolean getAmountOfACustomer(String amount) {
        String amountValue = findElementWhenVisible(By.id("advance-amount-field")).getText();
        String amountParameter = amount + ",00";
        String[] value = amountValue.split(" ", 2);
//        it is not in the use at the moment, if not used in future runs it will be deleted
//        for (String i : value) {
//        }
        return amountParameter.equals(value[1]);
    }

    public String findActiveContract(String input) {
        seleniumDriver.waitForRequestsToFinish();
        int counter = 2;
        String eanCode;
        String action = findElementWhenVisible(By.xpath("(//h6)[" + counter + "]")).getText();
        while (!action.equalsIgnoreCase(input)) {
            counter = counter + 2;
            action = findElementWhenVisible(By.xpath("(//h6)[" + counter + "]")).getText();
        }
        counter--;
        eanCode = findElementWhenVisible(By.xpath("(//h5)[" + counter + "]")).getText();

        return eanCode;
    }

    public void searchForEanCode(String eanCode) {
        seleniumDriver.waitForRequestsToFinish();
        getSearchInputElemnt().clear();
        seleniumDriver.waitAndSendKeys(getSearchInputElemnt(),eanCode);
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//input[@value='Search']")));
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//div[@class='multi-select__results']//ul[2]")));
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//section[@class='view__modal']//a[@href='']")));
    }

    public void fieldDropDownLabel(String label, String input) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.findElementWhenVisible(By.xpath("//validation-wrapper[@label='" + label + "']/div/div/ng-form/div/select-form-element/div/select/option[@label='" + input + "']")).click();
    }

    public void turnOnTestingAndMarketMock(String label) {
        if (label.equalsIgnoreCase("Testing")) {
            seleniumDriver.waitAndClick(findElementWhenVisible(By.id("dwp|toggle_testing")));
        } else if (label.equalsIgnoreCase("Market mock")) {
            seleniumDriver.waitAndClick(findElementWhenVisible(By.id("aos_products_quotes|market_mock_c")));
        }
    }

    private WebElement getTopSearchInputElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//input[@type='search']"));
    }

    public void searchForTaskId(String taskId) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(getTopSearchInputElement(), taskId);
        getTopSearchInputElement().sendKeys(Keys.ENTER);
    }

    public void sendEmailToCustomer(String test) {
        seleniumDriver.waitForRequestsToFinish();
        BaseObjectPage baseObject = new BaseObjectPage();
        baseObject.clickOnPlus();
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-row-action[@label='"+test+"']/a")));
    }
}
