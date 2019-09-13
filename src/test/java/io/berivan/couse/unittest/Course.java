package io.berivan.couse.unittest;

public class Course
{

    private final String course;
    private final String name;
    private final String surNAme;

    public Course(String course, String name, String surNAme)
    {
        this.course = course;
        this.name = name;
        this.surNAme = surNAme;
    }

    public String getCourse()
    {
        return course;
    }

    public String getName()
    {
        return name;
    }

    public String getSurNAme()
    {
        return surNAme;
    }


}
