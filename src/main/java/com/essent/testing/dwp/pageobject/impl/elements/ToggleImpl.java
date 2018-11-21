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
        if (!checkIfCheckboxIsChecked(box)) {
            seleniumDriver.waitAndClick(checkBox(box));
        }
    }

    public WebElement checkBoxWithDot (String box) {
        return  seleniumDriver.findElementWhenVisible(By.xpath("//validation-wrapper[@label='" + box + ".']//toggle-form-element/label"));
    }

    public boolean checkIfCheckboxIsCheckedWithDot(String box)  {
        WebElement cb =checkBoxWithDot(box);
        waitForRequestsToFinish();
        String classValue = cb.findElement(By.cssSelector("input")).getAttribute("class");
        return classValue.contains("not-empty");
    }

    public void clickCheckboxWithDot(String box)  {
        seleniumDriver.waitForRequestsToFinish();
        if (!checkIfCheckboxIsCheckedWithDot(box)) {
            seleniumDriver.waitAndClick(checkBoxWithDot(box));
        }
    }

}
