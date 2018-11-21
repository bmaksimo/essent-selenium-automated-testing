package stepdefinitions.dwp.b2b;
import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.dwp.pageobject.impl.page.ContractPage;
import com.essent.testing.dwp.pageobject.impl.page.MarketberichtenPage;
import com.essent.testing.dwp.pageobject.impl.service_contracting.ContractenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")

public class Contract extends DwpScenario {
    private static String Klantnummer;

    @Before("@REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Contract startdatum is today$")
    public void contractStartdatumIsToday() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        cp.startDateIsToday();
    }


    @And("^Get client number$")
    public void getClientNumber() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        Klantnummer=cp.getClientNumber();
    }

    @And("^Search by client number$")
    public void searchByClientNumber() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        ContractenPage contractenPage = new ContractenPage(webDriver);
        cp.selectAccount();
        cp.searchByClientNuiber(Klantnummer);
        contractenPage.searchForEanCode(Klantnummer);
    }

    @When("^Plus action of \"([^\"]*)\" element from \"([^\"]*)\" and click on \"([^\"]*)\"$")
    public void plusActionOfElementFromAndClickOn(String row, String table, String action) throws Throwable {
        webDriver.waitForRequestsToFinish();
        ContractPage cp = new ContractPage(webDriver);
        BaseObject baseObject = new BaseObject(webDriver);
        Thread.sleep(5000);
        cp.clickOnPlusMeniInTable(row,table);
        baseObject.plusSubaction(action);
    }

    @And("^Save EAN from active contract$")
    public void saveEANFromActiveContract() throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        parameterProvider.put("EAN-active-contract",cp.getActiveContractEAN());
    }

    @Then("^Contract is in \"([^\"]*)\" state$")
    public void contractIsInState(String status) throws Throwable {
        ContractPage cp = new ContractPage(webDriver);
        webDriver.waitForRequestsToFinish();
        assertEquals(cp.status(),status);
    }
}
