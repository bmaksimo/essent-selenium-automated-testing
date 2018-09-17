package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.BaseObject;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.openqa.selenium.By;

public class BaseSteps extends DwpScenario {
    BaseObject baseObject = new BaseObject(webDriver);

    @Before("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }



    @And("^Enter ean number$")
    public void enterEanNumber() throws Throwable {
        webDriver.waitForRequestsToFinish();
//        System.out.println("EAN: " + eanCode);
//        baseObject.findElementWhenVisible(By.xpath("//button[@class='button']")).click();
//        baseObject.findElementWhenVisible(By.id("ean-default-value-field")).sendKeys(eanCode);
    }
}
