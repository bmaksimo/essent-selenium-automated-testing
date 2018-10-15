package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.impl.page.DwpAccountOverviewPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertTrue;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")

public class Account extends DwpScenario {
    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @Then("^Change is immediately visible in Finance & Legal section that \"([^\"]*)\" is active$")
    public void changeIsImmediatelyVisibleInFinanceLegalSectionThatIsActive(String box) throws Throwable {
        ToggleImpl tg= new ToggleImpl(webDriver);
        assertTrue(tg.checkIfCheckboxIsChecked(box));
    }

    @And("^Activate \"([^\"]*)\"$")
    public void activate(String box) throws Throwable {
        DwpAccountOverviewPage aop = new DwpAccountOverviewPage(webDriver);
        ToggleImpl tg= new ToggleImpl(webDriver);
        webDriver.waitForRequestsToFinish();
        tg.clickCheckbox(box);
        aop.clickOnSaveButtonForFinanceAndLegalSection();
    }

    @After("@DWP, @REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
