package com.billinghouse.javascript;

import java.util.Map;

public interface JavascriptTestRunner {

    /**
     * Execute registered Javascript as method and return the result as map
     * @param registeredJsClass  registered Javascript Test Class (example: TrMenuHasLinkId)
     * @param options POJO (Java Bean). Example: {@link com.billinghouse.javascript.model.options.TrMenuHasLinkIdOptions}
     * @return
     */
    Map executeJavascriptMethod(String registeredJsClass, Object options);

    /**
     * Execute registered Javascript as test and return
     * <code>true</code> if the result contains 'status' field having 'PASSED' value,
     * and return <code>false</code> otherwise
     * @param registeredJsClass  registered Javascript Test Class (example: TrMenuHasLinkId)
     * @param options POJO (Java Bean). Example: {@link com.billinghouse.javascript.model.options.TrMenuHasLinkIdOptions}
     * @return
     */
    boolean executeJavascriptTest(String registeredJsClass, Object options);
}
