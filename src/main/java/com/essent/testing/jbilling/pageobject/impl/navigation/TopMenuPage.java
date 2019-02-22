package com.essent.testing.jbilling.pageobject.impl.navigation;

import com.essent.testing.jbilling.pageobject.impl.Component;
import org.openqa.selenium.By;

public class TopMenuPage extends Component {

    public void topMenu(String top){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"navList\"]/li/a/span[contains(text(),'"+top+"')]")));
    }

}
