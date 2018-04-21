package com.billinghouse.javascript;

import com.billinghouse.javascript.impl.SeleniumJsTestExpanderService;
import com.billinghouse.javascript.model.options.TrMenuHasLinkIdOptions;
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
