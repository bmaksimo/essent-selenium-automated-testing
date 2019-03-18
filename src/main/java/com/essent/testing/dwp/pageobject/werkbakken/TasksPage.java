package com.essent.testing.dwp.pageobject.werkbakken;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TasksPage extends Component {

    private static final String taskIDXPath = "(//h6)[2]";  
    private static final String inputResolutionId = "task-resolution-c-field";
    private static final String taskNumberId = "task-number-c-default-value-field";

    public WebElement findElementbyX(String path){
	WebElement element = seleniumDriver.findElementWhenVisible(By.xpath(path));
	return element;
    }
    
    public WebElement findElementbyId(String id){
  	WebElement element = seleniumDriver.findElementWhenVisible(By.id(id));
  	return element;
      }

    public String getTaskId() {
	return findElementbyX(taskIDXPath).getText();
    }
    
    public void waitAndInput(WebElement element, String text) {
	seleniumDriver.waitAndSendKeys(element, text);
    }
    
     
    public void inputResolution(String text) {	
	waitAndInput(findElementbyId(inputResolutionId), text);
    }

    public void findTaskId(String taskId) {
	seleniumDriver.waitForRequestsToFinish();
	waitAndInput(findElementbyId(taskNumberId), taskId);
    }
}
