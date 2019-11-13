package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class DashboardMenuPage extends Component {
    private static Map<String, String> menuMap = new HashMap<>();
    static {
        menuMap.put("Details", "icon-bedrijf");
        menuMap.put("Sales","icon-winkelwagen");
        menuMap.put("Billing","icon-euro");
        menuMap.put("Service","icon-agent");
        menuMap.put("Contracten","icon-contract");
        menuMap.put("Marktberichten","icon-flowchart");
        menuMap.put("BestRetentionOffer","icon-opportunity");
        menuMap.put("Documenten","icon-mappen");
    }

    private WebElement getDashboardElement(String name)  {
       return seleniumDriver.findElementWhenVisible(By.xpath("//span[@class='" + menuMap.get(name) + "']"));

    }

    public void clickOnDashboardElement(String element) {
        seleniumDriver.waitAndClick(getDashboardElement(element));
    }

    public void clickOnDashboardElementNow(String element) {
        seleniumDriver.clickNow(getDashboardElement(element));
    }
}
