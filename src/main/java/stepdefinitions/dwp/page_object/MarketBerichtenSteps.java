package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.dwp.pageobject.impl.page.MarktberichtenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;

public class MarketBerichtenSteps extends DwpScenario {
    BaseObject baseObject = new BaseObject(webDriver);
    private static String eanCode = null;
    private static String moveIn = "MOVE IN";

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
    public void turnOn(String label) throws Throwable {
        BaseObject baseObject = new BaseObject(webDriver);
        baseObject.clickOnToggle(label);
    }

    @And("^Insert EAN of customer$")
    public void insertEANOfCustomer() throws Throwable {
        webDriver.waitForRequestsToFinish();
        baseObject.insertEANcode(eanCode);
    }

    @When("^Save EAN code of customer$")
    public void saveEANCodeOfCustomer() throws Throwable {
        MarktberichtenPage marktberichtenPage = new MarktberichtenPage(webDriver);
        webDriver.waitForRequestsToFinish();
        eanCode = marktberichtenPage.getEanCode();
    }

    @Then("^Validate rejection$")
    public void validateRejection() throws Throwable {
        MarktberichtenPage marktberichtenPage = new MarktberichtenPage(webDriver);
        Assert.assertTrue(marktberichtenPage.validateRejectionHeader(eanCode));
        Assert.assertTrue(marktberichtenPage.validateRejectionHeader(moveIn));
    }
}
