package com.essent.testing.driver.chrome;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class ChromeDriverTest {

    public static void main(String[] args) throws Exception {
        String dutchNoun = "vrijheid";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("chrome.switches", "--disable-extensions", "headless", "window-size=1280x800");
        String userDataPath = System.getProperty("chrome.user.data.path");
        if(StringUtils.isNotEmpty(userDataPath)) {
            options.addArguments("user-data-dir=" + userDataPath);
        }
        WebDriver driver = new ChromeDriver(options);
        final String url = "https://ennl.dict.cc";
        try {
            driver.navigate().to(url);
            FluentWait<WebDriver> wait = new FluentWait(driver);
            wait.withTimeout(Duration.ofMillis(3000)).pollingEvery(Duration.ofSeconds(1)).until(
                d -> {
                    WebElement t1 = d.findElement(By.name("s"));
                    return t1 != null;
                });
            driver.findElement(By.name("s")).sendKeys(dutchNoun);
            driver.findElement(By.xpath("//input[@value='Search']")).click();
            WebElement conjugations = driver.findElement(By.xpath("//var[@title='mannelijk/vrouwelijk']"));
            System.out.println(conjugations.getText() + " " + dutchNoun);

        } finally {
            driver.close();
        }
    }
}
