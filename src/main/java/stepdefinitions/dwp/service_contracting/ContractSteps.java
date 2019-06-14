package stepdefinitions.dwp.service_contracting;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.guided_flow.move_in.MoveInPage;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpDashboardMenuPage;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPricesPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.details.DetailsPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.service.ServicePage;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.restassured.create_contract.helper.PrepareDataForContract;
import io.cucumber.datatable.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.List;

public class ContractSteps extends DwpScenario {
    private String amount;

    @Before("@DWP or @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Change amount for a customer$")
    public void changeAmountForACustomer(){
        ContractPage cp = new ContractPage();
        cp.openFirstContractFromList();
    }

    @And("^Contract plus and \"([^\"]*)\"$")
    public void contractPlusAnd(String subaction) {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage();
        cp.contractPlus();
        BaseObjectPage baseObject = new BaseObjectPage();
        baseObject.plusSubaction(subaction);
    }

    @And("^Amount values is \"([^\"]*)\"$")
    public void amountValuesIs(String value) {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage();
        cp.changeAmount(value);
        amount = value;
    }

    @Then("^Amount of a customer value$")
    public void amountOfACustomerValue() {
        ContractPage cp = new ContractPage();
        Assert.assertTrue("Amount is not correct.", cp.getAmountOfACustomer(amount));
    }

