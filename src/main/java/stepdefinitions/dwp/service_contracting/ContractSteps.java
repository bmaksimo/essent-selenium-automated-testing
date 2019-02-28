package stepdefinitions.dwp.service_contracting;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.dwp.pageobject.impl.page.ContractPage;
import com.essent.testing.dwp.pageobject.impl.service_contracting.ContractPageClass;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.restassured.create_contract.helper.PrepareDataForContract;
import cucumber.api.DataTable;
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

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Change amount for a customer$")
    public void changeAmountForACustomer(){
        ContractPageClass cp = new ContractPageClass();
        cp.openFirstContractFromList();
    }

    @And("^Contract plus and \"([^\"]*)\"$")
    public void contractPlusAnd(String subaction) {
        ContractPageClass cp = new ContractPageClass();
        cp.contractPlus();
        BaseObject baseObject = new BaseObject();
        baseObject.plusSubaction(subaction);
    }

    @And("^Amount values is \"([^\"]*)\"$")
    public void amountValuesIs(String value) {
        ContractPageClass cp = new ContractPageClass();
        cp.changeAmount(value);
        amount = value;
    }

    @Then("^Amount of a customer value$")
    public void amountOfACustomerValue() {
        ContractPageClass cp = new ContractPageClass();
        Assert.assertTrue("Amount is not correct.", cp.getAmountOfACustomer(amount));
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @When("^Old contract data is copied$")
    public void oldContractDataIsCopied() {
        ContractPage cp = new ContractPage();
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
        cp.clickOnBekijkPrijzenTariefkaatFromPlus();
        parameterProvider.put("typeProduct",cp.getTypeProduct());
        parameterProvider.put("energieprijsEnkelvoudigInclBtw",cp.getEnergieprijsEnkelvoudigInclBtw());
        parameterProvider.put("energieprijsDagInclBtw",cp.getEnergieprijsDagInclBtw());
        parameterProvider.put("energieprijsNachtInclBtw",cp.getEnergieprijsNachtInclBtw());
        parameterProvider.put("energieprijsExclusiefNachtInclBtw",cp.getEnergieprijsExclusiefNachtInclBtw());
        parameterProvider.put("vasteVergoedingInclBtw",cp.getVasteVergoedingInclBtw());
        parameterProvider.put("energieprijsEnkelvoudigExclBtw",cp.getEnergieprijsEnkelvoudigExclBtw());
        parameterProvider.put("energieprijsDagExclBtw",cp.getEnergieprijsDagExclBtw());
        parameterProvider.put("energieprijsNachExclBtw",cp.getEnergieprijsNachExclBtw());
        parameterProvider.put("energieprijsExclusiefNachtExclBtw",cp.getEnergieprijsExclusiefNachtExclBtw());
        parameterProvider.put("vasteVergoedingExclBtw",cp.getVasteVergoedingExclBtw());
        cp.closeBekijkPrijsDetailsTK1();
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
        ContractPage cp = new ContractPage();
        cp.clickOnBekijkPrijzenTariefkaatFromPlus();
        Assert.assertEquals("Actual Type Product differs from expected",cp.getTypeProduct(),parameterProvider.getValueOrParameterAsString("parameter:typeProduct"));
        Assert.assertEquals("Actual Energieprijs Enkelvoudig Incl Btw differs from expected",cp.getEnergieprijsEnkelvoudigInclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsEnkelvoudigInclBtw"));
        Assert.assertEquals("Actual Energieprijs Dag Incl Btw Incl Btw differs from expected",cp.getEnergieprijsDagInclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsDagInclBtw"));
        Assert.assertEquals("Actual Energieprijs Nacht Incl Btw differs from expected",cp.getEnergieprijsNachtInclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsNachtInclBtw"));
        Assert.assertEquals("Actual Energieprijs Exclusief Nacht Incl Btw differs from expected",cp.getEnergieprijsExclusiefNachtInclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsExclusiefNachtInclBtw"));
        Assert.assertEquals("Actual Vaste Vergoeding Incl Btw differs from expected",cp.getVasteVergoedingInclBtw(),parameterProvider.getValueOrParameterAsString("parameter:vasteVergoedingInclBtw"));
        Assert.assertEquals("Actual Energieprijs Enkelvoudig Excl Btw differs from expected",cp.getEnergieprijsEnkelvoudigExclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsEnkelvoudigExclBtw"));
        Assert.assertEquals("Actual Energieprijs Dag Excl Btw from expected",cp.getEnergieprijsDagExclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsDagExclBtw"));
        Assert.assertEquals("Actual Energieprijs Nach Excl Btw differs from expected",cp.getEnergieprijsNachExclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsNachExclBtw"));
        Assert.assertEquals("Actual Energieprijs Exclusief Nacht Excl Btw differs from expected",cp.getEnergieprijsExclusiefNachtExclBtw(),parameterProvider.getValueOrParameterAsString("parameter:energieprijsExclusiefNachtExclBtw"));
        Assert.assertEquals("Actual Vaste Vergoeding Excl Btw differs from expected",cp.getVasteVergoedingExclBtw(),parameterProvider.getValueOrParameterAsString("parameter:vasteVergoedingExclBtw"));
        cp.closeBekijkPrijsDetailsTK1();
    }

    @And("^New move customer address is$")
    public void nweMoveCustomerAddressIs(final DataTable dbTable) {
        List<List<String>> address = dbTable.raw();
        ContractPage cp = new ContractPage();
        cp.setNewMoveAddress(address.get(1).get(0),address.get(1).get(1),address.get(1).get(4),address.get(1).get(5));
    }

    @Then("^There is a case where onderwerp is \"([^\"]*)\"$")
    public void thereIsACaseWhereOnderwerpIs(String onderwerp) {
        ContractPage cp = new ContractPage();
        Assert.assertEquals(cp.getCaseOnderwerp(),onderwerp);
        parameterProvider.put("caseNumber",cp.getCaseNumber());

    }

    @And("^Interaction is created with Type \"([^\"]*)\" and Onderwerp \"([^\"]*)\" and verwante case is \"([^\"]*)\"$")
    public void interactionIsCreatedWithTypeAndOnderwerpAndVerwanteCaseIs(String type, String onderwerp, String number) {
        ContractPage cp = new ContractPage();
        String caseNumber = parameterProvider.getValueOrParameterAsString(number);
        Assert.assertEquals("Actual Interaction Type differs from expected",cp.getInteractionType(),type);
        Assert.assertEquals("Actual Interaction Onderwerp differs from expected",cp.getInteractionOnderwerp(),onderwerp);
        Assert.assertEquals("Actual Interaction Verwante Case differs from expected",cp.getInteractionVerwanteCase(),caseNumber);
    }

    @And("^Kortingen is \"([^\"]*)\"$")
    public void kortingenIs(String kortingen) {
        ContractPage cp = new ContractPage();
        cp.chooseDiscounts(kortingen);
        Sleeper.sleepTightInSeconds(0.5);

    }
}
