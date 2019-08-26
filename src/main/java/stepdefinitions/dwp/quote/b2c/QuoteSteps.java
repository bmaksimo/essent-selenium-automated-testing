package stepdefinitions.dwp.quote.b2c;

import com.billinghouse.random.RandomUser;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.modal.quote.SimilarAccountDialogImpl;
import com.essent.testing.dwp.pageobject.impl.quote.*;
import com.essent.testing.dwp.pageobject.impl.quote_for_account.OnlineQuoteSignatureModalPage;
import com.essent.testing.dwp.pageobject.impl.quote_for_account.QuoteForAccountOverviewPage;
import com.essent.testing.dwp.pageobject.modal.quote.SimilarAccountDialog;
import com.essent.testing.dwp.pageobject.quote.GuidedStep;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.restassured.create_contract.helper.PrepareDataForContract;
import com.essent.testing.util.resource.ResourceUtil;
import io.cucumber.datatable.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
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

import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.*;
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

    private static final String ROW_INDEX_XPATH = "//input-form-element//autocomplete//ul//li[${rowIndex}]/a/b";

    @Before("@DWP or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }



    @When("^B2C sales channel is \"([^\"]*)\"$")
    public void initSalesChannel(SalesChannel salesChannel){
        QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
        Sleeper.sleepTightInSeconds(5);
        quoteDetailsPage.setSalesChannel(salesChannel);
        boolean formInitialized = quoteDetailsPage.fillInFormData();
        assertThat("Failure occurred when filling in input values", formInitialized, is(true));
    }

    @And("^Deduplication dialogue \"([^\"]*)\" is shown$")
    public void deduplicationDialogueIsShown(String title){
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
      Map reply = executeJavascriptMethod(JS_TR_GET_RANDOM_USER, options);
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

    @And("^Quote details are confirmed$")
    public void confirmQuoteDetails(){
        QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
        quoteDetailsPage.next();
    }

    @And("^Customer is random$")
    public void checkAndGetRandomUser(){
        boolean success = generateRandomUser();
        assertThat("Random customer data was not fetched.", success,
            is(true));
    }

    @And("^Customer is duplicated$")
    public void duplicateRandomUser(){
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
    public void initCustomerAddress(final DataTable address){
        seleniumDriver.waitForRequestsToFinish();
        PersonalDetailsAddressPage pdap = new PersonalDetailsAddressPage();
        List<Map<String,String>> add = address.asMaps(String.class, String.class);
        pdap.fillInCustomerAddressx(add);
    }

    @And("^Customer details are confirmed$")
    public void confirmCustomerDetails(){
        seleniumDriver.waitForRequestsToFinish();
        GuidedStep quoteDetailsPage = new PersonalDetailsAddressPage();
        quoteDetailsPage.next();
    }

    @And("^Pricing details are confirmed$")
    public void confirmPricingDetails() throws Throwable {
        new QuoteDetailsPage().next();
    }

    @And("^Package is \"([^\"]*)\"$")
    public void
    selectPackage(String packaqe){
        seleniumDriver.waitForRequestsToFinish();
        TariffTable tariff = new TariffTable();
        tariff.setPackageName(packaqe);
        PackageAndFuelTypeSelectionPage selectPackageAndFuelTypeView = new PackageAndFuelTypeSelectionPage();
        selectPackageAndFuelTypeView.setTariffData(tariff);
        boolean success = selectPackageAndFuelTypeView.fillInFormData();
        assertThat(String.format("Failure when selecting the package %s.", packaqe),
            success,
            is(true));
        seleniumDriver.waitForRequestsToFinish();
    }



    @And("^Package and Fuel Type is confirmed$")
    public void confirmPackageAndFuelType(){
        seleniumDriver.waitForRequestsToFinish();
        PackageAndFuelTypeSelectionPage selectPackageAndFuelTypeView = new PackageAndFuelTypeSelectionPage();
        selectPackageAndFuelTypeView.next();
    }

    @And("^Price sheet alert doesn't pop up$")
    public void verifySelectTariffSheetAndPackage(){
        assertThat("Failure. Tariff sheet alerts were generated although they were not expected.", true,
            is(new VerifyTariffSheetPriceAlert().test(this)));
    }

    @And("^Electricity and gas meter numbers and their EANs are:$")
    public void selectMeterIdAndEan(final DataTable connectionTable){
        List<ConnectionDetails> list = connectionTable.asList(ConnectionDetails.class);
        ConnectionDetails electricityConnectionDetails = list.get(0);
        ConnectionDetails gasConnectionDetails = list.get(1);

        ConnectionDetailsPage connectionDetailsView = new ConnectionDetailsPage();
        connectionDetailsView.setElectricityConnectionDetails(electricityConnectionDetails);
        connectionDetailsView.setGasConnectionDetails(gasConnectionDetails);
        connectionDetailsView.fillInFormData();
    }

    @And("^([^\"]*) meter is ([^\"]*)$")
    public void setMeterState(final ProductType productType, final SwitchState meterState){
        ConnectionDetailsPage connectionDetailsView = new ConnectionDetailsPage();
        given().await()
            .ignoreExceptions()
            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
            .pollDelay(ONE_HUNDRED_MILLISECONDS)
            .atMost(new Duration(10, SECONDS)).until(connectionDetailsView::isNextButtonEnabled);
        connectionDetailsView.toggleMeter(productType, meterState);
    }

    @And("^Switch type is Move in$")
    public void setMoveIn(){
        ConnectionDetailsPage connectionDetailsView = new ConnectionDetailsPage();
        given().await()
            .ignoreExceptions()
            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
            .pollDelay(ONE_HUNDRED_MILLISECONDS)
            .atMost(new Duration(10, SECONDS)).until(connectionDetailsView::isNextButtonEnabled);
        connectionDetailsView.toggleMeter(ProductType.Electricity, SwitchState.Closed);
    }

    @And("^([^\"]*) market mock test is ([^\"]*)$")
    public void setMarketMockTest(final ProductType productType, final SwitchState state){
        seleniumDriver.waitForRequestsToFinish();
        ConnectionDetailsPage connectionDetailsView = new ConnectionDetailsPage();
        given().await()
            .ignoreExceptions()
            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
            .pollDelay(ONE_HUNDRED_MILLISECONDS)
            .atMost(new Duration(60, SECONDS)).until(connectionDetailsView::isNextButtonEnabled);
        connectionDetailsView.toggleMarketMockTest(productType, state);
        seleniumDriver.waitForRequestsToFinish();
    }

    @And("^Connection details are confirmed$")
    public void confirmConnection(){
        seleniumDriver.waitForRequestsToFinish();
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

    @And("^Prepaid advance amounts are collected as numbers$")
    public void collectAdvanceAmountsAsNumbers(final DataTable cardsInfo){
        seleniumDriver.waitForRequestsToFinish();

        List<Map<String,String>> fieldDescriptors = cardsInfo.asMaps(String.class, String.class);

        FieldDescriptor electricityAdvAmountField = new FieldDescriptor();
        electricityAdvAmountField.setCardName(fieldDescriptors.get(0).get("cardName"));
        electricityAdvAmountField.setFieldName(fieldDescriptors.get(0).get("fieldName"));
        electricityAdvAmountField.setParameterName(fieldDescriptors.get(0).get("parameterName"));

        FieldDescriptor gasAdvDescriptor = new FieldDescriptor();
        gasAdvDescriptor.setCardName(fieldDescriptors.get(1).get("cardName"));
        gasAdvDescriptor.setFieldName(fieldDescriptors.get(1).get("fieldName"));
        gasAdvDescriptor.setParameterName(fieldDescriptors.get(1).get("parameterName"));

        BillingDetailsPage billingDetailsPage = new BillingDetailsPage();
        parameterProvider.put(electricityAdvAmountField.getParameterName(), billingDetailsPage.getElectricityAdvancedPaymentAmount(electricityAdvAmountField));
        parameterProvider.put(gasAdvDescriptor.getParameterName(), billingDetailsPage.getGasAdvancedPaymentAmount(gasAdvDescriptor));
    }


    @And("^Billing details are confirmed$")
    public void confirmBillingDetaile(){
        BillingDetailsPage billingDetailsPage = new BillingDetailsPage();
        billingDetailsPage.next();
    }

    @And("^Quote is signed in \"([^\"]*)\"$")
    public void submitSignedQuote(String location){
        seleniumDriver.waitForRequestsToFinish();
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
        seleniumDriver.waitForRequestsToFinish();
    }


    @And("^Quote is signed$")
    public void submitQuote(){
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
    public void submitQuoteForAccount(){
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

    @And("Quote for account is signed online in modal")
    public void onlineSignQuote() throws Throwable {
        String path = ResourceUtil.toPath("/data/dwp/customer-signature.pdf");
        File document = new File(path);
        assertThat("File at path " + document.getAbsolutePath() + " doesn't exist.", true, is(document.exists()));
        SignatureData signature = new SignatureData(DwpDateFormats.DWP_TODAY, path);
        OnlineQuoteSignatureModalPage onlineQuoteSignatureModalPage = new OnlineQuoteSignatureModalPage();
        onlineQuoteSignatureModalPage.setSignatureData(signature);
        boolean success = onlineQuoteSignatureModalPage.fillInFormData();
        assertThat("Failure when signing up the quote.", success, is(true));
    }


    @And("^Quote is confirmed$")
    public void confirmQuote(){
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(5);
        QuoteOverviewPage quoteOverviewView = new QuoteOverviewPage();
        quoteOverviewView.next();
        seleniumDriver.waitForRequestsToFinish();
    }

    @And("^Electricity EAN code is selected$")
    public void selectEanCode(){
        Map<String, String> options = new HashMap<>();
        boolean success = executeJavascriptTest("TrSelectEanCode", options);
        assertThat(success, is(true));
    }

    @And("^EAN code is generated$")
    public void generateEan(){
        String eanCode = PrepareDataForContract.generateEAN();
        parameterProvider.put("EAN-code-generated", eanCode);
        logger().debug(" - Generated EAN code: " + eanCode);
    }

    @And("^Electricity EAN code is \"([^\"]*)\"$")
    public void electricityEANCodeIs(String ean){
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
        page.setElectricityConnectionDetails(electricityConnectionDetails);
        boolean success = page.fillInElectricityEanCode();
        assertThat("Electricity EAN code filling in failure", success, is(true));
    }

    @And("^Electricity EAN code is put as output parameter \"?([^\"]*)\"?$")
    public void putElectricityEanCode(String parameter){
        ConnectionDetailsPage page = new ConnectionDetailsPage();
        String eanCode = page.getEan();
        assertThat(StringUtils.isNotEmpty(eanCode), is(true));
        parameterProvider.put(parameter, eanCode);
    }

    @And("^EAN-code autocomplete value from the \"([^\"]*)\" row is checked$")
    public void selectEanCodeFromAutoComplete(String ordinal){
        int rowIndex = extractNumericValue(ordinal);
        String locator = createQuery(ROW_INDEX_XPATH, "rowIndex", String.valueOf(rowIndex));
        WebElement eanElement = seleniumDriver.findElementWhenPresent(By.xpath(locator));
        String ean = eanElement.getAttribute("textContent").split(" ")[0];
        boolean eanWasFound = StringUtils.isNotBlank(ean);
        assertThat(String.format("EAN code '%s' was not found.", ean), eanWasFound, is(true));
        parameterProvider.put("EAN-code", ean);
    }

    @Override
    @After("@DWP or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
