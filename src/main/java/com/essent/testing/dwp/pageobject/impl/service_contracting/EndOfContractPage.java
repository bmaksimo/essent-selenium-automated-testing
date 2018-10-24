package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EndOfContractPage extends Component {

    public EndOfContractPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public class ClickOnElement implements Predicate<String> {
        @Override
        public boolean test(String element) {
            Map<String, String> options = new HashMap<>();
            options.put("element", element);
            boolean success = executeJavascriptTest("TrStartNewMarketSection", options);
            return success;
        }
    }

    public void searchInputField(String input) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//top-search/div[@class='top-search']/input[@type='search']")), input);
        seleniumDriver.findElementWhenVisible(By.xpath("//top-search/div[@class='top-search']/input[@type='search']")).sendKeys(Keys.ENTER);
    }

    public void simpleExecuteJavaScript(String nameOfJavaScript) {
        boolean success = new ExecuteJavaScript().test(nameOfJavaScript);
        assertThat(String.format("JavaScript file %s is undefined.", nameOfJavaScript),
            success, is(true));
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
            boolean success = executeJavascriptTest("TrEanCheckBox", "");
            return success;
        }
    }

    public boolean checkEanCheckBox() {
        return new EanCheckBox().test("");
    }


    public boolean  startNewMarketSection(String element) {
        return new ClickOnElement().test(element);
    }
}
