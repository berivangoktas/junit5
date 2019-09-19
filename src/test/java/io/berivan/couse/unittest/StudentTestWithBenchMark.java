package io.berivan.couse.unittest;

import io.Contact;
import io.Kure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;



@ExtendWith({BenchMarkExtension.class})
public class StudentTestWithBenchMark
{

    @Contact(Kure.Bireysel)
    @Disabled
    @Test
     void test_4()
    {
        System.out.println("4 test çalıştı");
        System.out.println("4 test çalıştı");
        System.out.println("4 test çalıştı");
    }

    @Contact(Kure.Bireysel)
    @Test
    void test_5()
    {
        Assertions.assertTrue(false);
        System.out.println("5 test çalıştı");
    }

    @Contact(Kure.Bireysel)
    @Test
    void test_6()
    {

        System.out.println("6 test çalıştı");
    }

}
