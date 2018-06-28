package com.billinghouse.test_automation.javascript;

import com.billinghouse.test_automation.javascript.testrunner.impl.SeleniumJsTestExpanderService;
import com.billinghouse.test_automation.javascript.model.options.TrMenuHasLinkIdOptions;
import com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry;
import org.junit.Test;

public class JsTestProviderServiceTest {
    @Test
    public void getJsTestCall() throws Exception {
        TrMenuHasLinkIdOptions options = new TrMenuHasLinkIdOptions();
        options.setLinkId("sales-marketing");
        options.setMenu("mainMenu");
        JsTestRegistry.get().add("TrMenuHasLinkId");
        String invokeTest  = SeleniumJsTestExpanderService.get().expandToJavascript("TrMenuHasLinkId", options);
    }
}
