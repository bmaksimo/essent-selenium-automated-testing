package com.essent.testing.scenario;

import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.billinghouse.cucumber.runtime.scenario.ActiveScenarioProvider;
import com.essent.automation.util.Sleeper;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.api.Scenario;
import cucumber.runtime.CucumberException;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RegisteredScenario {

    @Autowired
    protected ParameterProvider parameterProvider;
    static protected SeleniumDriver webDriver;

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
    }

    protected RegisteredScenario getScenarioInstance(Class scenarioClass) {
        RegisteredScenario activeScenario = ActiveScenarioProvider.get().getActiveScenario(scenarioClass.getSimpleName());
        if(activeScenario == null) {
            throw new CucumberException(String.format("Scenario %s has not been registered. Please check @Before annotation and the list of Gherkin tags (@DWP, @REGRESSION, @E2E,...).",
                scenarioClass.getSimpleName()));
        }
        return activeScenario;
    }

    public void tidyUp() {
        if (webDriver != null) {
            webDriver.tearDown();
            webDriver = null;
        }
    }

    protected void takeScreenshot(boolean success) {
        webDriver.takeScreenshot(success);
    }

    protected void moveToElementAndClick(WebElement element, WebDriver driver) {
        Actions actions = new Actions(driver);
        Actions elementMovedTo = actions.moveToElement(element);
        elementMovedTo.perform();
        Sleeper.sleepTightInSeconds(3);
        elementMovedTo.click().perform();
    }

    @AfterClass
    public void tearDown()  {
        if (webDriver != null) {
            tidyUp();
        }
    }
}
