package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.navigation.DwpTopMenu;
import com.essent.testing.dwp.pageobject.impl.page.DwpHomePage;
import com.essent.testing.dwp.pageobject.impl.page.MarktBerichtenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;


public class Navigation extends DwpScenario {


    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @And("^\"([^\"]*)\" is clicked$")
    public void isClicked(String srt) throws Throwable {
        DwpHomePage hp = new DwpHomePage();
        hp.clickOnNewCase();
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @Then("^Verify status is \"([^\"]*)\" and \"([^\"]*)\"$")
    public void verifyStatusIsAnd(String external, String status) throws Throwable {
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
    public void goBackToHomeScreen() throws Throwable {
        DwpTopMenu tm = new DwpTopMenu();
        tm.goBackToHomePage();
    }

    @When("^Refresh \"([^\"]*)\" till \"([^\"]*)\" is visible in table$")
    public void refreshTillIsVisible(String name, String status) throws Throwable {
        seleniumDriver.waitForRequestsToFinish();
        MarktBerichtenPage mp = new MarktBerichtenPage();
        if (seleniumDriver.findElement(By.xpath("//tr[1]//list-link-bold-top-two-liner-cell/div/a/h5")).isDisplayed()) {
            while (!seleniumDriver.findElementWhenVisible(By.xpath("//tr[1]//list-simple-two-liner-cell/p/span[1]")).getText().equalsIgnoreCase(status)) {
                Thread.sleep(10000);
                mp.refreshByName(name);
            }
        } else if (seleniumDriver.findElement(By.xpath("//tr[3]//list-link-bold-top-two-liner-cell/div/a/h5")).isDisplayed()) {
            while (!seleniumDriver.findElementWhenVisible(By.xpath("//tr[3]//list-simple-two-liner-cell/p/span[1]")).getText().equalsIgnoreCase(status)) {
                Thread.sleep(10000);
                mp.refreshByName(name);
            }
        }
    }
}
