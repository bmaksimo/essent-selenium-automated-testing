package com.essent.testing.jbilling.pageobject.impl.main;

import com.essent.testing.jbilling.pageobject.Window;
import com.essent.testing.jbilling.pageobject.impl.Component;
import org.openqa.selenium.By;

public class JBillingMainWindow extends Component implements Window {

  private static final By MAIN_WINDOW_SELECTOR = By.id("main");

  public JBillingMainWindow() {
    super(MAIN_WINDOW_SELECTOR);
  }
}
