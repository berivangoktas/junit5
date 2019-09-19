package io.berivan.couse.unittest;

import io.Contact;
import io.Kure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(TestExtension.class)
public class ReportTestClassName
{


    WebDriver driver;

    @BeforeEach
    void setup()
    {

        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
    }

    @Contact(Kure.Bireysel)
    @Test
    void testOne() throws InterruptedException
    {
        Thread.sleep(5000);
        driver.close();
        driver.quit();
    }

    @Contact(Kure.Bireysel)
    @Test
    void testTwo() throws InterruptedException
    {
        Thread.sleep(5000);
        driver.close();
        driver.quit();
    }


    @Contact(Kure.Reklam)
    @DisplayName("Raporlama")
    @Tag("TagReport")
    @Test
    void testReportTestName()
    {
        String x = "2";
        String y = "3";
        Assertions.assertTrue(x.equals(y));
    }

    @Contact(Kure.Bireysel)
    @DisplayName("Raporlama")
    @Tag("TagReport")
    @Test
    void testReportTestName2() throws InterruptedException
    {
        Thread.sleep(5000);
        String x = "2";
        String y = "3";
        Assertions.assertFalse(x.equals(y));
    }
}
