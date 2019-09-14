package stepdefinitions.dwp.page_object;


import com.essent.testing.dwp.pageobject.impl.quote.QuoteCreationGuidedStep;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import static com.essent.testing.selenium.helper.fluent_wait.FluentWaitUtil.createPollingWaiter;


public class CustomerAcceptance extends QuoteCreationGuidedStep {

    public String getAcceptanceStatus() {
       seleniumDriver.waitForRequestsToFinish();
       FluentWait<WebDriver> waiter = createPollingWaiter(seleniumDriver.getDriver(), 20, 2);
       String[] status = new String[1];
       waiter.until((WebDriver callback) ->
            {
                WebElement element = callback.findElement(By.xpath("//*[@id=\"accounts-aos-quotes-ca-status-c-field\"]"));
                if(element != null) {
                    status[0] = element.getText();
                    return StringUtils.isNotEmpty(status[0]);
                }
                return false;
            }
       );
       return status[0];
    }

    @Override
    public boolean fillInFormData() {
        return true;
    }
}
