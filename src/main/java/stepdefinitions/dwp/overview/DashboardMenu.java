package stepdefinitions.dwp.overview;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DashboardMenu extends NavigationElements {

    private class ClickDashboardMenu implements Predicate<String> {
        @Override
        public boolean test(String menu) {
            int sec = 5;
            Map<String, Object> options = new HashMap<>();
            options.put("menu", menu);
            return executeJavascriptTest("TrClickDashboardMenuButton", options);
        }
    }

    @Before("@SMOKE, @QUOTE, @QUOTE_MI, @QUOTE_SS, @RENEWAL, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Dashboard menu is ([^\"]*)")
    public void checkDashboardMenuItem(String menuItem) throws Throwable {
        boolean success = new ClickDashboardMenu().test(menuItem);
        assertThat(String.format("Overview Menu  %s is undefined.", menuItem),
            success, is(true));
    }

    @Override
    @After("@SMOKE, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
