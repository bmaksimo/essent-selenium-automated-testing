package com.billinghouse.javascript.impl;

import com.billinghouse.javascript.JsTestExpanderService;
import com.billinghouse.javascript.JsTestRegistry;
import com.google.gson.Gson;
import cucumber.runtime.CucumberException;
import org.apache.commons.text.StrSubstitutor;

import java.util.HashMap;
import java.util.Map;

/**
 * Expands registered Javascript class name and TrMenuHasLinkIdOptions POJO to a string,
 * which invokes the Javascript method by Selenium Javascript executor.
 */
public class SeleniumJsTestExpanderService implements JsTestExpanderService {

    private static final String CALL_TEST = "new ${class-name}(${options}, arguments[arguments.length - 1]);";

    private static final JsTestExpanderService instance = new SeleniumJsTestExpanderService();

    public static final JsTestExpanderService get() {
        return instance;
    }

    private SeleniumJsTestExpanderService() {

    }

    /**
     * Expands the registered Javascript class name and POJO to a Javascript call.
     * The string will be invoking the asynchronous execution of Javascript method
     * by underlying Selenium Javascript Executor.
     * The internal Selenium callback parameter, arguments[arguments.length - 1], is added to the invoking string.
     * @param jsClass Javascriopt class containing the test. For example, TrMenuHasLinkId
     * @param options options passed as parameter to Javascript test.
     * @return
     */
    @Override
    public String expandToJavascript(String jsClass, Object options) {
        if(!JsTestRegistry.get().contains(jsClass)){
            throw new CucumberException("Javascript claas " + jsClass + " is not registered.");
        }
        Map<String, String> substitutions = new HashMap<>();
        substitutions.put("class-name", jsClass);
        substitutions.put("options", toJson(options));
        StrSubstitutor substitutor = new StrSubstitutor(substitutions);
        return substitutor.replace(CALL_TEST);
    }

    private String toJson(Object options) {
        Gson gson = new Gson();
        return gson.toJson(options);
    }
}
