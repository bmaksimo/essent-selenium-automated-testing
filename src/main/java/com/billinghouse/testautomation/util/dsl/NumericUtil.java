package com.billinghouse.testautomation.util.dsl;

import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.essent.testing.context.ContextService;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;
import static org.apache.commons.lang3.ObjectUtils.compare;

public class NumericUtil {


    private static final String NUMERIC_AND_ANYTHING_REGEX = "(\\d+)(.*)";
    private static final String ORDINAL_REGEX = "(?<=\\d)(rd|st|nd|th)\\b";
    private static final String EQUALITY_REGEX_EXT ="(?<=(not equal|greater than|greater than or equal|less than|less than or equal))?\\s*(\\d+)";
    private static final Map<String, BiFunction<Integer, Integer, Boolean>> equalityOperators = new HashMap<>();

    static {
        equalityOperators.put("not equal", NumericUtil::notEqualTo);
        equalityOperators.put("greater than", NumericUtil::greaterThan);
        equalityOperators.put("greater than or equal", NumericUtil::greaterThanOrEqual);
        equalityOperators.put("less than", NumericUtil::lessThan);
        equalityOperators.put("less than or equal", NumericUtil::lessThanOrEqual);
    }


    public static String extractFirstNumericPart(String input) {
        Matcher matcher = compile(NUMERIC_AND_ANYTHING_REGEX).matcher(input);
        if(matcher.find()) {
            return matcher.group(1);
        }
       else {
            throw new CucumberException(format("--Billing customer and tariff date input '%s' doesn't match the pattern '%s'", input, NUMERIC_AND_ANYTHING_REGEX));
        }
    }

    public static int ordinalAsInt(String ordinal) {
        return Integer.parseInt(ordinal.replaceAll(ORDINAL_REGEX, ""));
    }

    public static int sumOfAmounts(List<String> amounts) {
        final ParameterProvider parameterProvider = ((ParameterProvider) ContextService.getContext().getBean("parameterProvider")).consumingNullValues(true);
        return amounts.stream()
            .mapToInt(element -> (int) parameterProvider.getValueOrParameter(element))
            .sum();
    }

    public static int amountAsInt(String amountInCurrency, Locale locale) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        try {
            return numberFormat.parse(amountInCurrency.replaceAll("\\s+", " ")).intValue();
        } catch (ParseException e) {
            throw new CucumberException("Exception while parsing the advance amount string " + amountInCurrency);
        }
    }

    public static boolean checkAmount(int actualAmount, String expressionExpectedAmount) {
        Matcher matcher = compile(EQUALITY_REGEX_EXT).matcher(expressionExpectedAmount);
        if(matcher.find()) {
            String equalityGroup = matcher.group(1);
            Integer expectedAmount = Integer.parseInt(matcher.group(2));
            if(StringUtils.isEmpty(equalityGroup)) {
                return equalTo(actualAmount, expectedAmount);
            }
            BiFunction<Integer, Integer, Boolean> compare = equalityOperators.get(equalityGroup);
            return compare.apply(actualAmount, expectedAmount);
        }
        throw new CucumberException("Expression '" + expressionExpectedAmount + "' did not contain expected equality term.");
    }

    public static <T extends Comparable<? super T>> boolean equalTo(T c1, T c2) {
        return compare(c1, c2) == 0;
    }

    public static <T extends Comparable<? super T>> boolean notEqualTo(T c1, T c2) {
        return compare(c1, c2) != 0;
    }

    public static <T extends Comparable<? super T>> boolean greaterThan(T c1, T c2) {
        return compare(c1, c2) > 0;
    }
    public static <T extends Comparable<? super T>> boolean greaterThanOrEqual(T c1, T c2) {
        return compare(c1, c2) >= 0;
    }

    public static <T extends Comparable<? super T>> boolean lessThan(T c1, T c2) {
        return compare(c1, c2) < 0;
    }

    public static <T extends Comparable<? super T>> boolean lessThanOrEqual(T c1, T c2) {
        return compare(c1, c2) <= 0;
    }

}
