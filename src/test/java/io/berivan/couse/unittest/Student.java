package io.berivan.couse.unittest;

public class Student
{
    private final String name;
    private final String surName;
    private final String department;


    public Student(String name, String surName, String department)
    {
        this.name = name;
        this.surName = surName;
        this.department = department;
    }

    public String getName()
    {
        return name;
    }

    public String getSurName()
    {
        return surName;
    }

    public String getDepartment()
    {
        return department;
    }

    public void addStudentName(String name) throws InterruptedException
    {


        Thread.sleep(10);
        if(name == null)
            throw new IllegalArgumentException("Can't add name with null student");
        System.out.println("addStudentName metıuna girdi");

    }




}
