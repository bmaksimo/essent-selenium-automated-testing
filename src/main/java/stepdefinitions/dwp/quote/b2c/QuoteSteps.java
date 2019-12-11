package stepdefinitions.dwp.quote.b2c;

import com.billinghouse.testautomation.util.random.CustomerRandomDataGenerator;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.guidedflow.cupq.NewQuotePage;
import com.essent.testing.dwp.pageobject.impl.modal.quote.SimilarAccountDialogImpl;
import com.essent.testing.dwp.pageobject.impl.quote.*;
import com.essent.testing.dwp.pageobject.impl.quoteforaccount.OnlineQuoteSignatureModalPage;
import com.essent.testing.dwp.pageobject.impl.quoteforaccount.QuoteForAccountOverviewPage;
import com.essent.testing.dwp.pageobject.salesmarketing.customerdashboard.contracts.ContractPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.restassured.create_contract.helper.PrepareDataForContract;
import com.essent.testing.util.resource.ResourceUtil;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.autocrat.flow.FlowAwarePredicate;
import stepdefinitions.dwp.quote.DwpDateFormats;
import stepdefinitions.dwp.tables.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import static com.essent.testing.dwp.autocrat.element.quote.TariffElements.NO_PRICESHEET_ALERT;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.NEXT_STEP;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class QuoteSteps extends DwpScenario {

    private static final String ROW_INDEX_XPATH = "//input-form-element//autocomplete//ul//li[${rowIndex}]/a/b";

    @Before("@DWP or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }


    @When("^B2C sales channel is \"([^\"]*)\"$")
    public void initSalesChannel(SalesChannel salesChannel) {
        QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
        Sleeper.sleepTightInSeconds(5);
        quoteDetailsPage.setSalesChannel(salesChannel);
        boolean formInitialized = quoteDetailsPage.fillInFormData();
        assertThat("Failure occurred when filling in input values", formInitialized, is(true));
    }

    @And("^Deduplication dialogue \"([^\"]*)\" is shown$")
    public void deduplicationDialogueIsShown(String title) {
        assertThat("Similar clients dialogue was not shown.", new SimilarAccountDialogImpl(title).getTitle(), equalTo(title));
    }

    @And("^Deduplication dialogue link \"([^\"]*)\" is clicked$")
    public void deduplicationDialogueLinkIsClicked(String linkText){
        new SimilarAccountDialogImpl().clickOnLink(linkText);
    }

    @And("^Sepa signature location is \"([^\"]*)\"$")
    public void sepaSignatureLocationIs(String city){
        NewQuotePage nqp = new NewQuotePage();
        nqp.setSepaSignatureLocation(city);
    }

    @And("^Select one dealer$")
    public void selectOneDealer(){
        NewQuotePage nqp = new NewQuotePage();
        nqp.clickOnSelectOneDealer();
    }

    @And("^Search dialog is confirmed$")
    public void searchDialogIsConfirmed(){
        NewQuotePage nqp = new NewQuotePage();
        nqp.savePopUpChanges();
    }

    @And("^EAN code for gas is generated$")
    public void eanCodeForGasIsGenerated(){
        String eanCode = PrepareDataForContract.generateEAN();
        parameterProvider.put("EAN-code-gas", eanCode);
        logger().debug(" - Generated EAN code: " + eanCode);
    }

    @And("^EAN-code input is \"([^\"]*)\"$")
    public void eanCodeInputIs(String ean){
        String eanGas=parameterProvider.getValueOrParameterAsString(ean);
        NewQuotePage nqp = new NewQuotePage();
        Sleeper.sleepTightInSeconds(2);
        nqp.fillEanGassField(eanGas);
    }

    @And("^Meternummer input is \"([^\"]*)\"$")
    public void meternummerInputIs(String number){
        NewQuotePage nqp = new NewQuotePage();
        Sleeper.sleepTightInSeconds(2);
        nqp.setMeterNumberForGas(number);
    }

    @And("^Start datum is \"([^\"]*)\"$")
    public void startDatumIs(String parameter) {
        String datum = toDwpDate(parameterProvider.getValueOrParameterAsString(parameter));
        NewQuotePage nqp = new NewQuotePage();
        Sleeper.sleepTightInSeconds(2);
        nqp.setStartDateForGas(datum);

    }

    @And("^Click on \"([^\"]*)\" on create quote page$")
    public void clickOnAndGoBackTo(String tab){
        NewQuotePage nqp = new NewQuotePage();
        String number=null;
        switch (tab){
            case "Selecteer type offerte":
               number="1";
               break;
            case "Details klant":
                number="2";
                break;
            case "Selecteer pakket en producten":
                number="3";
                break;
            case "Connectiedetails":
                number="4";
                break;
            case "Facturatiedetails":
                number="5";
                break;
            case "Overzicht en ondertekenopties":
                number="6";
                break;
        }
        nqp.clickOnTab(number);
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

    @And("^Quote details are confirmed$")
    public void confirmQuoteDetails() {
        new QuoteDetailsPage().next(parameterProvider.getScenarioInfo());
    }

    @And("^Customer is random$")
    public void checkAndGetRandomUser() {
        generateRandomUser();
    }

    @And("^Customer is duplicated$")
    public void duplicateRandomUser() {
        CustomerDetails customerDetails = (CustomerDetails) parameterProvider.getValueOrParameter("parameter:suitecrm-customer");
        customerDetails.setFirstName("DUP-" + customerDetails.getFirstName());
        fillInCustomerDetails(customerDetails);
    }

    private void generateRandomUser() {
        CustomerDetails customer = new CustomerDetails();
        Map<String, String> customerName = CustomerRandomDataGenerator.createAccountName("prospect");
        String firstName = customerName.get("firstName");
        String lastName = customerName.get("lastName");
        String email = firstName + "." + lastName + "@person.com";
        customer.setFirstName(customerName.get("firstName"));
        customer.setLastName(customerName.get("lastName"));
        customer.setEmail(email);
        customer.setBirthDate(CustomerRandomDataGenerator.getDOB());
        parameterProvider.put("suitecrm-customer-name", customer.getFirstName() + " " + customer.getLastName());
        parameterProvider.put("suitecrm-customer", customer);
        fillInCustomerDetails(customer);
    }

    private boolean fillInCustomerDetails(CustomerDetails customer) {
        return new PersonalDetailsPage(customer).fillInFormData();
    }

    @And("^Customer address is$")
    public void initCustomerAddress(final DataTable address) {
        seleniumDriver.waitForRequestsToFinish();
        List<Map<String, String>> add = address.asMaps(String.class, String.class);
        new PersonalDetailsAddressPage().fillInCustomerAddressx(add);
    }

    @And("^Add customer address again if not populated first time$")
    public void secondTimeCustomerAddress(final DataTable address) {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage();
        List<Map<String, String>> add = address.asMaps(String.class, String.class);
        if (cp.checkDeliveryAddress().isEmpty()) {
            cp.populateAddressData(add);
        }
    }

    @And("^Customer details are confirmed$")
    public void confirmCustomerDetails() {
        new PersonalDetailsAddressPage().next(parameterProvider.getScenarioInfo());
    }

    @And("^Pricing details are confirmed$")
    public void confirmPricingDetails() {
        new QuoteDetailsPage().next(parameterProvider.getScenarioInfo());
    }

    @And("^Package is \"([^\"]*)\"$")
    public void selectPackage(String packaqe) {
        seleniumDriver.waitForRequestsToFinish();
        TariffTable tariff = new TariffTable();
        tariff.setPackageName(packaqe);
        PackageAndFuelTypeSelectionPage selectPackageAndFuelTypeView = new PackageAndFuelTypeSelectionPage();
        selectPackageAndFuelTypeView.setTariffData(tariff);
        boolean success = selectPackageAndFuelTypeView.fillInFormData();
        assertThat(String.format("Failure when selecting the package %s.", packaqe), success, is(true));
        seleniumDriver.waitForRequestsToFinish();
    }


    @And("^Package and Fuel Type is confirmed$")
    public void confirmPackageAndFuelType() {
        seleniumDriver.waitForRequestsToFinish();
        new PackageAndFuelTypeSelectionPage().next(parameterProvider.getScenarioInfo());
    }

    @And("^Price sheet alert doesn't pop up$")
    public void verifySelectTariffSheetAndPackage() {
        assertThat("Failure. Tariff sheet alerts were generated although they were not expected.", true,
            is(new VerifyTariffSheetPriceAlert().test(this)));
    }

    @And("^Electricity and gas meter numbers and their EANs are:$")
    public void selectMeterIdAndEan(final DataTable connectionTable) {
        List<ConnectionDetails> list = connectionTable.asList(ConnectionDetails.class);
        ConnectionDetails electricityConnectionDetails = list.get(0);
        ConnectionDetails gasConnectionDetails = list.get(1);

        ConnectionDetailsPage connectionDetailsPage = new ConnectionDetailsPage();
        connectionDetailsPage.setElectricityConnectionDetails(electricityConnectionDetails);
        connectionDetailsPage.setGasConnectionDetails(gasConnectionDetails);
        connectionDetailsPage.fillInFormData();
    }

    @And("^([^\"]*) market mock test is enabled")
    public void setMarketMockTest(final ProductType productType) {
        new ConnectionDetailsPage().toggleMarketMockTest(productType);
    }

    @And("^Connection details are confirmed$")
    public void confirmConnection() {
        new ConnectionDetailsPage().next(parameterProvider.getScenarioInfo());
    }

    @And("^Payment details are: method \"([^\"]*)\", IBAN \"([^\"]*)\", bic \"([^\"]*)\"$")
    public void selectPaymentMethod(String paymetnMethod, String iban, String bic) {
        BillingInformation billingInfo = new BillingInformation(paymetnMethod, iban, bic);
        BillingDetailsPage billingDetailsPage = new BillingDetailsPage();
        billingDetailsPage.setBillingInformation(billingInfo);
        billingDetailsPage.fillInFormData();
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
    public void collectAdvanceAmountsAsNumbers(final DataTable cardsInfo) {
        seleniumDriver.waitForRequestsToFinish();

        List<Map<String, String>> fieldDescriptors = cardsInfo.asMaps(String.class, String.class);

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

    @And("^All values at \"([^\"]*)\" are greater than zero$")
    public void checkValuesGreaterThanZero(String label) {
        Assert.assertTrue("Not all values are greater than zero", new NewQuotePage().areAllAmountsGreaterThanZero(label));

    }

    @And("^Billing details are confirmed$")
    public void confirmBillingDetails() {
        new BillingDetailsPage().next(parameterProvider.getScenarioInfo());
    }

    @And("^Quote is signed in \"([^\"]*)\"$")
    public void submitSignedQuote(String location) {
        seleniumDriver.waitForRequestsToFinish();
        String path = ResourceUtil.toPath("/data/dwp/customer-signature.pdf");
        File document = new File(path);
        assertThat("File at path " + document.getAbsolutePath() + " doesn't exist.", true, is(document.exists()));
        SignatureData signature = new SignatureData(DwpDateFormats.DWP_TODAY, location, path);
        Sleeper.sleepTightInSeconds(10);
        QuoteOverviewPage quoteOverviewView = new QuoteOverviewPage();
        quoteOverviewView.setSignatureData(signature);
        boolean success = quoteOverviewView.fillInFormData();
        assertThat("Failure when signing up the quote.", success, is(true));
        seleniumDriver.waitForRequestsToFinish();
    }


    @And("^Quote is signed$")
    public void submitQuote() {
        String path = ResourceUtil.toPath("/data/dwp/customer-signature.pdf");
        File document = new File(path);
        assertThat("File at path " + document.getAbsolutePath() + " doesn't exist.", true, is(document.exists()));
        SignatureData signature = new SignatureData(DwpDateFormats.DWP_TODAY, path);
        QuoteOverviewPage quoteOverviewView = new QuoteOverviewPage();
        quoteOverviewView.setSignatureData(signature);
        boolean success = quoteOverviewView.fillInFormData();
        assertThat("Failure when signing up the quote.", success, is(true));
    }


    @And("^Quote for account is signed$")
    public void submitQuoteForAccount() {
        String path = ResourceUtil.toPath("/data/dwp/customer-signature.pdf");
        File document = new File(path);
        assertThat("File at path " + document.getAbsolutePath() + " doesn't exist.", true, is(document.exists()));
        SignatureData signature = new SignatureData(DwpDateFormats.DWP_TODAY, path);
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
    public void confirmQuote() {
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(5);
        new QuoteOverviewPage().next(parameterProvider.getScenarioInfo());
        seleniumDriver.waitForRequestsToFinish();
    }

    @And("^EAN code is generated$")
    public void generateEan() {
        String eanCode = PrepareDataForContract.generateEAN();
        parameterProvider.put("EAN-code-generated", eanCode);
        logger().debug(" - Generated EAN code: " + eanCode);
    }

    @And("^Electricity EAN code is \"([^\"]*)\"$")
    public void electricityEANCodeIs(String ean) {
        ConnectionDetails electricityConnectionDetails = new ConnectionDetails();

        if ("random".equalsIgnoreCase(ean)) {
            String generatedEan = PrepareDataForContract.generateEAN();
            electricityConnectionDetails.setEan(generatedEan);
            parameterProvider.put("EAN-code-generated", generatedEan);
        } else electricityConnectionDetails.setEan(ean);

        ConnectionDetailsPage page = new ConnectionDetailsPage();
        Sleeper.sleepTightInSeconds(5);
        page.setElectricityConnectionDetails(electricityConnectionDetails);
        boolean success = page.fillInElectricityEanCode();
        assertThat("Electricity EAN code filling in failure", success, is(true));
    }

    @And("^Electricity EAN code is put as output parameter \"?([^\"]*)\"?$")
    public void putElectricityEanCode(String parameter) {
        ConnectionDetailsPage page = new ConnectionDetailsPage();
        String eanCode = page.getEan();
        assertThat(StringUtils.isNotEmpty(eanCode), is(true));
        parameterProvider.put(parameter, eanCode);
    }

    @And("^EAN-code autocomplete value from the \"([^\"]*)\" row is checked$")
    public void selectEanCodeFromAutoComplete(String ordinal) {
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
