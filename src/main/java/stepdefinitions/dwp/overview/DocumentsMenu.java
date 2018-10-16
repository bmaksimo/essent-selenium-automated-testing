package stepdefinitions.dwp.overview;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import stepdefinitions.dwp.navigation.NavigationElements;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DocumentsMenu extends NavigationElements {
    private class ClickDocumentsMenu implements Predicate<String> {
        @Override
        public boolean test(String documentType) {
            Map<String, Object> options = new HashMap<>();
            options.put("documentType", documentType);
            return executeJavascriptTest("TrCheckDocumentType", options);
        }
    }

    @Before("@SMOKE, @E2E, @QUOTE, @BILLING")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Then("^Document with document type ([^\"]*) is available")
    public void checkDashboardMenuItem(String documentType) {
        boolean success = new DocumentsMenu.ClickDocumentsMenu().test(documentType);
        assertThat(String.format("Document with document type  %s was not found.", documentType),
            success, is(true));
    }

    @Override
    @After("@SMOKE, @E2E, @QUOTE, @BILLING")
    public void tearDown(Scenario scenario) throws Exception {
        super.tearDown(scenario);
    }
}
