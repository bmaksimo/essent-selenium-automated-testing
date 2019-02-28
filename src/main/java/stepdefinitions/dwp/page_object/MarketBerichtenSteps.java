package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.dwp.pageobject.impl.page.MarktBerichtenPage;
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
    BaseObject baseObject = new BaseObject();
    private static String eanCode = null;

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^\"([^\"]*)\" turn on$")
    public void turnOn(String label) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        BaseObject baseObject = new BaseObject();
        baseObject.clickOnToggle(label);
    }

    @And("^Insert EAN of customer$")
    public void insertEANOfCustomer() throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        baseObject.insertEANcode(eanCode);
    }

    @When("^Save EAN code of customer$")
    public void saveEANCodeOfCustomer() throws Throwable {
        MarktberichtenPage marktberichtenPage = new MarktberichtenPage();
        seleniumDriver.waitForRequestsToFinish();
        eanCode = marktberichtenPage.getEanCode();
        parameterProvider.put("eanCode", eanCode);
    }

    @Then("^Validate rejection status is \"([^\"]*)\"$")
    public void validateRejection(String rejectionStatus) throws Throwable {
        MarktberichtenPage marktberichtenPage = new MarktberichtenPage();
        Assert.assertTrue(marktberichtenPage.validateRejectionHeader(eanCode));
        Assert.assertTrue(marktberichtenPage.validateRejectionHeader(rejectionStatus));
    }

    @And("^Search for ean code from filters")
    public void searchForEanCodeFromFillter() throws Throwable {
        MarktberichtenPage marktberichtenPage = new MarktberichtenPage();
        marktberichtenPage.setEanCodeInFilter(eanCode);
    }


    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @Then("^Validate contract was \"([^\"]*)\" and \"([^\"]*)\"$")
    public void validateContractWasTakenOver(String taken, String signed) throws Throwable {
        MarktBerichtenPage marktBerichtenPage = new MarktBerichtenPage();
        marktBerichtenPage.takenOver(taken, signed);
    }

    @When("^Refresh \"([^\"]*)\" till \"([^\"]*)\" is visible$")
    public void refreshTillIsVisible(String name, String status) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        MarktBerichtenPage mp = new MarktBerichtenPage();
        Thread.sleep(15000);
        while(!mp.marketberichtStatus().equalsIgnoreCase(status)){
            mp.refreshByName(name);
        }
    }

    @Then("^Confirm status is \"([^\"]*)\"$")
    public void confirmStatusIs(String status) throws Throwable {
        MarktBerichtenPage mp = new MarktBerichtenPage();
        Assert.assertTrue(mp.marketberichtStatus().equalsIgnoreCase(status));
    }

    @And("^\"([^\"]*)\" is now$")
    public void isNow(String label) throws Throwable {
        BaseObject bo = new BaseObject();
        bo.dateIsNow(label);

    }

    @Then("^Marketbericht with EAN \"([^\"]*)\" and module \"([^\"]*)\" is in status \"([^\"]*)\"$")
    public void marketbirichWithEANAndModuleIsInStatus(String enaP, String modul, String status) throws Throwable {
        String ean = parameterProvider.getValueOrParameterAsString(enaP);
        MarktBerichtenPage mp = new MarktBerichtenPage();
        Assert.assertEquals(ean, mp.getEanFromTheFirstTransaction());
        Assert.assertEquals(modul,mp.getModulFromTheFirstTransaction());
        Assert.assertEquals(status,mp.marketberichtStatus());

    }

    @Then("^Marketbericht with module \"([^\"]*)\" changed to status \"([^\"]*)\"$")
    public void marketbirichWithEANAndModuleSecondTransactionIsInStatus(String modul, String status) throws Throwable {
        MarktBerichtenPage mp = new MarktBerichtenPage();
        Assert.assertEquals(modul,mp.getModulFromCancelTransaction());
        Assert.assertEquals(status,mp.marketberichtCancelStatus());

    }


    @Then("^Marketbericht with EAN-CODE \"([^\"]*)\" and MODULE \"([^\"]*)\" is in STATUS \"([^\"]*)\" and has ED \"([^\"]*)\"$")
    public void marketberichtWithEANCODEAndMODULEIsInSTATUSAndHasED(String eanCode, String modul, String status, String date) throws Throwable {
        String ean = parameterProvider.getValueOrParameterAsString(eanCode);
        MarktBerichtenPage mp = new MarktBerichtenPage();
        if (ean == mp.getEanFromMarketbericht("1")){
            Assert.assertEquals(modul,mp.getModulFromMarketbericht("2"));
            Assert.assertEquals(status,mp.marketberichtStatusMarketbericht("1"));
            Assert.assertEquals(mp.getMarketberichtEndDateElement("1"), toDwpEndDate(parameterProvider.getValueOrParameterAsString(date)));
        }else {
            if (ean == mp.getEanFromMarketbericht("3")) {
                Assert.assertEquals(modul, mp.getModulFromMarketbericht("4"));
                Assert.assertEquals(status, mp.marketberichtStatusMarketbericht("5"));
                Assert.assertEquals(mp.getMarketberichtEndDateElement("5"), toDwpEndDate(parameterProvider.getValueOrParameterAsString(date)));
            }else {
                if (ean == mp.getEanFromMarketbericht("5")) {
                    Assert.assertEquals(modul, mp.getModulFromMarketbericht("6"));
                    Assert.assertEquals(status, mp.marketberichtStatusMarketbericht("9"));
                    Assert.assertEquals(mp.getMarketberichtEndDateElement("9"), toDwpEndDate(parameterProvider.getValueOrParameterAsString(date)));
                }
            }
        }
    }
}
