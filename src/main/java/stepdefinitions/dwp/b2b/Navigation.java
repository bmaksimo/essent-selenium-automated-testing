package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.Navigation.DwpLeftMenu;
import com.essent.testing.dwp.pageobject.impl.Navigation.DwpLowerLeftMenu;
import com.essent.testing.dwp.pageobject.impl.Navigation.DwpPlusMenu;
import com.essent.testing.dwp.pageobject.impl.Navigation.DwpTopMenu;
import com.essent.testing.dwp.pageobject.impl.Page.DwpServicePage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.PendingException;
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
        switch (plus.toLowerCase()){
            case "service":
                pl.clickOnServiceDropdownMenu();
                break;

        }
    }

    @And("^b2b \"([^\"]*)\" is selected in Service$")
    public void bBIsSelectedInService(String menu) throws Throwable {
        DwpPlusMenu pl = new DwpPlusMenu(webDriver);
        switch (menu.toLowerCase()){
            case "log a case for account":
                pl.clickOnLogAcaseForAccountOption("Log a case for account");
                break;//'Log a case for account'
            case "case aanmaken voor de klant":
                pl.clickOnLogAcaseForAccountOption("Case aanmaken voor de klant");
                break;
        }

    }


    @When("^b2b Top menu is \"([^\"]*)\"$")
    public void topMenuIs(String top) throws Throwable {
        DwpTopMenu tm = new DwpTopMenu(webDriver);
        switch (top.toLowerCase()){
            case "accounts":
            case "klanten":
                webDriver.waitUntilAngularPageIsLoaded();
		        tm.clickOnAccountsListLink();
		        break;
        }

    }

    @When("^b2b Left menu is \"([^\"]*)\"$")
    public void bBLeftMenuIs(String left) throws Throwable {
        DwpLeftMenu lm = new DwpLeftMenu(webDriver);
        switch (left.toLowerCase()){
            case "sales-marketing":
                lm.clickOnsalesMarketingLink();
                break;
        }
    }

    @When("^b2b Left button menu is \"([^\"]*)\"$")
    public void bBLeftButtonMenuIs(String menu) throws Throwable {
        DwpLowerLeftMenu lbm = new DwpLowerLeftMenu(webDriver);
        webDriver.waitUntilAngularPageIsLoaded();
        switch (menu.toLowerCase()){
            case "service":
                lbm.clickOnServiceButton();
        }

    }

    @And("^b2b \"([^\"]*)\" is clicked$")
    public void bBAddCaseIsClicked(String addCase) throws Throwable {
        webDriver.waitUntilAngularPageIsLoaded();
        DwpServicePage sp = new DwpServicePage(webDriver);
        sp.clickOnAddCaseButton(addCase.toUpperCase());

    }
}
