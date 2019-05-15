package com.essent.testing.dwp.scenario;

import com.billinghouse.random.RandomUser;
import com.billinghouse.test_automation.util.dsl.DateExpressionsUtil;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model.Execution;
import com.essent.automation.autocrat.Model.Step;
import com.essent.automation.util.Sleeper;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.datagenerator.vat.VatNumberGenerator;
import com.essent.testing.scenario.RegisteredScenario;
import com.essent.testing.selenium.DWPSeleniumDriver;
import com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter;
import com.google.gson.Gson;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StrSubstitutor;
import org.iban4j.CountryCode;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.JS_TR_GET_RANDOM_USER;
import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToDwpDate;
import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.convertToDwpTime;
import static com.billinghouse.test_automation.util.dsl.NumericUtil.amountAsInt;
import static com.billinghouse.test_automation.util.dsl.NumericUtil.sumOfAmounts;
import static com.essent.testing.dwp.constant.DwpConstants.FLEMISCH_LOCALE;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jim on 27-12-2017.
 *
 */
public abstract class DwpScenario extends RegisteredScenario {




    @Resource(name="dwpSeleniumDriver")
    protected DWPSeleniumDriver seleniumDriver;

    protected void isDwpRunning() throws Exception {
        String dwpUrl = ConfigProvider.getProperty(ConfigKey.DWP_BASE_URL);
        seleniumDriver.setBaseUrl(dwpUrl);
        seleniumDriver.goToHomePage();
        String currentUrl = seleniumDriver.getDriver().getCurrentUrl();
        if (null != currentUrl && !currentUrl.equals(dwpUrl)) {
            seleniumDriver.setBaseUrl(currentUrl);
            seleniumDriver.goToHomePage();
        }
        Sleeper.sleepTightInSeconds(3);
        logger().info("Current URL: " + currentUrl);
        assertTrue(currentUrl.startsWith(seleniumDriver.getBaseUrl()));
    }

    protected String generateVat(String generatorParam) {
        String countryCode = generatorParam.replace("generator:vat:", "");
        if(countryCode.length() > 3 || CountryCode.getByCode(countryCode) == null) {
            throw new CucumberException("Wrong country code: " + countryCode);
        }
        return new VatNumberGenerator().getVatNum(CountryCode.getByCode(countryCode));
    }

    protected String generateCompanyName() {
        Map<String, String> options = new HashMap<>();
        Map reply = executeJavascriptMethod("TrGetRandomUser", options);
        String status = ((String) reply.get("status"));
        boolean success = StringUtils.equals("PASSED", status);
        if (success) {
            Map userData = (Map) reply.get("user");
            RandomUser randomUser = randomUser(userData);
            String first = randomUser.getName().getFirst();
            String last = randomUser.getName().getLast();
            parameterProvider.put("suitecrm-company-account", randomUser);
            parameterProvider.put("contact-person-first-name", first);
            parameterProvider.put("contact-person-last-name", last);
            return first + " " + last;
        }
        else throw new CucumberException("ramdomuser.me API failure");
    }

    protected RandomUser randomUser(Map reply) {
        Gson gson = new Gson();
        String randomUserJs = gson.toJson(reply);
        return gson.fromJson(randomUserJs, RandomUser.class);
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
        return seleniumDriver.executeJavascriptTest(registeredJsClass, options);
    }

    public boolean executeJavascriptTest(String registeredJsClass, Object options, boolean withException) {
        return seleniumDriver.executeJavascriptTest(registeredJsClass, options, withException);
    }

    public boolean executeJavascriptTestImmediately(String registeredJsClass, Object options, boolean withException) {
        return seleniumDriver.executeJavascriptTestImmediately(registeredJsClass, options, withException);
    }

    protected Map executeJavascriptMethod(String registeredJsClass, Object options) {
        return seleniumDriver.executeJavascriptMethod(registeredJsClass, options);
    }

    protected Map executeJavascriptMethodImmediately(String registeredJsClass, Object options) {
        return seleniumDriver.executeJavascriptMethodWithImmediateFlag(registeredJsClass, options, true);
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

  protected Integer amountInCurrencyAsInt(String amountInCurrency) {
    return amountAsInt(amountInCurrency, FLEMISCH_LOCALE);
  }

    protected Integer sumOf(List<String> amounts) {
        return sumOfAmounts(amounts);
    }

    protected String createQuery(String template, String key, String value) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put(key, value);
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(template);
    }
}
