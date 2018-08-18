package com.essent.testing.util.selenium.dwp;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;

public class LocalStorageUtil {
    private  final static Logger logger = Logger.getLogger(LocalStorageUtil.class);

    public static String fetchPreferredLanguage(WebStorage webStorage) {
        LocalStorage localStorage = webStorage.getLocalStorage();
        String userLanguage = localStorage.getItem("NG_TRANSLATE_LANG_KEY");
        if(StringUtils.isEmpty(userLanguage)) {
            logger.warn(" - WARNING: Application did not contain user language value. Default will be en_BE.");
            return "en_BE";
        } else {
            logger.info(" - RESULT: setting preferred language: " + userLanguage);
            return userLanguage;
        }
    }
}
