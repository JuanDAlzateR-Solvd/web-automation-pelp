package com.solvd;

import com.solvd.webAutomation.components.Header;
import com.solvd.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;

public class SimpleNavBarTest {

    @Test (testName = "Home Page NavBar", description = "verifies that home page loads, reject cookies and NavBar works")
    public void simpleTest() {

        WebDriver driver = new ChromeDriver();
        HomePage homePage = new HomePage(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.next.co.uk/");

        homePage.clickRejectCookiesButton();

        //Thread sleep just to debug code
        int timeSleep=1000;
        try{
            Thread.sleep(timeSleep);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }

        Header header = new Header(driver);

        header.clickButton(Header.NavItem.WOMEN);

        try{
            Thread.sleep(timeSleep);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        header.clickButton(Header.NavItem.MEN);

        try{
            Thread.sleep(timeSleep);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }

        driver.quit();

    }
}

