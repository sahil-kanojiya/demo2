package com.hello.basic.programs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class P5_EmployeeSalarySorter {

    public static void main(String[] args) {
        List<Employee> employeeList = new ArrayList<>(List.of(
                new Employee(101, "sahil", 85000),
                new Employee(101, "shyam", 150000),
                new Employee(101, "raju", 100000)
        ));

        employeeList.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return Integer.compare(o1.getSalary(), o2.getSalary());
            }
        });

        System.out.println(employeeList);
    }

}

class Employee implements Comparable<Employee> {

    private long id;
    private String name;
    private int salary;

    public Employee(long id, String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public int compareTo(Employee o) {
        return Long.compare(id, o.id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }

}