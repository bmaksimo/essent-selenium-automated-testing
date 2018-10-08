package stepdefinitions.dwp.b2b;


import com.essent.testing.dwp.pageobject.impl.page.DwpAccountOverviewPage;
import com.essent.testing.dwp.pageobject.impl.page.DwpHomePage;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.api.Scenario;
import cucumber.api.java.Before;

public class Account extends DwpScenario {

    @Before("@B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

}
