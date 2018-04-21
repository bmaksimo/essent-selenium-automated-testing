package com.essent.testing.util;

import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Autocrat.ExecutionContext;
import com.essent.automation.autocrat.Model;
import com.essent.automation.autocrat.Model.Execution;
import com.essent.automation.autocrat.Model.Flow;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AutocratExecutionAdapter {

    public static Execution newExecution() {
        return new Execution();
    }

    public static ExecutionContext context(WebDriver webDriver, Execution execution) {
        return new ExecutionContext(webDriver, execution);
    }

    public static boolean execute(WebDriver driver, Execution execution) {
        ExecutionContext executionContext = new ExecutionContext(driver, execution);
        boolean success = executeFlow(executionContext, execution.flow());
        return success;
    }

    public static boolean executeStep(WebDriver driver, Model.Step step) {
        step.validate();
        ExecutionContext executionContext = new ExecutionContext(driver, step.getExecution());
        try {
            return Autocrat.executeStep(executionContext, step);
        } catch (IOException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean executeFlow(ExecutionContext context, Flow flow) {
        flow.validate();
        try {
            return Autocrat.executeFlow(context, flow);
        } catch (IOException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }
}
