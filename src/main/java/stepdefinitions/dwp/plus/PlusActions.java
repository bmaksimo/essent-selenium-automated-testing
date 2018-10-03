package stepdefinitions.dwp.plus;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import stepdefinitions.dwp.navigation.NavigationElements;

public class PlusActions extends NavigationElements {

    @Before("@SMOKE, @E2E_B2C, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Plus menu is \"([^\"]*)\"$")
    public void checkPlusMenu(String path) throws Throwable {
        clickTopAction("Plus Menu");
        clickPlusAction(path);
    }

    @And("^List plus action is ([^\"]*)$")
    public void checkPlusAction(String item) throws Exception {
        clickListPlusAction(item);
    }

    @Override
    @After("@SMOKE, @E2E_B2C, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
