package com.hello.advanced.programs;

//Generic Box & Student Record System
//        Requirements:
//        Create a generic class Box<T> to hold any data type.
//        Build a student record system using List, Map, and Set to manage marks and subjects.
//        Demonstrate usage of wildcards and generic methods.

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class P3_GenericBox_StudentRecordSystem {

    public static void main(String[] args) {

        Box<Integer> box1 = new Box<>(10);
        System.out.println(box1);
        Box<String> box2 = new Box<>("hello world");
        System.out.println(box2);

        StudentRecordSystem system = new StudentRecordSystem();

        EnumMap<Subject, Integer> marks1 = new EnumMap<>(Subject.class);
        marks1.put(Subject.MATH, 90);
        marks1.put(Subject.PHYSICS, 85);
        marks1.put(Subject.CHEMISTRY, 78);
        marks1.put(Subject.ENGLISH, 88);
        Student student1 = new Student(1, "Alice", 17, marks1);

        EnumMap<Subject, Integer> marks2 = new EnumMap<>(Subject.class);
        marks2.put(Subject.MATH, 72);
        marks2.put(Subject.BIOLOGY, 80);
        marks2.put(Subject.ENGLISH, 75);
        marks2.put(Subject.HISTORY, 90);
        Student student2 = new Student(2, "Bob", 18, marks2);

        EnumMap<Subject, Integer> marks3 = new EnumMap<>(Subject.class);
        marks3.put(Subject.COMPUTER_SCIENCE, 95);
        marks3.put(Subject.MATH, 88);
        marks3.put(Subject.PHYSICS, 91);
        marks3.put(Subject.ENGLISH, 84);
        Student student3 = new Student(3, "Charlie", 17, marks3);

        system.add(student1);
        system.add(student2);
        system.add(student3);

        system.printAll();

        system.remove(student3);

        system.printAll();

        system.remove(2);

        system.printAll();
    }

}

class StudentRecordSystem {

    private List<Student> students;

    public StudentRecordSystem() {
        this.students = new ArrayList<>();
    }

    public void add(Student student) {
        students.add(student);
    }

    public void remove(Student student) {
        students.remove(student);
    }

    public void remove(int id) {
        students.stream().filter(stud -> stud.getId() == id).findFirst().ifPresent(students::remove);
    }

    public void printAll() {
        System.out.println("All Students:");
        students.forEach(System.out::println);
    }

}

class Student {

    private int id;
    private String name;
    private int age;
    private EnumMap<Subject, Integer> marks;

    public Student(int id, String name, int age, EnumMap<Subject, Integer> marks) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public EnumMap<Subject, Integer> getMarks() {
        return marks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", marks=" + marks +
                '}';
    }
}

enum Subject {
    MATH,
    PHYSICS,
    CHEMISTRY,
    BIOLOGY,
    ENGLISH,
    HISTORY,
    COMPUTER_SCIENCE,
}

class Box<T> {

    private T object;

    public Box(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Box{" +
                "object=" + object +
                '}';
    }
}
