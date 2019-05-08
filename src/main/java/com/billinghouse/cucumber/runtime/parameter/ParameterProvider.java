package com.billinghouse.cucumber.runtime.parameter;

import com.billinghouse.exception.ExtendedCucumberException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ParameterProvider {

  private static final Logger log = Logger.getLogger(ParameterProvider.class);

  private static final String TEST_PARAMETER_PREFIX = "parameter:";

  private boolean consumeNullValues;

  public boolean containsKey(Object key) {
    return parameters.containsKey(key);
  }

  public Object get(Object key) {
    return parameters.get(key);
  }

  public String getValueOrParameterAsString(String value) {
    return (String) getValueOrParameter(value).toString();
  }

  public Optional<String> getParameterAsString(String parameter) {
    if (parameter.startsWith(TEST_PARAMETER_PREFIX)) {
      String key = StringUtils.replace(parameter, TEST_PARAMETER_PREFIX, "", 1);
      return Optional.ofNullable((String) parameters.get(key));
    } else {
      return Optional.empty();
    }
  }

  public Integer getValueOrParameterAsInt(String value) {
    Object expectedIntParameter = getValueOrParameter(value);
    if (expectedIntParameter instanceof Number) {
      return ((Number) expectedIntParameter).intValue();
    }
    try {
      return Integer.parseInt(expectedIntParameter.toString());
    } catch (NumberFormatException nfe) {
      throw new ExtendedCucumberException(
          "Input parameter " + expectedIntParameter + " doesn't have supported number format");
    }
  }

  public Object getValueOrParameter(String value) {
    if (value.startsWith(TEST_PARAMETER_PREFIX)) {
      String key = StringUtils.replace(value, TEST_PARAMETER_PREFIX, "", 1);
      if (!parameters.containsKey(key)) {
        throw new ExtendedCucumberException(
            String.format("Input parameter %s is undefined", value));
      }
      return parameters.get(key);
    } else return value;
  }

  public Object put(String key, Object value) {
    log.info("STEP:");
    log.info(" - ACTION: PUT_GLOBAL_PARAMETER");
    if (consumeNullValues && value == null) {
      log.warn("WARNING: Null value for output param " + key);
      return null;
    }
    log.info(" - RESULT: Registered global parameter '" + key + "' = " + value);
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

  protected static final Map<String, Object> parameters = new ConcurrentHashMap<>();

  public ParameterProvider consumingNullValues(boolean consumingNullValues) {
    this.consumeNullValues = consumingNullValues;
    return this;
  }

  public String toString() {
    return parameters.toString();
  }
}
