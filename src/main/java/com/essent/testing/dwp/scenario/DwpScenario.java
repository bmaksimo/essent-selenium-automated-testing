package com.essent.testing.dwp.scenario;

import com.billinghouse.random.RandomUser;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model.Execution;
import com.essent.automation.autocrat.Model.Step;
import com.essent.automation.util.Sleeper;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.datagenerator.vat.VatNumberGenerator;
import com.essent.testing.selenium.DWPSeleniumDriver;
import com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter;
import com.essent.testing.selenium.scenario.SeleniumScenario;
import com.google.gson.Gson;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.StringUtils;
import org.iban4j.CountryCode;

import java.util.HashMap;
import java.util.Map;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToDwpDate;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jim on 27-12-2017.
 *
 */
public abstract class DwpScenario extends SeleniumScenario {

    protected DWPSeleniumDriver webDriver;

    protected void isDwpRunning() throws Exception {

        String dwpUrl = ConfigProvider.getProperty(ConfigKey.DWP_BASE_URL);
        webDriver.setBaseUrl(dwpUrl);
        webDriver.goToHomePage();
        String currentUrl = webDriver.getDriver().getCurrentUrl();
        if (null != currentUrl && !currentUrl.equals(dwpUrl)) {
            webDriver.setBaseUrl(currentUrl);
            webDriver.goToHomePage();
        }
        Sleeper.sleepTightInSeconds(3);
        logger().info("Current URL: " + currentUrl);
        assertTrue(currentUrl.startsWith(webDriver.getBaseUrl()));
    }

    protected String generateVat(String generatorParam) {
        String countryCode = generatorParam.replace("generator:vat:", "");
        if(countryCode.length() > 3 || CountryCode.getByCode(countryCode) == null) {
            throw new CucumberException("Wrong country code: " + countryCode);
        }
        return new VatNumberGenerator().getVatNum(CountryCode.getByCode(countryCode));
    }

    private String generateCompanyName() {
        Map<String, String> options = new HashMap<>();
        Map reply = executeJavascriptMethod("TrGetRandomUser", options);
        String status = ((String) reply.get("status"));
        boolean success = StringUtils.equals("PASSED", status);
        if (success) {
            Map userData = (Map) reply.get("user");
            RandomUser randomUser = randomUser(userData);
            String first = randomUser.getName().getFirst();
            String last = randomUser.getName().getLast();
            return first + " & " + last + " Startup";
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
        webDriver.waitForRequestsToFinish();
        return AutocratExecutionAdapter.execute(webDriver.getDriver(), execution);
    }

    protected String toDwpDate(String parameter) {
        return checkAndConvertToDwpDate(parameter);
    }

    protected void injectJavaScriptTestRunner() {
        webDriver.injectJavaScriptTestRunner();
    }

    protected boolean executeJavascriptTest(String registeredJsClass, Object options) {
        return webDriver.executeJavascriptTest(registeredJsClass, options);
    }

    public boolean executeJavascriptTest(String registeredJsClass, Object options, boolean withException) {
        return webDriver.executeJavascriptTest(registeredJsClass, options, withException);
    }

    protected Map executeJavascriptMethod(String registeredJsClass, Object options) {
        return webDriver.executeJavascriptMethod(registeredJsClass, options);
    }

    public void tearDown() {
        if (webDriver != null) {
            tidyUp();
        }
    }

    @Override
    public void tidyUp() {

    }

    @Override
    public void setUpWebDriver() throws Exception {
        webDriver = new DWPSeleniumDriver();
    }

}
