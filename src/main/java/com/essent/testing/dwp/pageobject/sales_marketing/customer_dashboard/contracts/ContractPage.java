package com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static org.apache.camel.component.file.GenericFileExist.Append;

public class ContractPage extends Component {

    private static final String NUMBER_ELECTRICITY_CONTRACT = "//list-icon-text-cell/div";
    private static final String REPLACEMENT_KEY = "replacement_key";
    private static final String SEND_EMAIL = "//list-row-action[@label='${" + REPLACEMENT_KEY + "}']/a";
    private static final String START_DATA_ID = "contract-start-date-field";
    private static final String XPATH_SELECT_ACCOUNT = "//*[@id=\"account-id-field\"]//span[2]";
    //TODO rewrite to standard work with contract lines
    private static final String FIRST_CONTRACT_LINE_XPATH_EXPRESSION = "//div[@class = 'col-1-1']/div[@class = 'row-']/list[@list-key = 'ContractedEansOnAccount']//tbody[@id = 'rows']/tr[1]/td[4]";
    private static final String XPATH_ACTIVE_CONTRACT_EAN = "//*[@id=\"rows\"]//list-link-bold-top-two-liner-cell//a/h5";
    private static final String PAYMENT_PLAN_NUMBER = "//list[@list-key='PaymentPlansOnAccount']//tbody[@id='rows']";
    private static final String SALDO_CREDIT_INVOICE = "total-amount-open-field";
    private static final String FIRST_INVOICE = "//tbody/tr[1]/td[5]//span[1]";
    private static final String SECOND_INVOICE = "//tbody/tr[3]/td[5]//span[1]";
    private static final String THIRD_INVOICE = "//tbody/tr[5]/td[5]//span[1]";
    private static final String EAN_LOCATOR_INVOICE_AMOUNT = "dwp-ean-'${" + REPLACEMENT_KEY + "}'-field";
    private static final String CONTRACT_STATUS = "(//list[@list-key='ContractsOnAccount']//list-simple-two-liner-cell/p/span[2])[1]";
    private static final String PRODUCT_CONTRACT = "(//list[@list-key='ContractsOnAccount']//list-link-bold-top-two-liner-cell/div/a/h5)[3]";
    private static final String EAN_NEW_INVOICE_AMOUNT = "dwp-ean-${" + REPLACEMENT_KEY + "}-field";
    private static final String INVOICE_SUM = "total-amount-field";
    private static final String INSTALLMENTS_SUM = "balance-field";
    private static final String INSTALLMENTS_NUMBER = "//list[@list-key='InstallmentsOnPaymentPlan']//h5";
    private static final String BILLING_NUMBER = "//list[@list-key='BillingCustomerOnaccount']//td[1]//span[1]";

    private WebElement startData() {
        return seleniumDriver.findElementWhenVisible(By.id(START_DATA_ID));
    }

    private static SimpleDateFormat SIMPLE_DATEF_ORMAT = new SimpleDateFormat("dd/MM/yyyy");


    private static final String LABELFORPRODUCTCHANGE = "//wysiwyg-editor-form-element[@id='description']/div[@class = 'non-editable-editor']";
    private static final String ACCOUNT_NUMBER = "//div[@class='card__content__inner-wrapper']/h4";
    private static final String CONTRACT_NUMBER = "//*[@id=\"account_number_c\"]/div";
    private static final String COMPANY_NUMBER = "//*//*[@id=\"company-number-c-field\"]";


    public void startDateIsToday() {
        seleniumDriver.waitAndSendKeys(startData(), "date");
    }

