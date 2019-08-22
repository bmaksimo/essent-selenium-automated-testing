package com.essent.testing.datagenerator.vat;

import org.iban4j.CountryCode;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;

public class VatNumberGeneratorTest {

    private VatNumberGenerator generator;

    @Before
    public void setUp() {
       generator = new VatNumberGenerator();
    }

    @Test
    public void testBelgianVat(){
        for(int i = 0; i < 1000; i++)
            assertVat(generator.getVatNum(CountryCode.BE));
    }

    private void assertVat(String vat) {
        System.out.println(vat);
        int MOD = 97;
        // Modulus 97 check on last nine digits
        if (97 - Integer.parseInt(vat.substring(2,10)) % 97 == Integer.parseInt(vat.substring(10,12)))
            return;
        else
            fail(vat + " has invalid VAT format");
    }
}
