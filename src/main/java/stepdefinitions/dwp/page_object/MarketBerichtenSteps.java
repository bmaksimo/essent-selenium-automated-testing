package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.BaseObject;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

public class MarketBerichtenSteps extends DwpScenario {
    BaseObject baseObject = new BaseObject(webDriver);
    private static String eanCode = null;

    @Before("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @And("^\"([^\"]*)\" turn on$")
    public void turnOn(String input) throws Throwable {
        baseObject.clickOnToggle(input);
    }

    @And("^Insert EAN of customer$")
    public void insertEANOfCustomer() throws Throwable {
        webDriver.waitForRequestsToFinish();
        baseObject.insertEANcode(eanCode);
        webDriver.waitForRequestsToFinish();
    }

    @When("^Save EAN code of customer$")
    public void saveEANCodeOfCustomer() throws Throwable {
        eanCode = baseObject.getEANCode();
        System.out.println("EAN : " + eanCode);
    }
}
