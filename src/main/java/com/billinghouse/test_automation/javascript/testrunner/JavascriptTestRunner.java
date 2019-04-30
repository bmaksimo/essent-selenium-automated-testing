package com.billinghouse.test_automation.javascript.testrunner;

import java.util.Map;

public interface JavascriptTestRunner {

  /**
   * Execute registered Javascript as method and return the result as map
   *
   * @param registeredJsClass registered Javascript Test Class
   * @param options Javascript argument
   * @return
   */
  Map executeJavascriptMethod(String registeredJsClass, Object options);

  /**
   * Execute registered Javascript as test and return <code>true</code> if the result contains
   * 'status' field having 'PASSED' value, and return <code>false</code> otherwise
   *
   * @param registeredJsClass registered Javascript Test Class
   * @param options argument
   * @return
   */
  boolean executeJavascriptTest(String registeredJsClass, Object options);
}
