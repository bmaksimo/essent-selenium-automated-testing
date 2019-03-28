package stepdefinitions.dwp.quote.b2c;

import com.billinghouse.random.RandomUser;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.modal.quote.SimilarAccountDialogImpl;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import com.essent.testing.dwp.pageobject.impl.quote.*;
import com.essent.testing.dwp.pageobject.impl.quote_for_account.QuoteForAccountOverviewPage;
import com.essent.testing.dwp.pageobject.modal.quote.SimilarAccountDialog;
import com.essent.testing.dwp.pageobject.quote.GuidedStep;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.restassured.create_contract.helper.PrepareDataForContract;
import com.essent.testing.util.resource.ResourceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.autocrat.flow.FlowAwarePredicate;
import stepdefinitions.dwp.quote.DwpDateFormats;
import stepdefinitions.dwp.tables.*;
import stepdefinitions.dwp.tables.plus.SwitchState;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static com.essent.testing.dwp.autocrat.element.quote.TariffElements.NO_PRICESHEET_ALERT;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.NEXT_STEP;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.FIVE_HUNDRED_MILLISECONDS;
import static org.awaitility.Duration.ONE_HUNDRED_MILLISECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class QuoteSteps extends DwpScenario {

    @Before("@DWP, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }



    @When("^B2C sales channel is \"([^\"]*)\"$")
    public void initSalesChannel(SalesChannel salesChannel) throws Throwable {
        QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
        Sleeper.sleepTightInSeconds(5);
        quoteDetailsPage.setSalesChannel(salesChannel);
        boolean formInitialized = quoteDetailsPage.fillInFormData();
        assertThat("Failure occurred when filling in input values", formInitialized, is(true));
    }

    @And("^Deduplication dialogue \"([^\"]*)\" is shown$")
    public void deduplicationDialogueIsShown(String title) throws Throwable {
        SimilarAccountDialog dialog = new SimilarAccountDialogImpl(title);
        assertThat("Similar clients dialogue was not shown.",
            dialog.getTitle(),
            equalTo(title));
    }

    @And("^Deduplication dialogue link \"([^\"]*)\" is clicked$")
    public void deduplicationDialogueLinkIsClicked(String linkText) throws Throwable {
        SimilarAccountDialog dialog = new SimilarAccountDialogImpl();
        dialog.clickOnLink(linkText);
    }

    private class VerifyTariffSheetPriceAlert implements FlowAwarePredicate<QuoteSteps> {

        @Override
        public boolean test(QuoteSteps scenarios) {
            return execute(build(scenarios));
        }

        @Override
        public Model.Execution build(QuoteSteps input) {
            Model.Execution selectAlert = createExecution();
            selectAlert.element(NO_PRICESHEET_ALERT.element()).
                step(createStep(Action.REQUIRE_ABSENT).timeoutInSeconds(NEXT_STEP.getWaitInSeconds()).
                    element(NO_PRICESHEET_ALERT.name()));
            return selectAlert;
        }
    }

    private class RandomUserActions implements Predicate<CustomerDetails> {
        @Override
        public boolean test(CustomerDetails customer) {
            Map<String, String> options = new HashMap<>();
            Map reply = executeJavascriptMethod("TrGetRandomUser", options);
            String status = ((String) reply.get("status"));
            boolean success = StringUtils.equals("PASSED", status);
            if (success) {
                Map userData = (Map) reply.get("user");
                RandomUser randomUser = randomUser(userData);
                customer.setLastName(randomUser.getName().getLast());
                customer.setFirstName(randomUser.getName().getFirst());
                success = fillInCustomerDetails(randomUser);
                parameterProvider.put("suitecrm-customer", randomUser);
            }
            return success;
        }

        private boolean fillInCustomerDetails(RandomUser randomUser) {
            PersonalDetailsPage customerDetailsView = new PersonalDetailsPage();
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
            PersonalDetailsAddressPage customerAddressView = new PersonalDetailsAddressPage();
            customerAddressView.setCustometAddress(customerAddress);
            return customerAddressView.fillInCustomerAddress();
        }
    }

    @And("^Quote details are confirmed$")
    public void confirmQuoteDetails() throws Throwable {
        QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
        quoteDetailsPage.next();
    }

    @And("^Customer is random$")
    public void checkAndGetRandomUser() throws Throwable {
        boolean success = generateRandomUser();
        assertThat("Random customer data was not fetched.", success,
            is(true));
    }

    @And("^Customer is duplicated$")
    public void duplicateRandomUser() throws Throwable {
        RandomUser randomUser = (RandomUser) parameterProvider.getValueOrParameter("parameter:suitecrm-customer");
        boolean success = new RandomUserActions().fillInCustomerDetails(randomUser);
        assertThat("Random customer data was not fetched.", success,
            is(true));
    }

    private boolean generateRandomUser() {
        CustomerDetails customer = new CustomerDetails();
        boolean success = new RandomUserActions().test(customer);
        parameterProvider.put("suitecrm-customer-name", customer.getFirstName() + " " + customer.getLastName());
        return success;
    }

    @And("^Customer address is$")
    public void initCustomerAddress(final DataTable address) throws Throwable {
        List<CustomerAddress> list = address.asList(CustomerAddress.class);
        CustomerAddress customerAddress = list.get(0);
        boolean success = new InitialiseCustomerAddress().test(customerAddress);
        assertThat("Customer Address data wasn't initialised.", success, is(true));
    }

    @And("^Customer details are confirmed$")
    public void confirmCustomerDetails() throws Throwable {
        GuidedStep quoteDetailsPage = new PersonalDetailsAddressPage();
        quoteDetailsPage.next();
    }

    @And("^Package is \"([^\"]*)\"$")
    public void
    selectPackage(String packaqe) throws Throwable {
        TariffTable tariff = new TariffTable();
        tariff.setPackageName(packaqe);
        PackageAndFuelTypeSelectionPage selectPackageAndFuelTypeView = new PackageAndFuelTypeSelectionPage();
        selectPackageAndFuelTypeView.setTariffData(tariff);
        boolean success = selectPackageAndFuelTypeView.fillInFormData();
        assertThat(String.format("Failure when selecting the package %s.", packaqe),
            success,
            is(true));
    }



    @And("^Package and Fuel Type is confirmed$")
    public void confirmPackageAndFuelType() throws Throwable {
        PackageAndFuelTypeSelectionPage selectPackageAndFuelTypeView = new PackageAndFuelTypeSelectionPage();
        selectPackageAndFuelTypeView.next();
    }

    @And("^Price sheet alert doesn't pop up$")
    public void verifySelectTariffSheetAndPackage() throws Throwable {
        assertThat("Failure. Tariff sheet alerts were generated although they were not expected.", true,
            is(new VerifyTariffSheetPriceAlert().test(this)));
    }

    @And("^Electricity and gas meter numbers and their EANs are:$")
    public void selectMeterIdAndEan(final DataTable connectionTable) throws Throwable {
        List<ConnectionDetails> list = connectionTable.asList(ConnectionDetails.class);
        ConnectionDetails electricityConnectionDetails = list.get(0);
        ConnectionDetails gasConnectionDetails = list.get(1);

        ConnectionDetailsPage connectionDetailsView = new ConnectionDetailsPage();
        connectionDetailsView.setElectroConnectionDetails(electricityConnectionDetails);
        connectionDetailsView.setGasConnectionDetails(gasConnectionDetails);
        connectionDetailsView.fillInFormData();
    }

    @And("^([^\"]*) meter is ([^\"]*)$")
    public void setMeterState(final ProductType productType, final SwitchState meterState) throws Throwable {
        ConnectionDetailsPage connectionDetailsView = new ConnectionDetailsPage();
        given().await()
            .ignoreExceptions()
            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
            .pollDelay(ONE_HUNDRED_MILLISECONDS)
            .atMost(new Duration(10, SECONDS)).until(connectionDetailsView::isNextButtonEnabled);
        connectionDetailsView.toggleMeter(productType, meterState);
    }

    @And("^Switch type is Move in")
    public void setMoveIn() throws Throwable {
        ConnectionDetailsPage connectionDetailsView = new ConnectionDetailsPage();
        given().await()
            .ignoreExceptions()
            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
            .pollDelay(ONE_HUNDRED_MILLISECONDS)
            .atMost(new Duration(10, SECONDS)).until(connectionDetailsView::isNextButtonEnabled);
        connectionDetailsView.toggleMeter(ProductType.Electricity, SwitchState.Closed);
    }

    @And("^([^\"]*) market mock test is ([^\"]*)$")
    public void setMarketMockTest(final ProductType productType, final SwitchState state) throws Throwable {
        ConnectionDetailsPage connectionDetailsView = new ConnectionDetailsPage();
        given().await()
            .ignoreExceptions()
            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
            .pollDelay(ONE_HUNDRED_MILLISECONDS)
            .atMost(new Duration(10, SECONDS)).until(connectionDetailsView::isNextButtonEnabled);
        connectionDetailsView.toggleMarketMockTest(productType, state);
    }

    @And("^Connection details are confirmed$")
    public void confirmConnection() throws Throwable {
        ConnectionDetailsPage connectionDetailsView = new ConnectionDetailsPage();
        connectionDetailsView.next();
    }

    @And("^Payment details are: method \"([^\"]*)\", IBAN \"([^\"]*)\", bic \"([^\"]*)\"$")
    public void selectPaymentMethod(String paymetnMethod, String iban, String bic) {
        BillingInformation billingInfo = new BillingInformation(paymetnMethod, iban, bic);
        BillingDetailsPage billingDetailsView = new BillingDetailsPage();
        billingDetailsView.setBillingInformation(billingInfo);
        billingDetailsView.fillInFormData();
    }

    @And("^Payment details are: method \"([^\"]*)\", random IBAN, bic \"([^\"]*)\"$")
    public void selectPaymentMethod(String paymentMethod, String bic) {
        String iban = PrepareDataForContract.getValidIbanBE();
        selectPaymentMethod(paymentMethod, iban, bic);
        parameterProvider.put("iban", iban);
    }

    @And("^IBAN is generated$")
    public void generateIban() {
        String iban = PrepareDataForContract.getValidIbanBE();
        parameterProvider.put("iban", iban);
    }


    @And("^Billing details are confirmed$")
    public void confirmBillingDetaile() throws Throwable {
        BillingDetailsPage billingDetailsPage = new BillingDetailsPage();
        billingDetailsPage.next();
    }

    @And("^Quote is signed in \"([^\"]*)\"$")
    public void submitSignedQuote(String location) throws Throwable {
        String path = ResourceUtil.toPath("/data/dwp/customer-signature.pdf");
        File document = new File(path);
        assertThat("File at path " + document.getAbsolutePath() + " doesn't exist.", true,
            is(document.exists()));
        SignatureData signature = new SignatureData(
            DwpDateFormats.DWP_TODAY,
            location,
            path);
        Sleeper.sleepTightInSeconds(10);
        QuoteOverviewPage quoteOverviewView = new QuoteOverviewPage();
        quoteOverviewView.setSignatureData(signature);
        boolean success = quoteOverviewView.fillInFormData();
        assertThat("Failure when signing up the quote.", success, is(true));
    }

    @And("^Quote is signed$")
    public void submitQuote() throws Throwable {
        String path = ResourceUtil.toPath("/data/dwp/customer-signature.pdf");
        File document = new File(path);
        assertThat("File at path " + document.getAbsolutePath() + " doesn't exist.", true,
            is(document.exists()));
        SignatureData signature = new SignatureData(
            DwpDateFormats.DWP_TODAY,
            path);
        QuoteOverviewPage quoteOverviewView = new QuoteOverviewPage();
        quoteOverviewView.setSignatureData(signature);
        boolean success = quoteOverviewView.fillInFormData();
        assertThat("Failure when signing up the quote.", success, is(true));
    }

    @And("^Quote for account is signed$")
    public void submitQuoteForAccount() throws Throwable {
        String path = ResourceUtil.toPath("/data/dwp/customer-signature.pdf");
        File document = new File(path);
        assertThat("File at path " + document.getAbsolutePath() + " doesn't exist.", true,
            is(document.exists()));
        SignatureData signature = new SignatureData(
            DwpDateFormats.DWP_TODAY,
            path);
        QuoteForAccountOverviewPage quoteOverviewView = new QuoteForAccountOverviewPage();
        quoteOverviewView.setSignatureData(signature);
        boolean success = quoteOverviewView.fillInFormData();
        assertThat("Failure when signing up the quote.", success, is(true));
    }


    @And("^Quote is confirmed$")
    public void confirmQuote() throws Throwable {
        Sleeper.sleepTightInSeconds(5);
        QuoteOverviewPage quoteOverviewView = new QuoteOverviewPage();
        quoteOverviewView.next();
    }
    @And("^Quote for account is confirmed$")
    public void confirmQuoteForAccount() throws Throwable {
       confirmQuote();
    }

    @When("^I select the \"([^\"]*)\" element and click the link in the \"([^\"]*)\" column$")
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

    @And("^EAN code is generated$")
    public void generateEan() throws Throwable {
        String eanCode = PrepareDataForContract.generateEAN();
        parameterProvider.put("EAN-code-generated", eanCode);
        logger().info(" - Generated EAN code: " + eanCode);
    }

    @And("Gas EAN-code input in the \"([^\"]*)\" card is \"([^\"]*)\"$")
    public void setInput(String card, String value) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        String inputValue = parameterProvider.getValueOrParameterAsString(value);
        WebElement gasEAN = seleniumDriver.findElement(By.xpath("//h2[contains(text(),'"+card+"')]/parent::div/parent::div/div[@class='form__group']//label[contains(text(),'EAN-code')]/parent::div//input"));
        boolean gasEANWasFound = gasEAN != null;
        gasEAN.sendKeys(inputValue);
        assertThat("Gas EAN-code element was not found.", gasEANWasFound, is(true));
    }

    @And("^Electricity EAN code is \"([^\"]*)\"$")
    public void
    electricityEANCodeIs(String ean) throws Throwable {
        ConnectionDetails electricityConnectionDetails = new ConnectionDetails();
        switch (ean) {
            case "selected":
                selectEanCode();
                return;
            case "random":
                String generatedEan = PrepareDataForContract.generateEAN();
                electricityConnectionDetails.setEan(generatedEan);
                parameterProvider.put("EAN-code-generated", generatedEan);
                break;
            default:
                electricityConnectionDetails.setEan(ean);
        }
        ConnectionDetailsPage page = new ConnectionDetailsPage();
        Sleeper.sleepTightInSeconds(5);
        page.setElectroConnectionDetails(electricityConnectionDetails);
        boolean success = page.fillInElectricityEanCode();
        assertThat("Electricity EAN code filling in failure", success, is(true));
    }

    @And("^Electricity EAN code is put as output parameter \"?([^\"]*)\"?$")
    public void putElectricityEanCode(String parameter) throws Throwable {
        ConnectionDetailsPage page = new ConnectionDetailsPage();
        String eanCode = page.getEan();
        assertThat(StringUtils.isNotEmpty(eanCode), is(true));
        parameterProvider.put(parameter, eanCode);
    }

    @And("^EAN-code autocomplete value from the \"([^\"]*)\" row is checked$")
    public void selectEanCodeFromAutoComplete(String ordinal) throws Throwable {
        Integer rowIndex = Integer.parseInt(ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", ""));
        WebElement eanElement = seleniumDriver.findElementOrNull(By.xpath("//input-form-element//autocomplete//ul//li[" + rowIndex + "]/a/b"));

        String ean = eanElement.getAttribute("textContent").split(" ")[0];
        boolean eanWasFound = StringUtils.isNotBlank(ean);
        assertThat(String.format("EAN code '%s' was not found.", ean), eanWasFound, is(true));
        parameterProvider.put("EAN-code", ean);
    }

    @And("^Value at \"([^\"]*)\" in the card \"([^\"]*)\" is \"([^\"]*)\"$")
    public void checkValueInCard(String label, String cardName, String value) {
        BaseObjectPage baseObject = new BaseObjectPage();
        WebElement cardTextXPath = baseObject.cardTextXPathValue(cardName, label);
        String card = cardTextXPath.getText();

        boolean result = card.matches(value);

        assertThat("The value you entered differs from the real value", result, is(true));
    }

    @Override
    @After("@DWP, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
