package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static com.billinghouse.MatcherAssert.assertThat;
import static com.billinghouse.test_automation.javascript.testrunner.JsTestRegistry.JS_TR_EAN_CHECK_BOX;
import static org.hamcrest.Matchers.is;

public class EndOfContractPage extends Component {

  public void searchInputField(String input) {
    seleniumDriver.waitAndSendKeys(
        seleniumDriver.findElementWhenVisible(
            By.xpath("//top-search/div[@class='top-search']/input[@type='search']")),
        input);
    seleniumDriver
        .findElementWhenVisible(
            By.xpath("//top-search/div[@class='top-search']/input[@type='search']"))
        .sendKeys(Keys.ENTER);
  }

  public void searchInputFieldNow(String input) {
    seleniumDriver.sendKeysNow(
        seleniumDriver.findElementWhenVisible(
            By.xpath("//top-search/div[@class='top-search']/input[@type='search']")),
        input);
    seleniumDriver
        .findElementWhenVisible(
            By.xpath("//top-search/div[@class='top-search']/input[@type='search']"))
        .sendKeys(Keys.ENTER);
  }

  public void simpleExecuteJavaScript(String nameOfJavaScript) {
    boolean success = new ExecuteJavaScript().test(nameOfJavaScript);
    assertThat(
        String.format("JavaScript file %s is undefined.", nameOfJavaScript), success, is(true));
  }

  public class ExecuteJavaScript implements Predicate<String> {
    @Override
    public boolean test(String s) {
      Map<String, Object> options = new HashMap<>();
      options.put("value", s);
      System.out.println(s);
      boolean success = executeJavascriptTest(s, options);
      return success;
    }
  }

  public class EanCheckBox implements Predicate<String> {

    @Override
    public boolean test(String s) {
      boolean success = executeJavascriptTest(JS_TR_EAN_CHECK_BOX, "");
      return success;
    }
  }

  public boolean checkEanCheckBox() {
    return new EanCheckBox().test("");
  }
}
