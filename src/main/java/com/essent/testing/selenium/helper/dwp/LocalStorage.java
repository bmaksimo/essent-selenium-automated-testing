package com.essent.testing.selenium.helper.dwp;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.html5.WebStorage;

public class LocalStorage {
  private static final Logger logger = Logger.getLogger(LocalStorage.class);

  public static String fetchPreferredLanguage(WebStorage webStorage) {
    org.openqa.selenium.html5.LocalStorage localStorage = webStorage.getLocalStorage();
    String userLanguage = localStorage.getItem("NG_TRANSLATE_LANG_KEY");
    if (StringUtils.isEmpty(userLanguage)) {
      logger.warn(
          " - WARNING: Application did not contain user language value. Default will be en_BE.");
      return "en_BE";
    } else {
      logger.debug(" - RESULT: setting preferred language: " + userLanguage);
      return userLanguage;
    }
  }
}
