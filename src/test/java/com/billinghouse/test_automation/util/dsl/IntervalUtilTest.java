package com.billinghouse.test_automation.util.dsl;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class IntervalUtilTest {

  private static String EMPTY_DATE = "";

  @Test
  public void productInterval() throws Exception {
    String startEndDateRenewal = "01-04-2020 31-03-2021";
    assertThat(IntervalUtil.containsDate(startEndDateRenewal, EMPTY_DATE), is(true));
    assertThat(
        IntervalUtil.containsDate(
            startEndDateRenewal,
            DateTime.now().toString(DwpDateTimeFormat.DWP_BILLING_DATE_FORMAT.getFormat())),
        is(false));
  }
}
