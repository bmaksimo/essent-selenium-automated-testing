package com.billinghouse.test_automation.util.dsl;

import cucumber.runtime.CucumberException;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

public class DateExpressionsUtil {

    private static final String FRENCH_DATE_FORMAT_HYPHENATED = "dd-MM-yyyy";
    private static final String FRENCH_DATE_FORMAT_HYPHENATED_SOCTAR_ENDDATE = "31-12-yyyy";
    private static final String FRENCH_DATE_FORMAT = "dd/MM/yyyy";
    private static final String DWP_DATE_FORMAT_REGEX = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
    private static final String SOCTAR_STARTDAT_ENDDATE = "1yyyyMMddyyyy1231";
    private static final String DWP_SRART_END_DATE_FORMAT = "dd-MM-yyyy";
    private static final String DATE_SEPARATOR = " - ";
    private static final LocalDate LAST_DATE_OF_YEAR = LocalDate.now().dayOfYear().withMaximumValue();
    private static final String DATE_EXPR_REGEX = "((\\d+)\\s*(month|day|year|week)(s*)\\s+(from|before)\\s+)*now";

    public static DateTime expandFrom(String expression) throws CucumberException {

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

    public static String checkAndConvertToDwpDate(String input) throws CucumberException {
        if(matchesDwpDateFormat(input))
            return input;
        else
            return expandFrom(input).toString(FRENCH_DATE_FORMAT);
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
            StringBuilder dateBuilder = new StringBuilder(expandFrom(input).toString(DWP_SRART_END_DATE_FORMAT));
            dateBuilder.append(DATE_SEPARATOR);
            dateBuilder.append(LAST_DATE_OF_YEAR.toString(DWP_SRART_END_DATE_FORMAT));
            return dateBuilder.toString();
        }
    }

    private static String buildContractStartEndDate(String input) {
        StringBuilder dateBuilder = new StringBuilder();
        DateTimeFormatter fmt = DateTimeFormat.forPattern(FRENCH_DATE_FORMAT);
        dateBuilder.append(fmt.parseDateTime(input).toString(DWP_SRART_END_DATE_FORMAT));
        dateBuilder.append(DATE_SEPARATOR);
        dateBuilder.append(LAST_DATE_OF_YEAR.toString(DWP_SRART_END_DATE_FORMAT));
        return dateBuilder.toString();
    }

    static boolean matchesDwpDateFormat(String date) {
        return date.matches(DWP_DATE_FORMAT_REGEX);
    }

    public static int numericValue(String ordinal) {
        return Integer.parseInt(ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", ""));
    }

    public static String toDwpDate(String consumptionsFormatDate) {
        DateTime dateTime = DateTime.parse(consumptionsFormatDate);
        return dateTime.toString(FRENCH_DATE_FORMAT);
    }
}
