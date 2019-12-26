package stepdefinitions.dwp.pageobject;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import com.essent.testing.dwp.pageobject.salesmarketing.customerdashboard.workflows.MarketMessagesPage;
import com.essent.testing.table.Filter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.ArrayList;
import java.util.List;


public class MarketBerichtenSteps extends NavigationElements {
    private static String eanCode = null;

    @Before("@DWP or @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @And("^\"([^\"]*)\" turn on$")
    public void turnOn(String label) {
        seleniumDriver.waitForRequestsToFinish();
        new ToggleImpl().clickOnToggle(label);
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
            return mp.getMarketMessageStatus().equalsIgnoreCase(status);
        });
    }

    @Then("^Confirm status is \"([^\"]*)\"$")
    public void confirmStatusIs(String status) {
        MarketMessagesPage mp = new MarketMessagesPage();
        Assert.assertTrue(mp.getMarketMessageStatus().equalsIgnoreCase(status));
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
        Assert.assertEquals(modul, mp.getModuleFromTheFirstTransaction());
        Assert.assertEquals(status, mp.getMarketMessageStatus());

    }

    @Then("^Marketbericht with module \"([^\"]*)\" changed to status \"([^\"]*)\"$")
    public void marketBerichtWithEANAndModuleSecondTransactionIsInStatus(String modul, String status) {
        MarketMessagesPage mp = new MarketMessagesPage();
        Assert.assertEquals(modul, mp.getModuleFromCancelTransaction());
        Assert.assertEquals(status, mp.getMarketMessageCancelStatus());

    }

    @Then("Market message contains:")
    public void checkMarketMessage(final DataTable dbTable) {
        List<List<String>> dataTableFilters = dbTable.asLists();
        List<Filter> filters = new ArrayList<>();
        String currentColumnName;
        String currentColumnValue;

        for (int i = 0; i <= dataTableFilters.size(); i++) {
            currentColumnName = dataTableFilters.get(0).get(i);
            currentColumnValue = currentColumnName.toUpperCase().contains("& ED") ?
                toDwpEndDate(dataTableFilters.get(1).get(i))
                : parameterProvider.getValueOrParameterAsString(dataTableFilters.get(1).get(i));
            Filter filter = new Filter(currentColumnName.toUpperCase(), currentColumnValue);
            filters.add(filter);
        }

        int attempt = 1;
        boolean found = false;
        int maxRetries = 50;
        String arrow = parameterProvider.getValueOrParameterAsString("parameter:navigation");
        String dashboardMenu = parameterProvider.getValueOrParameterAsString("parameter:dashboard-menu");

        while (attempt < maxRetries && !found) {
            try {
                List<WebElement> results = new MarketMessagesPage().selectRowOnTable("Marktberichten", filters, parameterProvider.getCurrentContextParameters());
                found = CollectionUtils.isNotEmpty(results);
            } catch (Exception e) {
                logger().warn(parameterProvider.getCurrentContextParameters() + " - Market message was not found.");
            } finally {
                attempt++;
                if (!found) {
                    Sleeper.sleepTightInSeconds(10);
                    loopBack(arrow, dashboardMenu);
                }
            }
        }

        Assert.assertTrue(found);
    }

    @Then("^Check marktbericht$")
    public void checkMarktbericht(final DataTable dbTable) {
        List<List<String>> info = dbTable.asLists();
        String ean = parameterProvider.getValueOrParameterAsString(info.get(1).get(0));
        String modul = info.get(1).get(1);
        String date = info.get(1).get(2);
        MarketMessagesPage mp = new MarketMessagesPage();
        String eanFromMarketberichtRow1 = "1";
        String eanFromMarketberichtRow2 = "3";
        String eanFromMarketberichtRow3 = "5";
        String modulFromMarketberichtRow1 = "2";
        String modulFromMarketberichtRow2 = "4";
        String modulFromMarketberichtRow3 = "6";
        String marketberichtEndDateElementRow1 = "1";
        String marketberichtEndDateElementRow2 = "5";
        String marketberichtEndDateElementRow3 = "9";

        if (ean.equalsIgnoreCase(mp.getEan(eanFromMarketberichtRow1)) && (mp.getModule(modulFromMarketberichtRow1).equals(modul))) {
            Assert.assertEquals(modul, mp.getModule(modulFromMarketberichtRow1));
            Assert.assertEquals(mp.getEndDate(marketberichtEndDateElementRow1), toDwpEndDate(parameterProvider.getValueOrParameterAsString(date)));
        } else {
            if (ean.equalsIgnoreCase(mp.getEan(eanFromMarketberichtRow2)) && (mp.getModule(modulFromMarketberichtRow2).equals(modul))) {
                Assert.assertEquals(modul, mp.getModule(modulFromMarketberichtRow2));
                Assert.assertEquals(mp.getEndDate(marketberichtEndDateElementRow2), toDwpEndDate(parameterProvider.getValueOrParameterAsString(date)));
            } else {
                if (ean.equalsIgnoreCase(mp.getEan(eanFromMarketberichtRow3)) && (mp.getModule(modulFromMarketberichtRow3).equals(modul))) {
                    Assert.assertEquals(modul, mp.getModule(modulFromMarketberichtRow3));
                    Assert.assertEquals(mp.getEndDate(marketberichtEndDateElementRow3), toDwpEndDate(parameterProvider.getValueOrParameterAsString(date)));
                }
            }
        }
    }

    //TODO delete if not needed
    @And("^Marktbericht has label \"([^\"]*)\"$")
    public void marketMessageHasLabel(String label) {
        seleniumDriver.waitForRequestsToFinish();
        MarketMessagesPage mp = new MarketMessagesPage();
        Assert.assertEquals("Actual label differs from expected", mp.getMarketMessageLabel(), label);
    }

    @And("^Extern bericht is \"([^\"]*)\"$")
    public void externBerichtIs(String label) {
        MarketMessagesPage mp = new MarketMessagesPage();
        mp.selectExternalMessage(label);
    }
}
