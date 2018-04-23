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
import com.essent.testing.dwp.pageobject.impl.MainWindow;
import com.essent.testing.dwp.pageobject.menu.AccordionWrapperMenu;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteStepView;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteView;
import com.essent.testing.dwp.pageobject.quote.impl.ConnectionDetailsView;
import com.essent.testing.dwp.pageobject.quote.impl.CustomerDetailsView;
import com.essent.testing.dwp.pageobject.quote.impl.SelectPackageAndFuelTypeView;
import com.essent.testing.dwp.pageobject.quote.impl.SelectQuoteTypeView;
import com.essent.testing.util.ResourceUtils;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.tables.*;
import stepdefinitions.dwp.tables.plus.CheckBoxState;

import java.io.File;
import java.util.List;

import static com.essent.testing.dwp.DwpTimingParameters.*;
import static com.essent.testing.dwp.elements.DwpBasicElements.NEXT_BUTTON;
import static com.essent.testing.dwp.elements.DwpBasicElements.SIBLING_OVERLAYING_ICONS_XPATH;
import static com.essent.testing.dwp.quote.elements.B2CQuoteElements.SIGNATURE_OPTIONS_ACTIVE;
import static com.essent.testing.dwp.quote.elements.BillingElements.*;
import static com.essent.testing.dwp.quote.elements.SignatureElements.*;
import static com.essent.testing.dwp.quote.elements.TariffElements.NO_PRICESHEET_ALERT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class HHQuoteScenarios extends DwpScenario {
    @Before("@QUOTE, @DWP_SETUP")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    private class HideAddressSuggestion implements Model.Callback {
        @Override
        public void onAccess(Autocrat.ExecutionContext context, Model.Step step, WebElement value) {
            JavascriptExecutor jsExec = (JavascriptExecutor) context.driver;
            String setProperty = "style = 'display:none'";
            logger().info("Executing javascript " + setProperty + " on target element");
            jsExec.executeScript("arguments[0]." + setProperty, value);
        }
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

    private class InitializeBillingDetails implements FlowAwarePredicate<BillingInformation> {
        @Override
        public boolean test(BillingInformation billingInformation) {
            return execute(webDriver.getDriver(), build(billingInformation));
        }

        @Override
        public Model.Execution build(BillingInformation billingInformation) {
            PaymentMethod paymentMethod = billingInformation.getPaymentMethod();
            String eban = billingInformation.getEban();
            String bic = billingInformation.getBic();
            Model.Execution execution = newExecution().
                element(PAYMENT_METHOD.element()).
                element(PAYMENT_IBAN.element()).
                element(PAYMENT_BIC.element()).
                element(NEXT_BUTTON.element()).
                element(SIGNATURE_OPTIONS_ACTIVE.element()).
                step(createStep(Action.SELECT).element(PAYMENT_METHOD.name()).value(paymentMethod.getLabel())).
                step(createStep(Action.TYPING).element(PAYMENT_IBAN.name()).value(eban), INPUT.getSleepInMillis()).
                step(createStep(Action.TYPING).element(PAYMENT_BIC.name()).value(bic), INPUT.getSleepInMillis()).
                step(createStep(Action.CLICK).timeoutInSeconds(NEXT_STEP.getWaitInSeconds()).element(NEXT_BUTTON.name())).
                step(createStep(Action.REQUIRE).timeoutInSeconds(WAIT_NEXT_PAGE.getWaitInSeconds()).element(SIGNATURE_OPTIONS_ACTIVE.name()));
            return execution;
        }
    }
    private class SignUp implements FlowAwarePredicate<SignatureData> {

        @Override
        public boolean test(SignatureData signatureData) {
            return execute(webDriver.getDriver(), build(signatureData));
        }

        public Model.Execution build(SignatureData signatureData) {
            String formattedDate = signatureData.getDate().print();
            String place = signatureData.getPlace();
            String filePath = signatureData.getFilePath();
            Model.Execution execution = newExecution()
                .element(SIGN_WANTTOSIGN_CHECKBOX.element())
                .element(SIGN_ALREADYSIGNED_CHECKBOX.element())
                .element(SIGN_DATE.element())
                .element(SIGN_LOCATION.element())
                .element(SIGN_UPLOAD_DOC.element())
                .element(NEXT_BUTTON.element())
                .step(createStep(Action.ACCESS).element(SIGN_WANTTOSIGN_CHECKBOX.name()).requireDisplayed(false).callback(new HideIconOverlays()))
                .step(createStep(Action.CLICK).requireDisplayed(false).element(SIGN_WANTTOSIGN_CHECKBOX.name()).timeoutInSeconds(TOGGLE_CHECKBOX.getWaitInSeconds()), TOGGLE_CHECKBOX.getSleepInMillis())
                .step(createStep(Action.CLICK).requireDisplayed(false).element(SIGN_ALREADYSIGNED_CHECKBOX.name()), TOGGLE_CHECKBOX.getSleepInMillis())
                .step(createStep(Action.TYPING).element(SIGN_DATE.name()).value(formattedDate), INPUT.getSleepInMillis())
                .step(createStep(Action.TYPING).element(SIGN_LOCATION.name()).value(place), INPUT.getSleepInMillis())
                .step(createStep(Action.REQUIRE).element(SIGN_UPLOAD_DOC.name()).requireDisplayed(false))
                .step(createStep(Action.UPLOAD)
                    .element(SIGN_UPLOAD_DOC.name()).value(filePath).requireDisplayed(false), UPLOAD_FILE.getSleepInMillis())
                .step(createStep(Action.CLICK).timeoutInSeconds(UPLOAD_FILE.getWaitInSeconds()).element(NEXT_BUTTON.name()).timeoutInSeconds(SUBMIT_QUOTE.getWaitInSeconds()));
            return execution;
        }
    }

    private class HideIconOverlays implements Model.Callback {
        @Override
        public void onAccess(Autocrat.ExecutionContext context, Model.Step step, WebElement value) {
            JavascriptExecutor jsExec = (JavascriptExecutor) context.driver;
            List<WebElement> elements = value.findElements(By.xpath(SIBLING_OVERLAYING_ICONS_XPATH.getQuery()));
            elements.forEach(siblingIcon -> {
                String setProperty = "style = 'display:none'";
                logger().info("Executing javascript " + setProperty + " on target element");
                jsExec.executeScript("arguments[0]." + setProperty, siblingIcon);
            });
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

    @And("^I select ([^\"]*) sales channel and accept standard quote type for B2C:$")
    public void toggleReguCheckbox(SalesChannel salesChannel) throws Throwable {
        SelectQuoteTypeView selectQuoteTypeView = new SelectQuoteTypeView(webDriver);
        selectQuoteTypeView.setSalesChannel(salesChannel);
        boolean formInitialized = selectQuoteTypeView.fillInInputValues();
        assertThat("Failure occurred when filling in input values", formInitialized, is(true));
        CreateQuoteStepView next = selectQuoteTypeView.next();
        assertThat("Failure accepting the standard quote type.", next,
            notNullValue());
    }

    @OutputParameter(name="customer")
    private String customerName;
    @And("^I enter customer details for B2C:$")
    public void initializeB2CCustomerDetails(DataTable customerTable) throws Throwable {
        List<CustomerTable> customers = customerTable.asList(CustomerTable.class);
        CustomerTable customer = customers.get(0);
        CustomerDetailsView customerDetailsView = new CustomerDetailsView(webDriver);
        customerDetailsView.setCustomer(customer);
        customerDetailsView.fillInInputValues();
        CreateQuoteStepView view = customerDetailsView.next();
        assertThat("Failure initializing B2C Customer details, check up the log.", view,
            notNullValue());
    }

    @And("^I select tariffsheet and package:$")
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

    @And("^I don't detect any price sheet alerts$")
    public void verifySelectTariffSheetAndPackage() throws Throwable {
        assertThat("Failure. Tariff sheet alerts were generated although they were not expected.", true,
            is(new VerifyTariffSheetPriceAlert().test(this)));

    }

    @And("^I select product \"([^\"]*)\":$")
    public void i_select_product(String product) throws Throwable {
    }
    @And("^I fill in the electricity and gas meter numbers and their EANs respectively:$")
    public void selectMeterIdAndEan(final DataTable connectionTable) throws Throwable {
        List<ConnectionDetails> list = connectionTable.asList(ConnectionDetails.class);
        ConnectionDetails electricityConnectionDetails = list.get(0);
        ConnectionDetails gasConnectionDetails = list.get(1);
        ConnectionDetailsView connectionDetailsView = new ConnectionDetailsView(webDriver);
        connectionDetailsView.setElectroConnectionDetails(electricityConnectionDetails);
        connectionDetailsView.setGasConnectionDetails(gasConnectionDetails);
        connectionDetailsView.fillInInputValues();
    }
    @And("^I optionally ([^\"]*) the ([^\"]*) meter$")
    public void setMeterState(final CheckBoxState meterState, final ProductType productType) throws Throwable {
        ConnectionDetailsView connectionDetailsView = new ConnectionDetailsView(webDriver);
        connectionDetailsView.openMeter(productType, meterState);
    }

    @And("^I confirm Connection details$")
    public void confirmConnectionDetails() throws Throwable {

        ConnectionDetailsView connectionDetailsView = new ConnectionDetailsView(webDriver);
        CreateQuoteStepView next = connectionDetailsView.next();
        assertThat("Failure filling in connection details, check up the log.", next,
            notNullValue());
    }

    @And("^I select payment method ([^\"]*) for IBAN \"([^\"]*)\" and bic \"([^\"]*)\":$")
    public void selectPaymentMethod(PaymentMethod paymetnMethod, String iban, String bic) throws Throwable {
        BillingInformation billingInfo = new BillingInformation(paymetnMethod, iban, bic);
        assertThat("Failure when initializing the payment, BIC and EBAN. +", true,
            is(new InitializeBillingDetails().test(billingInfo)));
    }

    @And("^I sign on date ([^\"]*) in location \"([^\"]*)\" with file \"([^\"]*)\":$")
    public void submitSignature(DwpDateFormats date, String location, String filePath) throws Throwable {
        String path = ResourceUtils.toPath(filePath);
        File document = new File(path);
        assertThat("File at path " + document.getAbsolutePath() + " doesn't exist.", true,
            is(document.exists()));
        SignatureData signature = new SignatureData(date, location, path);

        assertThat("Failure when initializing the signature or uploading the signature file.", true,
            is(new SignUp().test(signature)));

    }

    @Then("^I click on Home button, optionally dismissing the alert$")
    public void home() throws Throwable {

    }

    @InputParameter(name = "customer")
    private String quotedName;
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
