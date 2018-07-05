package com.billinghouse.test_automation.javascript;

import com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry;
import com.billinghouse.test_automation.javascript.testrunner.impl.SeleniumJsTestExpanderService;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class JsTestProviderServiceTest {
    @Test
    public void getJsTestCall() throws Exception {
        String trJsClass = "TrGetRandomUser";
        Map<String, Object> options = new HashMap<>();
        JsTestRegistry.get().register(trJsClass);
        assertNotNull(SeleniumJsTestExpanderService.get().expandToJavascript(trJsClass, options));

    }
}
