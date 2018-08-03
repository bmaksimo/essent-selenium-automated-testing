package com.essent.testing.restassured.helper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.iban4j.CountryCode;
import org.iban4j.Iban;

public final class GenerateDataForQuote {
	
	private GenerateDataForQuote() {
		
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
	public static String generateValidBECompanyNumber(){
		int lengthPart2 = 0;
		String part1 = "";
		String part2 = "";

		while(lengthPart2 != 2){
			 part1 = String.valueOf((long)(Math.random()*(9999999L-1000000L)+1000000L));
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
	public static String generateEAN(){
			String randomNumberForEAN = "54144" + (long)(Math.random()*(9999999999L-1000000000L)+1000000000L) + "40";
			char[] charArray = randomNumberForEAN.toCharArray();

			int sum = (((Character.getNumericValue(charArray[0]))+
			(Character.getNumericValue(charArray[2]))+
			(Character.getNumericValue(charArray[4]))+
			(Character.getNumericValue(charArray[6]))+
			(Character.getNumericValue(charArray[8]))+
			(Character.getNumericValue(charArray[10]))+
			(Character.getNumericValue(charArray[12]))+
			(Character.getNumericValue(charArray[14]))+
			(Character.getNumericValue(charArray[16])))*3) +
			(((Character.getNumericValue(charArray[1]))+
			(Character.getNumericValue(charArray[3]))+
			(Character.getNumericValue(charArray[5]))+
			(Character.getNumericValue(charArray[7]))+
			(Character.getNumericValue(charArray[9]))+
			(Character.getNumericValue(charArray[11]))+
			(Character.getNumericValue(charArray[13]))+
			(Character.getNumericValue(charArray[15]))));

			return randomNumberForEAN + String.valueOf(Math.round(Math.ceil(sum/10))*10 - sum);
	}
	
	public static String generateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}

	//TODO
	// Should merge these two methods updatePayloadJson which have different params
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
			if(fileContext.contains(entry.getKey())) {
				fileContext = fileContext.replace(entry.getKey(), entry.getValue());
			}
		}
		
		FileUtils.write(pathJsonFile, fileContext, Charset.forName("utf-8"));
	}
	
	//TODO
	// Should merge these two methods updatePayloadJson which have different params
	public static String createRequestJsonPayload(String payloadFromTemplate, String payloadWithRealValues, HashMap testMap) throws IOException {

		updatePayloadJson(payloadFromTemplate, payloadWithRealValues, testMap);
		return generateStringFromResource(payloadWithRealValues);
	}
	
	public static String createRequestJsonPayload(String payloadFromTemplate, String payloadWithRealValues, String templateValue, String realValues) throws IOException {
		
        updatePayloadJson(payloadFromTemplate, payloadWithRealValues, templateValue, realValues);
		return generateStringFromResource(payloadWithRealValues);
	}
}
