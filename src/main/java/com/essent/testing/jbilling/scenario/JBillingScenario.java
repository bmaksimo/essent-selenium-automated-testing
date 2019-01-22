package com.essent.testing.jbilling.scenario;

import com.essent.automation.util.Sleeper;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.selenium.scenario.AbstractSeleniumScenario;
import com.essent.testing.selenium.webdriver.jbilling.SeleniumDriverJBillingImpl;
import org.apache.log4j.Logger;

import static org.junit.Assert.assertTrue;

public abstract class JBillingScenario extends AbstractSeleniumScenario {

    private  final static Logger logger = Logger.getLogger(JBillingScenario.class);

    public void setUpWebDriver() throws Exception {
        tidyUp();
        webDriver = new SeleniumDriverJBillingImpl();
        webDriver.setUp();
    }

    protected SeleniumDriverJBillingImpl getJBillingWebDriver() {
        return (SeleniumDriverJBillingImpl) webDriver;
    }

    protected void isJBillingRunning() throws Exception {
        String dwpUrl = ConfigProvider.getProperty(ConfigKey.JBILLING_BASE_URL);
        webDriver.setBaseUrl(dwpUrl);
        webDriver.goToHomePage();
        String currentUrl = webDriver.getDriver().getCurrentUrl();
        if (null != currentUrl && !currentUrl.equals(dwpUrl)) {
            webDriver.setBaseUrl(currentUrl);
            webDriver.goToHomePage();
        }
        Sleeper.sleepTightInSeconds(3);
        logger.info("Current URL: " + currentUrl);
        assertTrue(currentUrl.startsWith(webDriver.getBaseUrl()));
    }

}
