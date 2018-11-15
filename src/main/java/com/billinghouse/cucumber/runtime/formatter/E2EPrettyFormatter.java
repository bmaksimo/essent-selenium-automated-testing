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
import gherkin.formatter.model.*;
import org.apache.commons.collections.SetUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;


public class E2EPrettyFormatter extends PrettyFormatter implements ColorAware {


    private static final Logger logger = Logger.getLogger(E2EPrettyFormatter.class);
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
        RegisteredScenario activeScenario = getActiveScenario(location);
        checkAndTerminate(activeScenario);
        assignInputFromOuputParameters(activeScenario);
        logger.info(" - LOCATION: " + location);
    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
        super.startOfScenarioLifeCycle(scenario);
        logger.info("CUCUMBER_HOOK (startOfScenarioLifeCycle)");

        Set<Tag> tags = new HashSet<>();
        tags.addAll(scenario.getTags());

        ParameterProvider  parameterProvider = ((ParameterProvider) ContextService.getContext().getBean("parameterProvider")).consumingNullValues(true);
        Set<Tag> previousScenarioTags = (Set)parameterProvider.get("scenario-tags");

        if(!SetUtils.isEqualSet(previousScenarioTags, tags)){
            logger.info("E2E scenario switched");
            parameterProvider.remove("cucumber-scenario-failure");
            logger.info("Previous scanario failure info is removed");
        }

    }

    private void checkAndTerminate(RegisteredScenario activeScenario) {
        ParameterProvider  parameterProvider = ((ParameterProvider) ContextService.getContext().getBean("parameterProvider")).consumingNullValues(true);
        if(parameterProvider.containsKey("cucumber-scenario-status")) {
            if(parameterProvider.containsKey("cucumber-scenario-failure")) {
                Throwable failure = (Throwable) parameterProvider.get("cucumber-scenario-failure");
                activeScenario.tidyUp();
                throw new CucumberException("Cannot execute scenario, previous scenario failed. ", failure);
            }
        }
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

    private RegisteredScenario getActiveScenario(String location) {
        String name = location.substring(0, location.indexOf("."));
        RegisteredScenario activeScenario = (RegisteredScenario)provideNotNull(ActiveScenarioProvider.get().getActiveScenario(name));
        return activeScenario;
    }

    private Object provideNotNull(Object parameter) {
        if (parameter == null) {
            throw new CucumberException("The parameter value cannot be null");
        }
        return parameter;
    }

    public E2EPrettyFormatter(Appendable out) {
        super(out, false, true);
    }

    public void setMonochrome(boolean monochrome) {
        super.setMonochrome(monochrome);
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        logger.info("CUCUMBER_HOOK (endOfScenarioLifeCycle)");
        super.endOfScenarioLifeCycle(scenario);
        HashSet<Tag> tags = new HashSet<>();
        tags.addAll(scenario.getTags());
        ParameterProvider  parameterProvider = ((ParameterProvider) ContextService.getContext().getBean("parameterProvider")).consumingNullValues(true);
        parameterProvider.put("scenario-tags", tags);
        logger.info(" - TEST SCENARIO PARAMETERS: " + parameterProvider.toString());
    }
}

