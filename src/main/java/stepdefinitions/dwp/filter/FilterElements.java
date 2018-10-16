package stepdefinitions.dwp.filter;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.function.Predicate;

import static com.essent.automation.autocrat.Action.CLICK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FilterElements extends NavigationElements {

    private static final String FILTER_BUTTON_ELEMENT = "FILTER_BUTTON_ELEMENT";
    private static final String FILTER_BUTTON_ELEMENT_QUERY = ".icon-filters";

    @Before("@SMOKE, @E2E, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @MENU, @FILTER")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    private class TogggleFilterMode implements Predicate<FilterElements> {

        @Override
        public boolean test(FilterElements param) {
            Model.Execution execution = createExecution().element(FILTER_BUTTON_ELEMENT, new Model.Element().search("SELECTOR").query(FILTER_BUTTON_ELEMENT_QUERY));
            execution
                .flow()
                .step((new Model.Step().action(CLICK).element(FILTER_BUTTON_ELEMENT)))
                .step(new Model.Step().action(Action.SLEEP).sleepInMillis(2500));
            return execute(execution);
        }
    }

    @When("^I click on the filter button$")
    public void i_click_on_filter_button() throws Throwable {
        TogggleFilterMode togggleFilterMode = new TogggleFilterMode();
        togggleFilterMode.test(this);
        boolean success = togggleFilterMode.test(this);
        assertThat("DWP Main Filter mode off was expected to be checked successfully, actualy there were verification errors.", success, is(true));

    }

    @Override
    @After("@SMOKE, @E2E, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @MENU, @FILTER")
    public void tearDown(Scenario scenario) throws Exception {
        super.tearDown(scenario);
    }
}
