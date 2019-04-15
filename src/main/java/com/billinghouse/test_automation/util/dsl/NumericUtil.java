package com.billinghouse.test_automation.util.dsl;

import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.essent.testing.context.ContextService;
import cucumber.runtime.CucumberException;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

public class NumericUtil {


    private static final String NUMERIC_AND_ANYTHING_REGEX = "(\\d+)(.*)";
    private static final String ORDINAL_REGEX = "(?<=\\d)(rd|st|nd|th)\\b";


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

    public static Integer sumOfAmounts(List<String> amounts) {
        final ParameterProvider parameterProvider = ((ParameterProvider) ContextService.getContext().getBean("parameterProvider")).consumingNullValues(true);
        return amounts.stream()
            .mapToInt(element -> (int) parameterProvider.getValueOrParameter(element))
            .sum();
    }

    public static Integer amountAsInt(String amountInCurrency, Locale locale) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        try {
            return numberFormat.parse(amountInCurrency.replaceAll("\\s+", " ")).intValue();
        } catch (ParseException e) {
            throw new CucumberException("Exception while parsing the advance amount string " + amountInCurrency);
        }
    }

}
