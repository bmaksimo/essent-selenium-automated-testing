package stepdefinitions.dwp.page_object;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.workflows.MarketMessagesPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import io.cucumber.datatable.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.List;


public class MarketBerichtenSteps extends DwpScenario {
    private static String eanCode = null;

    @Before("@DWP or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @And("^\"([^\"]*)\" turn on$")
    public void turnOn(String label) {
        seleniumDriver.waitForRequestsToFinish();
        ToggleImpl ti = new ToggleImpl();
        ti.clickOnToggle(label);
    }

    @And("^\"([^\"]*)\" turn on waiting for (\\d+) seconds$")
    public void turnOn(String label, int waitingTime) {
        Sleeper.sleepTightInSeconds(waitingTime);
        ToggleImpl ti = new ToggleImpl();
        ti.switchOnNow(label, waitingTime);
    }

    @When("^Save EAN code of customer$")
    public void saveEANCodeOfCustomer() {
        MarketMessagesPage marktberichtenPage = new MarketMessagesPage();
        seleniumDriver.waitForRequestsToFinish();
        eanCode = marktberichtenPage.getEanCode();
        parameterProvider.put("eanCode", eanCode);
    }

    @When("^Save EAN code of customer now$")
    public void saveEANCodeOfCustomerNow() {
        MarketMessagesPage marktberichtenPage = new MarketMessagesPage();
        eanCode = marktberichtenPage.getEanCodeNow();
        parameterProvider.put("eanCode", eanCode);
    }

    @Then("^Validate rejection status is \"([^\"]*)\"$")
    public void validateRejection(String rejectionStatus) {
        MarketMessagesPage marktberichtenPage = new MarketMessagesPage();
        Assert.assertTrue(marktberichtenPage.validateRejectionHeader(eanCode));
        Assert.assertTrue(marktberichtenPage.validateRejectionHeader(rejectionStatus));
    }

    @Override
    @After("@DWP or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @Then("^Validate contract was \"([^\"]*)\" and \"([^\"]*)\"$")
    public void validateContractWasTakenOver(String taken, String signed) {
        MarketMessagesPage marketMessagesPage = new MarketMessagesPage();
        marketMessagesPage.takenOver(taken, signed);
    }

    @When("^Refresh \"([^\"]*)\" till \"([^\"]*)\" is visible$")
    public void refreshTillIsVisible(String name, String status) {
        MarketMessagesPage mp = new MarketMessagesPage();
        int atMostSeconds = 450;
        FluentWait<MarketMessagesPage> waiter = waiter(mp, atMostSeconds, 10);
        waiter.withMessage(String.format("Message status did not switch to \"%s\" within \"%s\" seconds", status, atMostSeconds));
        waiter.until((MarketMessagesPage page) -> {
            mp.refreshByName(name);
            return mp.marketberichtStatus().equalsIgnoreCase(status);
        });
    }

    @Then("^Confirm status is \"([^\"]*)\"$")
    public void confirmStatusIs(String status) {
        MarketMessagesPage mp = new MarketMessagesPage();
        Assert.assertTrue(mp.marketberichtStatus().equalsIgnoreCase(status));
    }

    @And("^\"([^\"]*)\" is now$")
    public void isNow(String label) {
        BaseObjectPage bo = new BaseObjectPage();
        bo.dateIsNow(label);
    }

    @Then("^Marketbericht with EAN \"([^\"]*)\" and module \"([^\"]*)\" is in status \"([^\"]*)\"$")
    public void marketBerichtWithEANAndModuleIsInStatus(String enaP, String modul, String status) {
        String ean = parameterProvider.getValueOrParameterAsString(enaP);
        MarketMessagesPage mp = new MarketMessagesPage();
        Assert.assertEquals(ean, mp.getEanFromTheFirstTransaction());
        Assert.assertEquals(modul, mp.getModulFromTheFirstTransaction());
        Assert.assertEquals(status, mp.marketberichtStatus());

    }

    @Then("^Marketbericht with module \"([^\"]*)\" changed to status \"([^\"]*)\"$")
    public void marketBerichtWithEANAndModuleSecondTransactionIsInStatus(String modul, String status) {
        MarketMessagesPage mp = new MarketMessagesPage();
        Assert.assertEquals(modul, mp.getModulFromCancelTransaction());
        Assert.assertEquals(status, mp.marketberichtCancelStatus());

    }

    @Then("^Check marktbericht$")
    public void checkMarktbericht(final DataTable dbTable) {
        List<List<String>> info = dbTable.asLists();
        String ean = parameterProvider.getValueOrParameterAsString(info.get(1).get(0));
        String modul = info.get(1).get(1);
        String date = info.get(1).get(2);
        MarketMessagesPage mp = new MarketMessagesPage();
        String module = "UPDATE BUSINESS MASTER DATA";
        String eanFromMarketberichtRow1 = "1";
        String eanFromMarketberichtRow2 = "3";
        String eanFromMarketberichtRow3 = "5";
        String modulFromMarketberichtRow1 = "2";
        String modulFromMarketberichtRow2 = "4";
        String modulFromMarketberichtRow3 = "6";
        String marketberichtEndDateElementRow1 = "1";
        String marketberichtEndDateElementRow2 = "5";
        String marketberichtEndDateElementRow3 = "9";


        if (ean.equalsIgnoreCase(mp.getEanFromMarketbericht(eanFromMarketberichtRow1)) && !(mp.getModulFromMarketbericht(modulFromMarketberichtRow1).equals(module)) ) {
            Assert.assertEquals(modul, mp.getModulFromMarketbericht(modulFromMarketberichtRow1));
            Assert.assertEquals(mp.getMarketberichtEndDateElement(marketberichtEndDateElementRow1), toDwpEndDate(parameterProvider.getValueOrParameterAsString(date)));
        } else {
            if (ean.equalsIgnoreCase(mp.getEanFromMarketbericht(eanFromMarketberichtRow2)) && !(mp.getModulFromMarketbericht(modulFromMarketberichtRow2).equals(module)) ) {
                Assert.assertEquals(modul, mp.getModulFromMarketbericht(modulFromMarketberichtRow2));
                Assert.assertEquals(mp.getMarketberichtEndDateElement(marketberichtEndDateElementRow2), toDwpEndDate(parameterProvider.getValueOrParameterAsString(date)));
            } else {
                if (ean.equalsIgnoreCase(mp.getEanFromMarketbericht(eanFromMarketberichtRow3)) && !(mp.getModulFromMarketbericht(modulFromMarketberichtRow3).equals(module)) ) {
                    Assert.assertEquals(modul, mp.getModulFromMarketbericht(modulFromMarketberichtRow3));
                    Assert.assertEquals(mp.getMarketberichtEndDateElement(marketberichtEndDateElementRow3), toDwpEndDate(parameterProvider.getValueOrParameterAsString(date)));
                }
            }
        }
    }

    @And("^Marktbericht has label \"([^\"]*)\"$")
    public void marketMessageHasLabel(String label) {
        MarketMessagesPage mp = new MarketMessagesPage();
        Assert.assertEquals("Actual label differs from expected", mp.getMarketMessageLabel(), label);
    }
}
