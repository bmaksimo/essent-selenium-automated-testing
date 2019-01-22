package stepdefinitions.seleniuim;

import com.essent.testing.selenium.scenario.AbstractSeleniumScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")
public class SetupWebDriver extends AbstractSeleniumScenario {

    //@Before("@DWP, @ODOO, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
        setUpWebDriver();
    }

    @Override
    public void setUpWebDriver() throws Exception {

    }

    //@After("@DWP, @ODOO, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        tidyUp();
    }

}
