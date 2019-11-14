package com.billinghouse.testautomation.util.file;

import com.essent.testing.util.resource.ResourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {

    private static final String DEFAULT_SOCTAR_LOCATION =
        ResourceUtil.toPath(File.separator + "data" + File.separator + "dwp" + File.separator + "soctar" + File.separator);

    public static final String JBILLING_CONSUMPTION_LOCATION =
        ResourceUtil.toPath(File.separator + "data" + File.separator + "jbilling" + File.separator + "consumption" + File.separator);

    public static final String DESTINATION_LOCATION = System.getProperty("user.dir") + File.separator + "target" + File.separator;

    public static String getSoctarFileFromTemplate(String custId, String eanId, String soctarStartDatEndDate) throws IOException {
        String sourcePath = DEFAULT_SOCTAR_LOCATION + "soctar-template.csv";
        String destinationPath = DESTINATION_LOCATION + String.format("soctar-%s-%s.csv", custId, eanId);
        String custIdPadded = StringUtils.rightPad(custId, 10, ' ');
        try (BufferedReader br = new BufferedReader(new FileReader(sourcePath));
             PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(destinationPath)))) {
             StrSubstitutor substitutor = createStringSubstitutor(eanId, soctarStartDatEndDate, custIdPadded);
             String sCurrentLine;
             while ((sCurrentLine = br.readLine()) != null) {
                pw.println(substitutor.replace(sCurrentLine));
             }
        }
        return destinationPath;
    }

    private static StrSubstitutor createStringSubstitutor(String eanId, String soctarStartDatEndDate, String custIdPadded) {
        Map<String, String> substitutions = new HashMap<>();
        StrSubstitutor substitutor = new StrSubstitutor(substitutions);
        substitutions.put("ean-id", eanId);
        substitutions.put("start-end-date", soctarStartDatEndDate);
        substitutions.put("cust-id", custIdPadded);
        return substitutor;
    }
}
