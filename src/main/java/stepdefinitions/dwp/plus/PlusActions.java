package stepdefinitions.dwp.plus;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import stepdefinitions.dwp.navigation.NavigationElements;

public class PlusActions extends NavigationElements {

    @Before("@DWP or @CORE or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
