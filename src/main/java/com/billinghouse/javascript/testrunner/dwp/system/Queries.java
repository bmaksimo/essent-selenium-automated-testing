package com.billinghouse.javascript.testrunner.dwp.system;

public enum Queries {
    JQUERY_IS_NOT_ACTIVE("return window.jQuery != undefined && jQuery.active === 0");
    private Queries(String test) {
        this.test = test;
    }
    private String test;
    public String getTest() {
        return test;
    }
}
