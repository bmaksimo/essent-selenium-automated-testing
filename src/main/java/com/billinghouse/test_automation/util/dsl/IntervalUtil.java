package com.billinghouse.test_automation.util.dsl;

import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class IntervalUtil {

    private static final DateTimeFormatter INTERVAL_DATE_FORMATTER = DateTimeFormat.forPattern(DwpDateTimeFormat.DWP_BILLING_DATE_FORMAT.getFormat());

    private  IntervalUtil() {

    }

    public static Interval productTnterval(String periodOfRenewal) {
        if (!periodOfRenewal.matches(DateTimeRegex.DWP_START_END_DATE_FORMAT_REGEX.getExpression()))
            throw new CucumberException(periodOfRenewal + "is not DWP start-end interval");
        String[] startAndEnd = periodOfRenewal.split("\\s+");
        String start  = startAndEnd[0];
        String end = startAndEnd[1];
        return new Interval(INTERVAL_DATE_FORMATTER.parseDateTime(start), INTERVAL_DATE_FORMATTER.parseDateTime(end));
    }

    public static boolean containsDate(String period, String dateToContain) {
        if(StringUtils.isEmpty(dateToContain)) {
            return true;
        }
        DateTime dateTime = INTERVAL_DATE_FORMATTER.parseDateTime(dateToContain);
        return productTnterval(period).contains(dateTime);
    }
}
