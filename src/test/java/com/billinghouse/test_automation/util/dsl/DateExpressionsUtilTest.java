package com.billinghouse.test_automation.util.dsl;

import org.junit.Test;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.expandFrom;
import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.matchesDwpDateFormat;
import static java.lang.String.format;
import static java.lang.System.out;


public class DateExpressionsUtilTest {


    @Test
    public void testDateInputExpressions() throws Exception {
        String[] dateF = {"3months from now",
            "4 months from now",
            "3 months from now",
            "3 months before now",
            "1month before now",
            "21day from now",
            "30 days before now",
            "30 years before now",
            "21 week from now",
            "now"
        };
        for (String dateFrom : dateF) {
            out.println(format("--Input '%s' expanded to date-time %s", dateFrom, expandFrom(dateFrom).toString("dd/MM/yyyy")));
        }
    }

    @Test
    public void testJavaRegexHell_formattedDate() throws Exception {
        String[] dateF = {
            "28/06/2018",
            "28/09/2018",
            "28/10/2018"
        };
        for (String date : dateF)
            matchesDwpDateFormat(date);
    }
}
