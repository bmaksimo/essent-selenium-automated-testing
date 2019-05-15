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
import org.springframework.test.context.ContextConfiguration;
import stepdefinitions.dwp.tables.SalesChannel;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.testng.AssertJUnit.assertEquals;



@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")

public class Contract extends DwpScenario {
    private static String Klantnummer;

    @Before("@REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Contract startdatum is today$")
    public void contractStartdatumIsToday(){
        ContractPage cp = new ContractPage();
        cp.startDateIsToday();
    }


    @And("^Get client number$")
    public void getClientNumber() {
        ContractPage cp = new ContractPage();
        Klantnummer=cp.getClientNumber();
        parameterProvider.put("accountNumber",Klantnummer);
    }

    @And("^Get billing number$")
    public void getBillingNumber() {
        ContractPage cp = new ContractPage();
        String billingNumber=cp.getBillingNumber();
        parameterProvider.put("billingNumber",billingNumber);
    }

    @And("^Search by client number$")
    public void searchByClientNumber() {
        ContractPage cp = new ContractPage();
        cp.selectAccount();
        cp.searchByClientNumber(Klantnummer);
        cp.searchForEanCode(Klantnummer);
    }

    @When("^Plus action of \"([^\"]*)\" element from \"([^\"]*)\" and click on Mark As Done/Markeren Als Verwerkt$")
    public void plusActionOfElementFromAndClickOnMarkAsDone(String row, String table){
        seleniumDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage();
        BaseObjectPage baseObject = new BaseObjectPage();
        Sleeper.sleepTightInSeconds(5);
        cp.clickOnPlusMeniInTable(row,table);
        baseObject.clickOnMarkAsDonePlusMenuSubAction();
    }

    @When("^Plus action of \"([^\"]*)\" element from \"([^\"]*)\" and click on \"([^\"]*)\"$")
    public void plusActionOfElementFromAndClickOn(String row, String table, String action) {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage();
        BaseObjectPage baseObject = new BaseObjectPage();
        Sleeper.sleepTightInSeconds(5);
        cp.clickOnPlusMeniInTable(row,table);
        baseObject.plusSubaction(action);
    }

    @And("^Save EAN from active contract$")
    public void saveEANFromActiveContract() {
        ContractPage cp = new ContractPage();
        parameterProvider.put("EAN-active-contract",cp.getActiveContractEAN());
    }

    @Then("^Contract is in \"([^\"]*)\" state$")
    public void contractIsInState(String status) {
        ContractPage cp = new ContractPage();
        seleniumDriver.waitForRequestsToFinish();
        assertEquals(cp.contractStatus(),status);
    }

    @And("^Clicked on sign X$")
    public void clickOnX() {
        NewQuotePage nq = new NewQuotePage();
        nq.clickOnX();
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
        NewQuotePage nq = new NewQuotePage();
        nq.selectItemLegalForm();
    }

    @And("^Gender is male$")
    public void gender() {
        NewQuotePage nq = new NewQuotePage();
        nq.selectGender();
    }

    @And("^E-mailadres is \"([^\"]*)\"$")
    public void emailContract(String emailContract) {
        NewQuotePage nq = new NewQuotePage();
        nq.getEmail(emailContract);
    }


    @And("^Select Nace-Code$")
    public void select() throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        Thread.sleep(2000);
        NewQuotePage nq = new NewQuotePage();
        nq.clickNaceCodeButton();
    }

    @And("^NaceCode in search is ([^\"]*)$")
    public void searchByNaceCode(String NaceCode) {
        NewQuotePage nq = new NewQuotePage();
        ContractPage cp = new ContractPage();
        cp.searchByClientNumber(NaceCode);
        nq.clickOnSearch();
        nq.checkNaceCodeCheckBox();
        nq.saveSelectedItem();
    }


    @And("^Customer Details are populated with: Address is \"([^\"]*)\" and HouseNumber is \"([^\"]*)\" and PostalCode is \"([^\"]*)\" and City is \"([^\"]*)\"$")
    public void populateAddress(String Address, String houseNumber, String postalCode, String City) {
        NewQuotePage nq = new NewQuotePage();
        nq.setAddress(Address, houseNumber, postalCode, City);
    }

    @And("^Telefoon is \"([^\"]*)\"$")
    public void populateTelephone(String telephone) {
        NewQuotePage nq = new NewQuotePage();
        nq.setTelephone(telephone);

    }

    @And("^First Name is \"([^\"]*)\" and Last Name is \"([^\"]*)\"$")
    public void populateName(String fname, String lname) {
        NewQuotePage nq = new NewQuotePage();
        nq.setName(fname, lname);
    }

    @And("^BEDRIJFSNAAM is \"([^\"]*)\"$")
    public void companyName(String cname) {
        NewQuotePage nq = new NewQuotePage();
        nq.setCompanyName(cname);
    }


    @And("^Ean-Code is \"([^\"]*)\"$")
    public void eanCode(String eancode) {
        seleniumDriver.waitForRequestsToFinish();
        NewQuotePage nq = new NewQuotePage();
        nq.setEanCode(eancode);
    }


    @And("^New Quote is saved$")
    public void newQuoteSaved() {
        NewQuotePage nq = new NewQuotePage();
        nq.next();
    }

    @And("^Save End Date from active contract$")
    public void saveEndDateFromActiveContract() {
        ContractPage cp = new ContractPage();
        parameterProvider.put("EndDate-active-contract",cp.getActiveContractEndDate());

    }
    @After("@REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
