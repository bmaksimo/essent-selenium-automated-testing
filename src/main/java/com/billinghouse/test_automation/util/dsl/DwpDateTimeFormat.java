package com.billinghouse.test_automation.util.dsl;

public enum DwpDateTimeFormat {
  DWP_FRENCH_DATE_FORMAT("dd/MM/yyyy"),
  DWP_API_DATE_FORMAT("yyyy-MM-dd"),
  DWP_SOCTAR_STARTDAT_ENDDATE("1yyyyMMddyyyy1231"),
  DWP_SOCTAR_ENDDATE("31-12-yyyy"),
  DWP_BILLING_DATE_FORMAT("dd-MM-yyyy"),
  DWP_PRODUCT_VALIDNESS_DATE_FORMAT("yyyy-MM-dd"),
  DWP_TIME_FORMAT("HH:mm"),
  DWP_INTERVAL_SEPARATOR("-");

  private String format;

  public String getFormat() {
    return format;
  }

  DwpDateTimeFormat(String format) {
    this.format = format;
  }
}
