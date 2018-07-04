package com.billinghouse.test_automation.util.gherkin;

import org.joda.time.DateTime;

public class DateTimeFormatUtil {
    private static final String DWP_BIRT_DATE_FOMAT = "dd/MM/yyyy";
    public static String getBirthDate(String isoBirtDate) {
        return DateTime.parse(isoBirtDate).toString(DWP_BIRT_DATE_FOMAT);
    }
}
