package stepdefinitions.quote.api;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.restassured.B2CCreateContractScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.Cookies;
import stepdefinitions.quote.api.helper.AsyncExecutor;
import stepdefinitions.quote.api.model.ContractDetails;
import stepdefinitions.quote.api.model.QuoteDetails;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;



/**
 * @author n.grkavac
 *
 */


public class QuoteBasicFlowB2CSteps extends B2CCreateContractScenario {

    private Cookies cookie;
    private String tariffSheetID;
    private QuoteDetails quoteDetails;
    private ContractDetails contractDetails;
    private String docId;
    private String jbillingId;
    private String flow;


    @Before("@API")
    public void setupTest(Scenario scenario) throws Throwable {
	    registerActiveScenario(scenario);
    }

    @Given("^I login to iWelcome as \"([^\"]*)\"$")
    public void iLoginToIWelcomeAs(String username) throws Throwable {
        String password = ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_SOAPUI_B2C);
        this.cookie = new IWelcomeLoginAPI().getCookie(username, password);
    }

    @Given("^\"([^\"]*)\" flow is started$")
    public void flowIsStarted(String arg1) throws Throwable {
	    this.tariffSheetID = new QuoteDetailsAPI().getTariffSheetID(cookie, arg1);
    }

    @When("^Data is prepared for Create quote request for \"([^\"]*)\" and meter open is \"([^\"]*)\" and contract date is \"([^\"]*)\"$")
    public void dataIsPreparedForCreateQuoteWithDateRequestFor(String arg1, String meterOpen, String residentialStartdate) throws Throwable {
        this.flow = arg1;
	    this.quoteDetails = new QuoteDetailsAPI().getQuoteDetails(cookie, tariffSheetID, this.flow, meterOpen, residentialStartdate);
        String retrievedAccountNumber = quoteDetails.getAccountNumber();
        int result = Integer.parseInt(retrievedAccountNumber);
        parameterProvider.put("accountNumber", result);
	    parameterProvider.put("EAN-code", quoteDetails.getEan());
	    parameterProvider.put("suitecrm-customer-name", quoteDetails.getAccountName());

    }

    @When("^New tc(\\d+)_quote is created$")
    public void newTcQuoteIsCreated(int arg1) throws Throwable {
        String retreivedQuoteNumber = new QuoteDetailsAPI().getQuoteNumber(cookie, quoteDetails.getRecordId());
        assertThat(retreivedQuoteNumber, is(equalTo(quoteDetails.getQuoteNumber())));
    }

    @Then("^Quote status is \"([^\"]*)\"$")
    public void quoteStatusIs(String arg1) throws Throwable {
        String status = new QuoteDetailsAPI().checkStatus(cookie, quoteDetails.getQuoteId());
        assertThat(status.toLowerCase(), is(equalTo(arg1.toLowerCase())));
    }

    @Then("^Quoteline exists$")
    public void quotelineExists() throws Throwable {
        boolean eanExists = new QuoteDetailsAPI().checkIfEANexists(cookie, quoteDetails);
        assertThat(eanExists, is(true));
    }

    @Then("^Quoteline status is \"([^\"]*)\"$")
    public void quotelineStatusIs(String arg1) throws Throwable {
        String status = new QuoteDetailsAPI().getStatus(cookie, quoteDetails.getQuoteId());
        assertThat(status.toLowerCase(), is(equalTo(arg1.toLowerCase())));
    }

    @When("^Simulation that customer signature is received$")
    public void simulationThatCustomerSignatureIsReceived() throws Throwable {
	    new QuoteSignatureAPI().setSignatureReceived(cookie, quoteDetails);
    }

    @Then("^Quote stage status is \"([^\"]*)\"$")
    public void quoteStageStatusIs(String arg1) throws Throwable {
        String status = new QuoteDetailsAPI().checkStageStatus(cookie, quoteDetails.getQuoteId());
        assertThat(status.toLowerCase(), is(equalTo(arg1.toLowerCase())));
    }

    @When("^File is uploaded as scanned signature$")
    public void fileIsUploadedAsScannedSignature() {
	    this.docId = new QuoteSignatureAPI().uploadSignature(cookie, quoteDetails);
    }

    @Then("^Signin is confirmed$")
    public void signinIsConfirmed() throws Throwable {
	    new QuoteSignatureAPI().confirmSigning(cookie, quoteDetails.getQuoteId(), docId);
    }

    @Then("^Contract is created$")
    public void contractIsCreated() throws Throwable {
	    this.contractDetails = new ContractDetailsAPI().getContractDetails(cookie, quoteDetails);
	    String contractDate = new ContractDetailsAPI().getContractDetails(cookie, quoteDetails).getContractStartDate();
        StringBuilder builder = new StringBuilder();
        String[] str = contractDate.split("-");
        String yearContractDate = str[0], monthContractDate = str[1], dayContractDate = str[2];
        builder.append(dayContractDate).append("-").append(monthContractDate).append("-").append(yearContractDate);
        parameterProvider.put("contractDate", builder);
    }

    @Then("^Contracted EAN exists on account$")
    public void contracted_EAN_exists_on_account() throws Throwable {
	    assertThat(new ContractDetailsAPI().checkIfEanExists(cookie, quoteDetails), is(true));
    }

    @When("^Payment details are received$")
    public void paymentDetailsAreReceived() throws Throwable {
	    this.jbillingId = new ContractDetailsAPI().getPaymentDetails(cookie, quoteDetails.getQuoteId(), contractDetails);
    }

    @Then("^Wait until contract instance starts$")
    public void waitUntilContractInstanceStarts() {
        await().pollInterval(5, TimeUnit.SECONDS).atMost(600, TimeUnit.SECONDS)
            .until(AsyncExecutor.isStatusSuccessfull(cookie, contractDetails));
    }

    @And("^Check order in jbilling$")
    public void checkOrderInJbilling() {
        await().pollInterval(5, TimeUnit.SECONDS).atMost(600, TimeUnit.SECONDS)
            .until(AsyncExecutor.isOrderCreated(cookie, quoteDetails, contractDetails));
    }
}
