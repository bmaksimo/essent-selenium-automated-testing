package com.essent.testing.dwp.pageobject.werkbakken;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;

public class TasksPage extends Component {

  private static final String TASK_ID = "(//h6)[2]";
  private static final String RESOLUTION_INPUT_ID = "task-resolution-c-field";
  private static final String TASK_ID_SEARCH_FIELD_ID = "task-number-c-default-value-field";

  public String getTaskId() {
    final String taskId;
    taskId = seleniumDriver.findElementWhenVisible(By.xpath(TASK_ID)).getText();
    return taskId;
  }

  public void inputResolution(String text) {
    seleniumDriver.waitAndSendKeys(
        seleniumDriver.findElementWhenVisible(By.id(RESOLUTION_INPUT_ID)), text);
  }

  public void inputResolution(String text, int waitingTime) {
    Sleeper.sleepTightInSeconds(waitingTime);
    seleniumDriver.sendKeysNow(
        seleniumDriver.findElementWhenVisible(By.id(RESOLUTION_INPUT_ID)), text);
  }

  public void findTaskId(String taskId) {
    seleniumDriver.waitForRequestsToFinish();
    seleniumDriver.waitAndSendKeys(
        seleniumDriver.findElementWhenVisible(By.id(TASK_ID_SEARCH_FIELD_ID)), taskId);
  }
}
