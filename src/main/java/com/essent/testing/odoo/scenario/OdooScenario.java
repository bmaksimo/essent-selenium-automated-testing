package com.essent.testing.odoo.scenario;

import com.essent.automation.core.WebDriverWait;
import com.essent.automation.util.Sleeper;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.scenario.RegisteredScenario;
import com.essent.testing.selenium.OdooSeleniumDriver;
import org.apache.commons.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import javax.annotation.Resource;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public abstract class OdooScenario extends RegisteredScenario {

    private final static Logger logger = Logger.getLogger(OdooScenario.class);
    private String name;

    public String getName() {
        return name;
    }

    @Resource(name="odooSeleniumDriver")
    protected OdooSeleniumDriver seleniumDriver;

    protected void isOdooRunning() throws Exception {

        String dwpUrl = ConfigProvider.getProperty(ConfigKey.ODOO_BASE_URL);
        seleniumDriver.setBaseUrl(dwpUrl);
        seleniumDriver.goToHomePage();
        String currentUrl = seleniumDriver.getDriver().getCurrentUrl();
        if (null != currentUrl && !currentUrl.equals(dwpUrl)) {
            seleniumDriver.setBaseUrl(currentUrl);
            seleniumDriver.goToHomePage();
        }
        Sleeper.sleepTightInSeconds(3);
        logger.info("Current URL: " + currentUrl);
        assertTrue(currentUrl.startsWith(seleniumDriver.getBaseUrl()));
    }

    protected void awaitOdooRequestToFinish(int seconds) {
        new WebDriverWait(seleniumDriver.getDriver(), seconds).until(webDriver -> webDriver.findElements(By.cssSelector(".oe_wait")).isEmpty());
    }

    protected String createQuery(String template, Map valuesMap) {
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(template);
    }

    protected void setUpWebDriver() throws Exception {
        setUpWebDriver(seleniumDriver);
        seleniumDriver.initOdooWebDriver();
    }

    protected void tearDown() {
        if (seleniumDriver != null) {
            tidyUp(seleniumDriver);
        }
    }
}
