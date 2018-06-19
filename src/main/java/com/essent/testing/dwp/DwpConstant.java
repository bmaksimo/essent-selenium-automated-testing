package com.essent.testing.dwp;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;

public interface DwpConstant {
  String BASE_URL = ConfigProvider.getProperty(ConfigKey.DWP_BASE_URL);
}
