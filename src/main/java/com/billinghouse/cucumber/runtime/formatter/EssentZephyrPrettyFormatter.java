package com.billinghouse.cucumber.runtime.formatter;

import com.billinghouse.cucumber.runtime.annotations.OutputParameter;
import com.essent.automation.bean.zephyr.*;
import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.billinghouse.cucumber.runtime.scenario.ActiveScenarioProvider;
import com.essent.automation.service.ZephyrService;
import com.billinghouse.cucumber.runtime.parameter.ParametersUtil;
import com.essent.testing.dwp.DwpScenario;
import cucumber.runtime.CucumberException;
import cucumber.runtime.formatter.ColorAware;
import gherkin.formatter.PrettyFormatter;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class EssentZephyrPrettyFormatter extends PrettyFormatter implements ColorAware {

    private static final Logger log = Logger.getLogger(EssentZephyrPrettyFormatter.class);
    private static final Map<Class, BiConsumer> annotationRules  = new HashMap<>();
    private static final Map<String, Result> scenarioStepResults = Collections.synchronizedMap(new HashMap<>());
    private String    location;
    private Throwable scenarioFailure;

    static {
        annotationRules.put(OutputParameter.class, (BiConsumer<String, Object>) (n, p) -> {
            ParameterProvider.get().put(n, p);
        });
    }

    @Override
    public void result(Result result) {
        super.result(result);
        log.info("Execution result: " + result.getStatus());
        DwpScenario activeScenario = (DwpScenario) getActiveScenario(location);
        switch(result.getStatus()) {
            case Result.PASSED:
                collectOutputParameters(OutputParameter.class, activeScenario);
                break;
            case Result.FAILED:
                scenarioFailure = result.getError();
                break;
        }
        Test.Step testStepResponse = ZephyrService.get().createTestStep(location, "Execution stats: " + "Duration, millis: " + result.getDuration()
            + " Status: " + result.getStatus(), "");
        scenarioStepResults.put(testStepResponse.getId(), result);
    }

    private String extractStepFailureInfo(Throwable error) {
        StackTraceElement[] stackTrace = error.getStackTrace();
        if(stackTrace.length > 0) {
            StackTraceElement element = stackTrace[stackTrace.length - 1];
            return " feature: " +
                element.getFileName() + " step: " + element.getMethodName() + " line number: " + element.getLineNumber();
        } else {
            return location;
        }
    }

    @Override
    public void match(Match var1) {
        super.match(var1);
        this.location = var1.getLocation();
        assignInputFromOuputParameters(getActiveScenario(location));
        log.info("match: " + location);
    }

    private void assignInputFromOuputParameters(Object activeScenario) {
        ParametersUtil.assignOutToEachInputParam((Function<String, Object>) (s)->{
            return ParameterProvider.get().get(s);
        }, activeScenario);
    }

    private void collectOutputParameters(Class clazz, Object activeScenario) {
        ParametersUtil.visitOutputParameters(activeScenario, annotationRules.get(clazz));
    }

    private Object getActiveScenario(String location) {
        String name = location.substring(0, location.indexOf("."));
        Object activeScenario = provideNotNull(ActiveScenarioProvider.get().getActiveScenario(name));
        return activeScenario;
    }

    private Object provideNotNull(Object parameter) {
        if (parameter == null) {
            throw new CucumberException("The parameter value cannot be null");
        }
        return parameter;
    }

    public EssentZephyrPrettyFormatter(Appendable out) {
        super(out, false, true);
    }

    public void setMonochrome(boolean monochrome) {
        super.setMonochrome(monochrome);
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        super.endOfScenarioLifeCycle(scenario);
        Execution execution = ZephyrService.get().createTestExecution(ExecutionStatus.UNEXECUTED.getId());
        ZephyrService.get().createStepResults(execution);
        StepResults results = new StepResults();
        FluentWait<StepResults> wait = new FluentWait(results).withTimeout(Duration.ofSeconds(30));
        wait.until((arg) -> {
            List<StepResult> stepResults = ZephyrService.get().getStepResults(execution);
            arg.setStepResults(stepResults);
            return stepResults.size() > 0;
        });
        long minStatus = updateStepResults(results.getStepResults());
        updateExecution(execution, scenario.getDescription(), scenarioFailure, minStatus);
        scenarioFailure = null;
    }

    private void updateExecution(Execution execution, String description, Throwable scenarioFailure, long minStatus) {

        long statusId = ExecutionStatus.UNEXECUTED.getId();
        if(scenarioFailure != null) {
            statusId = ExecutionStatus.FAILED.getId();
        } else if(minStatus == ExecutionStatus.PASSED.getId()) {
            statusId = minStatus;
        }
        Execution updateExecution = new Execution();
        updateExecution.setStatus(new Status.Builder().withId(statusId).build());
        updateExecution.setComment(description);
        updateExecution.setId(execution.getId());
        updateExecution.setProjectId(execution.getProjectId());
        updateExecution.setCycleId(execution.getCycleId());
        updateExecution.setIssueId(execution.getIssueId());
        ZephyrService.get().updateExecution(updateExecution);
    }

    private long updateStepResults(List<StepResult> stepResults) {
        long[] minStatus  = new long[1];
        minStatus[0] = ExecutionStatus.FAILED.getId();

        stepResults.forEach(stepResult -> {
            Result result = (Result)provideNotNull(scenarioStepResults.get(stepResult.getStepId()));
            String comment = null;
            long statusId = ExecutionStatus.UNEXECUTED.getId();
            switch(result.getStatus()) {
                case Result.PASSED:
                    statusId = ExecutionStatus.PASSED.getId();
                    break;
                case Result.FAILED:
                    statusId = ExecutionStatus.FAILED.getId();
                    String message = result.getError().getMessage();
                    comment = message + ", " + extractStepFailureInfo(result.getError());
                    break;
                default:
            }
            StepResult updateResult = new StepResult();
            updateResult.setComment(comment);
            updateResult.setStatus(new Status.Builder().withId(statusId).build());
            updateResult.setExecutionId(stepResult.getExecutionId());
            updateResult.setId(stepResult.getId());
            updateResult.setIssueId(stepResult.getIssueId());
            updateResult.setStepId(stepResult.getStepId());
            ZephyrService.get().updateStepResult(updateResult);
            if (statusId < minStatus[0]) {
                minStatus[0] = statusId;
            }
        });
        return minStatus[0];
    }
}
