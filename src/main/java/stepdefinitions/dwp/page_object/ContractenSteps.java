package stepdefinitions.dwp.page_object;

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

public class ContractenSteps extends DwpScenario {

    private String eanCode = null;
    private String contractEanCode;

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }


    @And("^Search for ean code$")
    public void searchForEanCode() throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.searchForEanCode(eanCode);
    }

    @When("^Input in \"([^\"]*)\" is \"([^\"]*)\"$")
    public void inputInModuleIs(String label, String input) throws Throwable {
        webDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(3);
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.fieldDropDownLabel(label, input);
    }

    @And("^Check toggle \"([^\"]*)\"$")
    public void checkToggle(String label) throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.turnOnTestingAndMarketMock(label);
    }

    @When("^Find \"([^\"]*)\" contract$")
    public void findContract(String input) throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        eanCode = contractenPage.findActiveContract(input);
        logger().info("EAN CODE: " + eanCode);
        parameterProvider.put("contractEanCode", eanCode);
    }

    @Then("^Confirm task was \"([^\"]*)\"$")
    public void confirmTaskWas(String input) throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.confirmTaskStatus(input);
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^\"([^\"]*)\" input in omschrijving$")
    public void inputInOmschrijving(String text) throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.inputText(text);
    }

    @And("^Offertes plus options is \"([^\"]*)\"$")
    public void sendEMailToCustomer(String test) throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.sendEmailToCustomer(test);
    }

    @And("^List option is \"([^\"]*)\"$")
    public void openInvoiceOnly(String option) throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.openListOption(option);
    }

    @Then("^Payment delayed$")
    public void paymentDelayed() throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.checkPayDate();
    }

    @And("^Find \"([^\"]*)\" facture and \"([^\"]*)\"$")
    public void findFactureAnd(String type, String option) throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.findIssuedAndPayDelay(type, option);
    }

    @Then("^Validate bank account was changed on \"([^\"]*)\"$")
    public void validateBankAccountWasChangedOn(String iban) throws Throwable {
        String inputIban = parameterProvider.getValueOrParameterAsString(iban);
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.findIban(inputIban);
    }

    @And("^Get Contract Ean Code$")
    public void getEanCode() throws Throwable {
        String eanCode = webDriver.findElementWhenVisible(By.xpath("(//h5)[1]")).getText();
        parameterProvider.put("contractEanCode", eanCode);
    }

    @Then("^Save changes$")
    public void saveChanges() throws Throwable {
        ContractPage contractenPage = new ContractPage(webDriver);
        contractenPage.saveButtton();
    }

    @Then("^Confirm contract with ean \"([^\"]*)\" was copied$")
    public void confirmContractWithEanWasCopied(String eanCode) throws Throwable {
        webDriver.waitForRequestsToFinish();
        String inputEanCode = parameterProvider.getValueOrParameterAsString(eanCode);
        logger().info("input EAN CODE: " + inputEanCode);
        Assert.assertTrue("Correct ean code was not found.", webDriver.findElementWhenVisible(By.xpath("//h5[.='" + inputEanCode + "']")).isDisplayed());
    }

    @Then("^Get Contract Number$")
    public void searchForContractNumber() throws Throwable {
        String contractNumber = webDriver.findElementWhenVisible(By.xpath("//*[@id=\"account_number_c\"]/div")).getText();
        parameterProvider.put("contractNumber", contractNumber);

    }


    @And("^Invoice with key \"([^\"]*)\" is checked$")
    public void CheckInvoiceOpenBalance(String text) {
        webDriver.waitForRequestsToFinish();
        ContractPage contractenPage = new ContractPage(webDriver);
        contractenPage.checkInvoiceOpenBalance(text);
    }

    @Then("^Customer Status is \"([^\"]*)\"$")
    public void customerStatus(String status) {
        webDriver.waitForRequestsToFinish();
        CustomerAcceptance customerAcceptance = new CustomerAcceptance(webDriver);
        customerAcceptance.customerStatus(status);

    }

    @Then("^Get Company Number$")
    public void searchForCompanyNumber() throws Throwable {
        String companyNumber = webDriver.findElementWhenVisible(By.xpath("//*//*[@id=\"company-number-c-field\"]")).getText();
        parameterProvider.put("companyNumber", companyNumber);

    }

}
