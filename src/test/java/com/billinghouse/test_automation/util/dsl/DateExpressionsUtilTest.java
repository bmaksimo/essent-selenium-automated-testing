package com.billinghouse.test_automation.util.dsl;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import org.junit.Test;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


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
           assertTrue(matchesDwpDateFormat(expandFrom(dateFrom).toString("dd/MM/yyyy")));
        }
    }

    @Test
    public void testConvertDateFormat() throws Exception {
        String expectedDwpDate = "30/09/2019";
        String actual = toDwpDate("2019-09-30");
        assertEquals(String.format("Actual DWP date '%s' differs from the expected '%s'", actual, expectedDwpDate), expectedDwpDate, actual);
    }

    @Test
    public void testConvertToSoctarFileDate() throws Exception {
        String expectedSoctarFileDate = "12019013020191231";
        String actual = checkAndConvertToSoctarFileDate("30/01/2019");
        assertEquals(String.format("Actual Soctar start and end date '%s' differs from the expected '%s'", actual, expectedSoctarFileDate), expectedSoctarFileDate, actual);
    }
    @Test
    public void testConvertToStartAndEndDate() throws Exception {
        String expectedDwpStartEndDate = "01-02-2019 - 31-12-2019";
        String actual = checkAndConvertToDwpContractStartEndDate("01/02/2019");
        assertEquals(String.format("Actual Dwp start and end date '%s' differs from the expected '%s'", actual, expectedDwpStartEndDate), expectedDwpStartEndDate, actual);

    }
}
