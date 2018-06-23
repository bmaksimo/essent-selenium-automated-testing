package com.essent.testing.selenium;

import com.google.gson.Gson;
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

    /**
     * @deprecated
     * Use {@link #executeJavascriptMethod(String, Object)}
     * @param callTestRunnerTemplate
     * @param param
     * @return
     */
    protected boolean executeJsTest(String callTestRunnerTemplate, String ...param)  {
        return webDriver.executeJsTest(callTestRunnerTemplate, param);
    }

    /**
     *
     * @param registeredJsClass
     * @param options
     * @return
     */
    protected boolean executeJavascriptTest(String registeredJsClass, Object options)  {
        return webDriver.executeJavascriptTest(registeredJsClass, options);
    }

    /**
     * @deprecated
     * Use {@link #executeJavascriptMethod(String, Object)}
     * @param callJsMethod
     * @param param
     * @return
     */
    public static Map executeJsMethod(String callJsMethod, String... param) {
        return webDriver.executeJsMethod(callJsMethod, param);
    }


    public static String executeJavascriptMethod(String registeredJsClass, Object options)  {
        Map map = webDriver.executeJavascriptMethod(registeredJsClass, options);
        String json = new Gson().toJson(map);
        return json;
    }

    @AfterClass
    public void tearDown() throws Exception {
        if (webDriver != null) {
            tidyUp();
        }
    }
}
