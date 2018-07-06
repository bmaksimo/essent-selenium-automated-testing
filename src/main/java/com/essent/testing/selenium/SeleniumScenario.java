package com.essent.testing.selenium;

import org.junit.AfterClass;

import java.math.BigDecimal;
import java.util.Map;


public class SeleniumScenario {


    static protected SeleniumDriver webDriver;

    public void tidyUp() {
        if(webDriver != null) {
            webDriver.tearDown();
            webDriver = null;
        }
    }

    public void setUpWebDriver() throws Exception {
        if (webDriver == null) {
            webDriver = new SeleniumDriver();
            webDriver.setUp();
        } else {
            tidyUp();
            webDriver = new SeleniumDriver();
            webDriver.setUp();
        }
        webDriver.getDriver().manage().window().maximize();
    }

    protected boolean equals(Object expected, Object actual) {
        if (expected == null || (expected instanceof String && ((String) expected).isEmpty())) {
            return actual == null || (actual instanceof String && ((String) actual).isEmpty());
        }

        if (expected instanceof BigDecimal) {
            expected = ((BigDecimal) expected).doubleValue();
        }

        if (actual instanceof BigDecimal) {
            actual = ((BigDecimal) actual).doubleValue();
        }

        return expected.equals(actual);
    }

    protected void injectJavaScriptTestRunner() {
        webDriver.injectJavaScriptTestRunner();

    }

    protected boolean executeJavascriptTest(String registeredJsClass, Object options)  {
        return webDriver.executeJavascriptTest(registeredJsClass, options);
    }

    public static boolean executeJavascriptTest(String registeredJsClass, Object options, boolean withException) {
        return webDriver.executeJavascriptTest(registeredJsClass, options, withException);
    }

    protected Map executeJavascriptMethod(String registeredJsClass, Object options)  {
        Map map = webDriver.executeJavascriptMethod(registeredJsClass, options);
        return map;
    }

    @AfterClass
    public void tearDown() throws Exception {
        if (webDriver != null) {
            tidyUp();
        }
    }
}
