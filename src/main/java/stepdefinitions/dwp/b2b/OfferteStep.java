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
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertTrue;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")

public class OfferteStep extends DwpScenario {
    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }


    @And("^Take Offertenummer from firs offerte$")
    public void takeOffertenummerFromFirsOfferte() throws Throwable {
        OffertePage op = new OffertePage(webDriver);
        String offertenummer = op.getOfferteNumber();
        parameterProvider.put("offertenummer", offertenummer);
    }

    @And("^Reset filter$")
    public void resetFilter() throws Throwable {
        OffertePage op = new OffertePage(webDriver);
        op.resetFilter();
    }

    @And("^Click on Offerte with \"([^\"]*)\"$")
    public void clickOnOfferteWith(String offertenummer) throws Throwable {
        String offerteNummer = parameterProvider.getValueOrParameterAsString(offertenummer);

    }

    @And("^Label \"([^\"]*)\" is \"([^\"]*)\"$")
    public void labelIs(String label, String value) throws Throwable {
        OffertePage op = new OffertePage(webDriver);
        op.clickOnLabel(label, value);
    }

    @And("^Filter button is clicked$")
    public void filterButtonIsClicked() throws Throwable {
        OffertePage of = new OffertePage(webDriver);
        of.clickOnFilter();
    }
}
