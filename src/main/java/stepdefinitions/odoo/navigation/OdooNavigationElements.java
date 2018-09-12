package stepdefinitions.odoo.navigation;

import com.essent.testing.odoo.scenario.OdooScenario;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class OdooNavigationElements extends OdooScenario {

    protected void OdooClickTopMenuAction(String name) {
        boolean success = new OdooClickTopMenuAction().test(name);
        assertThat(String.format("Top Menu item %s was not available.", name),
            success, is(true));
    }

    private class OdooClickTopMenuAction implements Predicate<String> {
        @Override
        public boolean test(String name) {
            Map<String, String> options = new HashMap<>();
            options.put("name", name);
            boolean success = executeJavascriptTest("TrOdooClickTopMenu", options);
            return success;
        }
    }

}