    @Override
    @After("@DWP or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @When("^Old contract data is copied$")
    public void oldContractDataIsCopied() {
        ContractPage cp = new ContractPage();
        ContractPricesPage cpp = new ContractPricesPage();
        parameterProvider.put("randomEAN", PrepareDataForContract.generateEAN());
        parameterProvider.put("oldContractEan",cp.getEanFromContract());
        parameterProvider.put("contractStatus", cp.getStatusFromContract());
        parameterProvider.put("startDate",cp.getActiveContractStartDate());
        parameterProvider.put("endDate",cp.getActiveContractEndDate());
        parameterProvider.put("contractType",cp.getContractType());
        cp.clickOnContractenNummer();
        parameterProvider.put("productName",cp.getProductName());
        parameterProvider.put("kortingsCode",cp.getKortingenOpContractKortingscode());
        parameterProvider.put("productType",cp.getKortingenOpContractProducttype());
        cpp.clickOnBekijkPrijzenTariefkaatFromPlus();
        parameterProvider.put("typeProduct",cpp.getTypeProduct());
        parameterProvider.put("energieprijsEnkelvoudigInclBtw",cpp.getEnergieprijsEnkelvoudigInclBtw());
        parameterProvider.put("energieprijsDagInclBtw",cpp.getEnergieprijsDagInclBtw());
        parameterProvider.put("energieprijsNachtInclBtw",cpp.getEnergieprijsNachtInclBtw());
        parameterProvider.put("energieprijsExclusiefNachtInclBtw",cpp.getEnergieprijsExclusiefNachtInclBtw());
        parameterProvider.put("vasteVergoedingInclBtw",cpp.getVasteVergoedingInclBtw());
        parameterProvider.put("energieprijsEnkelvoudigExclBtw",cpp.getEnergieprijsEnkelvoudigExclBtw());
        parameterProvider.put("energieprijsDagExclBtw",cpp.getEnergieprijsDagExclBtw());
        parameterProvider.put("energieprijsNachExclBtw",cpp.getEnergieprijsNachExclBtw());
        parameterProvider.put("energieprijsExclusiefNachtExclBtw",cpp.getEnergieprijsExclusiefNachtExclBtw());
        parameterProvider.put("vasteVergoedingExclBtw",cpp.getVasteVergoedingExclBtw());
        cpp.closeBekijkPrijsDetailsTK1();
    }

    @And("^Check if start date of new ean is the same date as filled in as “Move date”-\"([^\"]*)\"$")
    public void checkIfStartDateOfNewEanIsTheSameDateAsFilledInAsMoveDate(String date) {
        ContractPage cp = new ContractPage();
        String moveDate= toDwpEndDate(parameterProvider.getValueOrParameterAsString(date));
        Assert.assertEquals("Actual Contract Start Date differs from expected",cp.getActiveContractStartDate(),moveDate);
    }

    @And("^Check if the end date of new ean is the same date as the end date of the old one$")
    public void checkIfTheEndDateOfNewEanIsTheSameDateAsTheEndDateOfTheOldOne() {
        ContractPage cp = new ContractPage();
        Assert.assertEquals("Actual Contract End Date differs from expected",cp.getActiveContractEndDate(),parameterProvider.getValueOrParameterAsString("parameter:endDate"));
    }

    @And("^Check if products of both contracts are the same$")
    public void checkIfProductsOfBothContractsAreTheSame() {
        ContractPage cp = new ContractPage();
        cp.clickOnContractenNummer();
        Assert.assertEquals("Actual Product Name differs from expected",cp.getProductName(),parameterProvider.getValueOrParameterAsString("parameter:productName"));
    }

    @And("^Check if discounts of both contracts are the same$")
    public void checkIfDiscountsOfBothContractsAreTheSame() {
        ContractPage cp = new ContractPage();
        Assert.assertEquals("Actual Kortingen Op Contract Kortings code differs from expected",cp.getKortingenOpContractKortingscode(),parameterProvider.getValueOrParameterAsString("parameter:kortingsCode"));
        Assert.assertEquals("Actual Kortingen Op Contract Product type differs from expected",cp.getKortingenOpContractProducttype(),parameterProvider.getValueOrParameterAsString("parameter:productType"));
    }

    @And("^Check if prices of both contracts are the same$")
    public void checkIfPricesOfBothContractsAreTheSame() {
        ContractPricesPage cpp = new ContractPricesPage();
        cpp.clickOnBekijkPrijzenTariefkaatFromPlus();
        Assert.assertEquals("Actual Type Product differs from expected",cpp.getTypeProduct(),parameterProvider.getValueOrParameterAsString("parameter:typeProduct"));
        Assert.assertEquals("Actual Energieprijs Enkelvoudig Incl Btw differs from expected",cpp.getEnergieprijsEnkelvoudigInclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsEnkelvoudigInclBtw"));
        Assert.assertEquals("Actual Energieprijs Dag Incl Btw Incl Btw differs from expected",cpp.getEnergieprijsDagInclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsDagInclBtw"));
        Assert.assertEquals("Actual Energieprijs Nacht Incl Btw differs from expected",cpp.getEnergieprijsNachtInclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsNachtInclBtw"));
        Assert.assertEquals("Actual Energieprijs Exclusief Nacht Incl Btw differs from expected",cpp.getEnergieprijsExclusiefNachtInclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsExclusiefNachtInclBtw"));
        Assert.assertEquals("Actual Vaste Vergoeding Incl Btw differs from expected",cpp.getVasteVergoedingInclBtw(),parameterProvider.getValueOrParameterAsString("parameter:vasteVergoedingInclBtw"));
        Assert.assertEquals("Actual Energieprijs Enkelvoudig Excl Btw differs from expected",cpp.getEnergieprijsEnkelvoudigExclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsEnkelvoudigExclBtw"));
        Assert.assertEquals("Actual Energieprijs Dag Excl Btw from expected",cpp.getEnergieprijsDagExclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsDagExclBtw"));
        Assert.assertEquals("Actual Energieprijs Nach Excl Btw differs from expected",cpp.getEnergieprijsNachExclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsNachExclBtw"));
        Assert.assertEquals("Actual Energieprijs Exclusief Nacht Excl Btw differs from expected",cpp.getEnergieprijsExclusiefNachtExclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsExclusiefNachtExclBtw"));
        Assert.assertEquals("Actual Vaste Vergoeding Excl Btw differs from expected",cpp.getVasteVergoedingExclBtw(),parameterProvider.getValueOrParameterAsString("parameter:vasteVergoedingExclBtw"));
        cpp.closeBekijkPrijsDetailsTK1();
    }

    @And("^New move customer address is$")
    public void newMoveCustomerAddressIs(final DataTable dbTable) {
        List<List<String>> address = dbTable.asLists();
        MoveInPage mip = new MoveInPage();
        mip.setNewMoveAddress(address.get(1).get(0),address.get(1).get(1),address.get(1).get(4),address.get(1).get(5));
    }

    @Then("^There is a case where onderwerp is \"([^\"]*)\"$")
    public void thereIsACaseWhereOnderwerpIs(String onderwerp) {
        ServicePage sp = new ServicePage();
        Assert.assertEquals(onderwerp, sp.getCaseOnderwerp());
        parameterProvider.put("caseNumber",sp.getCaseNumber());
    }

    @And("^Interaction is created with Type \"([^\"]*)\" and Onderwerp \"([^\"]*)\" and verwante case is \"([^\"]*)\"$")
    public void interactionIsCreatedWithTypeAndOnderwerpAndVerwanteCaseIs(String type, String onderwerp, String number) {
        ServicePage sp = new ServicePage();
        String caseNumber = parameterProvider.getValueOrParameterAsString(number);
        Assert.assertEquals("Actual Interaction Type differs from expected", type, sp.getInteractionType(type));
        Assert.assertEquals("Actual Interaction Onderwerp differs from expected", onderwerp, sp.getInteractionOnderwerp(type));
        Assert.assertEquals("Actual Interaction Verwante Case differs from expected", caseNumber, sp.getInteractionVerwanteCase());
    }

    @And("^Kortingen is \"([^\"]*)\"$")
    public void kortingenIs(String kortingen) {
        seleniumDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage();
        cp.chooseDiscounts(kortingen);
        Sleeper.sleepTightInSeconds(0.5);
        seleniumDriver.waitForRequestsToFinish();

    }

    @And("^Wait for the first contract to be activated$")
    public void waitForTheFirstContractToBeActivated() {
        DwpDashboardMenuPage ddmp = new  DwpDashboardMenuPage();
        ContractPage cp = new ContractPage();

        while (!cp.getStatusFromContract().equalsIgnoreCase("Actief")) {
            Sleeper.sleepTightInSeconds(10);
            ddmp.clickOnDashboardElement("Sales");
            ddmp.clickOnDashboardElement("Contracten");

        }
        Assert.assertTrue(cp.getStatusFromContract().equalsIgnoreCase("Actief"));
    }

    @And("^Two contracts are displayed$")
    public void twoContractsAreDisplayed() {
        ContractPage cp = new ContractPage();
        Assert.assertEquals("The number of electricity contracts is not 2", 2, cp.getNumberOfElectricityContracts());
    }

    @And("^There is one billing customer$")
    public void thereIsOneBillingCustomer() {
        DetailsPage dp = new DetailsPage();
        Assert.assertEquals("The number of billing customers is not 1", 1, dp.getNumberOfBillingCustomers());
    }

    @Then("^Check customer information$")
    public void checkProspectCustomerInformation(final DataTable dbTable) {
        List<List<String>> info = dbTable.asLists();
        DetailsPage dp = new DetailsPage();
        String address = dp.getAddress();
        Assert.assertEquals("Actual address differs from expected",address.replaceAll("\n"," "),info.get(1).get(0));
        if (info.get(1).get(1)!= null) {
            Assert.assertEquals("Actual phone differs from expected", dp.getPhone(), info.get(1).get(1));
            Assert.assertEquals("Actual email differs from expected", dp.getEmail(), info.get(1).get(2));
        }
    }

    @Then("^Check if customer name contains \"([^\"]*)\"$")
    public void checkIfCustomerNameContains(String name) {
        DetailsPage dp = new DetailsPage();
        String customerName = dp.getCustomerName();
        Assert.assertTrue("Actual customer name does not contain GLN",customerName.contains(name));
    }

    @Then("^Check contract$")
    public void checkContract(final DataTable dbTable) {
        ContractPage cp = new ContractPage();
        List<List<String>> info = dbTable.asLists();
        String type = info.get(1).get(0);
        String status = info.get(1).get(1);
        String date = toDwpEndDate(parameterProvider.getValueOrParameterAsString(info.get(1).get(2)));
        String ean = parameterProvider.getValueOrParameterAsString(info.get(1).get(3));
        String product = parameterProvider.getValueOrParameterAsString(info.get(1).get(4));

        Assert.assertEquals("Actual type differs from expected",cp.getContractType() ,type);
        Assert.assertEquals("Actual status differs from expected", cp.getStatusFromContracten() , status);
        Assert.assertEquals("Actual start date differs from expected", cp.getStartDate() , date);
        Assert.assertEquals("Actual EAN differs from expected",cp.getEanFromContract() , ean);
        Assert.assertEquals("Actual product differs from expected",cp.getProductFromContracten() , product);
    }

    @And("^Copy product name$")
    public void copyProductName() {
        ContractPage cp = new ContractPage();
        parameterProvider.put("product",cp.getProductFromContracten());
    }
}
