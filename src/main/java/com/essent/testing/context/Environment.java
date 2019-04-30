package com.essent.testing.context;

import org.apache.commons.lang3.StringUtils;

public enum Environment {
  LOCALHOST,
  LOCALHOST02,
  DEV_WEST,
  DEV2_WEST,
  REGRESSION_WEST,
  REG02,
  TEST_WEST,
  DEMO_WEST,
  EXP_WEST,
  DEV_01,
  DEV_02,
  DEV_03,
  DEV_04,
  DEV_05,
  DEV_INT,
  REG_01,
  BPA_01,
  UAT_01,
  MIG_01,
  MIG_02,
  POC_01,
  DEVINT01,
  DEVINT02,
  UAT02,
  UAT03,
  DEV10;

  public static Environment fromValue(String environmentString) throws Exception {
    for (Environment environment : values()) {
      if (environment.name().equalsIgnoreCase(environmentString)) {
        return environment;
      }
    }

    throw new Exception(
        "Environment is not valid: "
            + environmentString
            + ". Possible environment  are: "
            + StringUtils.join(Environment.values(), ","));
  }
}
