package com.billinghouse.regex;


import org.junit.Test;

import static com.billinghouse.test_automation.util.gherkin.ExpressionUtil.expandFrom;
import static com.billinghouse.test_automation.util.gherkin.ExpressionUtil.matchesDwpDateFormat;
import static java.lang.String.format;
import static java.lang.System.out;


/**
 * Playing around with regex, extracting the params like:
 * ${today} + 3months
 * Example:
 * regex: "(\\$today)\\s*(\\+|-)\\s*(\\d)((month|day)s*)\\b"
 * input: $today +  3months
 * group(0): $today - 1month
 * group(1): $today
 * group(2): +
 * group(3): 3
 * group(4): month
 * group(5): s
 */
public class DateExpressionsTest {


    @Test
    public void testJavaRegexHell_inputExpressionLegacy() throws Exception {
        String[] dateF = {"$today +  3months",
            "$today +  4 months",
            "$today +  3 months",
            "$today-  3 months",
            "$today -1month",
            "$today +  21day",
            "$today-30days",
            "$today"
        };
        for (String dateFrom : dateF) {
            out.println(format("--Input '%s' expanded to date-time %s", dateFrom, expandFrom(dateFrom).toString("dd/MM/yyyy")));
        }
    }

    @Test
    public void testInputExpressionNew() throws Exception {
        String[] dateF = {"3months from now",
            "4 months from now",
            "3 months from now",
            "3 months before now",
            "1month before now",
            "21day from now",
            "30 days before now",
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
        for(String date: dateF)
            matchesDwpDateFormat(date);
    }

}
