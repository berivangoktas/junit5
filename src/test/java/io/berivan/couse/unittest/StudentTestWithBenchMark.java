package io.berivan.couse.unittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BenchMarkExtension.class)
public class StudentTestWithBenchMark
{

    @ExtendWith(BenchMarkExtension.class)
    @DisplayName("")
    @Nested
    class studentNested
    {

        @BeforeEach
        void setUp()
        {
            System.out.println("Nested içine girdi");
        }

        @Test
        void test_1()
        {
            System.out.println("1.1 test çalıştı");
        }

        @Test
        void test_2()
        {

            System.out.println("1.2 test çalıştı");
        }

        @Test
        void test_3()
        {

            System.out.println("1.3 test çalıştı");
        }
    }


    @Test
    void test_4()
    {

        System.out.println("4 test çalıştı");
    }

    @Test
    void test_5()
    {

        System.out.println("5 test çalıştı");
    }

    @Test
    void test_6()
    {

        System.out.println("6 test çalıştı");
    }

}
