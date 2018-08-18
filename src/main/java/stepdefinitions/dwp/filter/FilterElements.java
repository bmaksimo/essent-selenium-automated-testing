package stepdefinitions.dwp.filter;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.model.FilterElementConverter;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.essent.automation.autocrat.Action.CLICK;
import static com.essent.automation.autocrat.Action.REQUIRE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FilterElements extends NavigationElements {

    private static final String FILTER_BUTTON_ELEMENT = "FILTER_BUTTON_ELEMENT";
    private static final String FILTER_BUTTON_ELEMENT_QUERY = ".icon-filters";


    @Before("@SMOKE, @QUOTE, @MENU, @FILTER")
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

    private class VerifyFilterElements implements Function<DataTable, List<String>> {
        @Override
        public List<String> apply(DataTable filterElements) {
            List<Model.Element> elements = FilterElementConverter.get().getElements(filterElements);
            List<String> failures = elements.stream().filter(element ->
            {
                Model.Execution execution = createExecution().element("LABEL", element);
                execution.flow().step((new Model.Step().action(REQUIRE).element("LABEL")));
                return !execute(execution);
            })
                .map(element->{ return element.getName() + ", " + element.query;}).collect(Collectors.toList());
            return failures;
        }
    }


    @When("^I click on the filter button$")
    public void i_click_on_filter_button() throws Throwable {
        TogggleFilterMode togggleFilterMode = new TogggleFilterMode();
        togggleFilterMode.test(this);
        boolean success = togggleFilterMode.test(this);
        assertThat("DWP Main Filter mode off was expected to be checked successfully, actualy there were verification errors.", success, is(true));

    }

    @When("^Available filter elements are:$")
    public void visitLeftMenuItemFilter(DataTable filterElements) throws Throwable {
        TogggleFilterMode togggleFilterMode = new TogggleFilterMode();
        togggleFilterMode.test(this);
        List<String> failingElements = new VerifyFilterElements().apply(filterElements);
        boolean success = failingElements.isEmpty();
        togggleFilterMode.test(this);
        assertThat(String.format("Filter elements: {%s} were not available.",
            StringUtils.join(failingElements, ";")), success, is(true));
    }


    @Override
    @After("@SMOKE, @QUOTE, @MENU, @FILTER")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
