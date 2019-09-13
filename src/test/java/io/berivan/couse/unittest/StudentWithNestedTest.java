package io.berivan.couse.unittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentWithNestedTest
{

    final Student student1 = new Student("Ali", "Gok", "1");

    @BeforeEach
    void setUp()
    {
        System.out.println("Ana before içinde");
    }


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
            assertEquals("Ali", student1.getName());
        }

        @Test
        void test_2()
        {

            assertEquals("Ali", student1.getName());
        }

        @Test
        void test_3()
        {

            assertEquals("Ali", student1.getName());
        }
    }


    @Test
    void test_4()
    {

        assertEquals("Ali", student1.getName());
    }

    @Test
    void test_5()
    {

        assertEquals("Ali", student1.getName());
    }

    @Test
    void test_6()
    {

        assertEquals("Ali", student1.getName());
    }







}
