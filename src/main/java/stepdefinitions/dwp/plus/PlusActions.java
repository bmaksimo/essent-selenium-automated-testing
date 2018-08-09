package stepdefinitions.dwp.plus;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import stepdefinitions.dwp.navigation.NavigationElements;

public class PlusActions extends NavigationElements {

    @Before("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Plus Menu is \"([^\"]*)\"$")
    public void checkPlusMenu(String path) throws Throwable {
        clickPlusAction(path);
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @And("^List Plus Action is ([^\"]*)$")
    public void checkPlusAction(String item) throws Exception {
        clickListPlusAction(item);
    }
}
