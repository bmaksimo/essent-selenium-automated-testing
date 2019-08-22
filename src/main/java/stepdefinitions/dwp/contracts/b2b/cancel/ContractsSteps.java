package stepdefinitions.dwp.contracts.b2b.cancel;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.guided_flow.cupq.NewQuotePage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.Invoice_list.InvoiceListPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details.DetailsPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.sales.SalesPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.workflows.MarktBerichtenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import stepdefinitions.dwp.page_object.CustomerAcceptance;
import stepdefinitions.dwp.tables.CustomerStatus;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.testng.AssertJUnit.assertTrue;

public class ContractsSteps extends DwpScenario{

    private String eanCodeInput = null;

    @Before("@DWP or @REGRESSION or @API")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }


    @When("^Input in \"([^\"]*)\" is \"([^\"]*)\"$")
    public void inputInModuleIs(String label, String input) {
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(3);
        ContractPage contractenPage = new ContractPage();
        contractenPage.fieldDropDownLabel(label, input);
    }

    @And("^Check toggle \"([^\"]*)\"$")
    public void checkToggle(String label) {
        ContractPage contractenPage = new ContractPage();
        contractenPage.turnOnTestingAndMarketMock(label);
    }

    @When("^Find \"([^\"]*)\" contract$")
    public void findContract(String input) {
        ContractPage contractenPage = new ContractPage();
        eanCodeInput = contractenPage.findActiveContract(input);
        logger().debug("EAN CODE: " + eanCodeInput);
        parameterProvider.put("contractEanCode", eanCodeInput);
    }

    @When("^Contract line EAN-code \"([^\"]*)\" is submitted$")
    public void submitEanCode(String value) {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        ContractPage contractenPage = new ContractPage();
        contractenPage.searchForEanCode(inputValue);
    }

    @Then("^Confirm task was \"([^\"]*)\"$")
    public void confirmTaskWas(String value) {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        MarktBerichtenPage mp = new MarktBerichtenPage();
        Assert.assertTrue(mp.getTaskStatus(inputValue).isDisplayed());
    }

    @Override
    @After("@DWP or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^\"([^\"]*)\" input in omschrijving$")
    public void inputInOmschrijving(String text) {
        SalesPage sp = new SalesPage();
        sp.inputText(text);
    }

    @And("^Offertes plus options is \"([^\"]*)\"$")
    public void sendEMailToCustomer(String test) {
        ContractPage contractenPage = new ContractPage();
        contractenPage.sendEmailToCustomer(test);
    }

    @And("^List option is \"([^\"]*)\"$")
    public void openInvoiceOnly(String option)  {
        InvoiceListPage ilp = new InvoiceListPage();
        ilp.openListOption(option);
    }

    @Then("^Payment delayed$")
    public void paymentDelayed() {
        InvoiceListPage ilp = new InvoiceListPage();
        ilp.checkPayDate(parameterProvider.getValueOrParameterAsString("parameter:Datum & Vervaldatum"));
    }

    @Then("^Validate bank account was changed on \"([^\"]*)\"$")
    public void validateBankAccountWasChangedOn(String iban) {
        String inputIban = parameterProvider.getValueOrParameterAsString(iban);
        DetailsPage dp = new DetailsPage();
        dp.findIban(inputIban);
    }

    @And("^Get Contract Ean Code$")
    public void getEanCode() {
        String eanCode = seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[1]")).getText();
        parameterProvider.put("contractEanCode", eanCode);
    }

    @Then("^Save changes$")
    public void saveChanges() {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage contractenPage = new ContractPage();
        contractenPage.saveButton();
    }

    @Then("^Confirm contract with ean \"([^\"]*)\" was copied$")
    public void confirmContractWithEanWasCopied(String eanCode) {
        seleniumDriver.waitForRequestsToFinish();
        String inputEanCode = parameterProvider.getValueOrParameterAsString(eanCode);
        logger().debug("input EAN CODE: " + inputEanCode);
        Assert.assertTrue("Correct ean code was not found.", seleniumDriver.findElementWhenVisible(By.xpath("//h5[.='" + inputEanCode + "']")).isDisplayed());
    }

    /**
     * @deprecated - use generic '"1st" list element has cell value "value" at column "columnName"'
     */
    @Then("^Get Contract Number$")
    public void searchForContractNumber() {
        ContractPage contractenPage = new ContractPage();
        parameterProvider.put("contractNumber", contractenPage.getContractNumber());
    }


    @And("^Invoice checkbox with key \"([^\"]*)\" is clicked$")
    public void checkInvoiceOpenBalance(String text) {
        ContractPage contractenPage = new ContractPage();
        contractenPage.checkInvoiceOpenBalance(text);
    }

    @Then("^Customer Status is \"([^\"]*)\"$")
    public void customerStatus(String expectedStatus) {
        seleniumDriver.waitForRequestsToFinish();
        CustomerAcceptance customerAcceptance = new CustomerAcceptance();
        String actualStatus = customerAcceptance.getAcceptanceStatus();
        seleniumDriver.waitForRequestsToFinish();
        assertThat(String.format("Actual customer acceptance status \"%s\" differs from the expected \"%s\"", actualStatus, expectedStatus), actualStatus, equalTo(expectedStatus));

    }

    @Then("^Customer Status is an existing status$")
    public void customerStatus() {
        CustomerAcceptance customerAcceptance = new CustomerAcceptance();
        String actualStatus = customerAcceptance.getAcceptanceStatus();
        boolean success = CustomerStatus.containsStatus(actualStatus);
        assertThat(String.format("Actual customer acceptance status \"%s\" does not exist", actualStatus),
            success, is(true));

    }

    @Then("^Get Company Number$")
    public void searchForCompanyNumber() {
        ContractPage cp = new ContractPage();
        String companyNumber = cp.getCompanyNumber();
        parameterProvider.put("companyNumber", companyNumber);

    }

    @Then("^Get Account Number$")
    public void searchForAccountNumber() {
        ContractPage cp = new ContractPage();
        String accountNumber = cp.getAccountNumber();
        parameterProvider.put("accountNumber", accountNumber);

    }

    @And("^Sign place is \"([^\"]*)\"$")
    public void signPlaceIs(String place) {
        NewQuotePage nqp = new NewQuotePage();
        nqp.confirmTheSign(place);
    }

    @Then("^Populate Soctar with dates \"([^\"]*)\" and \"([^\"]*)\"$")
    public void searchForAttestDate(String startDate, String attestDate) {
        ContractPage contractenPage = new ContractPage();
        String sd = parameterProvider.getValueOrParameterAsString(startDate);
        String ad = parameterProvider.getValueOrParameterAsString(attestDate);

        String startDateByQuarter = contractenPage.getQuarterForChosenStartDate(sd, ad);
        String endDateByYear = contractenPage.getLastDayOfYear(sd, ad);
        parameterProvider.put("EndDateByYear", endDateByYear);
        parameterProvider.put("StartDateByQuarter", startDateByQuarter);
    }

    @Then("^Get Start Date$")
    public void searchForStartDate() {
        ContractPage contractenPage = new ContractPage();
        String startDate = contractenPage.getStartDate();
        parameterProvider.put("startDate", startDate);

    }

    @Then("^Start Date \"([^\"]*)\" is \"([^\"]*)\" day bigger than End Date \"([^\"]*)\"$")
    public void compareStartAndEndDate(String startDate, String ex, String endDate) {

        String[] expectedRange = ex.split(" or ");
        String sd = parameterProvider.getValueOrParameterAsString(startDate);
        String ed = parameterProvider.getValueOrParameterAsString(endDate);

        parameterProvider.put("startDate", startDate);
        parameterProvider.put("endDate", endDate);

        long actualRange = ContractPage.rangeDates(sd, ed);
        String ar = Long.toString(actualRange);
        Assert.assertTrue(String.format("Start date \"%s\" differs from the end date \"%s\" by more than 1 year ", sd, ed), Arrays.asList(expectedRange).contains(ar));
    }


    @Then("^Check is product change \"([^\"]*)\"$")
    public void checkProductChangeSuccess(String expectedMessage) {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage();
        int refreshCount = 30;
        for (int i = 0; i < refreshCount; i++) {
            if (cp.locateMessageElement().getText().contains(expectedMessage)) {
                break;
            } else {
                seleniumDriver.getDriver().navigate().back();
                seleniumDriver.getDriver().navigate().forward();
            }
        }
        Assert.assertTrue("Product change wasn't successfully done", cp.locateMessageElement().getText().contains(expectedMessage));
    }

    @Then("^Product Change dates are \"([^\"]*)\" and \"([^\"]*)\"$")
    public void productChangeDates(
        final String productChangeStartDate, final String productChangeEndDate) {
        String pcsd = parameterProvider.getValueOrParameterAsString(productChangeStartDate);
        String pced = parameterProvider.getValueOrParameterAsString(productChangeEndDate);
        parameterProvider.put("productChangeStartDate", pcsd);
        parameterProvider.put("productChangeEndDate", pced);
    }

    @When("^Payment table is not empty$")
    public void checkPaymentTableNotEmpty() throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage contractenPage = new ContractPage();
        boolean success = contractenPage.checkPaymentTableNotEmpty();

        assertThat("Rows in invoice table are empty", success, is(true));
        seleniumDriver.waitForRequestsToFinish();
    }

    @And("^Save Installments Sum$")
    public void installmentSum() {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage();
        cp.getInstallmentSum();
        parameterProvider.put("installmentsAmount", cp.getInstallmentSum());
        seleniumDriver.waitForRequestsToFinish();
    }

    @And("^Save Invoice Sum$")
    public void invoiceSum() {
        ContractPage cp = new ContractPage();
        cp.getInvoiceSum();
        parameterProvider.put("invoiceAmount", cp.getInvoiceSum());
    }


    @Then("^Check is Number of Installments at least \"([^\"]*)\" for given amount \"([^\"]*)\"$")
    public void checkInstallmentsNumber(int expectedNumberOfInstallments, String amount) {
        ContractPage cp = new ContractPage();
        int actualNumberOfInstallments = cp.installmentsNumber(amount);
        assertTrue("Insufficient Number of installments with given amount.",expectedNumberOfInstallments<=actualNumberOfInstallments);
    }

    @Then("^Installments Amount of \"([^\"]*)\" is bigger than Invoice Amount of \"([^\"]*)\"$")
    public void checkIsInstallmentAmountBiggerThanInvoiceAmount(
        String installmentsAmount, int expectedDifference, String invoiceAmount) {
//        ContractPage cp = new ContractPage();
        String installAmount = parameterProvider.getValueOrParameterAsString(installmentsAmount);
        String invAmount = parameterProvider.getValueOrParameterAsString(invoiceAmount);
        invAmount.replaceAll(" .+$", "");
        String insAmount = StringUtils.substringBefore(installAmount, ",");
        int result1 = Integer.parseInt(insAmount);
        int result2 = Integer.parseInt(invAmount);
//        int actualDifference = cp.findDifferenceInAmounts(installAmount, invAmount);
        assertThat("Installments amount is not bigger than invoice amount", result1>result2);
    }
}
