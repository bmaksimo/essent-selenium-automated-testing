package stepdefinitions.dwp.contracts.b2b.cancel;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.page.ContractPage;
import com.essent.testing.dwp.pageobject.impl.service_contracting.ContractenPage;
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
    private String contractEanCode;

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }


    @When("^Input in \"([^\"]*)\" is \"([^\"]*)\"$")
    public void inputInModuleIs(String label, String input) {
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(3);
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.fieldDropDownLabel(label, input);
    }

    @And("^Check toggle \"([^\"]*)\"$")
    public void checkToggle(String label) {
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.turnOnTestingAndMarketMock(label);
    }

    @When("^Find \"([^\"]*)\" contract$")
    public void findContract(String input) throws Throwable {
        ContractenPage contractenPage = new ContractenPage();
        eanCodeInput = contractenPage.findActiveContract(input);
        logger().info("EAN CODE: " + eanCodeInput);
        parameterProvider.put("contractEanCode", eanCodeInput);
    }

    @When("^Contract line EAN-code \"([^\"]*)\" is submitted$")
    public void submitEanCode(String value) throws Throwable {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.searchForEanCode(inputValue);
    }

    @Then("^Confirm task was \"([^\"]*)\"$")
    public void confirmTaskWas(String value) throws Throwable {
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.confirmTaskStatus(inputValue);
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^\"([^\"]*)\" input in omschrijving$")
    public void inputInOmschrijving(String text) {
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.inputText(text);
    }

    @And("^Offertes plus options is \"([^\"]*)\"$")
    public void sendEMailToCustomer(String test) {
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.sendEmailToCustomer(test);
    }

    @And("^List option is \"([^\"]*)\"$")
    public void openInvoiceOnly(String option)  {
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.openListOption(option);
    }

    @Then("^Payment delayed$")
    public void paymentDelayed() {
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.checkPayDate();
    }

    @And("^Find \"([^\"]*)\" facture and \"([^\"]*)\"$")
    public void findFactureAnd(String type, String option) {
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.findIssuedAndPayDelay(type, option);
    }

    @Then("^Validate bank account was changed on \"([^\"]*)\"$")
    public void validateBankAccountWasChangedOn(String iban) {
        String inputIban = parameterProvider.getValueOrParameterAsString(iban);
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.findIban(inputIban);
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


    @And("^Invoice with key \"([^\"]*)\" is checked$")
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
        ContractPage cp = new ContractPage();
        cp.confirmTheSign(place);
      }

    @Then("^Populate Soctar with dates \"([^\"]*)\" and \"([^\"]*)\"$")
    public void searchForAttestDate(String startDate, String attestDate) throws Throwable {
        ContractPage contractenPage = new ContractPage();
        String sd = parameterProvider.getValueOrParameterAsString(startDate);
        String ad = parameterProvider.getValueOrParameterAsString(attestDate);

        String StartDateByQuarter = contractenPage.getQuarterForChosenStartDate(sd, ad);
        String EndDateByYear = contractenPage.getLastDayOfYear(sd, ad);
        parameterProvider.put("EndDateByYear", EndDateByYear);
        parameterProvider.put("StartDateByQuarter", StartDateByQuarter);
    }

    @Then("^Get Start Date$")
    public void searchForStartDate() throws Throwable {
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
        assertThat(String.format("Start date \"%s\" differs from the end date \"%s\" by 1 day ", actualRange, expectedRange), actualRange, equalTo(expectedRange));
    }


    @Then("Check is product change \"([^\"]*)\"$")
    public void checkProductChangeSuccess(String expectedMessage) {
        ContractPage cp = new ContractPage();
        String messageActual = cp.checkSuccessMessage();
        Assert.assertThat("Product change successfully done", messageActual, equalTo(expectedMessage));

    }

}
