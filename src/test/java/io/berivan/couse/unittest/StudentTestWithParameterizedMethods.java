package io.berivan.couse.unittest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class StudentTestWithParameterizedMethods
{
    private Student student;

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class ValueSourceDemo
    {

        private int studentSize = 0;

        @BeforeAll
        void setUp()
        {
            student = new Student("Ahmet", "Ahmet", "1");

        }


        //Verilen her parameter için bu test methodu çalıştırıldı.
        @ParameterizedTest
        @ValueSource(strings = {"101", "102", "103 "})
        void addCourse(String studentNo)
        {

            student = new Student("Ahmet", "Ahmet", studentNo);
            studentSize++;
            System.out.println("Student NO: " + studentSize);
        }


        @ParameterizedTest
        @EnumSource(TimeUnitEnum.class)
        void testWithEnumSource(TimeUnitEnum timeUnit)
        {
            System.out.println("timeUnit" + timeUnit);
            assertNotNull(timeUnit);
        }

        @ParameterizedTest
        @EnumSource(value = TimeUnitEnum.class, names = "DAYS")
        void testWithEnumSourceInclude(TimeUnit timeUnit)
        {
            System.out.println("timeUnit" + timeUnit);
            assertNotNull(timeUnit);
        }


    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class ValueSourceDemoMethod
    {

        private int studentSize = 0;

        @BeforeAll
        void setUp()
        {
            student = new Student("Ahmet", "Ahmet", "1");

        }


        //Verilen her parameter için bu test methodu çalıştırıldı.
        @ParameterizedTest
        @MethodSource
        void addCourseToMethod(String lessonCode)
        {

           Lesson lesson = new Lesson(lessonCode);
            System.out.println("Student NO: " + lesson.getLessonCode());
        }

        Stream<String> addCourseToMethod()
        {
            return Stream.of("101","102");
        }

    }
}