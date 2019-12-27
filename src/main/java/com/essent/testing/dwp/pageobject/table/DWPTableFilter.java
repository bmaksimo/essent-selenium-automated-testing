package com.essent.testing.dwp.pageobject.table;

import com.essent.testing.context.ContextService;
import com.essent.testing.selenium.SeleniumDriver;
import com.essent.testing.table.TableFilterBase;

public class DWPTableFilter extends TableFilterBase {

  private static final String TABLE_PATH_BASE =
      "//list//div//h2[text()='${tableName}']/parent::div/parent::div";
  private static final String TABLE_ROWS_BASE = "//tbody[@id='rows']/tr";

  public DWPTableFilter(String contextParameters) {
    super(
        contextParameters,
        (SeleniumDriver) ContextService.getContext().getBean("dwpSeleniumDriver"),
        TABLE_PATH_BASE,
        TABLE_ROWS_BASE);
  }
}
