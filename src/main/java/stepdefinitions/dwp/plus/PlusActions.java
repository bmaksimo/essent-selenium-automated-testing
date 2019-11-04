package stepdefinitions.dwp.plus;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.runtime.CucumberException;
import stepdefinitions.dwp.navigation.NavigationElements;

public class PlusActions extends NavigationElements {

    @Before("@DWP or @CORE or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    public void checkPlusMenu(String path) throws CucumberException {
        try {
            seleniumDriver.waitForRequestsToFinish();
            clickTopAction("Plus Menu");
            clickPlusAction(path);
            parameterProvider.put("plus-menu-item", path);
        } catch (Throwable t) {
          throw  new CucumberException(t);
        }
    }

    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
