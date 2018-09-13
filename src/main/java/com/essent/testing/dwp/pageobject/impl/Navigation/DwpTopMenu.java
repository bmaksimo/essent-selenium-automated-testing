package com.essent.testing.dwp.pageobject.impl.Navigation;

import com.essent.testing.dwp.pageobject.Component;
//import com.essent.testing.selenium.SeleniumDriver;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DwpTopMenu extends Component {

    Map<String, String> topMenu = new HashMap<>();

    public DwpTopMenu(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);

        List<WebElement> elrmrntList = seleniumDriver.findElements(By.xpath("//sub-menu-link"));
        for ( WebElement e : elrmrntList ) {
            String label= e.getAttribute("label");
            //String id=e.findElement(By.xpath("//a")).getAttribute("id");
            String id = seleniumDriver.findElementOrNull(By.xpath("//sub-menu-link[@label='"+label+"']//a")).getAttribute("id");

            topMenu.put(label,id);
        }
    }
//
//    public WebElement accountsListLink(String top) {
//        return seleniumDriver.findElementWhenVisible(By.id(topMenu.get(top.toLowerCase())));
//    }
//
//    public void clickOnAccountsListLink(String top) throws InterruptedException {
//        accountsListLink(top).click();
//    }

//    public WebElement accountsListLink(String element) {
//        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'"+element.toLowerCase()+"')]"));
//    }
//
//    public void clickOnAccountsListLink() throws InterruptedException {
//        accountsListLink().click();
//    }

//
//    public WebElement accountsListLink() {
//        return seleniumDriver.findElementWhenVisible(By.xpath("//sub-menu-link[@label='Klanten']//a"));
//    }
//
//    public void clickOnAccountsListLink() throws InterruptedException {
//        accountsListLink().click();
//    }


    public WebElement topMenu(String label) {
        return seleniumDriver.findElementWhenVisible(By.xpath("//sub-menu-link[@label='"+label+"']//a"));
    }

    public void clickTopMenu(String label) throws InterruptedException {
        topMenu(label).click();
    }

}
