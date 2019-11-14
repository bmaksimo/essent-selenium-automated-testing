package com.essent.testing.scenario;

import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.billinghouse.cucumber.runtime.scenario.ActiveScenarioProvider;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.api.Scenario;
import cucumber.runtime.CucumberException;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Autowired;

import static com.billinghouse.testautomation.util.dsl.NumericUtil.ordinalAsInt;
import static com.essent.testing.selenium.helper.fluent_wait.FluentWaitUtil.createPollingWaiter;

public abstract class RegisteredScenario {

    @Autowired
    protected ParameterProvider parameterProvider;
    private  final static Logger logger = Logger.getLogger(RegisteredScenario.class);


    protected final Logger logger() {
        return logger;
    }

    /**
     * The method will mapthe instance of active scenario to the simple scenario name.
     * Then, EssentPretyFormatter plugin methods, such as match(),
     * gain access to active scenario
     * @param scenario
     */
    protected void registerActiveScenario(Scenario scenario) {
        logger().debug("STEP:");
        logger().debug(" - ACTION: REGISTER_GHERKIN_SCENARIO");
        logger().debug(" - CLASS: " + this.getClass().getSimpleName());
        logger().debug(" - NAME: " + scenario.getName());
        ActiveScenarioProvider.get().setActiveScenario(this.getClass().getSimpleName(), this);
        parameterProvider.setNewScenario(scenario);
    }

    protected RegisteredScenario getScenarioInstance(Class scenarioClass) {
        RegisteredScenario activeScenario = ActiveScenarioProvider.get().getActiveScenario(scenarioClass.getSimpleName());
        if(activeScenario == null) {
            throw new CucumberException(String.format("Scenario %s has not been registered. Please check @Before annotation and the list of Gherkin tags (@DWP, @REGRESSION, @E2E,...).",
                scenarioClass.getSimpleName()));
        }
        return activeScenario;
    }

    protected void tidyUp(SeleniumDriver seleniumDriver) {
        if (seleniumDriver != null
            && seleniumDriver.getDriver() != null
            && ((RemoteWebDriver) seleniumDriver.getDriver()).getSessionId() != null) {
            seleniumDriver.tearDown();
        }
    }

    protected void setUpWebDriver(SeleniumDriver seleniumDriver) throws Exception {
        seleniumDriver.createWebDriver();
        seleniumDriver.setUp();
    }

  protected <T> FluentWait<T> waiter(T testObject, long secondsTimeout, long secondsPollingEvery) {
    return createPollingWaiter(testObject, secondsTimeout, secondsPollingEvery);
  }

  protected int extractNumericValue(String ordinal) {
    return ordinalAsInt(ordinal);
  }

  protected int asArrayIndex(String ordinal) {
    return extractNumericValue(ordinal) - 1;
  }
}
