package stepdefinitions.quote.api;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.restassured.B2CCreateContractScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
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
    public void i_login_to_iWelcome_as(String username) throws Throwable {
	String password = ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_SOAPUI_B2C);
	this.cookie = new IWelcomeLoginAPI().getCookie(username, password);
    }

    @Given("^\"([^\"]*)\" flow is started$")
    public void flow_is_started(String arg1) throws Throwable {
	this.tariffSheetID = new QuoteDetailsAPI().getTariffSheetID(cookie, arg1);
    }

    @When("^Data is prepared for Create qoute request for \"([^\"]*)\"$")
    public void data_is_prepared_for_Create_qoute_request_for(String arg1) throws Throwable {
        this.flow = arg1;
	this.quoteDetails = new QuoteDetailsAPI().getQuoteDetails(cookie, tariffSheetID, this.flow);
    }

    @When("^New tc(\\d+)_quote is created$")
    public void new_tc__quote_is_created(int arg1) throws Throwable {
	String retreivedQuoteNumber = new QuoteDetailsAPI().getQuoteNumber(cookie, quoteDetails.getRecordId());
	// assertEquals(quoteDetails.getQuoteNumber(),retreivedQuoteNumber);
	assertThat(retreivedQuoteNumber, is(equalTo(quoteDetails.getQuoteNumber())));
    }

    @Then("^Quote status is \"([^\"]*)\"$")
    public void quote_status_is(String arg1) throws Throwable {
	String status = new QuoteDetailsAPI().checkStatus(cookie, quoteDetails.getQuoteId());
	// assertEquals(arg1.toLowerCase(), status.toLowerCase());
	assertThat(status.toLowerCase(), is(equalTo(arg1.toLowerCase())));
    }

    @Then("^Quoteline exists$")
    public void quoteline_exists() throws Throwable {
	boolean eanExists = new QuoteDetailsAPI().checkIfEANexists(cookie, quoteDetails);
	// assertEquals(true, eanExists);
	assertThat(eanExists, is(true));
    }

    @Then("^Quoteline status is \"([^\"]*)\"$")
    public void quoteline_status_is(String arg1) throws Throwable {
	String status = new QuoteDetailsAPI().getStatus(cookie, quoteDetails.getQuoteId());
	// assertEquals(arg1.toLowerCase(),status.toLowerCase());
	assertThat(status.toLowerCase(), is(equalTo(arg1.toLowerCase())));
    }

    @When("^Simulation that customer signature is recieved$")
    public void simulation_that_customer_signature_is_recieved() throws Throwable {
	new QuoteSignatureAPI().setSignatureReceived(cookie, quoteDetails);
    }

    @Then("^Quote stage status is \"([^\"]*)\"$")
    public void quote__stage_status_is(String arg1) throws Throwable {
	String status = new QuoteDetailsAPI().checkStageStatus(cookie, quoteDetails.getQuoteId());
	// assertEquals(arg1.toLowerCase(), status.toLowerCase());
	assertThat(status.toLowerCase(), is(equalTo(arg1.toLowerCase())));
    }

    @When("^File is uploaded as scanned signature$")
    public void file_is_uploaded_as_scanned_signature() throws Throwable {
	this.docId = new QuoteSignatureAPI().uploadSignature(cookie, quoteDetails);
    }

    @Then("^Signin is confirmed$")
    public void signin_is_confirmed() throws Throwable {
	new QuoteSignatureAPI().confirmSigning(cookie, quoteDetails.getQuoteId(), docId);
    }

    @Then("^Contract is created$")
    public void contract_is_created() throws Throwable {
	this.contractDetails = new ContractDetailsAPI().getContractDetails(cookie, quoteDetails);
    }

    @Then("^Contracted EAN exists on account$")
    public void contracted_EAN_exists_on_account() throws Throwable {

	// assertTrue(new ContractsOnAccountAPI().checkIfEanExists(cookie,
	assertThat(new ContractDetailsAPI().checkIfEanExists(cookie, quoteDetails), is(true));
    }

    @When("^Payment detials are recieved$")
    public void payment_detials_are_recieved() throws Throwable {
	this.jbillingId = new ContractDetailsAPI().getPaymentDetails(cookie, quoteDetails.getQuoteId());
    }

    @Then("^Wait until contract instance starts$")
    public void wait_until_contract_instance_starts() throws Throwable {
	await().pollInterval(5, TimeUnit.SECONDS).atMost(600, TimeUnit.SECONDS)
		.until(AsyncExecutor.isStatusSuccessfull(cookie, contractDetails));
    }

}
