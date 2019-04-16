package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.UpdateCustomerDetailsPage;
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
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Then("^Change is immediately visible in Finance & Legal section that \"([^\"]*)\" is active$")
    public void changeIsImmediatelyVisibleInFinanceLegalSectionThatIsActive(String box) {
        ToggleImpl tg= new ToggleImpl();
        assertTrue(tg.isOn(box));
    }

    @And("^Activate \"([^\"]*)\"$")
    public void activate(String box) {
        UpdateCustomerDetailsPage ucdp = new UpdateCustomerDetailsPage();
        ToggleImpl tg= new ToggleImpl();
        seleniumDriver.waitForRequestsToFinish();
        tg.switchOn(box);
        ucdp.clickOnSaveButtonForFinanceAndLegalSection();
    }

    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

}
