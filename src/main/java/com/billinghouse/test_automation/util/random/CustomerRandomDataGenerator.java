package com.billinghouse.test_automation.util.random;

import com.essent.testing.datagenerator.vat.VatNumberGenerator;
import com.essent.testing.restassured.create_contract.helper.PrepareDataForContract;
import cucumber.runtime.CucumberException;
import org.apache.log4j.Logger;
import org.iban4j.CountryCode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomerRandomDataGenerator {
    private final static Logger log = Logger.getLogger(CustomerRandomDataGenerator.class);

    public static String getDOB() {
        return PrepareDataForContract.generateDOBForAnAdult();
    }

    public static Map<String, String> createAccountName(String startedFlowName) {

        String firstName;
        String lastName;
        String accountName = startedFlowName + "_BasicQuoteB2C_TC1_YMR_MoveIn";
        String reversedLastName;
        Map<String,String> generatedNames = new HashMap<>();

        accountName = accountName + getCurrentDateTime();

        int nameLength = accountName.length();
        if (nameLength > 35) {
            firstName = accountName.substring(0,35);
            lastName = "L" + accountName.substring(35,nameLength);
            if (lastName.length() > 35){
                StringBuilder sb = new StringBuilder(lastName);
                reversedLastName = String.valueOf(sb.reverse());
                lastName = reversedLastName.substring(35,nameLength);
                StringBuilder sb2 = new StringBuilder(lastName);
                lastName = String.valueOf(sb2);
            }
        } else {
            firstName = accountName.substring(0,nameLength-2);
            lastName = "L" + accountName.substring(nameLength-2,nameLength);
        }

        generatedNames.put("firstName",firstName);
        generatedNames.put("lastName", lastName);
        generatedNames.put("accountName", accountName);

        log.debug("First name: " + generatedNames.get("firstName"));
        log.debug("Last name: " + generatedNames.get("lastName"));
        log.debug("Account name: " + generatedNames.get("accountName"));

        return generatedNames;
    }

    public static Map<String, String> createCompanyAccountName() {

        String firstName;
        String lastName;
        String accountName = "BasicQuoteB2B_YMR_MoveIn";
        String reversedLastName;
        Map<String,String> generatedNames = new HashMap<>();

        accountName = accountName + getCurrentDateTime();

        int nameLength = accountName.length();
        if (nameLength > 35) {
            firstName = accountName.substring(0,35);
            lastName = "L" + accountName.substring(35,nameLength);
            if (lastName.length() > 35){
                StringBuilder sb = new StringBuilder(lastName);
                reversedLastName = String.valueOf(sb.reverse());
                lastName = reversedLastName.substring(35,nameLength);
                StringBuilder sb2 = new StringBuilder(lastName);
                lastName = String.valueOf(sb2);
            }
        } else {
            firstName = accountName.substring(0,nameLength-2);
            lastName = "L" + accountName.substring(nameLength-2,nameLength);
        }

        generatedNames.put("firstName",firstName);
        generatedNames.put("lastName", lastName);
        generatedNames.put("accountName", accountName);

        log.debug("First name: " + generatedNames.get("firstName"));
        log.debug("Last name: " + generatedNames.get("lastName"));
        log.debug("Account name: " + generatedNames.get("accountName"));

        return generatedNames;
    }

    public static String getMobilePhone() {
        return "+3168" + (int) (Math.floor(Math.random() * 9000000) + 1000000);
    }

    private static String getCurrentDateTime() {
        Date now = new Date();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy_HHmmSS");
        return dateFormat.format(now);
    }

    public static String generateVat(String generatorParam) {
        String countryCode = generatorParam.replace("generator:vat:", "");
        if (countryCode.length() > 3 || CountryCode.getByCode(countryCode) == null) {
            throw new CucumberException("Wrong country code: " + countryCode);
        }
        return new VatNumberGenerator().getVatNum(CountryCode.getByCode(countryCode));
    }
}
