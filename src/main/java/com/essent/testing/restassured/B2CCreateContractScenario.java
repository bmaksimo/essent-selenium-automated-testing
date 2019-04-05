package com.essent.testing.restassured;


import com.essent.testing.scenario.RegisteredScenario;
//import org.apache.log4j.Logger;


public abstract class B2CCreateContractScenario extends RegisteredScenario {

//    private  final static Logger logger = Logger.getLogger(B2CCreateContractScenario.class);

    private String name;

    public String getName() {
        return name;
    }

    /*protected void isJBillingRunning() throws Exception {
+        webDriver.setBaseUrl(dwpUrl);
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
*/
}
