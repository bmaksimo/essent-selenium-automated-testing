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
        System.out.println("Obtain the de- / het article of Dutch noun." );
        System.out.println("STEP:");
        System.out.println(" - ACTION: INIT_SELENIUM_DRIVER");
        System.out.println(" - OPTIONS: " + options.toJson().toString());
        final String url = "https://ennl.dict.cc";

        try {
            System.out.println("STEP:");
            System.out.println(" - ACTION: Connecting to " + url);
            driver.navigate().to(url);
            System.out.println(" - Result: Connected to " + url);
            FluentWait<WebDriver> wait = new FluentWait(driver);
            wait.withTimeout(Duration.ofMillis(3000)).pollingEvery(Duration.ofSeconds(1)).until(
                d -> {
                    WebElement t1 = d.findElement(By.name("s"));
                    return t1 != null;
                });
            driver.findElement(By.name("s")).sendKeys(dutchNoun);
            driver.findElement(By.xpath("//input[@value='Search']")).click();
            WebElement deHet = driver.findElement(By.xpath("//var[@title='mannelijk/vrouwelijk']"));
            System.out.println("STEP:");
            System.out.println(" - ACTION: Scrapping the web data");
            System.out.println(String.format(" - Result: %s %s", deHet.getText(), dutchNoun));
        } finally {
            System.out.println("STEP:");
            System.out.println(" - ACTION: CLOSE_SELENIUM_DRIVER");
            driver.close();
            System.out.println(" - Result: connection closed");
        }
    }
}
