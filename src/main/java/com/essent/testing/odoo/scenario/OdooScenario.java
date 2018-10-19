package com.essent.testing.odoo.scenario;

import com.essent.automation.util.Sleeper;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.selenium.scenario.SeleniumScenario;
import org.apache.log4j.Logger;

import static org.junit.Assert.assertTrue;

public abstract class OdooScenario extends SeleniumScenario {

    private  final static Logger logger = Logger.getLogger(OdooScenario.class);

    private String name;

    public String getName() {
        return name;
    }

    protected void isOdooRunning() throws Exception {
        String dwpUrl = ConfigProvider.getProperty(ConfigKey.ODOO_BASE_URL);
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
