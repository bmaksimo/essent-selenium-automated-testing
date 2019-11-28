package com.essent.testing.dwp.scenario;

import com.billinghouse.testautomation.util.dsl.DateExpressionsUtil;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model.Execution;
import com.essent.automation.autocrat.Model.Step;
import com.essent.automation.util.Sleeper;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.scenario.RegisteredScenario;
import com.essent.testing.selenium.DWPSeleniumDriver;
import com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter;
import org.apache.commons.lang3.text.StrSubstitutor;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.billinghouse.testautomation.util.dsl.DateExpressionsUtil.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public abstract class DwpScenario extends RegisteredScenario {

    @Resource(name = "dwpSeleniumDriver")
    protected DWPSeleniumDriver seleniumDriver;

    protected void isDwpRunning() {
        String dwpUrl = ConfigProvider.getProperty(ConfigKey.DWP_BASE_URL);
        seleniumDriver.setBaseUrl(dwpUrl);
        seleniumDriver.goToHomePage();
        String currentUrl = seleniumDriver.getDriver().getCurrentUrl();
        if (null != currentUrl && !currentUrl.equals(dwpUrl)) {
            seleniumDriver.setBaseUrl(currentUrl);
            seleniumDriver.goToHomePage();
        }
        Sleeper.sleepTightInSeconds(3);
        logger().debug("Current URL: " + currentUrl);
        assertNotNull(currentUrl);
        assertTrue(currentUrl.startsWith(seleniumDriver.getBaseUrl()));
    }

    protected Execution createExecution() {
        return AutocratExecutionAdapter.newExecution();
    }

    protected Step createStep(Action action) {
        return new Step().action(action);
    }

    protected boolean execute(final Execution execution) {
        seleniumDriver.waitForRequestsToFinish();
        return AutocratExecutionAdapter.execute(seleniumDriver.getDriver(), execution);
    }

    protected String toDwpDate(String parameter) {
        return checkAndConvertToDwpDate(parameter);
    }

    protected String toDwpApiDate(String parameter) {
        return checkAndConvertToDwpApiDate(parameter);
    }

    protected String toDwpTime(String parameter) {
        return convertToDwpTime(parameter);
    }

    protected String toDwpEndDate(String parameter) {
        return DateExpressionsUtil.checkAndConvertToDwpContracEndDate(parameter);
    }

    protected void injectJavaScriptTestRunner() {
        seleniumDriver.injectJavaScriptTestRunner();
    }

    protected boolean executeJavascriptTest(String registeredJsClass, Object options) {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.executeJavascriptTest(registeredJsClass, options);
    }

    public boolean executeJavascriptTest(String registeredJsClass, Object options, boolean withException) {
        return seleniumDriver.executeJavascriptTest(registeredJsClass, options, withException);
    }

    public boolean executeJavascriptTestImmediately(String registeredJsClass, Object options, boolean withException) {
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(5);
        return seleniumDriver.executeJavascriptTestImmediately(registeredJsClass, options, withException);
    }

    protected Map executeJavascriptMethod(String registeredJsClass, Object options) {
        return seleniumDriver.executeJavascriptMethod(registeredJsClass, options);
    }

    public void tearDown() {
        if (seleniumDriver != null) {
            tidyUp(seleniumDriver);
        }
    }

    protected void setUpWebDriver() throws Exception {
        setUpWebDriver(seleniumDriver);
        seleniumDriver.initNgWebDriver();
    }

    protected String createQuery(String template, String key, String value) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put(key, value);
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(template);
    }
}
