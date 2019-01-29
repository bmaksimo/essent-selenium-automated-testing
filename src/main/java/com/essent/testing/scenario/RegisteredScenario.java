package com.essent.testing.scenario;

import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.billinghouse.cucumber.runtime.scenario.ActiveScenarioProvider;
import cucumber.api.Scenario;
import cucumber.runtime.CucumberException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import stepdefinitions.dwp.view_list.ViewListElements;

public class RegisteredScenario {

    @Autowired
    protected ParameterProvider parameterProvider;

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
        logger().debug("STEP:");
        logger().debug(" - ACTION: REGISTER_GHERKIN_SCENARIO");
        logger().debug(" - CLASS: " + this.getClass().getSimpleName());
        name = scenario.getName();
        logger().debug(" - NAME: " + name);
        ActiveScenarioProvider.get().setActiveScenario(this.getClass().getSimpleName(), this);
    }

    protected RegisteredScenario getScenarioInstance(Class scenarioClass) {
        RegisteredScenario activeScenario = ActiveScenarioProvider.get().getActiveScenario(scenarioClass.getSimpleName());
        if(activeScenario == null) {
            throw new CucumberException(String.format("Scenario %s has not been registered. Please double-check  @Before annotation and list of Gherkin tags in it.",
                scenarioClass.getSimpleName()));
        }
        return activeScenario;
    }

    public void tidyUp() {

    }
}
