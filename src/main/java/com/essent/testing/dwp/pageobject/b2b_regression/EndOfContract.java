package com.essent.testing.dwp.pageobject.b2b_regression;

import com.essent.testing.dwp.scenario.DwpScenario;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EndOfContract extends DwpScenario {

    public class ClickOnElement implements Predicate<String> {
        @Override
        public boolean test(String element) {
            Map<String, String> options = new HashMap<>();
            options.put("element", element);
            boolean success = executeJavascriptTest("TrStartNewMarketSection", options);
            return success;
        }
    }

    protected void searchInputField(String input) {
        boolean success = new SearchInputField().test(input);
        assertThat(String.format("Filter element %s is undefined.", input),
            success, is(true));
    }

    public class SearchInputField implements Predicate<String> {
        @Override
        public boolean test(String value) {
            Map<String, String> options = new HashMap<>();
            options.put("value", value);
            boolean success = executeJavascriptTest("TrSearch", options);
            return success;
        }
    }

    protected void simpleExecuteJavaScript(String nameOfJavaScript) {
        boolean success = new ExecuteJavaScript().test(nameOfJavaScript);
        assertThat(String.format("JavaScript file %s is undefined.", nameOfJavaScript),
            success, is(true));
    }

    public class ExecuteJavaScript implements Predicate<String> {
        @Override
        public boolean test(String s) {
            Map<String, Object> options = new HashMap<>();
            options.put("value", s);
            System.out.println(s);
            boolean success = executeJavascriptTest(s, options);
            return success;
        }
    }

    public class EanCheckBox implements Predicate<String> {

        @Override
        public boolean test(String s) {
            boolean success = executeJavascriptTest("TrEanCheckBox", "");
            return success;
        }
    }

    public class SelectButton implements Predicate<String> {
        @Override
        public boolean test(String s) {
            boolean success = executeJavascriptTest("TrSelectButton", "");
            return success;
        }
    }
}
