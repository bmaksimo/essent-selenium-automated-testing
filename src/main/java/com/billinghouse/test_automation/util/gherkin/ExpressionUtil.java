package com.billinghouse.test_automation.util.gherkin;

import cucumber.runtime.CucumberException;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

public class ExpressionUtil {

    private static final String DWP_DATE_FOMAT = "dd/MM/yyyy";

    private static final String DATE_EXPR_REGEX = "(\\$today)\\s*(\\+|-)\\s*(\\d*)\\s*(month|day)(s*)\\b";

    public static DateTime expandFrom(String expression) throws CucumberException {
        if (!expression.matches(DATE_EXPR_REGEX))
            throw new CucumberException(format("--Date-time input %s match none of patterns: ['%s', '%s']", expression, DATE_EXPR_REGEX, DWP_DATE_FORMAT_REGEX));

        Matcher matcher = compile(DATE_EXPR_REGEX).matcher(expression);
        matcher.find();

        DateTime dateTime = new DateTime();
        Map<String, Function<Integer, DateTime>> operations = new HashMap<>();
        operations.put("+month", (i) -> dateTime.plusMonths(i));
        operations.put("-month", (i) -> dateTime.minusMonths(i));
        operations.put("+day", (i) -> dateTime.plusDays(i));
        operations.put("-day", (i) -> dateTime.minusDays(i));
        return operations.get(matcher.group(2) + matcher.group(4))
            .apply(parseInt(matcher.group(3)));
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
