package com.essent.testing.datagenerator.vat;

import static org.junit.Assert.assertEquals;

import com.essent.testing.restassured.create_contract.helper.PrepareDataForContract;
import org.junit.Test;

public class EanCodeGeneratorTest {
  @Test
  public void testGeneratedEanCode() {
    for (int i = 0; i < 100; i++) {
      String eanCode = PrepareDataForContract.generateEAN();
      System.out.println(eanCode);
      assertEquals(18, eanCode.length());
    }
  }
}
