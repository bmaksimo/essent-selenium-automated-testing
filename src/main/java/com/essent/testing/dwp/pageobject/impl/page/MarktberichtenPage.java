package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

public class MarktberichtenPage extends Component {

    public MarktberichtenPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public String getEanCode() {
        return findElementWhenVisible(By.id("aos-products-quotes-ean-c-field")).getText();
    }

    public boolean validateRejectionHeader(String input) {
        return findElementWhenVisible(By.xpath("(//h5)[.='" + input + "'][1]")).isDisplayed();
    }

    public void setEanCodeInFilter(String eanCode) {
        waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(findElementWhenVisible(By.xpath("//validation-wrapper[@label='EAN-code']/div[@class='input label-inline']//input-form-element//input")), eanCode);
    }
}
