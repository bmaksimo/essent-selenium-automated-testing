package com.essent.testing.datagenerator.vat;

import java.util.Random;
import org.iban4j.CountryCode;

public class VatNumberGenerator {

  public String getVatNum(CountryCode countryCode) {
    switch (countryCode) {
      case BE:
      default:
        int min = 9000000;
        int max = 9999999;
        return getVatNumBe(min, max);
    }
  }

  private String getVatNumBe(int min, int max) {
    int MOD = 97;
    String vatTemplate = "BE0%d%02d";
    int vatNum = getRandom().nextInt((max - min) + 1) + min;
    int code = MOD - vatNum % MOD;
    return String.format(vatTemplate, vatNum, code);
  }

  private Random getRandom() {
    return new Random(System.nanoTime());
  }
}
