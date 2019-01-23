package com.essent.testing.dwp.scenario;

import com.billinghouse.random.RandomUser;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model.Execution;
import com.essent.automation.autocrat.Model.Step;
import com.essent.automation.util.Sleeper;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.datagenerator.vat.VatNumberGenerator;
import com.essent.testing.selenium.helper.autocrat.AutocratExecutionAdapter;
import com.essent.testing.selenium.scenario.AbstractSeleniumScenario;
import com.essent.testing.selenium.webdriver.dwp.SeleniumDriverDwpImpl;
import com.google.gson.Gson;
import cucumber.runtime.CucumberException;
import org.iban4j.CountryCode;

import java.util.Map;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToDwpDate;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jim on 27-12-2017.
 *
 */
public abstract class DwpScenario extends AbstractSeleniumScenario {

    public void setUpWebDriver() throws Exception {
        tidyUp();
        webDriver = new SeleniumDriverDwpImpl();
        webDriver.setUp();
    }

    protected SeleniumDriverDwpImpl getDwpWebDriver() {
        return (SeleniumDriverDwpImpl) webDriver;
    }

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

    protected void injectJavaScriptTestRunner() {
        getDwpWebDriver().injectJavaScriptTestRunner();
    }

    protected boolean executeJavascriptTest(String registeredJsClass, Object options) {
        return getDwpWebDriver().executeJavascriptTest(registeredJsClass, options);
    }

    public boolean executeJavascriptTest(String registeredJsClass, Object options, boolean withException) {
        return  getDwpWebDriver().executeJavascriptTest(registeredJsClass, options, withException);
    }

    protected Map executeJavascriptMethod(String registeredJsClass, Object options) {
        Map map = getDwpWebDriver().executeJavascriptMethod(registeredJsClass, options);
        return map;
    }

    protected String generateVat(String generatorParam) {
        String countryCode = generatorParam.replace("generator:vat:", "");
        if(countryCode.length() > 3 || CountryCode.getByCode(countryCode) == null) {
            throw new CucumberException("Wrong country code: " + countryCode);
        }
        return new VatNumberGenerator().getVatNum(CountryCode.getByCode(countryCode));
    }

    protected RandomUser randomUser(Map reply) {
        Gson gson = new Gson();
        String randomUserJs = gson.toJson(reply);
        RandomUser randomUser = gson.fromJson(randomUserJs, RandomUser.class);
        return randomUser;
    }

    protected Execution createExecution() {
        return AutocratExecutionAdapter.newExecution();
    }

    protected Step    createStep(Action action) {
        return new Step().action(action);
    }

    protected boolean execute(final Execution execution) {
        webDriver.waitForRequestsToFinish();
        return AutocratExecutionAdapter.execute(webDriver.getDriver(), execution);
    }

    protected String toDwpDate(String parameter) {
        return checkAndConvertToDwpDate(parameter);
    }
}
