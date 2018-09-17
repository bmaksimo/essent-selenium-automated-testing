package com.billinghouse;

import org.joda.time.*;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.junit.Test;

public class DateTimeTest {

    @Test
    public void testInterval() throws Exception {
        DateTime timerStart = DateTime.now();
        DateTime endOfMeasurement = DateTime.now().plusSeconds(155);
        Interval measurementInterval = new Interval(timerStart, endOfMeasurement);
        Period period = measurementInterval.toPeriod(PeriodType.dayTime());
        PeriodFormatter pf = new PeriodFormatterBuilder().appendHours().appendSuffix(" hour(s)")
            .appendSeparator(", ").appendMinutes().appendSuffix(" minute(s)")
            .appendSeparator(", ").appendSeconds().appendSuffix(" second(s)").toFormatter();
        System.out.println(String.format(" - Measured duration %s", pf.print(period)));
    }

}
