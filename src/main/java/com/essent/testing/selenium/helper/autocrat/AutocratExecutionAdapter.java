package com.essent.testing.selenium.helper.autocrat;

import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Autocrat.ExecutionContext;
import com.essent.automation.autocrat.Model.Execution;
import com.essent.automation.autocrat.Model.Flow;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class AutocratExecutionAdapter {

  private static final Logger logger = Logger.getLogger(AutocratExecutionAdapter.class);

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

  private static boolean executeFlow(ExecutionContext context, Flow flow) {
    flow.validate();
    try {
      return Autocrat.executeFlow(context, flow);
    } catch (IOException | InterruptedException | TimeoutException e) {
      logger.error("Flow execution failed.");
      return false;
    }
  }
}
