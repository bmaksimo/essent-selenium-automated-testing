package stepdefinitions.dwp.plus;

import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.runtime.CucumberException;
import stepdefinitions.dwp.navigation.NavigationElements;

public class PlusActions extends NavigationElements {

    @Before("@DWP or @CORE or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @OutputParameter(name = "plus-menu-item")
    private String plusMenuAction;
    @And("^Plus menu is \"([^\"]*)\"$")
    public void checkPlusMenu(String path) throws CucumberException {
        try {
            seleniumDriver.waitForRequestsToFinish();
            clickTopAction("Plus Menu");
            clickPlusAction(path);
            plusMenuAction = path;
        } catch (Throwable t) {
          throw  new CucumberException(t);
        }
    }

    @And("^List plus action is \"([^\"]*)\"$")
    public void checkPlusAction(String item) throws Exception {
        clickListPlusAction(item);
    }

    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
