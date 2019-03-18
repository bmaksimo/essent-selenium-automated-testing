package com.essent.testing.dwp.pageobject.werkbakken;

import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;

public class TasksPage extends BaseObjectPage {

    private static final String taskIDXPath = "(//h6)[2]";  
    private static final String inputResolutionId = "task-resolution-c-field";
    private static final String taskNumberId = "task-number-c-default-value-field";
           
    
    public String getTaskId() {
	return findElementbyXPath(taskIDXPath).getText();
    }
   
   
    public void inputResolution(String text) {	
	waitAndInput(findElementbyId(inputResolutionId), text);
    }

    public void findTaskId(String taskId) {
	seleniumDriver.waitForRequestsToFinish();
	waitAndInput(findElementbyId(taskNumberId), taskId);
    }
}
