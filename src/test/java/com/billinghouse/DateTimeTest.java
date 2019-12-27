package com.billinghouse;

import static com.billinghouse.testautomation.util.gherkin.DateTimeFormatUtil.printPeriod;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

public class DateTimeTest {

  @Test
  public void testInterval() throws Exception {
    DateTime timerStart = DateTime.now();
    DateTime endOfMeasurement = DateTime.now().plusSeconds(155);
    Period period = new Period(timerStart, endOfMeasurement);

    System.out.println(String.format(" - Measured duration %s", printPeriod(period)));
  }
}
