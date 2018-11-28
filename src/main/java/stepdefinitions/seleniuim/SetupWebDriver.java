package stepdefinitions.seleniuim;

import com.essent.testing.selenium.scenario.SeleniumScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")
public class SetupWebDriver extends SeleniumScenario {

    @Before("@DWP, @ODOO, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
        setUpWebDriver();
    }

    @Given("^Browser session is re-started$")
    public void browserSessionIsClosed() throws Throwable {
        setUpWebDriver();
    }

    @After("@DWP, @ODOO, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        tidyUp();
    }

}
