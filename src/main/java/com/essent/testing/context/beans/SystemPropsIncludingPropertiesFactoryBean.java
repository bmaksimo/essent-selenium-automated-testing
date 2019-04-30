package com.essent.testing.context.beans;

import org.springframework.beans.factory.config.PropertiesFactoryBean;

import java.io.IOException;
import java.util.Properties;

public class SystemPropsIncludingPropertiesFactoryBean extends PropertiesFactoryBean {

  @Override
  protected Properties createProperties() throws IOException {
    Properties properties = super.createProperties();
    Properties systemProperties = System.getProperties();

    for (Object key : systemProperties.keySet()) {
      String keyAsString = (String) key;
      if (keyAsString.startsWith("ssh.")) {
        properties.setProperty(keyAsString, systemProperties.getProperty(keyAsString));
      }
    }

    return properties;
  }
}
