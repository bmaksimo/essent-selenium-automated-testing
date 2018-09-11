package com.essent.testing.odoo.scenario;

import com.billinghouse.cucumber.runtime.scenario.ActiveScenarioProvider;
import com.essent.automation.util.Sleeper;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.selenium.scenario.SeleniumScenario;
import cucumber.api.Scenario;
import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertTrue;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")
public abstract class OdooScenario extends SeleniumScenario {

    private  final static Logger logger = Logger.getLogger(OdooScenario.class);

    private String name;

    public String getName() {
        return name;
    }

    protected void registerActiveScenario(Scenario scenario) {
        logger.info("STEP:");
        logger.info(" - ACTION: REGISTER_GHERKIN_SCENARIO");
        logger.info(" - CLASS: " + this.getClass().getSimpleName());
        name = scenario.getName();
        logger.info(" - NAME: " + name);
        ActiveScenarioProvider.get().setActiveScenario(this.getClass().getSimpleName(), this);
    }

    protected void isOdooRunning() throws Exception {
        String dwpUrl = ConfigProvider.getProperty(ConfigKey.ODOO_BASE_URL);
        webDriver.setBaseUrl(dwpUrl);
        webDriver.goToHomePage();
        String currentUrl = webDriver.getDriver().getCurrentUrl();
        if (null != currentUrl && !currentUrl.equals(dwpUrl)) {
            webDriver.setBaseUrl(currentUrl);
            webDriver.goToHomePage();
        }
        Sleeper.sleepTightInSeconds(3);
        logger.info("Current URL: " + currentUrl);
        assertTrue(currentUrl.startsWith(webDriver.getBaseUrl()));
    }

    protected void odooRegisterActiveScenario(Scenario scenario) {
        logger.info("STEP:");
        logger.info(" - ACTION: REGISTER_GHERKIN_SCENARIO");
        logger.info(" - CLASS: " + this.getClass().getSimpleName());
        name = scenario.getName();
        logger.info(" - NAME: " + name);
        ActiveScenarioProvider.get().setActiveScenario(this.getClass().getSimpleName(), this);
    }
}
