package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.dwp.pageobject.impl.page.ContractPage;
import com.essent.testing.dwp.pageobject.impl.quote.QuoteDetailsPage;
import com.essent.testing.dwp.pageobject.impl.service_contracting.ContractenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
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
    public void contractStartdatumIsToday() throws Throwable {
        ContractPage cp = new ContractPage();
        cp.startDateIsToday();
    }


    @And("^Get client number$")
    public void getClientNumber() throws Throwable {
        ContractPage cp = new ContractPage();
        Klantnummer=cp.getClientNumber();
        parameterProvider.put("accountNumber",Klantnummer);
    }

    @And("^Search by client number$")
    public void searchByClientNumber() throws Throwable {
        ContractPage cp = new ContractPage();
        ContractenPage contractenPage = new ContractenPage();
        cp.selectAccount();
        cp.searchByClientNuiber(Klantnummer);
        contractenPage.searchForEanCode(Klantnummer);
    }

    @When("^Plus action of \"([^\"]*)\" element from \"([^\"]*)\" and click on \"([^\"]*)\"$")
    public void plusActionOfElementFromAndClickOn(String row, String table, String action) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage();
        BaseObject baseObject = new BaseObject();
        Thread.sleep(5000);
        cp.clickOnPlusMeniInTable(row,table);
        baseObject.plusSubaction(action);
    }

    @And("^Save EAN from active contract$")
    public void saveEANFromActiveContract() throws Throwable {
        ContractPage cp = new ContractPage();
        parameterProvider.put("EAN-active-contract",cp.getActiveContractEAN());
    }

    @Then("^Contract is in \"([^\"]*)\" state$")
    public void contractIsInState(String status) throws Throwable {
        ContractPage cp = new ContractPage();
        seleniumDriver.waitForRequestsToFinish();
        assertEquals(cp.status(),status);
    }

    @And("^Clicked on sign X$")
    public void clickOnX() throws Throwable {
        ContractPage cp = new ContractPage();
        seleniumDriver.waitForRequestsToFinish();
        cp.clickOnX();
    }


    @When("^B2B sales channel is ([^\"]*)$")
    public void initSalesChannelB2B(SalesChannel salesChannel) throws Throwable {
        QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
        quoteDetailsPage.setSalesChannel(salesChannel);
        boolean formInitialized = quoteDetailsPage.fillInFormData();
        assertThat("Failure occurred when filling in input values", formInitialized, is(true));
    }


    @When("^Rechtsvorm is bvba")
    public void formLegal() throws Throwable {
        ContractPage cp = new ContractPage();
        cp.selectItemLegalForm();
    }

    @And("^Geslacht is Male")
    public void gender() throws Throwable {
        ContractPage cp = new ContractPage();
        cp.selectGender();
    }

    @And("^E-mailadres is \"([^\"]*)\"$")
    public void emailContract(String emailContract) throws Throwable {
        ContractPage cp = new ContractPage();
        cp.getEmail(emailContract);
    }


    @And("^Select Nace-Code")
    public void select() throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        Thread.sleep(2000);
        ContractPage cp = new ContractPage();
        cp.clickNaceCode();
    }

    @And("^NaceCode in search is ([^\"]*)$")
    public void searchByNaceCode(String NaceCode) throws Throwable {
        ContractPage cp = new ContractPage();
        cp.searchByClientNuiber(NaceCode);
        cp.clickOnSearch();
        cp.checkNaceCode();
        cp.saveSelectedItem();
    }


    @And("^Customer Details are populated with: Address is \"([^\"]*)\" and HouseNumber is \"([^\"]*)\" and PostalCode is \"([^\"]*)\" and City is \"([^\"]*)\"$")
    public void populateAddress(String Address, String houseNumber, String postalCode, String City) throws Throwable {
        ContractPage cp = new ContractPage();
        cp.setAddress(Address, houseNumber, postalCode, City);
    }

    @And("^Telefoon is \"([^\"]*)\"$")
    public void populateTelephone(String telephone) {
        ContractPage cp = new ContractPage();
        cp.setTelephone(telephone);

    }

    @And("^First Name is \"([^\"]*)\" and Last Name is \"([^\"]*)\"$")
    public void populateName(String fname, String lname) throws Throwable{
        ContractPage cp = new ContractPage();
        cp.setName(fname, lname);
    }

    @And("^BEDRIJFSNAAM is \"([^\"]*)\"$")
    public void companyName(String cname) {
        ContractPage cp = new ContractPage();
        cp.setCompanyName(cname);
    }


    @And("^Ean-Code is \"([^\"]*)\"$")
    public void eanCode(String eancode) {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage();
        cp.setEanCode(eancode);
    }


    @And("^New Quote is saved$")
    public void newQuoteSaved() throws Throwable {
        ContractPage quoteInitial = new ContractPage();
        quoteInitial.saveInitialQuote();
    }

    @And("^Save End Date from active contract$")
    public void saveEndDateFromActiveContract() throws Throwable {
        ContractPage cp = new ContractPage();
        parameterProvider.put("EndDate-active-contract",cp.getActiveContractEndDate());
    }


}
