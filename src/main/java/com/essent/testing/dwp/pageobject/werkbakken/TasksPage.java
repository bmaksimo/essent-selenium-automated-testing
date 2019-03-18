package com.essent.testing.dwp.pageobject.werkbakken;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;


public class TasksPage extends Component {

    public String getTaskId() {
	final String taskId;
	taskId = seleniumDriver.findElementWhenVisible(By.xpath("(//h6)[2]")).getText();
	return taskId;
    }

    public void inputResolution(String text) {
	seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("task-resolution-c-field")), text);
    }

    public void findTaskId(String taskId) {
	seleniumDriver.waitForRequestsToFinish();
	seleniumDriver.waitAndSendKeys(
		seleniumDriver.findElementWhenVisible(By.id("task-number-c-default-value-field")), taskId);
    }
}
