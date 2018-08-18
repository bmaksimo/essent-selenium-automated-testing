package stepdefinitions.dwp.quote.b2c;

import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.billinghouse.random.RandomUser;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.automation.flow.FlowAwarePredicate;
import com.essent.testing.dwp.DwpDateFormats;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.dwp.pageobject.quote.GuidedStep;
import com.essent.testing.dwp.pageobject.quote.impl.*;
import com.essent.testing.util.ResourceUtils;
import com.google.gson.Gson;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Duration;
import stepdefinitions.dwp.tables.*;
import stepdefinitions.dwp.tables.plus.CheckBoxState;
import static org.awaitility.Awaitility.*;
import static org.awaitility.Duration.*;
import static java.util.concurrent.TimeUnit.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static com.essent.testing.dwp.DwpTimingParameters.NEXT_STEP;
import static com.essent.testing.dwp.quote.elements.TariffElements.NO_PRICESHEET_ALERT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class QuoteSteps extends DwpScenario {

    @Before("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^B2C sales channel is ([^\"]*)$")
    public void initSalesChannel(SalesChannel salesChannel) throws Throwable {
        QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage(webDriver);
        quoteDetailsPage.setSalesChannel(salesChannel);
        boolean formInitialized = quoteDetailsPage.fillInFormData();
        assertThat("Failure occurred when filling in input values", formInitialized, is(true));
    }

    private class CheckFormHeader implements Predicate<String> {
        @Override
        public boolean test(String header) {
            int sec = 7;
            Map<String, Object> options = new HashMap<>();
            options.put("schedule_seconds", sec);
            options.put("header", header);
            return executeJavascriptTest("TrCheckFormHeader", options);
        }
    }

    private class VerifyTariffSheetPriceAlert implements FlowAwarePredicate<QuoteSteps> {

        @Override
        public boolean test(QuoteSteps scenarios) {
            return execute(webDriver.getDriver(), build(scenarios));
        }

        @Override
        public Model.Execution build(QuoteSteps input) {
            Model.Execution selectAlert = newExecution();
            selectAlert.element(NO_PRICESHEET_ALERT.element()).
                step(createStep(Action.REQUIRE_ABSENT).timeoutInSeconds(NEXT_STEP.getWaitInSeconds()).
                    element(NO_PRICESHEET_ALERT.name()));
            return selectAlert;
        }
    }

    private class GetRandomUser implements Predicate<CustomerDetails> {
        @Override
        public boolean test(CustomerDetails customer) {
            Map<String, String> options = new HashMap<>();
            Map reply = executeJavascriptMethod("TrGetRandomUser", options);
            String status = ((String) reply.get("status"));
            boolean success = StringUtils.equals("PASSED", status);
            if(success) {
                Map userData  = (Map)reply.get("user");
                RandomUser randomUser = randomUser(userData);
                customer.setLastName(randomUser.getName().getLast());
                customer.setFirstName(randomUser.getName().getFirst());
                success = fillInCustomerDetails(randomUser);
            }
            return success;
        }

        private RandomUser randomUser(Map reply) {
            Gson gson = new Gson();
            String randomUserJs = gson.toJson(reply);
            RandomUser randomUser = gson.fromJson(randomUserJs, RandomUser.class);
            return randomUser;
        }

        private boolean fillInCustomerDetails(RandomUser randomUser) {
            PersonalDetailsPage customerDetailsView = new PersonalDetailsPage(webDriver);
            customerDetailsView.setRandomUser(randomUser);
            return customerDetailsView.fillInFormData();
        }
    }

    private class InitialiseCustomerAddress implements Predicate<CustomerAddress> {
        @Override
        public boolean test(CustomerAddress customerAddress) {
           return fillInCustomerAddress(customerAddress);
        }

        private boolean fillInCustomerAddress(CustomerAddress customerAddress) {
            PersonalDetailsAddressPage customerAddressView = new PersonalDetailsAddressPage(webDriver);
            customerAddressView.setCustometAddress(customerAddress);
            return customerAddressView.fillInCustomerAddress();
        }
    }

    private class ToggleCheckBox implements Predicate<Map<String, String>> {

        @Override
        public boolean test(Map<String, String> options) {
            return executeJavascriptTest("TrToggleCheckBox", options);
        }
    }

    @And("^Quote details are confirmed$")
    public void confirmQuoteDetails() throws Throwable {
        QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage(webDriver);
        quoteDetailsPage.next();
    }



    @OutputParameter(name = "customer")
    private CustomerDetails newCustomer;

    @Then("^Form Header is \"([^\"]*)\"$")
    public void checkFormHeader(String formHeader) throws Throwable {
        given().await()
            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
            .pollDelay(ONE_SECOND)
            .atMost(new Duration(30, SECONDS)).until(()->new CheckFormHeader().test(formHeader));
    }

    @OutputParameter(name = "customers")
    private Map<String, CustomerDetails> customers = new HashMap<>();

    @And("^Customer is random$")
    public void findRandomUser() throws Throwable {
        CustomerDetails customer = new CustomerDetails();
        boolean success = new GetRandomUser().test(customer);
        customers.put("onboarding", customer);
        assertThat("Random customer data was not fetched.", success,
            is(true));
    }

    @And("^Customer Address is$")
    public void initCustomerAddress(final DataTable address) throws Throwable {
        List<CustomerAddress> list = address.asList(CustomerAddress.class);
        CustomerAddress cuatomerAddress = list.get(0);
        boolean success = new InitialiseCustomerAddress().test(cuatomerAddress);
        assertThat("Cusomer Address data wasn't initialised.", success, is(true));
    }

    @And("^Customer details are confirmed$")
    public void confirmCustomerDetails() throws Throwable {
        GuidedStep quoteDetailsPage = new PersonalDetailsAddressPage(webDriver);
        quoteDetailsPage.next();
    }

    @And("^Package is \"([^\"]*)\"$")
    public void selectPackage(String packaqe) throws Throwable {
        TariffTable tariff = new TariffTable();
        tariff.setPackageName(packaqe);
        SelectPackageAndFuelTypePage selectPackageAndFuelTypeView = new SelectPackageAndFuelTypePage(webDriver);
        selectPackageAndFuelTypeView.setTariffData(tariff);
        selectPackageAndFuelTypeView.fillInFormData();
    }

    @And("^Checkbox \"([^\"]*)\" is ([^\"]*)$")
    public void toggleCheckbox(String label, CheckBoxState state) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Map<String, String> options = new HashMap<>();
        options.put("label", label);
        options.put("state", state.name().toLowerCase());
        boolean success = new ToggleCheckBox().test(options);
        assertThat(String.format("Failure toggling checkbox %s to  target state %s.", label, state.name()),
            success,
            is(true));
    }

    @And("^Package and Fuel Type is confirmed$")
    public void confirmPackageAndFuelType() throws Throwable {
        SelectPackageAndFuelTypePage selectPackageAndFuelTypeView = new SelectPackageAndFuelTypePage(webDriver);
        selectPackageAndFuelTypeView.next();
    }

    @And("^No price sheet alerts popped up$")
    public void verifySelectTariffSheetAndPackage() throws Throwable {
        assertThat("Failure. Tariff sheet alerts were generated although they were not expected.", true,
            is(new VerifyTariffSheetPriceAlert().test(this)));
    }

    @And("^Electricity and gas meter numbers and their EANs are:$")
    public void selectMeterIdAndEan(final DataTable connectionTable) throws Throwable {
        List<ConnectionDetails> list = connectionTable.asList(ConnectionDetails.class);
        ConnectionDetails electricityConnectionDetails = list.get(0);
        ConnectionDetails gasConnectionDetails = list.get(1);

        ConnectionDetailsPage connectionDetailsView = new ConnectionDetailsPage(webDriver);
        connectionDetailsView.setElectroConnectionDetails(electricityConnectionDetails);
        connectionDetailsView.setGasConnectionDetails(gasConnectionDetails);
        connectionDetailsView.fillInFormData();
    }

    @And("^([^\"]*) meter is ([^\"]*)$")
    public void setMeterState(final ProductType productType, final CheckBoxState meterState) throws Throwable {
        ConnectionDetailsPage connectionDetailsView = new ConnectionDetailsPage(webDriver);
        connectionDetailsView.toggleMeter(productType, meterState);
    }

    @And("^Connection details are confirmed$")
    public void confirmConnection() throws Throwable {
        ConnectionDetailsPage connectionDetailsView = new ConnectionDetailsPage(webDriver);
        connectionDetailsView.next();
    }

    @And("^Payment details are: method ([^\"]*), IBAN \"([^\"]*)\", bic \"([^\"]*)\"$")
    public void selectPaymentMethod(String paymetnMethod, String iban, String bic) throws Throwable {
        BillingInformation billingInfo = new BillingInformation(paymetnMethod, iban, bic);
        BillingDetailsPage billingDetailsView = new BillingDetailsPage(webDriver);
        billingDetailsView.setBillingInformation(billingInfo);
        billingDetailsView.fillInFormData();
    }

    @And("^Billing details are confirmed$")
    public void confirmBillingDetaile() throws Throwable {
        BillingDetailsPage billingDetailsPage = new BillingDetailsPage(webDriver);
        billingDetailsPage.next();
    }

    @And("^Quote is signed in ([^\"]*)$")
    public void submitSignedQuote(String location) throws Throwable {
        String path = ResourceUtils.toPath("/data/dwp/customer-signature.pdf");
        File document = new File(path);
        assertThat("File at path " + document.getAbsolutePath() + " doesn't exist.", true,
            is(document.exists()));
        SignatureData signature = new SignatureData(
            DwpDateFormats.DWP_TODAY,
            location,
            path);
        QuoteOverviewPage quoteOverviewView = new QuoteOverviewPage(webDriver);
        quoteOverviewView.setSignatureData(signature);
        quoteOverviewView.fillInFormData();
    }

    @And("^Quote is confirmed$")
    public void confirmQuote() throws Throwable {
        QuoteOverviewPage quoteOverviewView = new QuoteOverviewPage(webDriver);
        quoteOverviewView.next();
    }

    @When("^I select the ([^\"]*) element and click the link in the \"([^\"]*)\" column$")
    public void navigateToListCellLink(String ordinal, String column) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("column", column);
        boolean success = executeJavascriptTest("TrGetColumnIndexList", options);
        assertThat(success, is(true));
    }

    @And("^Electricity EAN code is selected$")
    public void selectEanCode() throws Throwable {
        Map<String, String> options = new HashMap<>();
        boolean success = executeJavascriptTest("TrSelectEanCode", options);
        assertThat(success, is(true));
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL")
    public void tearDown() throws Exception {
        super.tearDown();
    }

}
