package com.essent.testing.jbilling.scenario;

import com.essent.automation.util.Sleeper;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.scenario.RegisteredScenario;
import com.essent.testing.selenium.JBillingSeleniumDriver;
import org.apache.log4j.Logger;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

public abstract class JBillingScenario extends RegisteredScenario {

    private  final static Logger logger = Logger.getLogger(JBillingScenario.class);

    @Resource(name="jBillingSeleniumDriver")
    protected JBillingSeleniumDriver seleniumDriver;

    private String name;

    public String getName() {
        return name;
    }

    protected void isJBillingRunning() throws Exception {
        String dwpUrl = ConfigProvider.getProperty(ConfigKey.JBILLING_BASE_URL);
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

    public void setUpWebDriver() throws Exception {
        seleniumDriver = new JBillingSeleniumDriver();
    }

}
