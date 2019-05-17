package com.billinghouse;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import static com.billinghouse.test_automation.util.gherkin.DateTimeFormatUtil.printPeriod;

public class DateTimeTest {

  @Test
  public void testInterval() throws Exception {
    DateTime timerStart = DateTime.now();
    DateTime endOfMeasurement = DateTime.now().plusSeconds(155);
    Period period = new Period(timerStart, endOfMeasurement);

    System.out.println(String.format(" - Measured duration %s", printPeriod(period)));
  }
}
