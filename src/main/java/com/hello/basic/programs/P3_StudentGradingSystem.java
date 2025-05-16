package com.hello.basic.programs;

import java.util.Arrays;

public class P3_StudentGradingSystem {

    public static void main(String[] args) {

//        Student student = new Student(10l, "sahil", 18, Person.Gender.MALE, new int[]{10, 80, 50, 60, 100});
//        System.out.println(student);

    }

}


class Person {

    private String name;
    private int age;
    private Gender gender;

    public Person(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    enum Gender {
        MALE, FEMALE, OTHER
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }

}

class Students extends Person {

    private long id;
    private int[] marks;
    private Grade grade;

    public Students(long id, String name, int age, Gender gender, int[] marks) {
        super(name, age, gender);
        this.id = id;
        this.marks = marks;
        int sum = 0;
        for (int mark : marks) {
            sum += mark;
        }
        float avg = sum / marks.length;
        System.out.println(avg);
        if (avg >= 90) {
            this.grade = Grade.A;
        } else if (avg >= 75) {
            this.grade = Grade.B;
        } else if (avg >= 35) {
            this.grade = Grade.C;
        } else {
            this.grade = Grade.F;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int[] getMarks() {
        return marks;
    }

    public void setMarks(int[] marks) {
        this.marks = marks;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + super.getName() + '\'' +
                ", age=" + super.getAge() +
                ", gender=" + super.getGender() +
                ", marks=" + Arrays.toString(marks) +
                ", grade=" + grade +
                '}';
    }

    enum Grade {
        A, B, C, F
    }

}