package stepdefinitions.dwp.service_contracting;

import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.dwp.pageobject.impl.service_contracting.ContractPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class ContractSteps extends DwpScenario {
    private String amount;

    ContractPage contractPage = new ContractPage(webDriver);

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Change amount for a customer$")
    public void changeAmountForACustomer() throws Throwable {
        contractPage.openFirstContractFromList();
    }

    @And("^Contract plus and \"([^\"]*)\"$")
    public void contractPlusAnd(String subaction) throws Throwable {
        contractPage.contractPlus();
        BaseObject baseObject = new BaseObject(webDriver);
        baseObject.plusSubaction(subaction);
    }

    @And("^Amount values is ([^\"]*)$")
    public void amountValuesIs(String value) throws Throwable {
        contractPage.changeAmount(value);
        amount = value;
    }

    @Then("^Amount of a customer value$")
    public void amountOfACustomerValue() throws Throwable {
        Assert.assertTrue("Amount is not correct.", contractPage.getAmountOfACustomer(amount));
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }

}
