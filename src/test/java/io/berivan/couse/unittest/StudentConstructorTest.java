package io.berivan.couse.unittest;

import io.Contact;
import io.Kure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Student test with testInfo and Test Report parameter")
public class StudentConstructorTest
{
    private Student student;
    private Course course;

    //constructorda da yapılabilir
    public StudentConstructorTest(TestInfo info)
    {
        assertEquals("Student test with testInfo and Test Report parameter", info.getDisplayName());
    }

// createStudent_2 testi patlayacak
    /*@BeforeEach
    void setUpSetStudent(){

        student = new Student("Ahmet", "Ahmet", "1");
         course = new Course("ABC", "Mehmet","Mehmet");
    }*/


    @BeforeEach
    void setUpSetStudent(TestInfo info)
    {
        if (info.getTags().contains("addstudent"))
        {
            student = new Student("Ahmet", "Ahmet", "");
        }
        if (info.getTags().contains("addCourse"))
        {
            course = new Course("ABC", "Mehmet", "Mehmet");
        }

    }


    @Contact(Kure.Bireysel)
    @Test
    @DisplayName("create Student")
    @Tag("addstudent")
    void createStudent()
    {
        assertEquals("Ahmet", student.getName());
    }

    @Contact(Kure.Bireysel)
    @Test
    @DisplayName("create Student")
    @Tag("addCourse")
    void createCourse()
    {
        assertEquals("Mehmet", course.getName());
    }

    @Contact(Kure.Bireysel)
    //Ekstra bilgi yayınlamak için
    @Test
    @DisplayName("create Student")
    @Tag("addCourse")
    void testReporter(TestReporter reporter)
    {
        reporter.publishEntry("Satart Time", LocalDateTime.now().toString());
        assertEquals("Mehmet", course.getName());
        reporter.publishEntry("End Time", LocalDateTime.now().toString());
    }


}
