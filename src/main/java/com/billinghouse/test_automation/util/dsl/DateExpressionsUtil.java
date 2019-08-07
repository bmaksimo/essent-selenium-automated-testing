package com.billinghouse.test_automation.util.dsl;

import cucumber.runtime.CucumberException;
import org.joda.time.*;
import org.joda.time.base.BaseSingleFieldPeriod;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

public class DateExpressionsUtil {

  private DateExpressionsUtil() {}

  private static final DateTimeFormatter FRENCH_DATE_FORMATTER_HYPHENATED =
      org.joda.time.format.DateTimeFormat.forPattern(
          DwpDateTimeFormat.DWP_BILLING_DATE_FORMAT.getFormat());

  public static final String DATE_SEPARATOR = " - ";
  private static final LocalDate LAST_DATE_OF_YEAR = LocalDate.now().dayOfYear().withMaximumValue();

  private static final Map<
          String, BiFunction<ReadableInstant, ReadableInstant, BaseSingleFieldPeriod>>
      operations = new HashMap<>();

  static {
    operations.put("day", Days::daysBetween);
    operations.put("week", Weeks::weeksBetween);
    operations.put("month", Months::monthsBetween);
    operations.put("year", Years::yearsBetween);
  }
  private static final Map<String, DurationFieldType> duration = new HashMap<>();
  static {
    duration.put("day", DurationFieldType.days());
    duration.put("week", DurationFieldType.weeks());
    duration.put("month", DurationFieldType.months());
    duration.put("year", DurationFieldType.years());
  }

  public static int checkTimeBetween(String earlierDate, String laterDate, String interval) {
    if (!interval.matches(DateTimeLanguageRegex.INTERVAL_EXPR_REGEX.getExpression())) {
      throw new CucumberException("Unable to parse interval expression " + interval);
    }
    Matcher matcher =
        compile(DateTimeLanguageRegex.INTERVAL_EXPR_REGEX.getExpression()).matcher(interval);

    DateTime ed = FRENCH_DATE_FORMATTER_HYPHENATED.parseDateTime(earlierDate);
    DateTime ld = FRENCH_DATE_FORMATTER_HYPHENATED.parseDateTime(laterDate);
    if (matcher.find()) {
      String unit = matcher.group(3);
      BaseSingleFieldPeriod apply = operations.get(unit).apply(ed, ld);
      final int amount = apply.get(duration.get(unit));
      final int amount2 = parseInt(matcher.group(2));
      return amount - amount2;
    }
    throw new CucumberException("Unable to parse intarval expression " + interval);
  }

  public static LocalDate getFirstDateOfNextMonth() {
      LocalDate today = new LocalDate();
      return today.plusMonths(1).withDayOfMonth(1);
  }

  public static DateTime expandFrom(String expression) {
    if (!expression.matches(DateTimeLanguageRegex.DATE_EXPR_REGEX.getExpression())) {
      throw new CucumberException(
          format(
              "--Date-time input '%s' doesn't match the pattern '%s'",
              expression, DateTimeLanguageRegex.DATE_EXPR_REGEX.getExpression()));
    }

    Matcher matcher =
        compile(DateTimeLanguageRegex.DATE_EXPR_REGEX.getExpression()).matcher(expression);

    Map<String, Function<Integer, DateTime>> operations = new HashMap<>();
    DateTime dateTime = new DateTime();

    if (matcher.find()) {
            if(matcher.group(0).equals("now"))
                return dateTime;
      operations.put("month from", dateTime::plusMonths);
      operations.put("month before", dateTime::minusMonths);
      operations.put("day from", dateTime::plusDays);
      operations.put("day before", dateTime::minusDays);
      operations.put("year from", dateTime::plusYears);
      operations.put("year before", dateTime::minusYears);
      operations.put("week from", dateTime::plusWeeks);
      operations.put("week before", dateTime::minusWeeks);
      return operations
          .get(matcher.group(3) + " " + matcher.group(5))
          .apply(parseInt(matcher.group(2)));
    } else {
      throw new CucumberException(
          format(
              "--Date-time input '%s' doesn't match the pattern '%s'",
              expression, DateTimeLanguageRegex.DATE_EXPR_REGEX.getExpression()));
    }
  }

  private static DateTime expandFromTime(String expression) {
    if (!expression.matches(DateTimeLanguageRegex.TIME_EXPR_REGEX.getExpression())) {
      throw new CucumberException(
          format(
              "--Time input '%s' doesn't match the pattern '%s'",
              expression, DateTimeLanguageRegex.TIME_EXPR_REGEX.getExpression()));
    }
    Matcher matcher =
        compile(DateTimeLanguageRegex.TIME_EXPR_REGEX.getExpression()).matcher(expression);

    Map<String, Function<Integer, DateTime>> operations = new HashMap<>();
    DateTime dateTime = new DateTime();

    if (matcher.find()) {
      if (matcher.group(0).equals("now")) return dateTime;
      operations.put("hour from", dateTime::plusHours);
      operations.put("hour before", dateTime::minusHours);
      operations.put("second from", dateTime::plusSeconds);
      operations.put("second before", dateTime::minusSeconds);
      return operations
          .get(matcher.group(3) + " " + matcher.group(5))
          .apply(parseInt(matcher.group(2)));
    } else {
      throw new CucumberException(
          format(
              "--Time input '%s' doesn't match the pattern '%s'",
              expression, DateTimeLanguageRegex.TIME_EXPR_REGEX.getExpression()));
    }
  }

