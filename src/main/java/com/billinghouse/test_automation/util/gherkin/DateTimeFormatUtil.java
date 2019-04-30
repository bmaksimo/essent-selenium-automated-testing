package com.billinghouse.test_automation.util.gherkin;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class DateTimeFormatUtil {
  private static final String DWP_BIRT_DATE_FOMAT = "dd/MM/yyyy";

  private static final PeriodFormatter PERIOD_FORMATTER =
      new PeriodFormatterBuilder()
          .appendHours()
          .appendSuffix(" hour(s)")
          .appendSeparator(", ")
          .appendMinutes()
          .appendSuffix(" minute(s)")
          .appendSeparator(", ")
          .appendSeconds()
          .appendSuffix(" second(s)")
          .appendSeparator(", ")
          .appendMillis()
          .appendSuffix(" milli(s)")
          .toFormatter();

  public static String getBirthDate(String isoBirtDate) {
    return DateTime.parse(isoBirtDate).toString(DWP_BIRT_DATE_FOMAT);
  }

  public static final String printPeriod(Period period) {
    return PERIOD_FORMATTER.print(period);
  }
}
