package com.billinghouse.test_automation.util.dsl;

import cucumber.runtime.CucumberException;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

public class DateExpressionsUtil {

    private static final String FRENCH_DATE_FOMAT = "dd/MM/yyyy";


    private static final String DATE_EXPR_REGEX = "((\\d+)\\s*(month|day|year|week)(s*)\\s+(from|before)\\s+)*now";

    public static DateTime expandFrom(String expression) throws CucumberException {

        Matcher matcher = compile(DATE_EXPR_REGEX).matcher(expression);

        Map<String, Function<Integer, DateTime>> operations = new HashMap<>();
        DateTime dateTime = new DateTime();

        if (matcher.find() == true) {
            if(matcher.group(0).equals("now"))
                return dateTime;
            operations.put("month from", (i) -> dateTime.plusMonths(i));
            operations.put("month before", (i) -> dateTime.minusMonths(i));
            operations.put("day from", (i) -> dateTime.plusDays(i));
            operations.put("day before", (i) -> dateTime.minusDays(i));
            operations.put("year from", (i) -> dateTime.plusYears(i));
            operations.put("year before", (i) -> dateTime.minusYears(i));
            operations.put("week from", (i) -> dateTime.plusWeeks(i));
            operations.put("week before", (i) -> dateTime.minusWeeks(i));
            return operations.get(matcher.group(3) + " " + matcher.group(5)).apply(parseInt(matcher.group(2)));
        } else {
            throw new CucumberException(format("--Date-time input '%s' doesn't match the pattern '%s'", expression, DATE_EXPR_REGEX));
        }
    }

    public static String checkAndConvertToDwpDate(String input) throws CucumberException {
        if(matchesDwpDateFormat(input))
            return input;
        else
            return expandFrom(input).toString(FRENCH_DATE_FOMAT);
    }

    private static final String DWP_DATE_FORMAT_REGEX = "[0-9]{2}/[0-9]{2}/[0-9]{4}";

    public static boolean matchesDwpDateFormat(String date) {
        return date.matches(DWP_DATE_FORMAT_REGEX);
    }

    public static int numericValue(String ordinal) {
        return Integer.parseInt(ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", ""));
    }
}