  public static String checkAndConvertToDwpDate(String input) {
    if (matchesDwpDateFormat(input)) return input;
    else return expandFrom(input).toString(DwpDateTimeFormat.DWP_FRENCH_DATE_FORMAT.getFormat());
  }

  public static String checkAndConvertToDwpApiDate(String input) {
    if (matchesDwpDateFormat(input)) return input;
    else return expandFrom(input).toString(DwpDateTimeFormat.DWP_API_DATE_FORMAT.getFormat());
  }

  public static String convertToDwpTime(String input) {
    return expandFromTime(input).toString(DwpDateTimeFormat.DWP_TIME_FORMAT.getFormat());
  }

  public static String checkAndConvertToSoctarFileDate(String input) {

    if (matchesDwpDateFormat(input)) {
      DateTimeFormatter fmt =
          org.joda.time.format.DateTimeFormat.forPattern(
              DwpDateTimeFormat.DWP_FRENCH_DATE_FORMAT.getFormat());
      return fmt.parseDateTime(input)
          .toString(DwpDateTimeFormat.DWP_SOCTAR_STARTDAT_ENDDATE.getFormat());
    } else {
      return expandFrom(input).toString(DwpDateTimeFormat.DWP_SOCTAR_STARTDAT_ENDDATE.getFormat());
    }
  }

  public static List<String> getSoctarStartAndEndDates(String input) {
    List<String> dates = new ArrayList<>();
    String startDate =
        expandFrom(input).toString(DwpDateTimeFormat.DWP_BILLING_DATE_FORMAT.getFormat());
    String endDate = expandFrom(input).toString(DwpDateTimeFormat.DWP_SOCTAR_ENDDATE.getFormat());
    dates.add(startDate);
    dates.add(endDate);

    return dates;
  }

  public static String checkAndConvertToDwpContractStartEndDate(String input) {
    if (matchesDwpDateFormat(input)) {
      return buildContractStartEndDate(input);
    } else {

      String dateBuilder =
          expandFrom(input).toString(DwpDateTimeFormat.DWP_BILLING_DATE_FORMAT.getFormat());
      dateBuilder = dateBuilder.concat(DATE_SEPARATOR);
      dateBuilder =
          dateBuilder.concat(
              LAST_DATE_OF_YEAR.toString(DwpDateTimeFormat.DWP_BILLING_DATE_FORMAT.getFormat()));
      return dateBuilder;
    }
  }

  public static String checkAndConvertToDwpContracEndDate(String input) {
    if (matchesDwpDateFormat(input)) {
      return buildContractStartEndDate(input);
    } else {
      return expandFrom(input).toString(DwpDateTimeFormat.DWP_BILLING_DATE_FORMAT.getFormat());
    }
  }

  private static String buildContractStartEndDate(String input) {
    StringBuilder dateBuilder = new StringBuilder();
    DateTimeFormatter fmt =
        org.joda.time.format.DateTimeFormat.forPattern(
            DwpDateTimeFormat.DWP_FRENCH_DATE_FORMAT.getFormat());
    dateBuilder.append(
        fmt.parseDateTime(input).toString(DwpDateTimeFormat.DWP_BILLING_DATE_FORMAT.getFormat()));
    dateBuilder.append(DATE_SEPARATOR);
    dateBuilder.append(
        LAST_DATE_OF_YEAR.toString(DwpDateTimeFormat.DWP_BILLING_DATE_FORMAT.getFormat()));
    return dateBuilder.toString();
  }

  static boolean matchesDwpDateFormat(String date) {
    return date.matches(DateTimeRegex.DWP_DATE_FORMAT_REGEX.getExpression());
  }

  public static String toDwpDate(String consumptionsFormatDate) {
    DateTime dateTime = DateTime.parse(consumptionsFormatDate);
    return dateTime.toString(DwpDateTimeFormat.DWP_FRENCH_DATE_FORMAT.getFormat());
  }

  /** @param interval DWP interval, formatted "dd-MM-yyyy dd-MM-yyyy" */
  public static String getFormattedEnd(String interval, int daysEarlierOrLater) {
    if (!interval.matches(DateTimeRegex.DWP_START_END_DATE_FORMAT_REGEX.getExpression()))
      throw new CucumberException(interval + "is not DWP start-end interval");
    String[] split = interval.split("\\s+");
    String end = split[1];
    DateTimeFormatter fmt =
        org.joda.time.format.DateTimeFormat.forPattern(
            DwpDateTimeFormat.DWP_BILLING_DATE_FORMAT.getFormat());
    return fmt.parseDateTime(end)
        .plusDays(daysEarlierOrLater)
        .toString(DwpDateTimeFormat.DWP_FRENCH_DATE_FORMAT.getFormat());
  }

  /** @param interval DWP interval, formatted "dd-MM-yyyy dd-MM-yyyy" */
  public static String getFormattedEnd(String interval) {
    if (!interval.matches(DateTimeRegex.DWP_START_END_DATE_FORMAT_REGEX.getExpression()))
      throw new CucumberException(interval + "is not DWP start-end interval");
    String[] split = interval.split("\\s+");
    String end = split[1];
    DateTimeFormatter fmt =
        org.joda.time.format.DateTimeFormat.forPattern(
            DwpDateTimeFormat.DWP_BILLING_DATE_FORMAT.getFormat());
    return fmt.parseDateTime(end).toString(DwpDateTimeFormat.DWP_FRENCH_DATE_FORMAT.getFormat());
  }
}
