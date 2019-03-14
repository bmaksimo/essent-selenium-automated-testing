package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.customer_dashboard.service.DwpServicePage;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpTopMenu;
import com.essent.testing.dwp.pageobject.customer_dashboard.workflows.MarktBerichtenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.TEN_SECONDS;


public class Navigation extends DwpScenario {


    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @And("^\"([^\"]*)\" is clicked$")
    public void isClicked(String srt) {
        DwpServicePage sp = new DwpServicePage();
        sp.clickOnNewCase();
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @Then("^Verify status is \"([^\"]*)\" and \"([^\"]*)\"$")
    public void verifyStatusIsAnd(String external, String status) {
        seleniumDriver.waitForRequestsToFinish();
        if (status.equalsIgnoreCase("Normaal") || (status.equalsIgnoreCase("Normal"))) {
            Assert.assertTrue(checkStatusIsNormal(external));
        } else {
            Assert.assertTrue(checkStatusValidation(external, status));
        }
    }

    private boolean checkStatusIsNormal(String external) {
        seleniumDriver.waitForRequestsToFinish();
        String externalFromPage = seleniumDriver.findElementWhenVisible(By.xpath("//gridlr[@class='']//blue-sidebar/div/div[2]")).getText();
        return externalFromPage.equalsIgnoreCase(external);
    }

    private boolean checkStatusValidation(String external, String status) {
        seleniumDriver.waitForRequestsToFinish();
        String externalFromPage = seleniumDriver.findElementWhenVisible(By.xpath("//gridlr[@class='']//blue-sidebar/div/div[2]")).getText();
        String statusFromPage = seleniumDriver.findElementWhenVisible(By.xpath("//gridlr[@class='']//blue-sidebar/div/div[3]")).getText();
        return externalFromPage.equalsIgnoreCase(external) && statusFromPage.equalsIgnoreCase(status);
    }

    @And("^Go back to home screen$")
    public void goBackToHomeScreen() {
        DwpTopMenu tm = new DwpTopMenu();
        tm.goBackToHomePage();
    }

    @When("^Refresh \"([^\"]*)\" till \"([^\"]*)\" is visible in table$")
    public void refreshTillIsVisible(String name, String status) {
        seleniumDriver.waitForRequestsToFinish();
        MarktBerichtenPage mp = new MarktBerichtenPage();
        if (seleniumDriver.findElement(By.xpath("//tr[1]//list-link-bold-top-two-liner-cell/div/a/h5")).isDisplayed()) {
            By selector = By.xpath("//tr[1]//list-simple-two-liner-cell/p/span[1]");
            given().await()
                .pollInterval(TEN_SECONDS)
                .atMost(new org.awaitility.Duration(450, SECONDS))
                .until(()-> mp.isRefreshedByName(name)
                    && seleniumDriver.findElementWhenVisible(selector).getText().equalsIgnoreCase(status));
        } else if (seleniumDriver.findElement(By.xpath("//tr[3]//list-link-bold-top-two-liner-cell/div/a/h5")).isDisplayed()) {
            By selector = By.xpath("//tr[3]//list-simple-two-liner-cell/p/span[1]");
            given().await()
                .pollInterval(TEN_SECONDS)
                .atMost(new org.awaitility.Duration(450, SECONDS))
                .until(()-> mp.isRefreshedByName(name)
                    && seleniumDriver.findElementWhenVisible(selector).getText().equalsIgnoreCase(status));
        }
    }
}
