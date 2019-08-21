package stepdefinitions.jbilling.navigation.menu;

import com.essent.testing.jbilling.pageobject.impl.navigation.ConfigurationManuPage;
import com.essent.testing.jbilling.pageobject.impl.navigation.TopMenuPage;
import com.essent.testing.jbilling.scenario.JBillingScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

public class JBillingMenu extends JBillingScenario {

    @Before("@JBILLING or @B2B or @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^JBilling top menu item is \"([^\"]*)\"$")
    public void jbillingTopMenuItemIs(String item){
        TopMenuPage menu = new TopMenuPage();
        menu.topMenu(item);
    }

    @Override
    @After("@JBILLING or @B2B or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^Configuration left menu item is \"([^\"]*)\"$")
    public void configurationLeftMenuItemIs(String left){
        ConfigurationManuPage confManu = new ConfigurationManuPage();
        confManu.configurationLeftMenu(left);
    }
}
