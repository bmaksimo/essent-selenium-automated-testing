package com.essent.testing.dwp.scenario;

import com.billinghouse.cucumber.runtime.scenario.ActiveScenarioProvider;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model.Execution;
import com.essent.automation.autocrat.Model.Step;
import com.essent.automation.util.Sleeper;
import com.essent.testing.selenium.SeleniumScenario;
import com.essent.testing.util.AutocratExecutionAdapter;
import cucumber.api.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;
import static  com.essent.testing.util.selenium.dwp.LocalStorageUtil.*;

/**
 * Created by Jim on 27-12-2017.
 * Here we implement methods for available for all scenarios
 */
public abstract class DwpScenario extends SeleniumScenario {

    private  final static Logger logger = Logger.getLogger(DwpScenario.class);

    private String name;

    protected final Logger logger() {
        return logger;
    }

    public String getName() {
        return name;
    }


    protected void isDwpRunning(String baseUrl) throws Exception {
        webDriver.setBaseUrl(baseUrl);
        webDriver.goToHomePage();
        String currentUrl = webDriver.getDriver().getCurrentUrl();
        if (null != currentUrl && !currentUrl.equals(baseUrl)) {
            webDriver.setBaseUrl(currentUrl);
            webDriver.goToHomePage();
        }
        Sleeper.sleepTightInSeconds(3);
        logger.info("Current URL: " + currentUrl);
        assertTrue(currentUrl.startsWith(webDriver.getBaseUrl()));
    }

    protected Execution createExecution() {
        return AutocratExecutionAdapter.newExecution();
    }

    protected Step    createStep(Action action) {
        return new Step().action(action);
    }


    protected boolean execute(final Execution execution) {
        return AutocratExecutionAdapter.execute(webDriver.getDriver(), execution);
    }

    /**
     * The method will mapthe instance of active scenario to the simple scenario name.
     * Then, EssentPretyFormatter plugin methods, such as match(),
     * gain access to active scenario
     * @param scenario
     */
    protected void registerActiveScenario(Scenario scenario) {
        logger.info("STEP:");
        logger.info(" - ACTION: REGISTER_GHERKIN_SCENARIO");
        logger.info(" - CLASS: " + this.getClass().getSimpleName());
        name = scenario.getName();
        logger.info(" - NAME: " + name);
        ActiveScenarioProvider.get().setActiveScenario(this.getClass().getSimpleName(), this);
    }
}
