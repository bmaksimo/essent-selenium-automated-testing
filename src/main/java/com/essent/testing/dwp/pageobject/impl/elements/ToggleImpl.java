package com.essent.testing.dwp.pageobject.impl.elements;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ToggleImpl extends Component {

    public ToggleImpl(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }
    public WebElement checkBox (String box) {
        return  seleniumDriver.findElementWhenVisible(By.xpath("//validation-wrapper[@label='" + box + "?']//toggle-form-element/label"));

    }

    public boolean checkIfCheckboxIsChecked(String box)  {
        String classValue = checkBox(box).findElement(By.cssSelector("input")).getAttribute("class");
        return classValue.contains("not-empty");

    }

    public void clickCheckbox(String box)  {
        if (checkIfCheckboxIsChecked(box)==false) {
            seleniumDriver.waitAndClick(checkBox(box));
        }
    }
}
