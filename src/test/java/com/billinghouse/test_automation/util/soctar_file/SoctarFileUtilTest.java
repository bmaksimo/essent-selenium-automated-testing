package com.billinghouse.test_automation.util.soctar_file;

import com.essent.testing.util.resource.ResourceUtil;
import org.junit.Test;
import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToSoctarFileDate;

public class SoctarFileUtilTest {
    @Test
    public void testSoctarFile() throws Throwable {
        String path = ResourceUtil.toPath("/data/dwp/soctar/soctar-template.csv");
        String soctarStartDEndDate  = checkAndConvertToSoctarFileDate("now");
        System.out.println("--Soctar file: " + SoctarFileUtil.getSoctarFileFromTemplate(path, "541455700000117145", soctarStartDEndDate));

    }
}
