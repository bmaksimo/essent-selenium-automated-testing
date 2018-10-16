package stepdefinitions.seleniuim;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.roles.UserRoles;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.modal.login.LoginAction;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.selenium.scenario.SeleniumScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")
public class SetupWebDriver extends SeleniumScenario {

    @Before("@DWP, @ODOO, @CORE, @E2E, @REGRESSION, @SALES-MARKETING, @CONTRACTING-SWITCHING, @BUSINESS-DESK, @BILLING")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
        setUpWebDriver();
    }

    @After("@DWP, @ODOO, @CORE, @E2E, @REGRESSION, @SALES-MARKETING, @CONTRACTING-SWITCHING, @BUSINESS-DESK, @BILLING")
    public void tearDown() throws Exception {
        tidyUp();
    }

}
