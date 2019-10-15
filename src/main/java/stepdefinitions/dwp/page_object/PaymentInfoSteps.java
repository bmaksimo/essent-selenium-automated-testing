package stepdefinitions.dwp.page_object;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.*;
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
