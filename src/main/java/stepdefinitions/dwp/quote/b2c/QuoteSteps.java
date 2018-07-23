package stepdefinitions.dwp.quote.b2c;

import com.billinghouse.cucumber.runtime.annotations.InputParameter;
import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.billinghouse.random.Location;
import com.billinghouse.random.RandomUser;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.automation.flow.FlowAwarePredicate;
import com.essent.testing.dwp.DwpDateFormats;
import com.essent.testing.dwp.DwpScenario;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteStepView;
import com.essent.testing.dwp.pageobject.quote.impl.*;
import com.essent.testing.util.ResourceUtils;
import com.google.gson.Gson;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import stepdefinitions.dwp.tables.*;
import stepdefinitions.dwp.tables.plus.CheckBoxState;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static com.essent.testing.dwp.DwpDateFormats.TIMESTAMP;
import static com.essent.testing.dwp.DwpTimingParameters.NEXT_STEP;
import static com.essent.testing.dwp.quote.elements.TariffElements.NO_PRICESHEET_ALERT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class QuoteSteps extends DwpScenario {

    @Before("@QUOTE, @B2B_REGRESSION")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
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

    private class GetRandomUser implements Predicate<CustomerTable> {
        @Override
        public boolean test(CustomerTable customer) {
            Map<String, String> options = new HashMap<>();
            Map reply = executeJavascriptMethod("TrGetRandomUser", options);
            String status = ((String) reply.get("status"));
            boolean success = StringUtils.equals("PASSED", status);
            if(success) {
                Map userData  = (Map)reply.get("user");
                RandomUser randomUser = randomUser(userData);
                customer.setLastName(randomUser.getName().getLast());
                customer.setFirstName(randomUser.getName().getFirst());
                reply = executeJavascriptMethod("TrGetRandomValidAddress", options);
                status = ((String) reply.get("status"));
                if (!StringUtils.equals("PASSED", status))
                    return false;
                Map locationData  = (Map)reply.get("location");
                Location randomAddress = randomAddress(locationData);
                success = fillInCustomerDetails(randomUser, randomAddress);
            }
            return success;
        }

        private RandomUser randomUser(Map reply) {
            Gson gson = new Gson();
            String randomUserJs = gson.toJson(reply);
            RandomUser randomUser = gson.fromJson(randomUserJs, RandomUser.class);
            return randomUser;
        }

        private Location randomAddress(Map reply) {
            Gson gson = new Gson();
            String randomAddressJs = gson.toJson(reply);
            Location randomAddress = gson.fromJson(randomAddressJs, Location.class);
            return randomAddress;
        }

        private boolean fillInCustomerDetails(RandomUser randomUser, Location randomAddress) {
            CustomerDetailsView customerDetailsView = new CustomerDetailsView(webDriver);
            customerDetailsView.setRandomUser(randomUser);
            customerDetailsView.setRandomAddress(randomAddress);
            customerDetailsView.fillInFormData();
            CreateQuoteStepView view = customerDetailsView.next();
            return notNullValue().matches(view);
        }
    }

    @And("^Confirm Default B2C Channel Info$")
    public void confirmDefaultBCChannelInfo() throws Throwable {
        SelectQuoteTypeView selectQuoteTypeView = new SelectQuoteTypeView(webDriver);
        CreateQuoteStepView next = selectQuoteTypeView.next();
        assertThat("Failure accepting the standard quote type.", next,
            notNullValue());
    }

    @And("^B2C sales channel is ([^\"]*)$")
    public void toggleReguCheckbox(SalesChannel salesChannel) throws Throwable {
        SelectQuoteTypeView selectQuoteTypeView = new SelectQuoteTypeView(webDriver);
        selectQuoteTypeView.setSalesChannel(salesChannel);
        boolean formInitialized = selectQuoteTypeView.fillInFormData();
        assertThat("Failure occurred when filling in input values", formInitialized, is(true));
        CreateQuoteStepView next = selectQuoteTypeView.next();
        assertThat("Failure accepting the standard quote type.", next,
            notNullValue());
    }

    @OutputParameter(name = "customer")
    private CustomerTable newCustomer;

    @And("^Customer details for B2C are:$")
    public void initializeB2CCustomerDetails(DataTable customerTable) throws Throwable {
        List<CustomerTable> customers = customerTable.asList(CustomerTable.class);
        CustomerTable customer = customers.get(0);
        String lastName = customer.getLastName().replace("${TIMESTAMP}", TIMESTAMP.print());
        customer.setLastName(lastName);
        newCustomer = customer;
        CustomerDetailsView customerDetailsView = new CustomerDetailsView(webDriver);
        customerDetailsView.setCustomer(customer);
        customerDetailsView.fillInFormData();
        CreateQuoteStepView view = customerDetailsView.next();
        assertThat("Failure initializing B2C Customer details, check up the log.", view,
            notNullValue());
    }

    @Then("^Form Header is \"([^\"]*)\"$")
    public void checkFormHeader(String formHeader) throws Throwable {
        boolean success = new CheckFormHeader().test(formHeader);
        assertThat(String.format("Form %s did not appear.", formHeader), success,
            is(true));
    }

    @OutputParameter(name = "customers")
    private Map<String, CustomerTable> customers = new HashMap<>();
    @And("^Customer details are random$")
    public void findRandomUser() throws Throwable {
        CustomerTable customer = new CustomerTable();
        boolean success = new GetRandomUser().test(customer);
        customers.put("onboarding", customer);
        assertThat("Random customer data was not fetched.", success,
            is(true));
    }

    @And("^Valid customer address is random$")
    public void validCustomerAddressIsRandom() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^Tariffsheet and package are:$")
    public void selectTariffSheetAndPackage(final DataTable tariffTable) throws Throwable {
        List<TariffTable> tariffs = tariffTable.asList(TariffTable.class);
        TariffTable tariff = tariffs.get(0);
        SelectPackageAndFuelTypeView selectPackageAndFuelTypeView = new SelectPackageAndFuelTypeView(webDriver);
        selectPackageAndFuelTypeView.setTariffData(tariff);
        selectPackageAndFuelTypeView.fillInFormData();
        CreateQuoteStepView next = selectPackageAndFuelTypeView.next();
        assertThat("Failure selecting the Essent package and product(s), check up the log.", next,
            notNullValue());
    }

    @And("^Package is \"([^\"]*)\"$")
    public void selectPackage(String packaqe) throws Throwable {
        TariffTable tariff = new TariffTable();
        tariff.setPackageName(packaqe);
        SelectPackageAndFuelTypeView selectPackageAndFuelTypeView = new SelectPackageAndFuelTypeView(webDriver);
        selectPackageAndFuelTypeView.setTariffData(tariff);
        selectPackageAndFuelTypeView.fillInFormData();
        CreateQuoteStepView next = selectPackageAndFuelTypeView.next();
        assertThat("Failure selecting the Essent package and product(s), check up the log.", next,
            notNullValue());
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

        ConnectionDetailsView connectionDetailsView = new ConnectionDetailsView(webDriver);
        connectionDetailsView.setElectroConnectionDetails(electricityConnectionDetails);
        connectionDetailsView.setGasConnectionDetails(gasConnectionDetails);
        connectionDetailsView.fillInFormData();
    }

    @And("^The ([^\"]*) meter is ([^\"]*)$")
    public void setMeterState(final ProductType productType, final CheckBoxState meterState) throws Throwable {
        ConnectionDetailsView connectionDetailsView = new ConnectionDetailsView(webDriver);
        connectionDetailsView.openMeter(productType, meterState);
    }

    @And("^Confirm Connection$")
    public void confirmConnection() throws Throwable {
        ConnectionDetailsView connectionDetailsView = new ConnectionDetailsView(webDriver);
        CreateQuoteStepView next = connectionDetailsView.next();
        assertThat("Failure filling in connection details, check up the log.", next,
            notNullValue());
    }

    @And("^Payment details are: method ([^\"]*), IBAN \"([^\"]*)\", bic \"([^\"]*)\"$")
    public void selectPaymentMethod(PaymentMethod paymetnMethod, String iban, String bic) throws Throwable {
        BillingInformation billingInfo = new BillingInformation(paymetnMethod, iban, bic);
        BillingDetailsView billingDetailsView = new BillingDetailsView(webDriver);
        billingDetailsView.setBillingInformation(billingInfo);
        billingDetailsView.fillInFormData();
        CreateQuoteStepView createQuoteStepView = billingDetailsView.next();

        assertThat("Failure when initializing the payment, BIC and EBAN. +", createQuoteStepView,
            notNullValue());

    }

    @And("^Confirm Quote$")
    public void confirmQuote() throws Throwable {
        QuoteOverviewView quoteOverviewView = new QuoteOverviewView(webDriver);
        quoteOverviewView.next();
    }

    @And("^Confirm Change$")
    public void confirmChange() {
//        QuoteOverviewView quoteOverviewView = new QuoteOverviewView(webDriver);
//        quoteOverviewView.confirm();
        webDriver.findElementOrNull(By.id("confirm-button")).click();
    }

    @InputParameter(name = "customer")
    private CustomerTable quoteCustomer;

    @And("^Signature date is ([^\"]*), place is \"([^\"]*)\", hand signature file is \"([^\"]*)\":$")
    public void submitSignedQuote(DwpDateFormats date, String location, String filePath) throws Throwable {
        String path = ResourceUtils.toPath(filePath);
        File document = new File(path);
        assertThat("File at path " + document.getAbsolutePath() + " doesn't exist.", true,
            is(document.exists()));
        SignatureData signature = new SignatureData(quoteCustomer.getFirstName(),
            quoteCustomer.getLastName(),
            date,
            location,
            path);
        QuoteOverviewView quoteOverviewView = new QuoteOverviewView(webDriver);
        quoteOverviewView.setSignatureData(signature);
        quoteOverviewView.fillInFormData();
        quoteOverviewView.next();
    }

    @When("^I select the ([^\"]*) element and click the link in the \"([^\"]*)\" column$")
    public void navigateToListCellLink(String ordinal, String column) throws Throwable {
        Map<String, String> options = new HashMap<>();
        options.put("column", column);
        boolean success = executeJavascriptTest("TrGetColumnIndexList", options);
        assertThat(success, is(true));
    }

}
