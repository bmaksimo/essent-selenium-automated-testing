package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.page.DwpAccountOverviewPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.Assert.assertTrue;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")

public class Account extends DwpScenario {
    @Before("@B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @And("^Activate dunning stop$")
    public void activateDunningstop() throws InterruptedException {
        DwpAccountOverviewPage aop = new DwpAccountOverviewPage(webDriver);
        Thread.sleep(2500);
        webDriver.waitForRequestsToFinish();
        aop.clickOnDuningStopCheckbox();
        aop.clickOnSaveButtonForFinanceAndLegalSection();

//        if(!aop.checkIfCheckboxIsChecked(box)){
//            aop.clickOnDuningStopCheckbox();
//        }
    }

    @Then("^Change is immediately visible in Finance & Legal section$")
    public void changeIsImmediatelyVisibleInFinanceLegalSection() throws Throwable {
        DwpAccountOverviewPage daop = new DwpAccountOverviewPage(webDriver);
        assertTrue(daop.checkIfDunningStopCheckboxIsChecked());
        //daop.putDuningStopBackToOff();

    }

    @And("^Activate \"([^\"]*)\"$")
    public void activate(String box) throws Throwable {
        DwpAccountOverviewPage aop = new DwpAccountOverviewPage(webDriver);
        Thread.sleep(2500);
        webDriver.waitForRequestsToFinish();
        aop.clickCheckbox(box);
        aop.clickOnSaveButtonForFinanceAndLegalSection();
    }
}









