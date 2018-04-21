package com.essent.testing.config;

import com.essent.testing.context.ContextService;


public class ConfigProvider {


    private static String getProperty(String propertyKey) {

        String systemProperty = System.getProperty(propertyKey);

        // system properties get priority
        if (systemProperty != null) {
            return systemProperty;
        } else {
            return ContextService.getProperties().getProperty(propertyKey);
        }

    }

    /**
     * Properties can only be accessed by using the ConfigKey.
     * @param configKey Identifies the property
     * @return its value.
     */
    public static String getProperty(ConfigKey configKey) {
        return getProperty(configKey.getResourceKey());
    }

}
