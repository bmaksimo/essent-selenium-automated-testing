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

public class ContractsSteps extends DwpScenario {

    private String eanCodeInput = null;
    private String contractEanCode;

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }


    @When("^Input in \"([^\"]*)\" is \"([^\"]*)\"$")
    public void inputInModuleIs(String label, String input) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(3);
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.fieldDropDownLabel(label, input);
    }

    @And("^Check toggle \"([^\"]*)\"$")
    public void checkToggle(String label) throws Throwable {
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
    public void inputInOmschrijving(String text) throws Throwable {
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.inputText(text);
    }

    @And("^Offertes plus options is \"([^\"]*)\"$")
    public void sendEMailToCustomer(String test) throws Throwable {
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.sendEmailToCustomer(test);
    }

    @And("^List option is \"([^\"]*)\"$")
    public void openInvoiceOnly(String option) throws Throwable {
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.openListOption(option);
    }

    @Then("^Payment delayed$")
    public void paymentDelayed() throws Throwable {
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.checkPayDate();
    }

    @And("^Find \"([^\"]*)\" facture and \"([^\"]*)\"$")
    public void findFactureAnd(String type, String option) throws Throwable {
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.findIssuedAndPayDelay(type, option);
    }

    @Then("^Validate bank account was changed on \"([^\"]*)\"$")
    public void validateBankAccountWasChangedOn(String iban) throws Throwable {
        String inputIban = parameterProvider.getValueOrParameterAsString(iban);
        ContractenPage contractenPage = new ContractenPage();
        contractenPage.findIban(inputIban);
    }

    @And("^Get Contract Ean Code$")
    public void getEanCode() throws Throwable {
        String eanCode = seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[1]")).getText();
        parameterProvider.put("contractEanCode", eanCode);
    }

    @Then("^Save changes$")
    public void saveChanges() throws Throwable {
        ContractPage contractenPage = new ContractPage();
        contractenPage.saveButtton();
    }

    @Then("^Confirm contract with ean \"([^\"]*)\" was copied$")
    public void confirmContractWithEanWasCopied(String eanCode) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        String inputEanCode = parameterProvider.getValueOrParameterAsString(eanCode);
        logger().info("input EAN CODE: " + inputEanCode);
        Assert.assertTrue("Correct ean code was not found.", seleniumDriver.findElementWhenVisible(By.xpath("//h5[.='" + inputEanCode + "']")).isDisplayed());
    }

    @Then("^Get Contract Number$")
    public void searchForContractNumber() throws Throwable {
        String contractNumber = seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"account_number_c\"]/div")).getText();
        parameterProvider.put("contractNumber", contractNumber);

    }


    @And("^Invoice with key \"([^\"]*)\" is checked$")
    public void CheckInvoiceOpenBalance(String text) {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage contractenPage = new ContractPage();
        contractenPage.checkInvoiceOpenBalance(text);
    }

    @Then("^Customer Status is \"([^\"]*)\"$")
    public void customerStatus(String status) {
        seleniumDriver.waitForRequestsToFinish();
        CustomerAcceptance customerAcceptance = new CustomerAcceptance();
        customerAcceptance.customerStatus(status);

    }

    @Then("^Get Company Number$")
    public void searchForCompanyNumber() throws Throwable {
        String companyNumber = seleniumDriver.findElementWhenVisible(By.xpath("//*//*[@id=\"company-number-c-field\"]")).getText();
        parameterProvider.put("companyNumber", companyNumber);

    }

}
