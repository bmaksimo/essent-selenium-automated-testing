package stepdefinitions.dwp.overview;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.NavigationElements;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class OverviewMenu extends NavigationElements {

    private class ClickOverviewMenu implements Predicate<String> {
        @Override
        public boolean test(String menu) {
            int sec = 5;
            Map<String, Object> options = new HashMap<>();
            options.put("menu", menu);
            return executeJavascriptTest("TrClickOverviewMenuButton", options);
        }
    }

    @Before("@SMOKE, @QUOTE, @MENU, @DWP_SETUP, @FILTER, @RENEWAL")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Overview is ([^\"]*)")
    public void check_overview_menu_item(String menuItem) throws Throwable {
        boolean success = new ClickOverviewMenu().test(menuItem);
        assertThat(String.format("Overview Menu  %s is undefined.", menuItem),
            success, is(true));
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @DWP_SETUP, @FILTER, @RENEWAL")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
