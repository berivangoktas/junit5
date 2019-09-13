package io.berivan.couse.unittest;

import org.junit.jupiter.api.Assertions;
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
import org.junit.jupiter.api.function.Executable;


import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestLoggerExtension.class)
@Tag("student")
@DisplayName("student oprations")
public class StudentsTest
{

    final Student student1 = new Student("Ali", "Gok", "1");

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

    @Test
    void assertTimeOut()
    {

        // addStudentName metodunun bitmesi bekliyor.
        assertTimeout(Duration.ofMillis(0), () -> student1.addStudentName("Berivan"));

        // addStudentName metodunun bitmesi beklemiyor. süre 10ms i geçtiği gibi hata verir.
        assertTimeoutPreemptively(Duration.ofMillis(0), () -> student1.addStudentName("Berivan"));

    }

    //-----------
    @Disabled("private message")
    @Test
    void disabledTest()
    {
        //Ignore

    }

    //-----------
    @EnabledOnOs({OS.MAC})
    @Test
    void testOpreationSystemMac()
    {

        System.out.println("Berivan");
    }

    @DisabledOnOs({OS.MAC})
    @Test
    void testOpreationSystemNotMac()
    {

        System.out.println("Berivan");

    }

    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9})
    @Test
    void testJREVersion()
    {

        System.out.println("Berivan");
    }

    @DisabledOnJre({JRE.JAVA_8})
    @Test
    void testJREVersionNor()
    {

        System.out.println("Berivan");
    }

    @EnabledIfSystemProperty(named = "ENV", matches = "dev")
    @Test
    void testSystemProperty()
    {
        //dev1 yaptığımızda test çalışmıyor.
        System.out.println("Berivan");
    }

    //-----------
    @Tag("tag")
    @Test
    void testTag()
    {

        System.out.println("Berivan");
    }





}
