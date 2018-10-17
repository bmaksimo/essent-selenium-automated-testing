package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.UPLOAD_FILE;

public class ChangeAccountStatusPage extends Component {
    public ChangeAccountStatusPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public ChangeAccountStatusPage(By selector, SeleniumDriver seleniumDriver) {
        super(selector, seleniumDriver);
    }

    public ChangeAccountStatusPage(WebElement element, SeleniumDriver seleniumDriver) {
        super(element, seleniumDriver);
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

    public void findDocument() {
        waitForRequestsToFinish();
        Assert.assertTrue(findElementWhenVisible(By.xpath("(//span[.='customer-signature.pdf'])[1]")).isDisplayed());
    }
}
