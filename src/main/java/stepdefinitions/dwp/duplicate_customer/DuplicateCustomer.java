package stepdefinitions.dwp.duplicate_customer;

import com.essent.testing.dwp.DwpScenario;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;

public class DuplicateCustomer extends DwpScenario {
    @Before("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL @B2B_REGRESSION")
    public void SetupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @FILTER, @RENEWAL, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @And("^Validate customer duplicate$")
    public void validateCustomerDuplicate() {
        Assert.assertTrue(webDriver.findElementOrNull(By.xpath("//*[@id=\"rows\"]/tr[1]/td[3]/list-link-bold-top-two-liner-cell/div/a/h5")).isDisplayed());
    }
}
