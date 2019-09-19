package io.berivan.couse.unittest;

import io.Contact;
import io.Kure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.function.Executable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.SessionId;


import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({TestLoggerExtension.class})
@Tag("student")
@DisplayName("student oprations")
public class StudentsTest
{

    final Student student1 = new Student("Ali", "Gok", "1");
    private WebDriver driver;
   // SessionId session = ((ChromeDriver)driver).getSessionId();

  //  private  final ExtensionContext.Namespace EXTENSION_NAMESPACE = ExtensionContext.Namespace.create(new Object[]{session});

    @Contact(Kure.Bireysel)
    @Test
    void testAssertionsGroup()
    {
        //junit 4
        assertEquals("Ali", student1.getName());
        assertEquals("Gok", student1.getSurName());

        //junit 5
        //birinden bağımsız
        assertAll(
                () -> assertEquals("Ali", student1.getName()),
                () -> assertEquals("Gok", student1.getSurName())
        );

        //içi birbirine bağımlı
        assertAll(
                () -> {
                    assertEquals("Ali", student1.getName());
                    assertEquals("Gok", student1.getSurName());
                },
                () -> {

                    assertEquals("Ber", student1.getName());
                    assertEquals("Gok", student1.getSurName());
                });

    }

    @Contact(Kure.Bireysel)
    @Test
    void testExcepitonAssertions()
    {

        //junit4 ile
        boolean thrown = false;
        try
        {
            student1.addStudentName(null);
        }
        catch (IllegalArgumentException e)
        {
            thrown = true;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        assertTrue(thrown);


        //junit 5
        assertThrows(IllegalArgumentException.class, () -> student1.addStudentName(null), "Throws IllegalArgumentException");

        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> student1.addStudentName(null), "Throws IllegalArgumentException");

        assertEquals("Can't add name with null student", illegalArgumentException.getMessage());
    }

    @Contact(Kure.Bireysel)
    @Test
    void assertTimeOut()
    {

        // addStudentName metodunun bitmesi bekliyor.
        assertTimeout(Duration.ofMillis(0), () -> student1.addStudentName("Berivan"));

        // addStudentName metodunun bitmesi beklemiyor. süre 10ms i geçtiği gibi hata verir.
        assertTimeoutPreemptively(Duration.ofMillis(0), () -> student1.addStudentName("Berivan"));

    }

    //-----------
    @Contact(Kure.Bireysel)
    @Disabled("private message")
    @Test
    void disabledTest()
    {
        //Ignore


        System.out.println("berivan");
    }

    //-----------
    @Contact(Kure.Bireysel)
    @EnabledOnOs({OS.MAC})
    @Test
    void testOpreationSystemMac()
    {

        System.out.println("Berivan");
    }

    @Contact(Kure.Bireysel)
    @DisabledOnOs({OS.MAC})
    @Test
    void testOpreationSystemNotMac()
    {

        System.out.println("Berivan");

    }

    @Contact(Kure.Bireysel)
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9})
    @Test
    void testJREVersion()
    {

        System.out.println("Berivan");
    }

    @Contact(Kure.Bireysel)
    @DisabledOnJre({JRE.JAVA_8})
    @Test
    void testJREVersionNor()
    {

        System.out.println("Berivan");
    }

    @Contact(Kure.Bireysel)
    @EnabledIfSystemProperty(named = "ENV", matches = "dev")
    @Test
    void testSystemProperty()
    {
        //dev1 yaptığımızda test çalışmıyor.
        System.out.println("Berivan");
    }

    //-----------
    @Contact(Kure.Bireysel)
    @Tag("tag")
    @Test
    void testTag()
    {

        System.out.println("Berivan");
    }

    @BeforeEach
    void setup(ExtensionContext extensionContext)
    {

        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
       // driver = new ChromeDriver();
       // extensionContext.getStore(EXTENSION_NAMESPACE )

    }

    @Contact(Kure.Bireysel)
    @Test
    void testOne() throws InterruptedException
    {
        Thread.sleep(5000);
        driver.close();
        driver.quit();
    }


}
