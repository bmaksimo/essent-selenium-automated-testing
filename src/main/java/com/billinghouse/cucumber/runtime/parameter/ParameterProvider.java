package com.billinghouse.cucumber.runtime.parameter;

import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToDwpDate;


public class ParameterProvider {

    private static final Logger            log = Logger.getLogger(ParameterProvider.class);

    private static final String TEST_PARAMETER_PREFIX = "parameter:";

    private boolean consumeNullValues;

    public Object get(Object key) {
        return parameters.get(key);
    }

    public String getValueOrParameterAsString(String value) {
        return (String)getValueOrParameter(value);
    }

    public String getValueOrParameterAsDate(String value) {
        String valueOrParameter = (String) getValueOrParameter(value);
        return checkAndConvertToDwpDate(valueOrParameter);
    }


    public Object getValueOrParameter(String value) {
        if (value.startsWith(TEST_PARAMETER_PREFIX)) {
            String key = StringUtils.replace(value, TEST_PARAMETER_PREFIX, "", 1);
            if(!parameters.containsKey(key)) {
                throw new CucumberException(String.format("Input parameter %s is undefined", value));
            }
            return parameters.get(key);
        }
        else
            return value;
    }

    public Object put(String key, Object value) {
        if(consumeNullValues && value == null) {
            log.warn("Null value for output param '" + key);
            return null;
        }
        log.info("Registering Output param '" + key + ", value: " + value);
        return parameters.put(key, value);
    }

    public Object remove(Object key) {
        return parameters.remove(key);
    }

    public boolean remove(Object key, Object value) {
        return parameters.remove(key, value);
    }

    public Object replace(String key, Object value) {
        return parameters.replace(key, value);
    }

    protected final static Map<String, Object> parameters = new ConcurrentHashMap<>();

    public ParameterProvider consumingNullValues(boolean consumingNullValues) {
        this.consumeNullValues = consumingNullValues;
        return this;
    }

    private String convertToDwpDate(String formattedDate)  {
        return checkAndConvertToDwpDate(formattedDate);
    }

}
