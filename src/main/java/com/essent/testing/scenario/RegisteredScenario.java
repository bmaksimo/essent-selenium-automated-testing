package com.essent.testing.scenario;

import com.billinghouse.cucumber.runtime.scenario.ActiveScenarioProvider;
import cucumber.api.Scenario;
import org.apache.log4j.Logger;

public class RegisteredScenario {

    private  final static Logger logger = Logger.getLogger(RegisteredScenario.class);
    private String name;

    protected final Logger logger() {
        return logger;
    }

    public String getName() {
        return name;
    }

    /**
     * The method will mapthe instance of active scenario to the simple scenario name.
     * Then, EssentPretyFormatter plugin methods, such as match(),
     * gain access to active scenario
     * @param scenario
     */
    protected void registerActiveScenario(Scenario scenario) {
        logger().info("STEP:");
        logger().info(" - ACTION: REGISTER_GHERKIN_SCENARIO");
        logger().info(" - CLASS: " + this.getClass().getSimpleName());
        name = scenario.getName();
        logger().info(" - NAME: " + name);
        ActiveScenarioProvider.get().setActiveScenario(this.getClass().getSimpleName(), this);
    }
}
