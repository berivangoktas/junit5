package io.berivan.couse.unittest;

import io.Contact;
import io.Kure;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StudentTestWithImplement implements CreateDomain<Student>,TestLifeCycleReporter
{

    @Override
    public Student createDomain()
    {
        return new Student("Ahmet", "Ahmet", "1");
    }

    @Contact(Kure.Bireysel)
    @Test
    void createStudent()
    {

        final Student student = createDomain();

        assertAll("student",

                () -> assertEquals("Ahmet", student.getName())
        );


    }

}
