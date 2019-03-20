package stepdefinitions.dwp.contracts.b2b.cancel;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.Invoice_list.InvoiceListPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details.DetailsPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.sales.SalesPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.workflows.MarktBerichtenPage;
import com.essent.testing.dwp.pageobject.guided_flow.cupq.NewQuotePage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import stepdefinitions.dwp.page_object.CustomerAcceptance;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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


    @And("^Invoice with key \"([^\"]*)\" is stored")
    public void CheckInvoiceOpenBalance(String text) {
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

}
