package com.billinghouse.testautomation.util.dsl;

public enum EssentDateTimeFormat {
  DWP_FRENCH_DATE_FORMAT("dd/MM/yyyy"),
  DWP_API_DATE_FORMAT("yyyy-MM-dd"),
  DWP_SOCTAR_STARTDAT_ENDDATE("1yyyyMMddyyyy1231"),
  DWP_SOCTAR_ENDDATE("31-12-yyyy"),
  DWP_BILLING_DATE_FORMAT("dd-MM-yyyy"),
  DWP_PRODUCT_VALIDNESS_DATE_FORMAT("yyyy-MM-dd"),
  DWP_TIME_FORMAT("HH:mm"),
  DWP_INTERVAL_SEPARATOR("-"),
  ODOO_DATE_FORMAT("MM/dd/yyyy");

  private String format;

  public String getFormat() {
    return format;
  }

  EssentDateTimeFormat(String format) {
    this.format = format;
  }
}
