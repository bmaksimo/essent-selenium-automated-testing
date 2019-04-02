package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.UPLOAD_FILE;

public class ChangeAccountStatusPage extends Component {
    public ChangeAccountStatusPage() {}
    public ChangeAccountStatusPage(By selector) {
        super(selector);
    }

    public ChangeAccountStatusPage(WebElement element) {
        super(element);
    }

    public void chooseAccountStatus(String status) {
        seleniumDriver.findElementWhenVisible(By.id("status-field")).sendKeys(status);
    }

    public boolean uploadFile(String path) {
        String elementName = "dwp.attachment.field";
        String query = "#dwp-attachment-field";
        Model.Execution execution = createExecution();
        execution
            .element(elementName, createElement("SELECTOR", query))
            .step(createStep(Action.UPLOAD).element(elementName).value(path).requireDisplayed(false), UPLOAD_FILE.getSleepInMillis());
        return execute(execution);
    }
    public boolean uploadFileForSign(String path) {
        String elementName = "dwp.attachment.field";
        String query = "#signed-contract-docguid-c-field";
        Model.Execution execution = createExecution();
        execution
            .element(elementName, createElement("SELECTOR", query))
            .step(createStep(Action.UPLOAD).element(elementName).value(path).requireDisplayed(false), UPLOAD_FILE.getSleepInMillis());
        return execute(execution);
    }


}
