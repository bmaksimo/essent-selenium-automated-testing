package com.billinghouse.test_automation.util.dsl;

import org.junit.Test;

import static com.billinghouse.test_automation.util.dsl.NumericUtil.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
    String[] ordinals = {
      "0th", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th"
    };
    for (int i = 1; i < ordinals.length; i++) {
      assertEquals(i, ordinalAsInt(ordinals[i]));
    }
  }

  @Test
  public void checkAmountEqualityExpressions() throws Exception {
    String[][] testData =
        new String[][] {
          {"80", "80"},
          {"80", "not equal 100"},
          {"80", "less than 81"},
          {"80", "less then or equal 80"},
          {"80", "greater than 79"},
          {"80", "greater than or equal 80"}
        };

    for (int i = 0; i < testData.length; i++) {
      int actual = Integer.parseInt(testData[i][0]);
      String expressionExpected = testData[i][1];
      assertThat(
          "Actual value '"
              + actual
              + String.format("' did not meet the expected '%s'", expressionExpected),
          checkAmount(actual, expressionExpected),
          is(true));
    }
  }
}
