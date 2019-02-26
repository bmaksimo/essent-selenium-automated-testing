package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;


public class ContractPage extends Component {

    public WebElement startData() {
        return seleniumDriver.findElementWhenVisible(By.id("contract-start-date-field"));
    }

    String pattern = "dd/MM/yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    String date = simpleDateFormat.format(new Date());

    public void startDateIsToday() throws InterruptedException {
        seleniumDriver.waitAndSendKeys(startData(), "date");
    }

    public void saveButtton() throws InterruptedException {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("primaryButton")));
    }

    public String getClientNumber() throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.xpath("//blue-sidebar//h4")).getText();
    }

    public void selectAccount() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"account-id-field\"]//span[2]")));
    }

    public void searchByClientNuiber(String nubmer) throws InterruptedException {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("search-input")), nubmer);
    }

    public void clickOnPlusMeniInTable(String row, String table) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementOrNull(By.xpath("(//list[@list-key='" + table + "']//tbody[@id='rows']//list-plus-cell//a[@class='show-actions icon-plus'])[" + row + "]")));
    }

    public String getActiveContractEAN() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"rows\"]//list-link-bold-top-two-liner-cell//a/h5")).getText();
    }

    public String status() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"rows\"]/tr[1]/td[2]/list-simple-two-liner-cell/p/span[1]")).getText();
    }

    public void clickOnX() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//guidance-modal//div[@class = 'modal__header']/a")));

    }

    public void selectItemLegalForm() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"legal-form-c-field\"]/option[2]")));
    }

    public void selectGender() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"gender-c-field\"]/option[2]")));
    }

    public void getEmail(String emailContract) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"leads-contact-details-contact-details-type-email-contact-details-value-field\"]")), emailContract);
    }

    public void clickNaceCode() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"nace-code-c-field\"]")));
    }

    public void clickOnSearch() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//div[@class = 'input__with-button']/input")));
    }

    public void checkNaceCode() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//select-with-search-modal/section//span")));
    }

    public void saveSelectedItem() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//select-with-search-modal/section//div[@class = 'modal__header']/a")));
    }

    public void setAddress(String address, String houseNumber, String postalCode, String City) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-street-field\"]")), address);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-number-field\"]")), houseNumber);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-postalcode-field\"]")), postalCode);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-city-field\"]")), City);

    }

    public void setTelephone(String telephone) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field\"]")), telephone);
    }

    public void setName(String fname, String lname) throws Throwable {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"first-name-field\"]")), fname);
        Thread.sleep(2000);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"last-name-field\"]")), lname);

    }

    public void setCompanyName(String cname) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"company-name-c-field\"]")), cname);

    }

    public void setEanCode(String eanCode) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"ean-c-accounts-aos-quotes-aos-products-quotes-7-cc-91145-f-43-f-4800-d-705-58-ffa-7899-fbd-field\"]")), eanCode);

    }

    public void saveInitialQuote() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"primaryButton\"]/span[2]")));

    }

    public void checkInvoiceOpenBalance(String key) {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list-checkbox-cell[@list-key='" + key + "']")));
    }

    public String getActiveContractEndDate() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"rows\"]/tr[1]/td[5]/list-simple-two-liner-cell/p/span[2]")).getText();
    }

    public String getStartDate() {
        seleniumDriver.waitForRequestsToFinish();
        String startDate = seleniumDriver.findElementWhenVisible(By.xpath("//td[@class='list__cell cell__text'][4]//p/span[1]")).getText();
        return startDate;
    }


    public String getFirstDayOfQuarter(String startDate, String attestDate) {
        String str[] = startDate.split("-");
        Integer month = Integer.parseInt(str[1]);
        String year = str[2];

        String str2[] = attestDate.split("/");
        Integer month2 = Integer.parseInt(str2[1]);
        String year2 = str2[2];

        String quarterMonth;


        if (startDate.compareTo(attestDate)>0) {
            if (month <= 3) {
                quarterMonth = "03";
            }

            else if (month >= 4 && month <= 6) {
                quarterMonth = "06";
            }
            else if (month >= 7 && month <= 9) {
                quarterMonth = "09";
            }
            else{
                quarterMonth = "12";
            }
        }
        else if (startDate.compareTo(attestDate)<0)
        {
             if (year.compareTo(year2)<0) {
                 quarterMonth = "01";

             }
             else {
                 if (month <= 3) {
                     quarterMonth = "03";

                 }

                 else if (month >= 4 && month <= 6) {
                 quarterMonth = "06";

                 }

                 else if (month >= 7 && month <= 9) {
                 quarterMonth = "09";

                 }

                 else{
                 quarterMonth = "12";

                 }

             }
            year = year2;
        }

        else {
            if (month <= 3) {
                quarterMonth = "03";
            }

            else if (month >= 4 && month <= 6) {
                quarterMonth = "06";
            }

            else if (month >= 7 && month <= 9) {
                quarterMonth = "09";
            }

            else{
                quarterMonth = "12";
            }

        }

            StringBuilder builder = new StringBuilder();

            builder.append(date);
            builder.replace(0, builder.length(), "01/");
            builder.append(quarterMonth + "/");
            builder.append(year);
            String quarterDate = builder.toString();

            return quarterDate;

    }


    public String getLastDayOfYearByContractStartDate(String date) {

        String str[] = date.split("-");
        String month = str[1];
        String year = str[2];

        StringBuilder builder = new StringBuilder();
        builder.append(date);
        builder.replace(0,builder.length(),"31/12/");
        builder.append(year.toString());
        String endDate = builder.toString();
        return endDate;
    }

}
