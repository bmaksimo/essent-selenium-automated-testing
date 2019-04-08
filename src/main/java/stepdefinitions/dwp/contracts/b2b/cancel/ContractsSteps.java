package stepdefinitions.dwp.contracts.b2b.cancel;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.guided_flow.cupq.NewQuotePage;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpDashboardMenuPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.Invoice_list.InvoiceListPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details.DetailsPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.sales.SalesPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.workflows.MarktBerichtenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.jbilling.pageobject.impl.page.InvoicesPage;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import stepdefinitions.dwp.page_object.CustomerAcceptance;


import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToDwpContractStartEndDate;
import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToSoctarFileDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class ContractsSteps extends DwpScenario {

    private String eanCodeInput = null;

    @Before("@DWP, @REGRESSION")
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
        logger().info("EAN CODE: " + eanCodeInput);
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
        mp.confirmTaskStatus(inputValue);
    }

    @Override
    @After("@DWP, @REGRESSION")
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
        ilp.checkPayDate();
    }

    @And("^Find \"([^\"]*)\" facture and \"([^\"]*)\"$")
    public void findFactureAnd(String type, String option) {
        InvoiceListPage ilp = new InvoiceListPage();
        ilp.findIssuedAndPayDelay(type, option);
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
        ContractPage contractenPage = new ContractPage();
        contractenPage.saveButton();
    }

    @Then("^Confirm contract with ean \"([^\"]*)\" was copied$")
    public void confirmContractWithEanWasCopied(String eanCode) {
        seleniumDriver.waitForRequestsToFinish();
        String inputEanCode = parameterProvider.getValueOrParameterAsString(eanCode);
        logger().info("input EAN CODE: " + inputEanCode);
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


    @And("^Invoice checkbox with key \"([^\"]*)\" is clicked")
    public void checkInvoiceOpenBalance(String text) {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage contractenPage = new ContractPage();
        contractenPage.checkInvoiceOpenBalance(text);
    }

    @Then("^Customer Status is \"([^\"]*)\"$")
    public void customerStatus(String expectedStatus) {
        CustomerAcceptance customerAcceptance = new CustomerAcceptance();
        String actualStatus = customerAcceptance.getAcceptanceStatus();
        assertThat(String.format("Actual customer acceptance status \"%s\" differs from the expected \"%s\"", actualStatus, expectedStatus), actualStatus, equalTo(expectedStatus));

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

        String StartDateByQuarter = contractenPage.getQuarterForChosenStartDate(sd, ad);
        String EndDateByYear = contractenPage.getLastDayOfYear(sd, ad);
        parameterProvider.put("EndDateByYear", EndDateByYear);
        parameterProvider.put("StartDateByQuarter", StartDateByQuarter);
    }

    @Then("^Get Start Date$")
    public void searchForStartDate() {
        ContractPage contractenPage = new ContractPage();
        String startDate = contractenPage.getStartDate();
        parameterProvider.put("startDate", startDate);

    }

    @Then("Start Date \"([^\"]*)\" is \"([^\"]*)\" day bigger than End Date \"([^\"]*)\"$")
    public void compareStartAndEndDate(String startDate, long expectedRange, String endDate) {

        String sd = parameterProvider.getValueOrParameterAsString(startDate);
        String ed = parameterProvider.getValueOrParameterAsString(endDate);

        parameterProvider.put("startDate", startDate);
        parameterProvider.put("endDate", endDate);

        long actualRange = ContractPage.rangeDates(sd, ed);
        assertThat(String.format("Start date \"%s\" differs from the end date \"%s\" by 1 year ", actualRange, expectedRange), actualRange, equalTo(expectedRange));
    }


    @Then("Check is product change \"([^\"]*)\"$")
    public void checkProductChangeSuccess(String expectedMessage) {
        ContractPage cp = new ContractPage();
        String messageActual = cp.checkSuccessMessage();
        Assert.assertThat("Product change successfully done", messageActual, equalTo(expectedMessage));

    }

    @Then("^Product Change dates are \"([^\"]*)\" and \"([^\"]*)\"$")
    public void ProductChangeDates(final String productChangeStartDate, final String productChangeEndDate){
        String pcsd  = parameterProvider.getValueOrParameterAsString(productChangeStartDate);
        String pced = parameterProvider.getValueOrParameterAsString(productChangeEndDate);
        parameterProvider.put("productChangeStartDate", pcsd);
        parameterProvider.put("productChangeEndDate", pced);

    }

    @When("Payment table is not empty$")
    public void checkPaymentTableNotEmpty() throws Throwable {
        ContractPage contractenPage = new ContractPage();
        boolean success = contractenPage.checkPaymentTableNotEmpty();

        assertThat("Rows in invoice table are empty", success, is(true));
    }

    @Then("Payment plan has \"([^\"]*)\" on date \"([^\"]*)\"$")
    public void PaymentPlanNumberOfInstallments(String expectedNumberOfInstallments, String paymentPlanDate){
        ContractPage contractenPage = new ContractPage();
        String ppd = parameterProvider.getValueOrParameterAsString(paymentPlanDate);
        
        String actualNumberOfInstallments = contractenPage.checkNumberOfInstallments(ppd);
        assertThat(String.format("Number of actual installments \"%s\" differs from the expected ones \"%s\" on payment plan", actualNumberOfInstallments, expectedNumberOfInstallments), actualNumberOfInstallments, equalTo(expectedNumberOfInstallments));

    }

    @Then("Payment plan has installment values of \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
    public void PaymentPlanValuesOfInstallments(String firstExpectedValue, String secondExpectedValue, String thirdExpectedValue){
        ContractPage contractenPage = new ContractPage();
        String valueOfFirstActualInstallment = contractenPage.checkValueOfInstallments("2");
        String valueOfSecondActualInstallment = contractenPage.checkValueOfInstallments("3");
        String valueOfThirdActualInstallment = contractenPage.checkValueOfInstallments("4");

        boolean correctFirstInstallment = valueOfFirstActualInstallment.contains(firstExpectedValue);
        boolean correctSecondInstallment = valueOfSecondActualInstallment.contains(secondExpectedValue);
        boolean correctThirdInstallment = valueOfThirdActualInstallment.contains(thirdExpectedValue);

        assertTrue("First Installment has a correct value", correctFirstInstallment);
        assertTrue("Second Installment has a correct value", correctSecondInstallment);
        assertTrue("Third Installment has a correct value", correctThirdInstallment);

    }



}
