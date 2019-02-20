package stepdefinitions.quote.api;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.restassured.B2CCreateContractScenario;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.Cookies;
import stepdefinitions.quote.api.model.QuoteDetails;

import static org.junit.Assert.assertEquals;

public class QuoteBasicFlowB2CSteps extends B2CCreateContractScenario {

    protected Cookies cookie;
    protected String tariffSheetID;
    protected QuoteDetails quoteDetails;

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
        this.tariffSheetID = new QuoteBTCAPI().getTariffSheetID(cookie);
    }

    @When("^Data is prepared for Create qoute request for \"([^\"]*)\"$")
    public void data_is_prepared_for_Create_qoute_request_for(String arg1) throws Throwable {
        this.quoteDetails = new QuoteDetailsAPI().getQuoteDetails(cookie, tariffSheetID);
    }

    @When("^New tc(\\d+)_quote is created$")
    public void new_tc__quote_is_created(int arg1) throws Throwable {
        String retreivedQuoteId = new QuoteDetailsAPI().getQuoteId(cookie, quoteDetails.getRecordId());
        assertEquals(retreivedQuoteId, quoteDetails.getQuoteId());
    }

    @Then("^Quote status is \"([^\"]*)\"$")
    public void quote_status_is(String arg1) throws Throwable {
        String status = new QuoteStatusAPI().checkStatus(cookie, quoteDetails.getQuoteNumber());
        assertEquals(status, "ACCEPTED");
    }

    @Then("^Quoteline exists$")
    public void quoteline_exists() throws Throwable {
        boolean eanExists = new QuoteLineAPI().checkIfEANexists(cookie, quoteDetails.getQuoteNumber());
        assertEquals(eanExists, true);
    }

    @Then("^Quoteline status is \"([^\"]*)\"$")
    public void quoteline_status_is(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Simulation that customer signature is recieved$")
    public void simulation_that_customer_signature_is_recieved() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^File is uploaded as scanned signature$")
    public void file_is_uploaded_as_scanned_signature() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Signin is confirmed$")
    public void signin_is_confirmed() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Contract is created$")
    public void contract_is_created() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Contracted EAN exists on account$")
    public void contracted_EAN_exists_on_account() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Quote details are recieved$")
    public void quote_details_are_recieved() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Payment detials are recieved$")
    public void payment_detials_are_recieved() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Wait until contract instance starts$")
    public void wait_until_contract_instance_starts() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Check if end time is valid$")
    public void check_if_end_time_is_valid() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
