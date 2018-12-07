package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.dwp.pageobject.impl.page.MarketberichtenPage;
import com.essent.testing.dwp.pageobject.impl.service_contracting.MarktberichtenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class MarketBerichtenSteps extends DwpScenario {
    BaseObject baseObject = new BaseObject(webDriver);
    private static String eanCode = null;

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^\"([^\"]*)\" turn on$")
    public void turnOn(String label) throws Throwable {
        webDriver.waitForRequestsToFinish();
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
        parameterProvider.put("eanCode", eanCode);
    }

    @Then("^Validate rejection status is \"([^\"]*)\"$")
    public void validateRejection(String rejectionStatus) throws Throwable {
        MarktberichtenPage marktberichtenPage = new MarktberichtenPage(webDriver);
        Assert.assertTrue(marktberichtenPage.validateRejectionHeader(eanCode));
        Assert.assertTrue(marktberichtenPage.validateRejectionHeader(rejectionStatus));
    }

    @And("^Search for ean code from filters")
    public void searchForEanCodeFromFillter() throws Throwable {
        MarktberichtenPage marktberichtenPage = new MarktberichtenPage(webDriver);
        marktberichtenPage.setEanCodeInFilter(eanCode);
    }


    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @Then("^Validate contract was \"([^\"]*)\" and \"([^\"]*)\"$")
    public void validateContractWasTakenOver(String taken, String signed) throws Throwable {
        MarketberichtenPage marketberichtenPage = new MarketberichtenPage(webDriver);
        marketberichtenPage.takenOver(taken, signed);
    }

    @When("^Refresh \"([^\"]*)\" till \"([^\"]*)\" is visible$")
    public void refreshTillIsVisible(String name, String status) throws Throwable {
        webDriver.waitForRequestsToFinish();
        MarketberichtenPage mp = new MarketberichtenPage(webDriver);
        Thread.sleep(15000);
        while(!mp.marketberichtStatus().equalsIgnoreCase(status)){
            mp.refreshByName(name);
        }
    }

    @Then("^Confirm status is \"([^\"]*)\"$")
    public void confirmStatusIs(String status) throws Throwable {
        MarketberichtenPage mp = new MarketberichtenPage(webDriver);
        Assert.assertTrue(mp.marketberichtStatus().equalsIgnoreCase(status));
    }

    @And("^\"([^\"]*)\" is now$")
    public void isNow(String label) throws Throwable {
        BaseObject bo = new BaseObject(webDriver);
        bo.dateIsNow(label);

    }

}
