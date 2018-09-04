package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.b2b_regression.ConsumptionsPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class ConsumptionsTest extends DwpScenario {

    @Before("@SMOKE, @QUOTE, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }
    @Then("^View list is not empty$")
    public void viewListIsNotEmpty() throws Throwable {
        ConsumptionsPage consumptionsPage = new ConsumptionsPage();
        Assert.assertTrue(consumptionsPage.listIsVisible());
    }
}
