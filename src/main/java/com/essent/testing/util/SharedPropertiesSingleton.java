package com.essent.testing.util;

import java.util.HashMap;
import java.util.Map;

public class SharedPropertiesSingleton {

    private static SharedPropertiesSingleton instance = null;
    private Map<String, Object> sharedProperties;

    private SharedPropertiesSingleton() { this.sharedProperties = new HashMap<>(); }

    public static SharedPropertiesSingleton getInstance() {
        return null == instance ? new SharedPropertiesSingleton() : instance;
    }

    public Map<String, Object> getSharedProperties() {
        return sharedProperties;
    }
}
