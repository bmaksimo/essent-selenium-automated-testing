package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.dwp.pageobject.impl.page.ContractPage;
import com.essent.testing.dwp.pageobject.impl.quote.QuoteDetailsPage;
import com.essent.testing.dwp.pageobject.impl.service_contracting.ContractenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.springframework.test.context.ContextConfiguration;
import static org.testng.AssertJUnit.assertEquals;
import stepdefinitions.dwp.tables.LegalForm;
import stepdefinitions.dwp.tables.SalesChannel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")

public class Contract extends DwpScenario {
    private static String Klantnummer;
    private LegalForm legalForm;

    @Before("@REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Contract startdatum is today$")
    public void contractStartdatumIsToday() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        cp.startDateIsToday();
    }


    @And("^Get client number$")
    public void getClientNumber() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        Klantnummer=cp.getClientNumber();
    }

    @And("^Search by client number$")
    public void searchByClientNumber() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        ContractenPage contractenPage = new ContractenPage(webDriver);
        cp.selectAccount();
        cp.searchByClientNuiber(Klantnummer);
        contractenPage.searchForEanCode(Klantnummer);
    }

    @When("^Plus action of \"([^\"]*)\" element from \"([^\"]*)\" and click on \"([^\"]*)\"$")
    public void plusActionOfElementFromAndClickOn(String row, String table, String action) throws Throwable {
        webDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage(webDriver);
        BaseObject baseObject = new BaseObject(webDriver);
        Thread.sleep(5000);
        cp.clickOnPlusMeniInTable(row,table);
        baseObject.plusSubaction(action);
    }

    @When("^B2B sales kanaal is ([^\"]*)$")
    public void InitSalesChannelB2B(SalesChannel salesChannel) throws Throwable {
        QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage(webDriver);
        quoteDetailsPage.setSalesChannel(salesChannel);
        boolean formInitialized = quoteDetailsPage.fillInFormData();
        assertThat("Failure occurred when filling in input values", formInitialized, is(true));
    }

//    @When("^Rechtsvorm is ([^\"]*)$")
//    public void LegalForm(LegalForm legalForm) throws Throwable {
//        QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage(webDriver);
//        quoteDetailsPage.setLegalForm(legalForm);
//        boolean formInitialized = quoteDetailsPage.fillInFormData_LegalForm();
//        assertThat("Failure occurred when filling in input values", formInitialized, is(true));
//    }

    @When("^Rechtsvorm is bvba")
        public void FormLegal() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        cp.selectItemLegalForm();
    }

    @And("^Geslacht is Onbekend")
    public void Gender() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        cp.selectGender();
    }

    @And("^E-mailadres is ([^\"]*)$")
    public void EmailContract(String emailContract) throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        cp.getEmail(emailContract);
    }


    @And("^Select Nace-Code")
    public void Select() throws Throwable {
        webDriver.waitForRequestsToFinish();
        Thread.sleep(2000);
        ContractPage cp = new ContractPage(webDriver);
        cp.clickNaceCode();
    }

    @And("^NaceCode in search is ([^\"]*)$")
    public void searchByNaceCode(String NaceCode) throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        cp.searchByClientNuiber(NaceCode);
        cp.clickOnSearch();
        cp.checkNaceCode();
        cp.SaveSelectedItem();
    }


    @And("^Customer Details are populated with: Address is ([^\"]*) and HouseNumber is \"([^\"]*)\" and PostalCode is \"([^\"]*)\" and City is \"([^\"]*)\"$")
    public void populateAddress(String Address, String houseNumber, String postalCode, String City) throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        cp.PopulateAddress(Address, houseNumber, postalCode, City);
    }

    @And("^Telefoon is ([^\"]*)$")
        public void PopulateTelephone(String telephone) {
        ContractPage cp = new ContractPage(webDriver);
        cp.PopulateTelephone(telephone);

    }

    @And("^ContactPersoon is ([^\"]*) and \"([^\"]*)\"$")
    public void populateName(String fname, String lname) throws Throwable{
        ContractPage cp = new ContractPage(webDriver);
        cp.PopulatefName(fname, lname);
    }

    @And("^BEDRIJFSNAAM is ([^\"]*)$")
    public void CompanyName(String cname) {
        ContractPage cp = new ContractPage(webDriver);
        cp.companyName(cname);
    }


    @And("^Ean-Code is ([^\"]*)$")
        public void EanCode(String eancode) {
        webDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage(webDriver);
        cp.eanCode(eancode);
    }


    @And("^New Quote is saved$")
    public void newQuoteSaved() throws Throwable {
        QuoteDetailsPage quoteInitial = new QuoteDetailsPage(webDriver);
        quoteInitial.SaveInitialQuote();
    }



    @And("^Save EAN from active contract$")
    public void saveEANFromActiveContract() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        parameterProvider.put("EAN-active-contract",cp.getActiveContractEAN());
    }

    @Then("^Contract is in \"([^\"]*)\" state$")
    public void contractIsInState(String status) throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        webDriver.waitForRequestsToFinish();
        assertEquals(cp.status(),status);
    }
}
