package com.billinghouse.test_automation.util.dsl;

import cucumber.runtime.CucumberException;

import java.util.regex.Matcher;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

public class NumericExpressionsUtil {

    public static final String NUMERIC_AND_ANYTHING_REGEX = "(\\d+)(.*)";

    public static String extractFirstNumericPart(String input) {
        Matcher matcher = compile(NUMERIC_AND_ANYTHING_REGEX).matcher(input);
        if(matcher.find()) {
            return matcher.group(1);
        }
        else {
            throw new CucumberException(format("--Billing customer and tariff date input '%s' doesn't match the pattern '%s'", input, NUMERIC_AND_ANYTHING_REGEX));
        }
    }
}
