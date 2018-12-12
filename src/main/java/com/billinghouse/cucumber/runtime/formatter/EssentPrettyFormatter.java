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


public class EssentPrettyFormatter extends PrettyFormatter implements ColorAware {

    private static final Logger logger = Logger.getLogger(EssentPrettyFormatter.class);
    private static final Map<Class, BiConsumer> annotationRules  = new HashMap<>();
    private String    location;

    static {
        annotationRules.put(OutputParameter.class, (BiConsumer<String, Object>) (n, p) -> {
            ((ParameterProvider) ContextService.getContext().getBean("parameterProvider")).consumingNullValues(true).put(n, p);
        });
    }

    @Override
    public void result(Result result) {
        super.result(result);
        logger.info("CUCUMBER_HOOK (result)");
        RegisteredScenario activeScenario = (RegisteredScenario) getActiveScenario(location);
        ParameterProvider  parameterProvider = ((ParameterProvider) ContextService.getContext().getBean("parameterProvider")).consumingNullValues(true);
        switch(result.getStatus()) {
            case Result.PASSED:
                collectOutputParameters(OutputParameter.class, activeScenario);
                break;
            default:
                parameterProvider.put("cucumber-scenario-status", result.getStatus());
                parameterProvider.consumingNullValues(true).put("cucumber-scenario-failure", result.getError());
                break;
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
        this.location = match.getLocation();
        assignInputFromOuputParameters(getActiveScenario(location));
        logger.info(" - LOCATION: " + location);
    }

    private void assignInputFromOuputParameters(Object activeScenario) {
        ParameterProvider  parameterProvider = ((ParameterProvider) ContextService.getContext().getBean("parameterProvider")).consumingNullValues(true);
        ParametersUtil.assignOutToEachInputParam((Function<String, Object>) (s)->{
            return parameterProvider.get(s);
        }, activeScenario);
    }

    private void collectOutputParameters(Class clazz, Object activeScenario) {
        ParametersUtil.visitOutputParameters(activeScenario, annotationRules.get(clazz));
    }

    private Object getActiveScenario(String location) {
        String name = location.substring(0, location.indexOf("."));
        logger.info("Getting active scenario " + name);
        Object activeScenario = provideNotNull(ActiveScenarioProvider.get().getActiveScenario(name));
        return activeScenario;
    }

    private Object provideNotNull(Object parameter) {
        if (parameter == null) {
            throw new CucumberException("The parameter value cannot be null");
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

