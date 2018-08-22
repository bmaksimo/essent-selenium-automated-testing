package com.billinghouse.test_automation.util.gherkin;

import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import sun.swing.StringUIClientPropertyKey;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

public class ExpressionUtil {

    private static final String DWP_DATE_FOMAT = "dd/MM/yyyy";

    private static final String DATE_EXPR_REGEX = "(\\$today)(\\s*(\\+|-)\\s*(\\d*)\\s*(month|day)(s*)\\b)*";

    private static final String DATE_EXPR_REGEX_NEW = "(\\d+)\\s*(month|day)(s*)\\s+(from|before)\\s+now";

    private static final String DATE_EXPR_REGEX_NOW = "((\\d+)\\s*(month|day)(s*)\\s+(from|before)\\s+)*now";

    public static DateTime expandFrom(String expression) throws CucumberException {
        if (!expression.matches(DATE_EXPR_REGEX) && !expression.matches(DATE_EXPR_REGEX_NEW) && !expression.matches(DATE_EXPR_REGEX_NOW))
            throw new CucumberException(format("--Date-time input %s match none of patterns: ['%s', '%s']", expression, DATE_EXPR_REGEX + "/" + DATE_EXPR_REGEX_NEW, DWP_DATE_FORMAT_REGEX));

        Matcher matcher = compile(DATE_EXPR_REGEX).matcher(expression);


        Map<String, Function<Integer, DateTime>> operations = new HashMap<>();
        DateTime dateTime = new DateTime();

        if (matcher.find()) {
            operations.put("+month", (i) -> dateTime.plusMonths(i));
            operations.put("-month", (i) -> dateTime.minusMonths(i));
            operations.put("+day", (i) -> dateTime.plusDays(i));
            operations.put("-day", (i) -> dateTime.minusDays(i));
            return operations.get(matcher.group(2) + matcher.group(4)).apply(parseInt(matcher.group(3)));
        } else {
            matcher = compile(DATE_EXPR_REGEX_NEW).matcher(expression);
            matcher.matches();
            if(matcher.find()) {
                operations.put("month from", (i) -> dateTime.plusMonths(i));
                operations.put("month before", (i) -> dateTime.minusMonths(i));
                operations.put("day from", (i) -> dateTime.plusDays(i));
                operations.put("day before", (i) -> dateTime.minusDays(i));
                return operations.get(matcher.group(2) + " " + matcher.group(4)).apply(parseInt(matcher.group(1)));
            } else {
                matcher = compile(DATE_EXPR_REGEX_NOW).matcher(expression);
                matcher.matches();
                return now();
            }
        }

    }

    public static String checkAndConvertToDwpDate(String input) throws CucumberException {
        if(matchesDwpDateFormat(input))
            return input;
        else
            return expandFrom(input).toString(DWP_DATE_FOMAT);
    }

    private static final String DWP_DATE_FORMAT_REGEX = "[0-9]{2}/[0-9]{2}/[0-9]{4}";

    public static boolean matchesDwpDateFormat(String date) {
        return date.matches(DWP_DATE_FORMAT_REGEX);
    }

    public static int numericValue(String ordinal) {
        return Integer.parseInt(ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", ""));
    }
}
