package com.essent.testing.dwp.pageobject.impl.navigation;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class DwpDashboardMenuPage extends Component {
    private static Map<String, String> menuMap = new HashMap<>();
    static {
        menuMap.put("Details","icon-bedrijf");
        menuMap.put("Sales","icon-winkelwagen");
        menuMap.put("Billing","icon-euro");
        menuMap.put("Service","icon-agent");
        menuMap.put("Contracten","icon-contract");
        menuMap.put("Marktberichten","icon-flowchart");
        menuMap.put("BestRetentionOffer","icon-opportunity");
        menuMap.put("Documenten","icon-mappen");
    }

    private WebElement getDashboardElement(String name)  {
        //String name
//        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='col-1-4 blue-sidebar']//div[@class='icon-nav']/a/small[contains(text(),'"+name+"')]"));
//        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='icon-nav']"));
//        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='col-1-4 blue-sidebar']//div[@class='icon-nav']/a"));
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@class='"+menuMap.get(name)+"']"));
    }

    public void clickOnDashboardElement(String element) {
//        seleniumDriver.waitAndClick(getDashboardElement().findElement(By.xpath("//small[contains(text(),'"+element+"')]")));
        seleniumDriver.waitAndClick(getDashboardElement(element));
    }
}
