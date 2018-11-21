package com.essent.testing.restassured.create_contract.helper;

import com.essent.testing.restassured.create_contract.constants.ContractConstants;
import org.apache.commons.io.FileUtils;
import org.iban4j.CountryCode;
import org.iban4j.Iban;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class PrepareDataForContract {

    private PrepareDataForContract() {

    }

    public static String getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

    }

    public static String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    public static String setAccountName(String prefixName) {
        return prefixName + new SimpleDateFormat("MMdd HHmmss").format(new Date());
    }

    //This logic is took from SOAPUI tests
    public static String generateValidBECompanyNumber() {
        int lengthPart2 = 0;
        String part1 = "";
        String part2 = "";

        while (lengthPart2 != 2) {
            part1 = String.valueOf((long) (Math.random() * (9999999L - 1000000L) + 1000000L));
            part2 = Integer.toString((97 - (Integer.parseInt(part1) % 97)));
            lengthPart2 = part2.length();
        }

        return "BE0" + part1 + part2;

    }

    //This logic is took from SOAPUI tests
    public static String getValidIbanBE() {
        String bankCode = "001";
        int maxNumber = 9999999;
        Random rand = new Random();
        int randomNumber = rand.nextInt(maxNumber) + 1;
        String randomNumberPadded = String.valueOf(randomNumber);

        while (randomNumberPadded.length() < String.valueOf(maxNumber).length()) {
            randomNumberPadded = "0" + randomNumberPadded;
        }

        int modulo = (int) (Long.valueOf(bankCode + randomNumberPadded) % 97);
        String moduloPadded = String.valueOf(modulo);
        while (moduloPadded.length() < 2) {
            moduloPadded = "0" + moduloPadded;
        }

        Iban ibanObj = new Iban.Builder()
            .countryCode(CountryCode.BE)
            .bankCode(bankCode)
            .accountNumber(randomNumberPadded + moduloPadded)
            .nationalCheckDigit("")
            .build();

        return ibanObj.toString();

    }

    //This logic is took from SOAPUI tests
    public static String generateEAN() {
        String randomNumberForEAN = "54144" + (long) (Math.random() * (9999999999L - 1000000000L) + 1000000000L) + "40";
        char[] charArray = randomNumberForEAN.toCharArray();

        int sum = (((Character.getNumericValue(charArray[0])) +
            (Character.getNumericValue(charArray[2])) +
            (Character.getNumericValue(charArray[4])) +
            (Character.getNumericValue(charArray[6])) +
            (Character.getNumericValue(charArray[8])) +
            (Character.getNumericValue(charArray[10])) +
            (Character.getNumericValue(charArray[12])) +
            (Character.getNumericValue(charArray[14])) +
            (Character.getNumericValue(charArray[16]))) * 3) +
            (((Character.getNumericValue(charArray[1])) +
                (Character.getNumericValue(charArray[3])) +
                (Character.getNumericValue(charArray[5])) +
                (Character.getNumericValue(charArray[7])) +
                (Character.getNumericValue(charArray[9])) +
                (Character.getNumericValue(charArray[11])) +
                (Character.getNumericValue(charArray[13])) +
                (Character.getNumericValue(charArray[15]))));

        long result = 0;
        while (sum % 10 != 0) {
            result++;
            sum++;
        }

        return randomNumberForEAN + String.valueOf(result);
    }

    private static String increaseByOneStartContractDate(String path, String startContractDate, String todayDate, String currentContractStartDateInDWP) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTodayDate = sdf1.parse(todayDate);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStartContractDate = sdf2.parse(startContractDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        c1.setTime(sdf.parse(currentContractStartDateInDWP));
        c1.add(Calendar.DATE, 1);  // number of days to add
        currentContractStartDateInDWP = sdf.format(c1.getTime());  // currentContractStartDateInDWP is now the new date
        Date dateCurrentContractStartDateInDWP = sdf.parse(currentContractStartDateInDWP);

        if (path.equals(ContractConstants.PATH_TO_JSON_FILES_QUOTE_UP_B2B)) {
            if (dateCurrentContractStartDateInDWP.after(dateStartContractDate))
                return "NOT_VALID";
        } else if ((dateCurrentContractStartDateInDWP.after(dateTodayDate) || dateCurrentContractStartDateInDWP.equals(dateTodayDate))) {
            return "NOT_VALID";
        }

        return currentContractStartDateInDWP;
    }

    private static String setThirtyDaysInPast(String startContractDate) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        c1.setTime(sdf1.parse(startContractDate));
        c1.add(Calendar.MONTH, -1);
        c1.add(Calendar.DATE, 1);
        startContractDate = sdf1.format(c1.getTime());

        return startContractDate;

    }

    private static String decreaseByOneStartContractDate(String startContractDate, String todayDate, String currentContractStartDateInDWP) throws ParseException {
        // 1 month is past
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        c1.setTime(sdf1.parse(startContractDate));
        c1.add(Calendar.MONTH, -1);
        startContractDate = sdf1.format(c1.getTime());
        Date dateStartContractDate = sdf1.parse(startContractDate);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(currentContractStartDateInDWP));
        c.add(Calendar.DATE, -1);
        currentContractStartDateInDWP = sdf.format(c.getTime());  // currentContractStartDateInDWP is now the new date
        Date dateCurrentContractStartDateInDWP = sdf.parse(currentContractStartDateInDWP);

        if ((dateCurrentContractStartDateInDWP.before(dateStartContractDate) || dateCurrentContractStartDateInDWP.equals(dateStartContractDate))) {
            return "NOT_VALID";
        }

        return currentContractStartDateInDWP;
    }

    private static String getRandomStartContractDate(String startContractDate, String todayDate, String currentContractStartDateInDWP) throws ParseException {

        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startContractDate);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(todayDate);

        long random = ThreadLocalRandom.current().nextLong(date1.getTime(), date2.getTime());
        Date date = new Date(random);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.format(date);

    }

    public static String setStartContractDate(String path, String startContractDate, String todayDate, String currentContractStartDateInDWP) throws ParseException {

        if (currentContractStartDateInDWP.equals("")) {
            if (path.equals(ContractConstants.PATH_TO_JSON_FILES_QUOTE_UP_B2B)) {
                setThirtyDaysInPast(startContractDate);
            }
            return startContractDate;
        }

        return increaseByOneStartContractDate(path, startContractDate, todayDate, currentContractStartDateInDWP);
    }

    public static String getRandomAddressNumber() {
        return "1" + (long) (Math.random() * (99999 - 10000) + 10000);
    }

    public static String generateStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static void updatePayloadJson(String pathTemplate, String pathJson, String replaceString, String originalValue) throws IOException {
        File pathTemplateFile = new File(pathTemplate);
        File pathJsonFile = new File(pathJson);
        String fileContext = FileUtils.readFileToString(pathTemplateFile, Charset.forName("utf-8"));
        fileContext = fileContext.replace(replaceString, originalValue);
        FileUtils.write(pathJsonFile, fileContext, Charset.forName("utf-8"));
    }

    public static void updatePayloadJson(String pathTemplate, String pathJson, HashMap<String, String> mapValues) throws IOException {
        File pathTemplateFile = new File(pathTemplate);
        File pathJsonFile = new File(pathJson);
        String fileContext = FileUtils.readFileToString(pathTemplateFile, Charset.forName("utf-8"));

        for (Entry<String, String> entry : mapValues.entrySet()) {
            if (fileContext.contains(entry.getKey())) {
                fileContext = fileContext.replace(entry.getKey(), entry.getValue());
            }
        }

        FileUtils.write(pathJsonFile, fileContext, Charset.forName("utf-8"));
    }

    public static String createRequestJsonPayload(String payloadFromTemplate, String payloadWithRealValues, HashMap testMap) throws IOException {

        updatePayloadJson(payloadFromTemplate, payloadWithRealValues, testMap);
        return generateStringFromResource(payloadWithRealValues);
    }

    public static String createRequestJsonPayload(String payloadFromTemplate, String payloadWithRealValues, String templateValue, String realValues) throws IOException {

        updatePayloadJson(payloadFromTemplate, payloadWithRealValues, templateValue, realValues);
        return generateStringFromResource(payloadWithRealValues);
    }
}
