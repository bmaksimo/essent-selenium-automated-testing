package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpPlusMenu extends Component {

    private final static String XPATH_CONTAINS_TEXT_TEMPLATE = "//span[contains(text(),'${text}')]";

    public DwpPlusMenu(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }
    
    public void findAndClickOnPlusIcon()  {
        WebElement element = seleniumDriver.findElementWhenVisible(By.xpath("//a[@name='Plus Menu']"));
        seleniumDriver.waitAndClick(element);
    }

    public void findAndClickPlusElement(String text)  {
        String query = createQuery(XPATH_CONTAINS_TEXT_TEMPLATE, "text", text);
        WebElement element = seleniumDriver.findElementWhenVisible(By.xpath(query));
        seleniumDriver.waitAndClick(element);
    }

    public void findAndClickServiceElement(String key)  {
        String query = createQuery(XPATH_CONTAINS_TEXT_TEMPLATE, "text", key);
        WebElement element = seleniumDriver.findElementWhenVisible(By.xpath(query));
        seleniumDriver.waitAndClick(element);
    }
}
