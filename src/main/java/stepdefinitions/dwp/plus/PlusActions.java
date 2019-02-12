package stepdefinitions.dwp.plus;

import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import stepdefinitions.dwp.navigation.NavigationElements;

public class PlusActions extends NavigationElements {

    @Before("@DWP, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @OutputParameter(name = "plus-menu-item")
    private String plusMenuAction;

    @And("^Plus menu is \"([^\"]*)\"$")
    public boolean checkPlusMenu(String path) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        clickTopAction("Plus Menu");
        clickPlusAction(path);
        plusMenuAction = path;
        return true;
    }

    @And("^List plus action is \"([^\"]*)\"$")
    public void checkPlusAction(String item) throws Exception {
        clickListPlusAction(item);
    }

    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
