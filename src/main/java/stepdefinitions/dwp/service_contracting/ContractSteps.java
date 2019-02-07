package stepdefinitions.dwp.service_contracting;

import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.dwp.pageobject.impl.page.ContractPage;
import com.essent.testing.dwp.pageobject.impl.service_contracting.ContractPageClass;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import stepdefinitions.dwp.tables.CustomerAddress;

import java.util.List;

public class ContractSteps extends DwpScenario {
    private String amount;

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Change amount for a customer$")
    public void changeAmountForACustomer() throws Throwable {
        ContractPageClass cp = new ContractPageClass(webDriver);
        cp.openFirstContractFromList();
    }

    @And("^Contract plus and \"([^\"]*)\"$")
    public void contractPlusAnd(String subaction) throws Throwable {
        ContractPageClass cp = new ContractPageClass(webDriver);
        cp.contractPlus();
        BaseObject baseObject = new BaseObject(webDriver);
        baseObject.plusSubaction(subaction);
    }

    @And("^Amount values is \"([^\"]*)\"$")
    public void amountValuesIs(String value) throws Throwable {
        ContractPageClass cp = new ContractPageClass(webDriver);
        cp.changeAmount(value);
        amount = value;
    }

    @Then("^Amount of a customer value$")
    public void amountOfACustomerValue() throws Throwable {
        ContractPageClass cp = new ContractPageClass(webDriver);
        Assert.assertTrue("Amount is not correct.", cp.getAmountOfACustomer(amount));
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^Select EAN$")
    public void selectEAN() throws Throwable {
        ContractPageClass cp = new ContractPageClass(webDriver);
        cp.selectEAN();

    }

    @When("^Old contract data is copied$")
    public void oldContractDataIsCopied() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        parameterProvider.put("oldContractEan",cp.getEanFromContract());
        parameterProvider.put("contractStatus", cp.getStatusFromContract());
        parameterProvider.put("startDate",cp.getActiveContractStartDate());
        parameterProvider.put("endDate",cp.getActiveContractEndDate());
        parameterProvider.put("contractType",cp.getContractType());
//        cp.clickOnContractenNummer();
//        parameterProvider.put("productName",cp.getProductName());
//        parameterProvider.put("kortingsCode",cp.getKortingenOpContractKortingscode());
//        parameterProvider.put("productType",cp.getKortingenOpContractProducttype());
//        cp.clickOnBekijkPrijzenTariefkaatFromPlus();
        //parameterProvider.put("",cp.);

    }

    @Then("^Check if contract with old ean is still active$")
    public void checkIfContractWithOldEanIsStillActive() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
    }

    @And("^Check if contract with new ean is created and became active$")
    public void checkIfContractWithNewEanIsCreatedAndBecameActive() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
    }

    @And("^Check if start date of new ean is the same date as filled in as “Move date”$")
    public void checkIfStartDateOfNewEanIsTheSameDateAsFilledInAsMoveDate() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
//        Assert.assertEquals();
    }

    @And("^Check if the end date of new ean is the same date as the end date of the old one$")
    public void checkIfTheEndDateOfNewEanIsTheSameDateAsTheEndDateOfTheOldOne() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        Assert.assertEquals(cp.getActiveContractEndDate(),parameterProvider.getValueOrParameterAsString("endDate"));
    }

    @And("^Check if products of both contracts are the same$")
    public void checkIfProductsOfBothContractsAreTheSame() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        cp.clickOnContractenNummer();
        Assert.assertEquals(cp.getProductName(),parameterProvider.getValueOrParameterAsString("productName"));
    }

    @And("^Check if discounts of both contracts are the same$")
    public void checkIfDiscountsOfBothContractsAreTheSame() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        Assert.assertEquals(cp.getKortingenOpContractKortingscode(),parameterProvider.getValueOrParameterAsString("kortingsCode"));
        Assert.assertEquals(cp.getProductName(),parameterProvider.getValueOrParameterAsString("productType"));

    }

    @And("^Check if prices of both contracts are the same$")
    public void checkIfPricesOfBothContractsAreTheSame() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        cp.clickOnBekijkPrijzenTariefkaatFromPlus();
//        Assert.assertEquals();
//        Assert.assertEquals();
//        Assert.assertEquals();
//        Assert.assertEquals();
//        Assert.assertEquals();
//        Assert.assertEquals();
    }

    @And("^New move customer address is$")
    public void nweMoveCustomerAddressIs(final DataTable dbTable) throws Throwable {
        List<List<String>> address = dbTable.raw();
        ContractPage cp = new ContractPage(webDriver);
        cp.setNewMoveAddress(address.get(1).get(0),address.get(1).get(1),address.get(1).get(4),address.get(1).get(5));
    }

    @And("^Marketbericht with EAN \"([^\"]*)\" has ED \"([^\"]*)\"$")
    public void marketberichtWithEANHasED(String arg0, String ean) throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        Assert.assertEquals(cp.getMarketberichED(ean),parameterProvider.getValueOrParameterAsString("startDateNew"));
    }

    @Then("^There is a case where onderwerp is \"([^\"]*)\"$")
    public void thereIsACaseWhereOnderwerpIs(String onderwerp) throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        Assert.assertEquals(cp.getCaseOnderwerp(),onderwerp);
        parameterProvider.put("caseNumber",cp.getCaseNumber());

    }

    @And("^Interaction is created with Type \"([^\"]*)\" and Onderwerp \"([^\"]*)\" and verwante case is \"([^\"]*)\"$")
    public void interactionIsCreatedWithTypeAndOnderwerpAndVerwanteCaseIs(String type, String onderwerp, String number) throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        String caseNumber = parameterProvider.getValueOrParameterAsString(number);
        Assert.assertEquals(cp.getInteractionType(),type);
        Assert.assertEquals(cp.getInteractionOnderwerp(),onderwerp);
        Assert.assertEquals(cp.getInteractionVerwanteCase(),caseNumber);
    }

}
