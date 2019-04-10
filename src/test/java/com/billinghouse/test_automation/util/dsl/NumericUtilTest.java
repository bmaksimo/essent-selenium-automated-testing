package com.billinghouse.test_automation.util.dsl;

import org.junit.Test;

import java.util.Arrays;

import static com.billinghouse.test_automation.util.dsl.NumericUtil.*;
import static org.junit.Assert.assertEquals;


public class NumericUtilTest {


    @Test
    public void testExtractNumbericPart() throws Exception {
        String numericPartExpected = "1000901020";
        String billingCustomerAndTariffDate = numericPartExpected + " 10-09-2018 15:53";
        String numericPartActual = extractFirstNumericPart(billingCustomerAndTariffDate);
        assertEquals(numericPartExpected, numericPartActual);
    }

    @Test
    public void testOrdinalAsInt() throws Exception {
        String[] ordinals = {"0th", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th"};
        for(int i = 1; i < ordinals.length; i++) {
            assertEquals(i, ordinalAsInt(ordinals[i]));
        }
    }
}
