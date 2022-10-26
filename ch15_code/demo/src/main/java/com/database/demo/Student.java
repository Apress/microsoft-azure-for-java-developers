package com.database.demo;

import org.springframework.data.annotation.Id;
public class Student {

    public Student(){

    }

    public Student(String name, int Age, int marks){
        this.name = name;
        this.age = age;
        this.marks = marks;
    }

    @Id
    private int roll;

    private String name;

    private int age;

    private int marks;

    public int getRoll()
    {
        return roll;
    }

    public void setRoll(int roll)
    {
        this.roll = roll;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
