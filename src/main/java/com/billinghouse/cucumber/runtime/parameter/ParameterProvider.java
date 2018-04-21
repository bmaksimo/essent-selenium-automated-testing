package com.billinghouse.cucumber.runtime.parameter;

import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ParameterProvider {
    private static final ParameterProvider instance = new ParameterProvider();
    private static final Logger            log = Logger.getLogger(ParameterProvider.class);

    public Object get(Object key) {
        return parameters.get(key);
    }

    public Object put(String key, Object value) {
        log.info("Registering Output param '" + key + ", value: " + value);
        return parameters.put(key, value);
    }

    public Object remove(Object key) {
        return parameters.remove(key);
    }

    public void putAll(Map<? extends String, ? extends Object> m) {
        parameters.putAll(m);
    }

    public boolean remove(Object key, Object value) {
        return parameters.remove(key, value);
    }

    public Object replace(String key, Object value) {
        return parameters.replace(key, value);
    }

    protected final static Map<String, Object> parameters = Collections.synchronizedMap(new HashMap<>());

    private ParameterProvider() {

    }
    public static ParameterProvider get() {
        return instance;
    }

}
