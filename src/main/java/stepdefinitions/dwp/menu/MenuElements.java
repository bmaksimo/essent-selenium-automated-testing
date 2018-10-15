package stepdefinitions.dwp.menu;

import com.essent.testing.dwp.pageobject.impl.navigation.DwpLeftMenu;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpTopMenu;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.navigation.NavigationElements;

public class MenuElements extends NavigationElements {

    @Before("@DWP, @CORE, @E2E, @REGRESSION, @SALES-MARKETING, @CONTRACTING-SWITCHING, @BUSINESS-DESK, @BILLING")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Left menu is ([^\"]*)$")
    public void clickLeftMenuItem(String tabName) throws Throwable {
        DwpLeftMenu lm = new DwpLeftMenu(webDriver);
        lm.clickOnLeftElemet(tabName);
    }

    @When("^Left Tab is ([^\"]*)$")
    public void clickLeftTab(String itemName) throws Throwable {
        clickLeftMenuItem(itemName);
    }

    @When("^Top menu item is ([^\"]*)$")
    public void clickTopMenuItem(String tabName) throws Throwable {
        DwpTopMenu tm = new DwpTopMenu(webDriver);
        tm.findAndClickTopMenu(tabName);
    }


    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION, @SALES-MARKETING, @CONTRACTING-SWITCHING, @BUSINESS-DESK, @BILLING")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
