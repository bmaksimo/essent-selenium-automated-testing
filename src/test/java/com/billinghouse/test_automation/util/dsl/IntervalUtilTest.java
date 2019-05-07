package com.billinghouse.test_automation.util.dsl;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;



public class IntervalUtilTest {

    private static String EMPTY_DATE = "";

    @Test
    public void productInterval() throws Exception {
        String startEndDateRenewal = "01-04-2020 31-03-2021";
        assertThat(IntervalUtil.containsDate(startEndDateRenewal, EMPTY_DATE), is(true));
        assertThat(IntervalUtil.containsDate(startEndDateRenewal, DateTime.now().toString(DwpDateTimeFormat.DWP_BILLING_DATE_FORMAT.getFormat())), is(false));
    }

}
