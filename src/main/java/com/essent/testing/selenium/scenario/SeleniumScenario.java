package com.essent.testing.selenium.scenario;


import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.billinghouse.cucumber.runtime.scenario.ActiveScenarioProvider;
import com.essent.automation.util.Sleeper;
import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SeleniumScenario implements RegisteredScenario {

    private  final static Logger logger = Logger.getLogger(SeleniumScenario.class);
    protected String name;

    @Autowired
    protected ParameterProvider parameterProvider;

    public abstract void tidyUp();

    protected final Logger logger() {
        return logger;
    }

    public abstract void setUpWebDriver() throws Exception;


    /**
     * The method will mapthe instance of active scenario to the simple scenario name.
     * Then, EssentPretyFormatter plugin methods, such as match(),
     * gain access to active scenario
     * @param scenario
     */
    protected void registerActiveScenario(Scenario scenario) {
        logger.debug("STEP:");
        logger.debug(" - ACTION: REGISTER_GHERKIN_SCENARIO");
        logger.debug(" - CLASS: " + this.getClass().getSimpleName());
        name = scenario.getName();
        logger.debug(" - NAME: " + name);
        ActiveScenarioProvider.get().setActiveScenario(this.getClass().getSimpleName(), this);
    }

//    protected void takeScreenshot(boolean success) {
//        webDriver.takeScreenshot(success);
//    }

    protected void moveToElementAndClick(WebElement element, WebDriver driver) {
        Actions actions = new Actions(driver);
        Actions elementMovedTo = actions.moveToElement(element);
        elementMovedTo.perform();
        Sleeper.sleepTightInSeconds(3);
        elementMovedTo.click().perform();
    }

    @Override
    public String getName() {
        return name;
    }

    @AfterClass
    public abstract void tearDown();
}
