package com.essent.testing.dwp;

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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

/**
 * Created by Jim on 27-12-2017.
 * Here we implement methods for available for all scenarios
 */
public abstract class DwpScenario extends SeleniumScenario {

    private  final static Logger logger = Logger.getLogger(DwpScenario.class);

    protected String preferredLanguage = "en_BE";

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
        Sleeper.sleepTightInSeconds(3);
        assertTrue(currentUrl.startsWith(baseUrl));
    }

    /**
     * "Syntactic sugar" method which makes it easy to create
     * new Nova Autocrat Execution class in DWP Scenarios
     *
     * @return
     */
    protected Execution     newExecution() {
        return AutocratExecutionAdapter.newExecution();
    }

    protected Step    createStep(Action action) {
        return new Step().action(action);
    }

    protected boolean execute(final WebDriver driver, final Execution execution) {
        return AutocratExecutionAdapter.execute(driver, execution);
    }

    protected boolean executeStep(final WebDriver driver, final Step step) {
        return AutocratExecutionAdapter.executeStep(driver, step);
    }

    /**
     * The method will link the instance of active scenario to the simple scenario name.
     * Then, EssentPretyFormatter plugin methods, such as match(),
     * get access to active scebario
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

    protected Object checkFieldAccess(String fieldName, Object testData) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = testData.getClass().getMethod(fieldToGetter(fieldName));
        return method.invoke(testData);
    }

    private String fieldToGetter(String name) {
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
