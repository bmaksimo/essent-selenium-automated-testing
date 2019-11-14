package stepdefinitions.dwp.pageobject;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.navigation.NavigationElements;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PaymentInfoSteps extends NavigationElements {

    @Before("@DWP or @CORE or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @And("^Payment method is Wire Transfer$")
    public void listSwitchedPaymentMethod(){
        final By wireTramsferPaymentMethod = By.xpath("//list-simple-two-liner-cell[contains(@line-2,'Overschrijving')]");
        WebElement element = seleniumDriver.findElementWhenPresent(wireTramsferPaymentMethod);
        assertThat(String.format("View list did not contain payment method %s", "Overschrijving"),
            element, is(notNullValue()));
    }

    @Override
    @After("@DWP or @CORE or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
