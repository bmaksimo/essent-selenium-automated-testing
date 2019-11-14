package stepdefinitions.quote.api;

import com.billinghouse.testautomation.util.dsl.DateTimeRegex;
import com.billinghouse.testautomation.util.dsl.EssentDateTimeFormat;
import com.essent.automation.util.Sleeper;
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
import io.restassured.response.Response;
import org.junit.Assert;
import stepdefinitions.dwp.tables.CustomerDetails;
import stepdefinitions.quote.api.helper.AsyncExecutor;
import stepdefinitions.quote.api.helper.RequestHelper;
import stepdefinitions.quote.api.model.ContractDetails;
import stepdefinitions.quote.api.model.QuoteDetails;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.billinghouse.testautomation.util.dsl.DateExpressionsUtil.expandFrom;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static stepdefinitions.quote.api.AbstractAPI.*;


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
    public void setupTest(Scenario scenario){
	    registerActiveScenario(scenario);
    }

    public String toDwpAPIDate(String parameter) {
        return checkAndConvertToDwpAPIDate(parameter);
    }
    public static String checkAndConvertToDwpAPIDate(String input) {
        if (matchesDwpAPIDateFormat(input)) return input;
        else return expandFrom(input).toString(EssentDateTimeFormat.DWP_FRENCH_DATE_FORMAT.getFormat());
    }

    static boolean matchesDwpAPIDateFormat(String date) {
        return date.matches(DateTimeRegex.DWP_API_DATE_FORMAT_REGEX.getExpression());
    }

    @Given("^I login as API user \"([^\"]*)\"$")
    public void iLoginAsAPIUser(String username) throws IOException {
        String password = ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_SOAPUI_B2C);
        this.cookie = new IWelcomeLoginAPI().getCookie(username, password);
    }

    @Given("^\"([^\"]*)\" flow is started$")
    public void flowIsStarted(String arg1) throws IOException {
	    this.tariffSheetID = new QuoteDetailsAPI().getTariffSheetID(cookie, arg1);
    }

    @When("^Data is prepared for Create quote request for \"([^\"]*)\" and meter open is \"([^\"]*)\" and sign date is \"([^\"]*)\"$")
    public void dataIsPreparedForCreateQuoteWithDateRequestAndPostPreference(String flowType, String meterOpen, String signInDate) throws IOException {
        createQuoteAndSaveResult(flowType, meterOpen, signInDate, "POST");
    }

    @When("^Data is prepared for Create quote request for \"([^\"]*)\" and meter open is \"([^\"]*)\" and sign date is \"([^\"]*)\" with communication by email$")
    public void dataIsPreparedForCreateQuoteWithDateRequestAndEmailPreference(String flowType, String meterOpen, String signInDate) throws IOException {
        createQuoteAndSaveResult(flowType, meterOpen, signInDate, "EMAIL");
    }

    public void createQuoteAndSaveResult(String flowType, String meterOpen, String signInDate, String contactPreference) throws IOException {
        this.flow = flowType;
        String signInDateApiDate = toDwpAPIDate(parameterProvider.getValueOrParameterAsString(signInDate));
        this.quoteDetails = new QuoteDetailsAPI().getQuoteDetails(cookie, tariffSheetID, this.flow, meterOpen, signInDateApiDate, contactPreference);
        parameterProvider.put("suitecrm-customer", fillInCustomerDetailsContext(this.quoteDetails));
        parameterProvider.put("accountNumber", quoteDetails.getAccountNumber());
        parameterProvider.put("EAN-code", quoteDetails.getEan());
        parameterProvider.put("suitecrm-customer-name", quoteDetails.getAccountName());
    }

    private CustomerDetails fillInCustomerDetailsContext(QuoteDetails quoteDetails) {
        CustomerDetails customer = new CustomerDetails();
        customer.setFirstName(quoteDetails.getFirstName());
        customer.setLastName(quoteDetails.getLastName());
        customer.setBirthDate(quoteDetails.getDateOfBirth());

        return customer;
    }

    @When("^New tc1_quote is created$")
    public void newTcQuoteIsCreated() throws IOException {
        String retrievedQuoteNumber = new QuoteDetailsAPI().getQuoteNumber(cookie, quoteDetails.getRecordId());
        assertThat(retrievedQuoteNumber, is(equalTo(quoteDetails.getQuoteNumber())));

    }

    @Then("^Quote status is \"([^\"]*)\"$")
    public void quoteStatusIs(String arg1) throws IOException {
        String status = new QuoteDetailsAPI().checkStatus(cookie, quoteDetails.getQuoteId());
        assertThat(status.toLowerCase(), is(equalTo(arg1.toLowerCase())));
    }

    @Then("^Quoteline exists$")
    public void quotelineExists() throws IOException {
        boolean eanExists = new QuoteDetailsAPI().checkIfEANexists(cookie, quoteDetails);
        assertThat(eanExists, is(true));
    }

    @Then("^Quoteline status is \"([^\"]*)\"$")
    public void quotelineStatusIs(String arg1) throws IOException {
        String status = new QuoteDetailsAPI().getStatus(cookie, quoteDetails.getQuoteId());
        assertThat(status.toLowerCase(), is(equalTo(arg1.toLowerCase())));
    }

    @When("^Simulation that customer signature is received$")
    public void simulationThatCustomerSignatureIsReceived() throws IOException {
	    new QuoteSignatureAPI().setSignatureReceived(cookie, quoteDetails);
    }

    @Then("^Quote stage status is \"([^\"]*)\"$")
    public void quoteStageStatusIs(String arg1) throws IOException {
        String status = new QuoteDetailsAPI().checkStageStatus(cookie, quoteDetails.getQuoteId());
        assertThat(status.toLowerCase(), is(equalTo(arg1.toLowerCase())));
    }

    @When("^File is uploaded as scanned signature$")
    public void fileIsUploadedAsScannedSignature() {
	    this.docId = new QuoteSignatureAPI().uploadSignature(cookie, quoteDetails);
    }

    @Then("^Signin is confirmed$")
    public void signinIsConfirmed() throws IOException {
	    new QuoteSignatureAPI().confirmSigning(cookie, quoteDetails.getQuoteId(), docId);
    }

    @Then("^Contract is created$")
    public void contractIsCreated() throws IOException {
	    this.contractDetails = new ContractDetailsAPI().getContractDetails(cookie, quoteDetails);
	    String contractStartDate = new ContractDetailsAPI().getContractDetails(cookie, quoteDetails).getContractStartDate();
        String contractEndDate = new ContractDetailsAPI().getContractDetails(cookie, quoteDetails).getContractEndDate();
        parameterProvider.put("contractStartDate", contractStartDate);
        parameterProvider.put("contractEndDate", contractEndDate);
        StringBuilder builder = new StringBuilder();
        String[] str = contractStartDate.split("-");
        String yearContractDate = str[0];
        String monthContractDate = str[1];
        String dayContractDate = str[2];
        builder.append(dayContractDate).append("-").append(monthContractDate).append("-").append(yearContractDate);
        parameterProvider.put("contractDate", builder);
        String retrievedContractNumber = new ContractDetailsAPI().getContractDetails(cookie, quoteDetails).getContractNumber();
        parameterProvider.put("contractNumber", retrievedContractNumber);
    }

    @Then("^Contracted EAN exists on account$")
    public void contracted_EAN_exists_on_account() throws IOException {
	    assertThat(new ContractDetailsAPI().checkIfEanExists(cookie, quoteDetails), is(true));
    }

    @When("^Payment details are received$")
    public void paymentDetailsAreReceived() throws IOException {
	    this.jbillingId = new ContractDetailsAPI().getPaymentDetails(cookie, quoteDetails.getQuoteId(), contractDetails);
        parameterProvider.put("billingId", jbillingId);
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

    @And("the batchjob \"([^\"]*)\" is set to \"([^\"]*)\"$")
    public void setBatchJobStatus(String batchJobName, String targetStatus) {
        RequestHelper helper = new RequestHelper();
        String path =
                String.format(
                    ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_BATCHJOBSETTING_URL),
                    batchJobName,
                    targetStatus
                );

        helper.simplePutRequest(STATUS_OK, cookie, path);
    }

    @And("the batchjob \"([^\"]*)\" is not running")
    public void theBatchjobIsNotRunning(String batchJobName) throws InterruptedException {
        RequestHelper helper = new RequestHelper();
        Response response;
        final int waitSecondsInLoop = 15;
        int currentAttempt = 0;

        // 20 Minutes
        final int maxAttempts = (20 * 60) / waitSecondsInLoop;
        String path =
                String.format(
                        ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_BATCHJOBSTATE_URL),
                        batchJobName
                );

        do {
            response = helper.simpleGetRequest(STATUS_OK, cookie, path);
            if (currentAttempt > 0) {
                Sleeper.sleepTightInSeconds(waitSecondsInLoop);
            }
            currentAttempt++;
            if (currentAttempt > maxAttempts) {
                Assert.fail(String.format("Unable to stop batchjob %s - Aborting test", batchJobName));
            }
        } while (response.getBody().toString().contains("done"));

    }
}
