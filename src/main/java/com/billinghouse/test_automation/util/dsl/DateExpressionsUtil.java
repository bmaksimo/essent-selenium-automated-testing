package com.billinghouse.test_automation.util.dsl;

import cucumber.runtime.CucumberException;
import org.joda.time.*;
import org.joda.time.base.BaseSingleFieldPeriod;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
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

    private static final String FRENCH_DATE_FORMAT_HYPHENATED = "dd-MM-yyyy";
    private static final DateTimeFormatter FRENCH_DATE_FORMATTER_HYPHENATED = DateTimeFormat.forPattern(FRENCH_DATE_FORMAT_HYPHENATED);

    private static final String FRENCH_DATE_FORMAT_HYPHENATED_SOCTAR_ENDDATE = "31-12-yyyy";
    private static final String FRENCH_DATE_FORMAT = "dd/MM/yyyy";
    private static final String DWP_DATE_FORMAT_REGEX = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
    private static final String DWP_START_END_DATE_FORMAT_REGEX = "[0-9]{2}-[0-9]{2}-[0-9]{4}\\s+[0-9]{2}-[0-9]{2}-[0-9]{4}";
    private static final String SOCTAR_STARTDAT_ENDDATE = "1yyyyMMddyyyy1231";
    private static final String DWP_START_END_DATE_FORMAT = "dd-MM-yyyy";
    private static final String DATE_SEPARATOR = " - ";
    private static final LocalDate LAST_DATE_OF_YEAR = LocalDate.now().dayOfYear().withMaximumValue();
    private static final String DATE_EXPR_REGEX = "((\\d+)\\s*(month|day|year|week){1}(s*)\\s+(from|before)\\s+)*now";
    private static final String TIME_EXPR_REGEX = "((\\d+)\\s*(hour|second)(s*)\\s+(from|before)\\s+)*now";
    private static final String DWP_TIME_FORMAT = "HH:mm";
    private static final String INTERVAL_EXPR_REGEX = "((\\d+)\\s*(month|day|year|week)(s*))";
    private final static  Map<String, BiFunction<ReadableInstant, ReadableInstant, BaseSingleFieldPeriod>> operations = new HashMap<>();
    static {
        operations.put("day", Days::daysBetween);
        operations.put("week", Weeks::weeksBetween);
        operations.put("month", Months::monthsBetween);
        operations.put("year", Years::yearsBetween);
    }
    private static final  Map<String, DurationFieldType> duration = new HashMap<>();
    static {
        duration.put("day", DurationFieldType.days());
        duration.put("week", DurationFieldType.weeks());
        duration.put("month", DurationFieldType.months());
        duration.put("year", DurationFieldType.years());
    }

    public static int checkTimeBetween(String earlierDate, String laterDate, String interval) {
        if(!interval.matches(INTERVAL_EXPR_REGEX)) {
            throw new CucumberException("Unable to parse intarval expression " + interval);
        }
        Matcher matcher = compile(INTERVAL_EXPR_REGEX).matcher(interval);

        DateTime ed = FRENCH_DATE_FORMATTER_HYPHENATED.parseDateTime(earlierDate);
        DateTime ld = FRENCH_DATE_FORMATTER_HYPHENATED.parseDateTime(laterDate);
        if(matcher.find()) {
            String unit = matcher.group(3);
            BaseSingleFieldPeriod apply = operations.get(unit).apply(ed, ld);
            final int amount = apply.get(duration.get(unit));
            final int amount2 = parseInt(matcher.group(2));
            return amount - amount2;
        }
        throw new CucumberException("Unable to parse intarval expression " + interval);
    }

    public static DateTime expandFrom(String expression) throws CucumberException {
        if(!expression.matches(DATE_EXPR_REGEX)) {
            throw new CucumberException(format("--Date-time input '%s' doesn't match the pattern '%s'", expression, DATE_EXPR_REGEX));
        }

        Matcher matcher = compile(DATE_EXPR_REGEX).matcher(expression);

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
            return operations.get(matcher.group(3) + " " + matcher.group(5)).apply(parseInt(matcher.group(2)));
        } else {
            throw new CucumberException(format("--Date-time input '%s' doesn't match the pattern '%s'", expression, DATE_EXPR_REGEX));
        }
    }

    private static DateTime expandFromTime(String expression) throws CucumberException {
        if(!expression.matches(TIME_EXPR_REGEX)) {
            throw new CucumberException(format("--Time input '%s' doesn't match the pattern '%s'", expression, TIME_EXPR_REGEX));
        }
        Matcher matcher = compile(TIME_EXPR_REGEX).matcher(expression);

        Map<String, Function<Integer, DateTime>> operations = new HashMap<>();
        DateTime dateTime = new DateTime();

        if (matcher.find()) {
            if(matcher.group(0).equals("now"))
                return dateTime;
            operations.put("hour from", dateTime::plusHours);
            operations.put("hour before", dateTime::minusHours);
            operations.put("second from", dateTime::plusSeconds);
            operations.put("second before", dateTime::minusSeconds);
            return operations.get(matcher.group(3) + " " + matcher.group(5)).apply(parseInt(matcher.group(2)));
        } else {
            throw new CucumberException(format("--Time input '%s' doesn't match the pattern '%s'", expression, TIME_EXPR_REGEX));
        }
    }

    public static String checkAndConvertToDwpDate(String input) throws CucumberException {
        if(matchesDwpDateFormat(input))
            return input;
        else
            return expandFrom(input).toString(FRENCH_DATE_FORMAT);
    }

    public static String convertToDwpTime(String input) throws CucumberException {
        return expandFromTime(input).toString(DWP_TIME_FORMAT);
    }

    public static String checkAndConvertToSoctarFileDate(String input) throws CucumberException {

        if(matchesDwpDateFormat(input)) {
            DateTimeFormatter fmt = DateTimeFormat.forPattern(FRENCH_DATE_FORMAT);
            return fmt.parseDateTime(input).toString(SOCTAR_STARTDAT_ENDDATE);
        }
        else
            return expandFrom(input).toString(SOCTAR_STARTDAT_ENDDATE);
    }

    public static List<String> getSoctarStartAndEndDates(String input) {
        List<String> dates = new ArrayList<>();
        String startDate = expandFrom(input).toString(FRENCH_DATE_FORMAT_HYPHENATED);
        String endDate = expandFrom(input).toString(FRENCH_DATE_FORMAT_HYPHENATED_SOCTAR_ENDDATE);
        dates.add(startDate);
        dates.add(endDate);

        return dates;
    }

    public static String checkAndConvertToDwpContractStartEndDate(String input) throws CucumberException {
        if(matchesDwpDateFormat(input)) {
            return buildContractStartEndDate(input);
        }
        else {

            String dateBuilder = expandFrom(input).toString(DWP_START_END_DATE_FORMAT);
            dateBuilder = dateBuilder.concat(DATE_SEPARATOR);
            dateBuilder = dateBuilder.concat(LAST_DATE_OF_YEAR.toString(DWP_START_END_DATE_FORMAT));
            return dateBuilder;
        }
    }
    public static String checkAndConvertToDwpContracEndDate(String input) throws CucumberException {
        if(matchesDwpDateFormat(input)) {
            return buildContractStartEndDate(input);
        }
        else {
            return expandFrom(input).toString(DWP_START_END_DATE_FORMAT);
        }
    }

    private static String buildContractStartEndDate(String input) {
        StringBuilder dateBuilder = new StringBuilder();
        DateTimeFormatter fmt = DateTimeFormat.forPattern(FRENCH_DATE_FORMAT);
        dateBuilder.append(fmt.parseDateTime(input).toString(DWP_START_END_DATE_FORMAT));
        dateBuilder.append(DATE_SEPARATOR);
        dateBuilder.append(LAST_DATE_OF_YEAR.toString(DWP_START_END_DATE_FORMAT));
        return dateBuilder.toString();
    }

    static boolean matchesDwpDateFormat(String date) {
        return date.matches(DWP_DATE_FORMAT_REGEX);
    }


    public static String toDwpDate(String consumptionsFormatDate) {
        DateTime dateTime = DateTime.parse(consumptionsFormatDate);
        return dateTime.toString(FRENCH_DATE_FORMAT);
    }

    /**
     *
     * @param interval DWP interval, formatted "dd-MM-yyyy dd-MM-yyyy"
     */
    public static String getFormattedEnd(String interval, int daysEarlierOrLater) {
        if (!interval.matches(DWP_START_END_DATE_FORMAT_REGEX))
            throw new CucumberException(interval + "is not DWP start-end interval");
        String[] split = interval.split("\\s+");
        String end = split[1];
        DateTimeFormatter fmt = DateTimeFormat.forPattern(DWP_START_END_DATE_FORMAT);
        return fmt.parseDateTime(end).plusDays(daysEarlierOrLater).toString(FRENCH_DATE_FORMAT);
    }

    /**
     *
     * @param interval DWP interval, formatted "dd-MM-yyyy dd-MM-yyyy"
     */
    public static String getFormattedEnd(String interval) {
        if (!interval.matches(DWP_START_END_DATE_FORMAT_REGEX))
            throw new CucumberException(interval + "is not DWP start-end interval");
        String[] split = interval.split("\\s+");
        String end = split[1];
        DateTimeFormatter fmt = DateTimeFormat.forPattern(DWP_START_END_DATE_FORMAT);
        return fmt.parseDateTime(end).toString(FRENCH_DATE_FORMAT);
    }

}
