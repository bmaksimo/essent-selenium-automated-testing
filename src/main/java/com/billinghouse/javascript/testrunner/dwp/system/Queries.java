package com.billinghouse.javascript.testrunner.dwp.system;

public enum Queries {

    JQUERY_EVALUATE_XPATH("var runner = new TestRunner('evaluate_xpath', {xpath: '${value}'}, arguments[arguments.length - 1]);"),
    JQUERY_IS_NOT_ACTIVE("return window.jQuery != undefined && jQuery.active === 0");

    private Queries(String test) {
        this.test = test;
    }

    private String test;

    public String getTest() {
        return test;
    }
}
