package com.essent.testing.util;

import java.util.HashMap;
import java.util.Map;

public class SharedPropertiesSingleton {

    private static SharedPropertiesSingleton instance = null;
    private static Map<String, Object> sharedProperties = new HashMap<>();

    public static SharedPropertiesSingleton getInstance() {
        return null == instance ? new SharedPropertiesSingleton() : instance;
    }

    public Map<String, Object> getSharedProperties() {
        return sharedProperties;
    }
}
