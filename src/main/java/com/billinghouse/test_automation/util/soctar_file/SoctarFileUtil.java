package com.billinghouse.test_automation.util.soctar_file;

import org.apache.commons.text.StrSubstitutor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoctarFileUtil {

    public static String getSoctarFileFromTemplate(String filePath, String ean, String soctarStartDatEndDate)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return parametiseTemplate(contentBuilder.toString(), ean, soctarStartDatEndDate);
    }

    private static String parametiseTemplate(String template, String ean, String soctarStartDatEndDate) {
        Map<String, String> substitutions =  new HashMap<>();
        substitutions.put("ean-id", ean);
        substitutions.put("start-end-date", soctarStartDatEndDate);
        StrSubstitutor substitutor = new StrSubstitutor(substitutions);
        return substitutor.replace(template);
    }
}
