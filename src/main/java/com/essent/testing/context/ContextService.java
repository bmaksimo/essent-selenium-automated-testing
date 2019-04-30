package com.essent.testing.context;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Properties;

public class ContextService implements ApplicationContextAware {

  private static ApplicationContext context;

  public static ApplicationContext getContext() {
    return context;
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  public static Properties getProperties() {
    return context.getBean("qaProps", Properties.class);
  }

  public static Environment getEnvironment() throws Exception {
    if (getProperties().containsKey("environment")) {
      return Environment.fromValue(ConfigProvider.getProperty(ConfigKey.ENVIRONMENT));
    } else {
      throw new Exception(
          "No Environment property specified in the configuration file. This should never happen.");
    }
  }
}
