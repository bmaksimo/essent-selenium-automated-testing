package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.page.DwpAccountOverviewPage;
import com.essent.testing.dwp.pageobject.impl.service_contracting.EndOfContractPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.springframework.test.context.ContextConfiguration;
import com.essent.testing.dwp.pageobject.impl.filter.DwpFilter;
import cucumber.api.java.en.When;
import org.junit.Assert;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")

public class Account extends DwpScenario {
    @Before("@B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @And("^Activate dunning stop$")
    public void activateDunningstop() {
        DwpAccountOverviewPage aop = new DwpAccountOverviewPage(webDriver);
        webDriver.waitForRequestsToFinish();
        aop.clickOnDuningStopCheckbox();

//        if(!aop.checkIfCheckboxIsChecked(box)){
//            aop.clickOnDuningStopCheckbox();
//        }
    }
}









