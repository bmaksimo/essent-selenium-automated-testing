package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.navigation.DwpLeftMenu;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpPlusMenu;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpTopMenu;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;


public class Navigation extends DwpScenario {


    @Before("@B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @And("^b2b Plus menu is \"([^\"]*)\"$")
    public void bBPlusMenuIs(String plus) throws Throwable {
        DwpPlusMenu pl = new DwpPlusMenu(webDriver);
        pl.clickOnplusIcon();
        pl.clickplusElement(plus);
    }

    @And("^b2b \"([^\"]*)\" is selected in Service$")
    public void bBIsSelectedInService(String menu) throws Throwable {
        DwpPlusMenu pl = new DwpPlusMenu(webDriver);
            pl.clickServiceElemet(menu);
    }


    @When("^b2b Top menu is \"([^\"]*)\"$")
    public void topMenuIs(String top) throws Throwable {
        webDriver.waitUntilAngularPageIsLoaded();
        DwpTopMenu tm = new DwpTopMenu(webDriver);
        tm.clickTopMenu(top);
    }

    @When("^b2b Left menu is \"([^\"]*)\"$")
    public void bBLeftMenuIs(String left) throws Throwable {
        webDriver.waitUntilAngularPageIsLoaded();
        DwpLeftMenu lm = new DwpLeftMenu(webDriver);
        lm.clickOnLeftElemet(left.toLowerCase());
    }
}
