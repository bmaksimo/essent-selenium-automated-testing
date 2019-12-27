package com.billinghouse.testautomation.util.dsl;

import static com.billinghouse.testautomation.util.dsl.DateExpressionsUtil.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

public class DateExpressionsUtilTest {

  @Test
  public void testDateInputExpressions() throws Exception {
    String[] dateF = {
      "3months from now",
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
      // TODO - test has to be better
      assertTrue(matchesDwpDateFormat(expandFrom(dateFrom).toString("dd/MM/yyyy")));
    }
  }

  @Test
  public void testConvertDateFormat() throws Exception {
    String expectedDwpDate = "30/09/2019";
    String actual = toDwpDate("2019-09-30");
    assertEquals(
        String.format(
            "Actual DWP date \"%s\" differs from the expected \"%s\"", actual, expectedDwpDate),
        expectedDwpDate,
        actual);
  }

  @Test
  public void testConvertToSoctarFileDate() throws Exception {
    String expectedSoctarFileDate = "12019013020191231";
    String actual = checkAndConvertToSoctarFileDate("30/01/2019");
    assertEquals(
        String.format(
            "Actual Soctar start and end date '%s' differs from the expected '%s'",
            actual, expectedSoctarFileDate),
        expectedSoctarFileDate,
        actual);
  }

  @Test
  public void testConvertToStartAndEndDate() throws Exception {
    String expectedDwpStartEndDate = "01-02-2019 - 31-12-2019";
    String actual = checkAndConvertToDwpContractStartEndDate("01/02/2019");
    assertEquals(
        String.format(
            "Actual Dwp start and end date '%s' differs from the expected '%s'",
            actual, expectedDwpStartEndDate),
        expectedDwpStartEndDate,
        actual);
  }

  @Test
  public void testCheckDaysBetween() throws Exception {
    String[] split = "09-04-2019 28-04-2019".split("\\s+");
    String earlierDte = split[0], laterDate = split[1], interval = "19 days";
    assertThat(
        "Comparison of two dates expression conversion failure",
        checkTimeBetween(earlierDte, laterDate, interval),
        Matchers.equalTo(0));
  }

  @Test
  public void testCheckYearsBetween() throws Exception {
    String[] split = "09-04-2019 28-04-2020".split("\\s+");
    String earlierDte = split[0], laterDate = split[1], interval = "1 year";
    assertThat(
        "Comparison of two dates expression conversion failure",
        checkTimeBetween(earlierDte, laterDate, interval),
        Matchers.equalTo(0));
  }

  @Test
  public void testCheckWeeksBetween() throws Exception {
    String[] split = "09-04-2019 28-04-2019".split("\\s+");
    String earlierDte = split[0], laterDate = split[1], interval = "2 weeks";
    assertThat(
        "Comparison of two dates expression conversion failure",
        checkTimeBetween(earlierDte, laterDate, interval),
        Matchers.equalTo(0));
  }

  @Test
  public void testCheckMonthsBetween() throws Exception {
    String[] split = "09-04-2019 28-05-2019".split("\\s+");
    String earlierDte = split[0], laterDate = split[1], interval = "1 month";
    assertThat(
        "Comparison of two dates expression conversion failure",
        checkTimeBetween(earlierDte, laterDate, interval),
        Matchers.equalTo(0));
  }

  @Test
  public void testDwpInterval() throws Exception {
    String interval = "20-03-2019 31-03-2020";
    String newStartExpected = "30/03/2020";
    String newEndExpected = "01/04/2020";
    String newStart = getFormattedEnd(interval, -1);
    assertEquals(newStartExpected, newStart);

    String newEnd = getFormattedEnd(interval, +1);
    assertEquals(newEndExpected, newEnd);
  }
}
