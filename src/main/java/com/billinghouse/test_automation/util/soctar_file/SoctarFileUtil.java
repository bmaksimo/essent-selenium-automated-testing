package com.billinghouse.test_automation.util.soctar_file;

import com.essent.testing.util.resource.ResourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SoctarFileUtil {

    private static final String DEFAULT_SOCTAR_LOCATION =
        ResourceUtil.toPath(
            File.separator
                + "data"
                + File.separator
                + "dwp"
                + File.separator
                + "soctar"
                + File.separator);

    public static final String DESTINATION_LOCATION = System.getProperty("user.dir") + File.separator + "target" + File.separator;

    public static String getSoctarFileFromTemplate(String cust_Id, String ean_id, String soctarStartDatEndDate) throws IOException {
        String sourcePath = DEFAULT_SOCTAR_LOCATION + "soctar-template.csv";
        String destinationPath = DESTINATION_LOCATION + String.format("soctar-%s-%s.csv", cust_Id, ean_id);
        String custIdPadded = StringUtils.rightPad(cust_Id, 10, ' ');
        try (BufferedReader br = new BufferedReader(new FileReader(sourcePath));
             PrintWriter pw = new PrintWriter(Files.newBufferedWriter(
                 Paths.get(destinationPath)))) {
            StrSubstitutor substitutor = createStringSubstitutor(ean_id, soctarStartDatEndDate, custIdPadded);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                pw.println(substitutor.replace(sCurrentLine));
            }
        }
        return destinationPath;
    }

    private static StrSubstitutor createStringSubstitutor(String ean_id, String soctarStartDatEndDate, String custIdPadded) {
        Map<String, String> substitutions = new HashMap<>();
        StrSubstitutor substitutor = new StrSubstitutor(substitutions);
        substitutions.put("ean-id", ean_id);
        substitutions.put("start-end-date", soctarStartDatEndDate);
        substitutions.put("cust-id", custIdPadded);
        return substitutor;
    }

}
