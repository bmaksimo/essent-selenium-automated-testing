package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.page.SoctarTariffBatchDetails;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.Assert.assertTrue;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")
public class Soctar extends DwpScenario{

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @Then("^Soctar Tariff Type is \"([^\"]*)\" and Tariff Status is \"([^\"]*)\"$")
    public void checkSuccess(String tariffType, String tariffStatus) throws Throwable {
        SoctarTariffBatchDetails soc = new SoctarTariffBatchDetails(webDriver);
        assertTrue(soc.getTariffType().equalsIgnoreCase(tariffType));
        assertTrue(soc.getTariffStatus().equalsIgnoreCase(tariffStatus));

    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

}
