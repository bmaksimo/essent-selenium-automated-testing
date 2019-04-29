package com.billinghouse.test_automation.javascript;

import com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry;
import com.billinghouse.test_automation.javascript.testrunner.impl.SeleniumJsTestExpanderService;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.*;

public class JsTestProviderServiceTest {
    @Test
    public void getJsTestCall() throws Exception {
        Map<String, Object> options = new HashMap<>();
        JsTestRegistry.get().register(JS_TR_GET_RANDOM_USER);
        assertNotNull(SeleniumJsTestExpanderService.get().expandToJavascript(JS_TR_GET_RANDOM_USER, options));

    }
}
