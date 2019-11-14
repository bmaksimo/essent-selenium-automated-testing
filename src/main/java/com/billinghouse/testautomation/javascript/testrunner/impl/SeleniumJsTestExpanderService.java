package com.billinghouse.testautomation.javascript.testrunner.impl;

import com.billinghouse.testautomation.javascript.testrunner.JsTestExpanderService;
import com.billinghouse.testautomation.javascript.testrunner.JsTestRegistry;
import com.google.gson.Gson;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.text.StrSubstitutor;

import java.util.HashMap;
import java.util.Map;


public class SeleniumJsTestExpanderService implements JsTestExpanderService {

    private static final String CALL_TEST = "new ${class-name}(${options}, arguments[arguments.length - 1]);";

    private static final JsTestExpanderService instance = new SeleniumJsTestExpanderService();

    public static final JsTestExpanderService get() {
        return instance;
    }

    private SeleniumJsTestExpanderService() {

    }

    @Override
    public String expandToJavascript(String jsClass, Object options) {
        if(!JsTestRegistry.get().contains(jsClass)){
            throw new CucumberException("Javascript class " + jsClass + " is not registered.");
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
