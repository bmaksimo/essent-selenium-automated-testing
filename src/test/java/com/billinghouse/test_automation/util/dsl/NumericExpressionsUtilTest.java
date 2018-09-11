package com.billinghouse.test_automation.util.dsl;

import org.junit.Test;

import static com.billinghouse.test_automation.util.dsl.NumericExpressionsUtil.extractFirstNumericPart;
import static org.junit.Assert.assertEquals;


public class NumericExpressionsUtilTest {


    @Test
    public void testExtractNumbericPart() throws Exception {
        String numericPartExpected = "1000901020";
        String billingCustomerAndTariffDate = numericPartExpected + " 10-09-2018 15:53";
        String numericPartActual = extractFirstNumericPart(billingCustomerAndTariffDate);
        assertEquals(numericPartExpected, numericPartActual);
    }
}
