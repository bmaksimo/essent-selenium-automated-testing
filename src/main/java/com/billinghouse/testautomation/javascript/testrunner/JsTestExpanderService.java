package com.billinghouse.testautomation.javascript.testrunner;


/**
 * Expands registered Javascript class and options
 * to Javascript invocation string, executed by Selenium
 * */
public interface JsTestExpanderService {

    /**
     * Defines the contract of expanding registered Javascript class,
     * and arguments, in single string for Selenium JS executor
     * @param jsClass Javascriopt class name
     * @param options options passed as argument
     * @return string, invoking the Javascript, expanded to Selenium JS Executor format.
     */
    String expandToJavascript(String jsClass, Object options);
}
