package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.navigation.DwpPlusMenu;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;


public class Navigation extends DwpScenario {


    @Before("@B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @And("^b2b Plus menu is \"([^\"]*)\"$")
    public void bBPlusMenuIs(String plus) throws Throwable {
        DwpPlusMenu pl = new DwpPlusMenu(webDriver);
        pl.findAndClickOnPlusIcon();
        pl.findAndClickPlusElement(plus);
    }
}
