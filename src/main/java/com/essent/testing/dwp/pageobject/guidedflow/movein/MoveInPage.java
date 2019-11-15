package com.essent.testing.dwp.pageobject.guidedflow.movein;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;

public class MoveInPage extends Component {
    public void setNewMoveAddress(String address, String houseNumber, String postalCode, String City) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-street-field\"]")), address);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenClickable(By.xpath("//*[@id=\"aos-products-quotes-addresses-aos-products-quotes-field-container\"]//ul/li[1]/a/b")));
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-number-field\"]")), houseNumber);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"address-postalcode-field\"]")), postalCode);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenClickable(By.xpath("//*[@id=\"aos-products-quotes-addresses-aos-products-quotes-field-container\"]//ul/li/a/b")));

    }
}
