package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.navigation.DwpPlusMenu;
import com.essent.testing.dwp.pageobject.impl.page.DwpHomePage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;


public class Navigation extends DwpScenario {


    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @And("^\"([^\"]*)\" is clicked$")
    public void isClicked(String srt) throws Throwable {
        DwpHomePage hp= new DwpHomePage(webDriver);
        hp.clickOnNewCase();
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
