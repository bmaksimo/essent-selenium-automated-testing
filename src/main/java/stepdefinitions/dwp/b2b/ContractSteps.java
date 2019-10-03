package stepdefinitions.dwp.b2b;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.guided_flow.cupq.NewQuotePage;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import com.essent.testing.dwp.pageobject.impl.quote.QuoteDetailsPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.tables.SalesChannel;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class ContractSteps extends DwpScenario {
    @Before("@REGRESSION or @E2E or @API")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @And("^Contract startdatum is today$")
    public void contractStartdatumIsToday(){
        new ContractPage().startDateIsToday();
    }


    @And("^Get client number$")
    public void getClientNumber() {
        parameterProvider.put("accountNumber", new ContractPage().getClientNumber());
    }

    @And("^Get billing number$")
    public void getBillingNumber() {
        parameterProvider.put("billingNumber", new ContractPage().getBillingNumber());
    }

    @When("^Plus action of \"([^\"]*)\" element from \"([^\"]*)\" and click on Mark As Done/Markeren Als Verwerkt$")
    public void plusActionOfElementFromAndClickOnMarkAsDone(String row, String table){
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(5);
        new ContractPage().clickOnPlusMenuInTable(row,table);
        new BaseObjectPage().clickOnMarkAsDonePlusMenuSubAction();
    }

    @When("^Plus action of \"([^\"]*)\" element from \"([^\"]*)\" and click on \"([^\"]*)\"$")
    public void plusActionOfElementFromAndClickOn(String row, String table, String action) {
        Sleeper.sleepTightInSeconds(30);
        seleniumDriver.waitForRequestsToFinish();
        new ContractPage().clickOnPlusMenuInTable(row, table);
        seleniumDriver.waitForRequestsToFinish();
        new BaseObjectPage().plusSubaction(action);
    }

    @And("^Save EAN from active contract$")
    public void saveEANFromActiveContract() {
        parameterProvider.put("EAN-active-contract",new ContractPage().getActiveContractEAN());
    }

    @Then("^Contract is in \"([^\"]*)\" state$")
    public void contractIsInState(String status) {
        seleniumDriver.waitForRequestsToFinish();
        assertTrue(new ContractPage().contractStatus().equalsIgnoreCase(status));
    }

    @And("^Clicked on sign X$")
    public void closeModal() {
        new NewQuotePage().closeModal();
    }


    @When("^B2B sales channel is ([^\"]*)$")
    public void initSalesChannelB2B(SalesChannel salesChannel){
        QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
        quoteDetailsPage.setSalesChannel(salesChannel);
        boolean formInitialized = quoteDetailsPage.fillInFormData();
        assertThat("Failure occurred when filling in input values", formInitialized, is(true));
    }


    @When("^Rechtsvorm is bvba$")
    public void formLegal() {
        new NewQuotePage().selectItemLegalForm();
    }

    @And("^Gender is male$")
    public void gender() {
        new NewQuotePage().selectGender();
    }

    @And("^E-mailadres is \"([^\"]*)\"$")
    public void emailContract(String emailContract) {
        new NewQuotePage().getEmail(emailContract);
    }


    @And("^Select Nace-Code$")
    public void select() throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(10);
        new NewQuotePage().clickNaceCodeButton();
    }

    @And("^NaceCode in search is ([^\"]*)$")
    public void searchByNaceCode(String naceCode) {
        new ContractPage().searchByClientNumber(naceCode);

        NewQuotePage nq = new NewQuotePage();
        nq.clickOnSearch();
        nq.checkNaceCodeCheckBox();
        nq.saveSelectedItem();
    }


    @And("^Customer Details are populated with: Address is \"([^\"]*)\" and HouseNumber is \"([^\"]*)\" and PostalCode is \"([^\"]*)\" and City is \"([^\"]*)\"$")
    public void populateAddress(String Address, String houseNumber, String postalCode, String City) {
        new NewQuotePage().setAddress(Address, houseNumber, postalCode, City);
    }

    @And("^Telefoon is \"([^\"]*)\"$")
    public void populateTelephone(String telephone) {
        new NewQuotePage().setTelephone(telephone);

    }

    @And("^First Name is \"([^\"]*)\" and Last Name is \"([^\"]*)\"$")
    public void populateName(String fname, String lname) {
        new NewQuotePage().setName(fname, lname);
    }

    @And("^BEDRIJFSNAAM is \"([^\"]*)\"$")
    public void companyName(String cname) {
        new NewQuotePage().setCompanyName(cname);
    }


    @And("^Ean-Code is \"([^\"]*)\"$")
    public void eanCode(String eancode) {
        seleniumDriver.waitForRequestsToFinish();
        new NewQuotePage().setEanCode(eancode);
    }


    @And("^New Quote is saved$")
    public void newQuoteSaved() {
        new NewQuotePage().next(parameterProvider.getScenarioInfo());
    }

    @And("^Save End Date from active contract$")
    public void saveEndDateFromActiveContract() {
        parameterProvider.put("EndDate-active-contract", new ContractPage().getActiveContractEndDate());

    }
    @After("@REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