    public void saveButton() {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("primaryButton")));
    }


    public String getClientNumber() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//blue-sidebar//h4")).getText();
    }

    public String getBillingNumber() {
        return seleniumDriver.findElementWhenVisible(By.xpath(BILLING_NUMBER)).getText();
    }

    public void selectAccount() {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(XPATH_SELECT_ACCOUNT)));
    }

    private WebElement getSearchInputElemnt() {
        return seleniumDriver.findElementWhenVisible(By.id("search-input"));
    }

    public void searchByClientNumber(String number) {
        seleniumDriver.waitAndSendKeys(getSearchInputElemnt(), number);
        seleniumDriver.waitAndSendKeys(getSearchInputElemnt(), number);
    }

    public void clickOnPlusMeniInTable(String row, String table) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenPresent(By.xpath("(//list[@list-key='" + table + "']//tbody[@id='rows']//list-plus-cell//a[@class='show-actions icon-plus'])[" + row + "]")));
    }

    public String getActiveContractEAN() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath(XPATH_ACTIVE_CONTRACT_EAN)).getText();
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

    public String getActiveContractStartDate() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"rows\"]/tr[1]/td[5]/list-simple-two-liner-cell/p/span[1]")).getText();
    }

    public String getEanFromContract() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list-link-bold-top-two-liner-cell/div/a/h5)[1]")).getText();
    }

    public String getStatusFromContract() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath(CONTRACT_STATUS)).getText();
    }

    public String getContractType() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='ContractsOnAccount']//tr[1]/td[3]//span[1]")).getText();
    }

    public void clickOnContractenNummer() {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='ContractsOnAccount']//tr[1]/td[4]//h5")));
    }

    public String getProductName() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("(//list[@list-key='ContractlinesOnContract']//td[@class='list__cell cell__text']//p/span[2])[1]")).getText();
    }

    public String getKortingenOpContractKortingscode() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='DiscountsOnContract']//tr[1]/td[1]//span[1]")).getText();
    }

    public String getKortingenOpContractProducttype() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.xpath("//list[@list-key='DiscountsOnContract']//tr[1]/td[2]//span[1]")).getText();
    }

    public void chooseDiscounts(String discount) {
        Sleeper.sleepTightInSeconds(6);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("dwp|discount_id")));
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id='dwp-discount-id-field']/option[@label='" + discount + "']")));
    }

    public String getCompanyNumber() {
        return seleniumDriver.findElementWhenVisible(By.xpath(COMPANY_NUMBER)).getText();
    }

    public String getAccountNumber() {
        return seleniumDriver.findElementWhenVisible(By.xpath(ACCOUNT_NUMBER)).getText();
    }

    public String getContractNumber() {
        return seleniumDriver.findElementWhenVisible(By.xpath(CONTRACT_NUMBER)).getText();
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


        if (startDate.compareTo(attestDate) > 0) {
            if (monthStartDate <= 3) {
                quarterEndMonth = "03";
            } else if (monthStartDate <= 6) {
                quarterEndMonth = "06";
            } else if (monthStartDate <= 9) {
                quarterEndMonth = "09";
            } else {
                quarterEndMonth = "12";
            }
        } else if (startDate.compareTo(attestDate) < 0) {
            if (yearStartDate.compareTo(yearAttestDate) < 0) {
                quarterEndMonth = "01";

            } else {
                if (monthStartDate <= 3) {
                    quarterEndMonth = "03";

                } else if (monthStartDate <= 6) {
                    quarterEndMonth = "06";

                } else if (monthStartDate <= 9) {
                    quarterEndMonth = "09";

                } else {
                    quarterEndMonth = "12";

                }

            }
            yearStartDate = yearAttestDate;
        } else {
            if (monthStartDate <= 3) {
                quarterEndMonth = "03";
            } else if (monthStartDate <= 6) {
                quarterEndMonth = "06";
            } else if (monthStartDate <= 9) {
                quarterEndMonth = "09";
            } else {
                quarterEndMonth = "12";
            }

        }

        StringBuilder builder = new StringBuilder();
        String date = SIMPLE_DATEF_ORMAT.format(new Date());
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

        if (startDate.compareTo(attestDate) < 0) {
            yearStartDate = yearAttestDate;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(startDate);
        builder.replace(0, builder.length(), "31/12/");
        builder.append(yearStartDate);
        return builder.toString();
    }

    public static long rangeDates(String sd, String ed) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate sDate = LocalDate.parse(sd, format);
        LocalDate eDate = LocalDate.parse(ed, format);
        // Range = End date - Start date
        long range = ChronoUnit.DAYS.between(sDate, eDate);
        Logger.getLogger("Number of days between the start date : " + sDate + " and end date : " + eDate
            + " is  ==> " + range);

        return range;
    }

    public String checkSuccessMessage() {
        seleniumDriver.waitForRequestsToFinish();
        String messageProductChange = seleniumDriver.findElementWhenPresent(By.xpath(LABELFORPRODUCTCHANGE)).getText();
        String[] values = {"1 succeeded", "1 queued", "1 failed"};
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
            case "queued":
                break;
            case "failed":
                break;

        }
        return match;
    }

    public void openFirstContractFromList() {
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath(FIRST_CONTRACT_LINE_XPATH_EXPRESSION)));
        seleniumDriver.waitForRequestsToFinish();
    }

    public void contractPlus() {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[1]/td[12]")));
    }

    public void changeAmount(String value) {
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.id("dwp-recurring-amount-field")), value);
        seleniumDriver.waitForRequestsToFinish();
    }

    public boolean getAmountOfACustomer(String amount) {
        String amountValue = findElementWhenVisible(By.id("advance-amount-field")).getText();
        String amountParameter = amount + ",00";
        String[] value = amountValue.split(" ", 2);
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
        seleniumDriver.waitAndSendKeys(getSearchInputElemnt(), eanCode);
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

    private WebElement getTopSearchInputElement() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//input[@type='search']"));
    }

    public void searchForTaskId(String taskId) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.sendKeysNow(getTopSearchInputElement(), taskId);
        seleniumDriver.waitAndSendKeys(getTopSearchInputElement(), taskId);
        getTopSearchInputElement().sendKeys(Keys.ENTER);
    }

    public void sendEmailToCustomer(String test) {
        seleniumDriver.waitForRequestsToFinish();
        BaseObjectPage baseObject = new BaseObjectPage();
        baseObject.clickOnPlus();
        seleniumDriver.waitForRequestsToFinish();
        String xpathSubAction = createQuery(SEND_EMAIL, REPLACEMENT_KEY, test);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath(xpathSubAction)));

    }

    public int getNumberOfElectricityContracts() {
        return seleniumDriver.findElements(By.xpath(NUMBER_ELECTRICITY_CONTRACT)).size();

    }

    public boolean checkPaymentTableNotEmpty() {
        seleniumDriver.waitForRequestsToFinish();
        List<WebElement> rows = seleniumDriver.findElements(By.xpath(PAYMENT_PLAN_NUMBER));

        seleniumDriver.waitForRequestsToFinish();
        return CollectionUtils.isNotEmpty(rows);

    }

    public  String getStatusFromContracten(){
        return seleniumDriver.findElementWhenVisible(By.xpath(CONTRACT_STATUS)).getText();
    }

    public  String getProductFromContracten(){
        return seleniumDriver.findElementWhenVisible(By.xpath(PRODUCT_CONTRACT)).getText();
    }

    public String getActualValuesOfInvoicesAsString() {
        seleniumDriver.waitForRequestsToFinish();
        String firstInvoice  =  seleniumDriver.findElementWhenVisible(By.xpath(FIRST_INVOICE)).getText();
        String secondInvoice = seleniumDriver.findElementWhenVisible(By.xpath(SECOND_INVOICE)).getText();
        String thirdInvoice  = seleniumDriver.findElementWhenVisible(By.xpath(THIRD_INVOICE)).getText();

        StringBuilder sb;
        sb = new StringBuilder();

        return sb.append(firstInvoice).append(' ').append(secondInvoice).append(' ').append(thirdInvoice).toString();
    }

    public boolean checkIsInvoicesAmountsAsStringCorrect(String amountInvoicesCombination1, String amountInvoicesCombination2, String amountInvoicesCombination3, String amountInvoicesCombination4, String actualValuesOfInvoices)
    {
        boolean matchingValuesOfInvoices = false;
        if (amountInvoicesCombination1.equals(actualValuesOfInvoices) || amountInvoicesCombination2.equals(actualValuesOfInvoices)
            || amountInvoicesCombination3.equals(actualValuesOfInvoices) || amountInvoicesCombination4.equals(actualValuesOfInvoices))
        {
            matchingValuesOfInvoices = true;
        }

        return matchingValuesOfInvoices;
    }

    public String getBalance() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenVisible(By.id(SALDO_CREDIT_INVOICE)).getText();
    }


    public boolean compareActualAndExpectedBalances(String balance1, String balance2, String balance3, String actualBalance) {
        if (balance1.equals(actualBalance))
        {
            logger().info("- STEP: Values of invoices \"%s\" are correct - PASSED.");
            return true;
        }

        else if (balance2.equals(actualBalance))
        {
            logger().info("- STEP: Values of invoices \"%s\" are correct - PASSED.");
            return true;
        }

        else if (balance3.equals(actualBalance))
        {
            logger().info("- STEP: Values of invoices \"%s\" are correct - PASSED.");
            return true;
        }

        else return false;
    }


    public void getElementByEanNewInvoiceAmount(String ean, String value) {
        String xpathAction = createQuery(EAN_NEW_INVOICE_AMOUNT, REPLACEMENT_KEY, ean);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id(xpathAction)), value);

    }

    public String getInstallmentSum() {
       return seleniumDriver.findElementWhenPresent(By.id(INSTALLMENTS_SUM)).getText();
    }
    public String getInvoiceSum() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementWhenPresent(By.id(INVOICE_SUM)).getText();
    }

    public int installmentsNumber(String amount) {
       seleniumDriver.waitForRequestsToFinish();
       List<WebElement> installments = seleniumDriver.findElements(By.xpath(INSTALLMENTS_NUMBER));
       int numInstallThanHaveGivenAmount= 0;
       for (int i = 1; i <= installments.size()-1; i++) {
           if (amount.equalsIgnoreCase(installments.get(i).getText()))
               numInstallThanHaveGivenAmount+=1;
       }
       return numInstallThanHaveGivenAmount;
    }

    public int findDifferenceInAmounts(String installAmount, String invoiceAmount) {
        seleniumDriver.waitForRequestsToFinish();
        String invAmount = invoiceAmount.replaceAll(" .+$", "");
        int result1 = Integer.parseInt(installAmount);
        int result2 = Integer.parseInt(invAmount);
        return result1 - result2;
    }



}
