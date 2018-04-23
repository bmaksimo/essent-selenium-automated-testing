package com.essent.testing.dwp;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;

public interface DwpConstant {
  String DWP_USER = ConfigProvider.getProperty(ConfigKey.DWP_USER);
  String DWP_PASSWORD = ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD);
  String BASE_URL = ConfigProvider.getProperty(ConfigKey.DWP_BASE_URL);

}
