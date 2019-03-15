package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.dwp.pageobject.customer_dashboard.workflows.MarktBerichtenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.FluentWait;


public class MarketBerichtenSteps extends DwpScenario {
    private static String eanCode = null;

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^\"([^\"]*)\" turn on$")
    public void turnOn(String label) {
        seleniumDriver.waitForRequestsToFinish();
        ToggleImpl ti = new ToggleImpl();
        ti.clickOnToggle(label);
    }

    @When("^Save EAN code of customer$")
    public void saveEANCodeOfCustomer() {
        MarktBerichtenPage marktberichtenPage = new MarktBerichtenPage();
        seleniumDriver.waitForRequestsToFinish();
        eanCode = marktberichtenPage.getEanCode();
        parameterProvider.put("eanCode", eanCode);
    }

    @Then("^Validate rejection status is \"([^\"]*)\"$")
    public void validateRejection(String rejectionStatus) {
        MarktBerichtenPage marktberichtenPage = new MarktBerichtenPage();
        Assert.assertTrue(marktberichtenPage.validateRejectionHeader(eanCode));
        Assert.assertTrue(marktberichtenPage.validateRejectionHeader(rejectionStatus));
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @Then("^Validate contract was \"([^\"]*)\" and \"([^\"]*)\"$")
    public void validateContractWasTakenOver(String taken, String signed) {
        MarktBerichtenPage marktBerichtenPage = new MarktBerichtenPage();
        marktBerichtenPage.takenOver(taken, signed);
    }

    @When("^Refresh \"([^\"]*)\" till \"([^\"]*)\" is visible$")
    public void refreshTillIsVisible(String name, String status) {
        MarktBerichtenPage mp = new MarktBerichtenPage();
        int atMostSeconds = 450;
        FluentWait<MarktBerichtenPage> waiter = waiter(mp, atMostSeconds, 10);
        waiter.withMessage(String.format("Message status did not switch to \"%s\" within \"%s\" seconds", status, atMostSeconds));
        waiter.until((MarktBerichtenPage page) -> {
            mp.refreshByName(name);
            return mp.marketberichtStatus().equalsIgnoreCase(status);
        });
    }

    @Then("^Confirm status is \"([^\"]*)\"$")
    public void confirmStatusIs(String status) {
        MarktBerichtenPage mp = new MarktBerichtenPage();
        Assert.assertTrue(mp.marketberichtStatus().equalsIgnoreCase(status));
    }

    @And("^\"([^\"]*)\" is now$")
    public void isNow(String label) {
        BaseObject bo = new BaseObject();
        bo.dateIsNow(label);
    }

    @Then("^Marketbericht with EAN \"([^\"]*)\" and module \"([^\"]*)\" is in status \"([^\"]*)\"$")
    public void marketBerichtWithEANAndModuleIsInStatus(String enaP, String modul, String status) {
        String ean = parameterProvider.getValueOrParameterAsString(enaP);
        MarktBerichtenPage mp = new MarktBerichtenPage();
        Assert.assertEquals(ean, mp.getEanFromTheFirstTransaction());
        Assert.assertEquals(modul,mp.getModulFromTheFirstTransaction());
        Assert.assertEquals(status,mp.marketberichtStatus());

    }

    @Then("^Marketbericht with module \"([^\"]*)\" changed to status \"([^\"]*)\"$")
    public void marketBerichtWithEANAndModuleSecondTransactionIsInStatus(String modul, String status) {
        MarktBerichtenPage mp = new MarktBerichtenPage();
        Assert.assertEquals(modul,mp.getModulFromCancelTransaction());
        Assert.assertEquals(status,mp.marketberichtCancelStatus());

    }


    @Then("^Marketbericht with EAN-CODE \"([^\"]*)\" and MODULE \"([^\"]*)\" is in STATUS \"([^\"]*)\" and has ED \"([^\"]*)\"$")
    public void marketberichtWithEANCODEAndMODULEIsInSTATUSAndHasED(String eanCode, String modul, String status, String date) {
        String ean = parameterProvider.getValueOrParameterAsString(eanCode);
        MarktBerichtenPage mp = new MarktBerichtenPage();
        if (ean.equalsIgnoreCase(mp.getEanFromMarketbericht("1"))){
            Assert.assertEquals(modul,mp.getModulFromMarketbericht("2"));
            Assert.assertEquals(status,mp.marketberichtStatusMarketbericht("1"));
            Assert.assertEquals(mp.getMarketberichtEndDateElement("1"), toDwpEndDate(parameterProvider.getValueOrParameterAsString(date)));
        }else {
            if (ean.equalsIgnoreCase(mp.getEanFromMarketbericht("3"))) {
                Assert.assertEquals(modul, mp.getModulFromMarketbericht("4"));
                Assert.assertEquals(status, mp.marketberichtStatusMarketbericht("5"));
                Assert.assertEquals(mp.getMarketberichtEndDateElement("5"), toDwpEndDate(parameterProvider.getValueOrParameterAsString(date)));
            }else {
                if (ean.equalsIgnoreCase(mp.getEanFromMarketbericht("5"))) {
                    Assert.assertEquals(modul, mp.getModulFromMarketbericht("6"));
                    Assert.assertEquals(status, mp.marketberichtStatusMarketbericht("9"));
                    Assert.assertEquals(mp.getMarketberichtEndDateElement("9"), toDwpEndDate(parameterProvider.getValueOrParameterAsString(date)));
                }
            }
        }
    }
}
