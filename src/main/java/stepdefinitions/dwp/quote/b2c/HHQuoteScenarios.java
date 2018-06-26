package stepdefinitions.dwp.quote.b2c;

import com.billinghouse.cucumber.runtime.annotations.InputParameter;
import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Model;
import com.essent.automation.flow.FlowAwarePredicate;
import com.essent.testing.dwp.DwpDateFormats;
import com.essent.testing.dwp.DwpScenario;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.account.AccountCard;
import com.essent.testing.dwp.pageobject.account.impl.AccountCardImpl;
import com.essent.testing.dwp.pageobject.impl.MainWindow;
import com.essent.testing.dwp.pageobject.menu.AccordionWrapperMenu;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteStepView;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteView;
import com.essent.testing.dwp.pageobject.quote.impl.*;
import com.essent.testing.util.ResourceUtils;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.BooleanUtils;
import stepdefinitions.dwp.tables.*;
import stepdefinitions.dwp.tables.plus.CheckBoxState;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.essent.testing.dwp.DwpDateFormats.TIMESTAMP;
import static com.essent.testing.dwp.DwpTimingParameters.NEXT_STEP;
import static com.essent.testing.dwp.quote.elements.TariffElements.NO_PRICESHEET_ALERT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class HHQuoteScenarios extends DwpScenario {
    @Before("@QUOTE")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    private class VerifyTariffSheetPriceAlert implements FlowAwarePredicate<HHQuoteScenarios> {

        @Override
        public boolean test(HHQuoteScenarios scenarios) {
            return execute(webDriver.getDriver(), build(scenarios));
        }

        @Override
        public Model.Execution build(HHQuoteScenarios input) {
            Model.Execution selectAlert = newExecution();
            selectAlert.element(NO_PRICESHEET_ALERT.element()).
                step(createStep(Action.REQUIRE_ABSENT).timeoutInSeconds(NEXT_STEP.getWaitInSeconds()).
                    element(NO_PRICESHEET_ALERT.name()));
            return selectAlert;
        }
    }

    @When("^I start a B2C quote flow:$")
    public void startB2CQuoteFlow() throws Throwable {
        Window window = new MainWindow(webDriver);
        AccordionWrapperMenu plusMenu = window.getPlusMenu();
        assertThat(plusMenu, notNullValue());
        CreateQuoteView b2CQuote = plusMenu.createB2CQuote("Sales -> TC1 -> Create new quote B2C");
        assertThat(b2CQuote, notNullValue());

    }

    @And("^B2C sales channel is ([^\"]*) for standard quote type:$")
    public void toggleReguCheckbox(SalesChannel salesChannel) throws Throwable {
        SelectQuoteTypeView selectQuoteTypeView = new SelectQuoteTypeView(webDriver);
        selectQuoteTypeView.setSalesChannel(salesChannel);
        boolean formInitialized = selectQuoteTypeView.fillInInputValues();
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
        customerDetailsView.fillInInputValues();
        CreateQuoteStepView view = customerDetailsView.next();
        assertThat("Failure initializing B2C Customer details, check up the log.", view,
            notNullValue());
    }

    @And("^Tariffsheet and package are:$")
    public void selectTariffSheetAndPackage(final DataTable tariffTable) throws Throwable {
        List<TariffTable> tariffs = tariffTable.asList(TariffTable.class);
        TariffTable tariff = tariffs.get(0);
        SelectPackageAndFuelTypeView selectPackageAndFuelTypeView = new SelectPackageAndFuelTypeView(webDriver);
        selectPackageAndFuelTypeView.setTariffData(tariff);
        selectPackageAndFuelTypeView.fillInInputValues();
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
        connectionDetailsView.fillInInputValues();
    }

    @And("^The ([^\"]*) meter is ([^\"]*)$")
    public void setMeterState(final ProductType productType, final CheckBoxState meterState) throws Throwable {
        ConnectionDetailsView connectionDetailsView = new ConnectionDetailsView(webDriver);
        connectionDetailsView.openMeter(productType, meterState);
    }

    @And("^I confirm \"([^\"]*)\" details$")
    public void confirmConnectionDetails(String viewName) throws Throwable {
        ConnectionDetailsView connectionDetailsView = new ConnectionDetailsView(webDriver);
        CreateQuoteStepView next = connectionDetailsView.next();
        assertThat("Failure filling in connection details, check up the log.", next,
            notNullValue());
    }

    @And("^Payment details are: method: ([^\"]*), IBAN: \"([^\"]*)\" and bic: \"([^\"]*)\":$")
    public void selectPaymentMethod(PaymentMethod paymetnMethod, String iban, String bic) throws Throwable {
        BillingInformation billingInfo = new BillingInformation(paymetnMethod, iban, bic);
        BillingDetailsView billingDetailsView = new BillingDetailsView(webDriver);
        billingDetailsView.setBillingInformation(billingInfo);
        billingDetailsView.fillInInputValues();
        CreateQuoteStepView createQuoteStepView = billingDetailsView.next();

        assertThat("Failure when initializing the payment, BIC and EBAN. +", createQuoteStepView,
            notNullValue());

    }

    @InputParameter(name = "customer")
    private CustomerTable quoteCustomer;

    @And("^Signing contract on date: ([^\"]*) in \"([^\"]*)\" with hand signature file \"([^\"]*)\":$")
    public void submitSignature(DwpDateFormats date, String location, String filePath) throws Throwable {
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
        quoteOverviewView.fillInInputValues();
        quoteOverviewView.next();
    }

    @Then("^Redirect view is \"([^\"]*)\" with first name: \"([^\"]*)\" and last name: \"([^\"]*)\" customer details entry$")
    public void iAmRedirectedToAnAccountPageThatHasCardWithAndCustomerDetails(String redirectView, String firstName, String lastName) throws Throwable {
        String input[] = {firstName, lastName},
               customerFirstName = null,
               customerLastName = null,
               customerDetails[] = new String[2];
        for(int i = 0; i < customerDetails.length; i++) {
            try {
                customerDetails[i] = (String)checkFieldAccess(input[i], quoteCustomer);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException e) {
                throw new CucumberException("Customer details did not contain " + firstName + " data field.");
            }
        }
        customerFirstName = customerDetails[0];
        customerLastName = customerDetails[1];
        assertThat(customerFirstName, notNullValue());
        assertThat(customerLastName, notNullValue());

        AccountCard customerAccountCard = new AccountCardImpl(webDriver);
        assertThat(String.format("Customer first name '%s' and last name '%s' were not found on the current web page", customerFirstName, customerLastName),
            customerAccountCard.containsFirstAndLastName(customerFirstName, customerLastName), is(true));
    }

    @When("^I select the ([^\"]*) element and click the link in the \"([^\"]*)\" column$")
    public void navigateToListCellLink(String ordinal, String column) throws Throwable {
        //int position = Integer.parseInt(ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", ""));
        Map<String, String> options = new HashMap<>();
        options.put("column", column);
        boolean success = executeJavascriptTest("TrGetColumnIndexList", options);
        assertThat(success, is(true));
    }

    @And("^A quote with type \"([^\"]*)\" and status \"([^\"]*)\" is created$")
    public void checkCreatedQuote(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^A signed quote is created of type \"([^\"]*)\" and status \"([^\"]*)\" and shown in an account for \"([^\"]*)\" with ([0-9]+) products:$")
    public void a_signed_quote_is_created_for_an_account_with_products(String type, String status, String accountName, String nrOfProducts) throws Throwable {
        Model.Execution execution = new Model.Execution();
        // this.initializeFlowElements(execution);
        Model.Flow aSignedQuoteIsCreated = execution.flow();

        execution.element("DWP_QUOTEOVERVIEW_ACCOUNT_HEADER", new Model.Element().search("XPATH").query("//h1[1][text()='" + accountName + "']"));
        aSignedQuoteIsCreated.step(new Model.Step().action(Action.REQUIRE).element("DWP_QUOTEOVERVIEW_ACCOUNT_HEADER").timeoutInSeconds(30));

        execution.element("DWP_QUOTEOVERVIEW_STATUS", new Model.Element().search("XPATH").query("//list[@list-key='QuotesOnAccount']//tr[1]//td/list-simple-two-liner-cell[@line-1='" + type + "'][@line-2='" + status + "']"));
        aSignedQuoteIsCreated.step(new Model.Step().action(Action.REQUIRE).element("DWP_QUOTEOVERVIEW_STATUS").timeoutInSeconds(3));

        execution.element("DWP_QUOTEOVERVIEW_PRODUCTS", new Model.Element().search("XPATH").query("//list[@list-key='QuotesOnAccount']//tr[1]//select/option[text()='4 Products']"));
        aSignedQuoteIsCreated.step(new Model.Step().action(Action.REQUIRE).element("DWP_QUOTEOVERVIEW_PRODUCTS").timeoutInSeconds(3));

        Autocrat.ExecutionContext context = new Autocrat.ExecutionContext(webDriver.getDriver(), execution);
        Autocrat.executeFlow(context, aSignedQuoteIsCreated);
    }
}
