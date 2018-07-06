package com.billinghouse.test_automation.javascript.testrunner;

import java.util.HashSet;
import java.util.Set;

public class JsTestRegistry {
    private static final JsTestRegistry instance = new JsTestRegistry();
    public static final JsTestRegistry get() { return instance; }
    private static final Set<String> javaScriptClasses = new HashSet<>();

    private JsTestRegistry() {

    }

    public boolean register(String jsClass) {
        return javaScriptClasses.add(jsClass);
    }

    public boolean contains(String jsClass) {
        return javaScriptClasses.contains(jsClass);
    }
}
