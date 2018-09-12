package com.essent.testing.dwp.pageobject;

import com.essent.testing.dwp.scenario.DwpScenario;
import org.openqa.selenium.By;

public class BaseObject extends DwpScenario {

    protected String getTaskId() {
        final String taskId;
        taskId = webDriver.findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[1]/td[2]/list-link-bold-top-two-liner-cell[@icon='null']//a/h5")).getText();
        return taskId;
    }

    protected void clickOnPlus() {
        webDriver.findElementWhenVisible(By.xpath("(//a[@class='show-actions icon-plus'])[1]")).click();
    }

    protected void plusSubaction(String action) {
        webDriver.findElementWhenVisible(By.xpath("//list-row-action[@label='" + action + "']")).click();
    }
}
