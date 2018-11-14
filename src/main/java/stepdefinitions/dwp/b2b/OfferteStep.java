package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.impl.page.DwpAccountOverviewPage;
import com.essent.testing.dwp.pageobject.impl.page.OffertePage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertTrue;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")

public class OfferteStep extends DwpScenario {
    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }


    @And("^Take Offertenummer from first offerte$")
    public void takeOffertenummerFromFirstOfferte() throws Throwable {
        OffertePage op = new OffertePage(webDriver);
        String offertenummer = op.getOfferteNumber();
        parameterProvider.put("offertenummer", offertenummer);
    }

    @And("^Reset filter$")
    public void resetFilter() throws Throwable {
        OffertePage op = new OffertePage(webDriver);
        op.resetFilter();
        webDriver.waitForRequestsToFinish();
    }

    @And("^Label \"([^\"]*)\" is \"([^\"]*)\"$")
    public void labelIs(String label, String value) throws Throwable {
        String input = parameterProvider.getValueOrParameterAsString(value);
        OffertePage op = new OffertePage(webDriver);
        op.clickOnLabel(label, input);
    }

    @And("^Filter button is clicked$")
    public void filterButtonIsClicked() throws Throwable {
        OffertePage of = new OffertePage(webDriver);
        of.clickOnFilter();
    }

    @And("^Offertenummer input is \"([^\"]*)\"$")
    public void offertenummerInputIs(String value) throws Throwable {
        String input = parameterProvider.getValueOrParameterAsString(value);
        OffertePage of = new OffertePage(webDriver);
        of.offerteNumberFieldSendKeys(input);
    }

    @And("^Oplossing text is \"([^\"]*)\"$")
    public void oplossingTextIs(String input) throws Throwable {
        OffertePage of = new OffertePage(webDriver);
        of.markAsDoneOplossingSendKeys(input);
    }

    @Then("^Offerte status is \"([^\"]*)\"$")
    public void statusIs(String status) throws Throwable {
        OffertePage of = new OffertePage(webDriver);
        Assert.assertTrue(of.getStatus().contains(status));
    }
}
