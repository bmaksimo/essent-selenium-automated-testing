package stepdefinitions.dwp.form;

import com.essent.testing.dwp.pageobject.elements.NonEditable;
import com.essent.testing.dwp.pageobject.impl.elements.NonEditableImpl;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Duration;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.FIVE_HUNDRED_MILLISECONDS;
import static org.awaitility.Duration.ONE_SECOND;

public class FormElements extends DwpScenario {

    @Before("@DWP, @CORE, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    private class CheckFormHeader implements Predicate<String> {
        @Override
        public boolean test(String header) {
            int sec = 7;
            Map<String, Object> options = new HashMap<>();
            options.put("schedule_seconds", sec);
            options.put("header", header);
            boolean success = executeJavascriptTest("TrCheckFormHeader", options);
            return success;
        }
    }

    @Then("^Form header is \"([^\"]*)\"$")
    public void checkFormHeader(String formHeader) throws Throwable {
        given().await()
            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
            .pollDelay(ONE_SECOND)
            .atMost(new Duration(30, SECONDS)).until(() -> new CheckFormHeader().test(formHeader));
    }

    @And("^\"([^\"]*)\" field value is \"([^\"]*)\"$")
    public void fieldValueIs(String label, String expectedValue) throws Throwable {
        NonEditable field = new NonEditableImpl(webDriver);
        FluentWait<NonEditable> waiter = new FluentWait<>(field)
            .withTimeout(java.time.Duration.ofSeconds(50))
            .pollingEvery(java.time.Duration.ofSeconds(5));
        waiter.until(new Function<NonEditable, Object>() {
            @Override
            public Object apply(NonEditable nonEditable) {
                return StringUtils.equals(expectedValue, field.getValue(label));
            }
        });
        //assertThat(String.format("Actual value of '%s' was '%s', and this differs from expected '%s'", label, actualValue, expectedValue), expectedValue, equalTo(actualValue));
    }

    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

}
