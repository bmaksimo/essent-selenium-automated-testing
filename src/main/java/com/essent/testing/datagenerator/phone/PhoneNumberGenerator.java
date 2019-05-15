package com.essent.testing.datagenerator.phone;

public class PhoneNumberGenerator {

  private PhoneNumberGenerator() {}

  public static String getMobilePhone() {
      return "+3168" + (int) (Math.floor(Math.random() * 9000000) + 1000000);
  }
}
