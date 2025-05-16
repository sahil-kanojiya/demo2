package com.hello.advanced.serialization_deserialization;

import java.io.*;

public class Demo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student s1 = new Student(1, "sahil",30);
        Student s2 = new Student(2, "SK",40);

        FileOutputStream fileOutputStream = new FileOutputStream("hello.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(s1);
        objectOutputStream.writeObject(s2);
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream("hello.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Student s11 = (Student) objectInputStream.readObject();
        Student s22 = (Student) objectInputStream.readObject();
        System.out.println("s11 = " + s11);
        System.out.println("s22 = " + s22);
        fileInputStream.close();
        objectInputStream.close();
    }
}

class Student implements Serializable {
    private int id;
    private String name;
    private transient int age;

    private static final long serialVersionUID = 1L;

    public Student() {
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
