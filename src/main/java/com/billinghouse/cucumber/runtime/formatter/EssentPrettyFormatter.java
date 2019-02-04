package com.billinghouse.cucumber.runtime.formatter;

import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.billinghouse.cucumber.runtime.parameter.ParametersUtil;
import com.billinghouse.cucumber.runtime.scenario.ActiveScenarioProvider;
import com.essent.testing.context.ContextService;
import com.essent.testing.scenario.RegisteredScenario;
import cucumber.runtime.CucumberException;
import cucumber.runtime.formatter.ColorAware;
import gherkin.formatter.PrettyFormatter;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.Step;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The class prov
 */
public class EssentPrettyFormatter extends PrettyFormatter implements ColorAware {

    private static final Logger logger = Logger.getLogger(EssentPrettyFormatter.class);
    private static final Map<Class, BiConsumer> annotationRules  = new HashMap<>();
    private String activeScenarioName;

    static {
        annotationRules.put(OutputParameter.class, (BiConsumer<String, Object>) EssentPrettyFormatter::accept);
    }

    private static void accept(String parameterName, Object parameterValue) {
        ((ParameterProvider) ContextService.getContext().getBean("parameterProvider")).consumingNullValues(true).put(parameterName, parameterValue);
    }

    @Override
    public void result(Result result) {
        super.result(result);
        logger.info("CUCUMBER_HOOK (result)");
        RegisteredScenario activeScenario = getActiveScenario(activeScenarioName);
        ParameterProvider  parameterProvider = ((ParameterProvider) ContextService.getContext().getBean("parameterProvider")).consumingNullValues(true);
        if (Result.PASSED.equals(result.getStatus())) {
            collectOutputParameters(OutputParameter.class, activeScenario);
        } else {
            parameterProvider.put("cucumber-scenario-status", result.getStatus());
            parameterProvider.consumingNullValues(true).put("cucumber-scenario-failure", result.getError());
        }
        logger.info(" - TEST SCENARIO PARAMETERS: " + parameterProvider.toString());
    }

    @Override
    public void step(Step step) {
        super.step(step);
    }

    @Override
    public void match(Match match) {
        super.match(match);
        logger.info("CUCUMBER_HOOK (match)");
        this.activeScenarioName = match.getLocation();
        assignInputFromOuputParameters(getActiveScenario(activeScenarioName));
        logger.info(" - LOCATION: " + activeScenarioName);
    }

    private void assignInputFromOuputParameters(RegisteredScenario activeScenario) {
        ParameterProvider  parameterProvider = ((ParameterProvider) ContextService.getContext().getBean("parameterProvider")).consumingNullValues(true);
        ParametersUtil.assignOutValuesToInputParameters((Function<String, Object>) (s)-> parameterProvider.get(s), activeScenario);
    }

    private void collectOutputParameters(Class clazz, RegisteredScenario activeScenario) {
        ParametersUtil.collectScenarioOutputParameters(activeScenario, annotationRules.get(clazz));
    }

    private RegisteredScenario getActiveScenario(String scenarioName) {
        String name = scenarioName.substring(0, scenarioName.indexOf("."));
        RegisteredScenario activeScenario = provideNotNull(ActiveScenarioProvider.get().getActiveScenario(name),
                                                           String.format("Scenario %s has not been registered. Please check @Before annotation and the list of Gherkin tags (@DWP, @REGRESSION, @E2E,...).",
                                                                          name));
        return activeScenario;
    }

    private <T> T provideNotNull(T parameter, String messageWhenNull) {
        if (parameter == null) {
            throw new CucumberException(messageWhenNull);
        }
        return parameter;
    }

    public EssentPrettyFormatter(Appendable out) {
        super(out, false, true);
    }

    public void setMonochrome(boolean monochrome) {
        super.setMonochrome(monochrome);
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        logger.info("CUCUMBER_HOOK (endOfScenarioLifeCycle)");
        super.endOfScenarioLifeCycle(scenario);
        ParameterProvider  parameterProvider = ((ParameterProvider) ContextService.getContext().getBean("parameterProvider")).consumingNullValues(true);
        logger.info(" - TEST SCENARIO PARAMETERS: " + parameterProvider.toString());
    }
}

