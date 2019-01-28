package com.billinghouse.test_automation.util.soctar_file;

import org.junit.Test;

import static com.billinghouse.test_automation.util.dsl.DateExpressionsUtil.checkAndConvertToSoctarFileDate;


public class SoctarFileUtilTest {

    @Test
    public void testSoctarFile() throws Throwable {
        String soctarStartDEndDate  = checkAndConvertToSoctarFileDate("now");
        SoctarFileUtil.getSoctarFileFromTemplate(null, "541455700000117145", soctarStartDEndDate);
    }
}
