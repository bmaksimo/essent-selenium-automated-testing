package com.billinghouse.javascript;


/**
 * Expands registered Javascript class name and TrMenuHasLinkIdOptions POJO to a string,
 * which invokes the Javascript method by any underlying executor of Javascript code.
 */
public interface JsTestExpanderService {

    /**
     * Defines the contract of expanding the registered Javascript class name and POJO to a string,
     * invoking the Javascript method call by any underlying executor of Javascript code.
     * @param jsClass Javascriopt class containing the test. For example, TrMenuHasLinkId
     * @param options options passed as parameter to Javascript test.
     * @return string, invoking the Javascript method.
     */
    String expandToJavascript(String jsClass, Object options);
}
