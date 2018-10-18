package com.essent.testing.dwp.scenario;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model.Execution;
import com.essent.automation.autocrat.Model.Step;
import com.essent.automation.util.Sleeper;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter;
import com.essent.testing.selenium.scenario.SeleniumScenario;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToDwpDate;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jim on 27-12-2017.
 *
 */
public abstract class DwpScenario extends SeleniumScenario {

    protected void isDwpRunning() throws Exception {
        String dwpUrl = ConfigProvider.getProperty(ConfigKey.DWP_BASE_URL);
        webDriver.setBaseUrl(dwpUrl);
        webDriver.goToHomePage();
        String currentUrl = webDriver.getDriver().getCurrentUrl();
        if (null != currentUrl && !currentUrl.equals(dwpUrl)) {
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

    protected String toDwpDate(String parameter) {
        return checkAndConvertToDwpDate(parameter);
    }
}
