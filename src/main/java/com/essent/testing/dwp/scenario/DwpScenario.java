package com.essent.testing.dwp.scenario;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model.Execution;
import com.essent.automation.autocrat.Model.Step;
import com.essent.automation.util.Sleeper;
import com.essent.testing.selenium.scenario.SeleniumScenario;
import com.essent.testing.util.AutocratExecutionAdapter;

import static org.junit.Assert.assertTrue;

/**
 * Created by Jim on 27-12-2017.
 *
 */
public abstract class DwpScenario extends SeleniumScenario {


    protected void isDwpRunning(String baseUrl) throws Exception {
        webDriver.setBaseUrl(baseUrl);
        webDriver.goToHomePage();
        String currentUrl = webDriver.getDriver().getCurrentUrl();
        if (null != currentUrl && !currentUrl.equals(baseUrl)) {
            webDriver.setBaseUrl(currentUrl);
            webDriver.goToHomePage();
        }
        Sleeper.sleepTightInSeconds(3);
        logger().info("Current URL: " + currentUrl);
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

}
