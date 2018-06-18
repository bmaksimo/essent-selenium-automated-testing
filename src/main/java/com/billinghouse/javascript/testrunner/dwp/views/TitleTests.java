package com.billinghouse.javascript.testrunner.dwp.views;

public enum TitleTests {
    CONTENT_PAGE_CONTAINS_TITLE("var runner = new TestRunner('TrContentPageContainsTitle', {seconds: ${value}, title: '${value1}'}, arguments[arguments.length - 1]);");
    private TitleTests(String test) {
        this.test = test;
    }
    private String test;

    public String getTest() {
        return test;
    }
}
