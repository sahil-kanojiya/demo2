package com.hello.advanced.programs;

//Employee Data Persistence Tool
//        Requirements:
//        Create an Employee class and serialize a list of employees to a .ser file.
//        Deserialize the file back into Java objects.
//        Demonstrate custom serialization using writeObject()/readObject().

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class P5_EmployeeDataPersistenceTool {

    public static void main(String[] args) {
        try {
            EmployeeDataPersistenceTool employeeDataPersistenceTool = new EmployeeDataPersistenceTool();
//            employeeDataPersistenceTool.add(new Employee(1, "sahil", 20, 100000));
//            employeeDataPersistenceTool.printAllEmployee();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


class EmployeeDataPersistenceTool {

    private final List<Employee> employeeList;
    File file;

    public EmployeeDataPersistenceTool() throws IOException, ClassNotFoundException {
        this.employeeList = new ArrayList<>();
        this.file = new File("emp.ser");
        loadDataFromFile();
    }

    private void loadDataFromFile() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Employee emp = (Employee) objectInputStream.readObject();
                    employeeList.add(emp);
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (EOFException e) {
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("All data loaded.");
        System.out.println("All data:" + employeeList);
    }

    public boolean add(Employee employee) {
        try {
            persist(employee);
            if (employeeList.add(employee)) {
                System.out.println("employee persist successfully");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void persist(Employee employee) throws IOException {
        boolean append = file.exists() && file.length() > 0;
        try (ObjectOutputStream objectOutputStream = append ? new AppendingObjectOutputStream(new FileOutputStream(file, true)) : new ObjectOutputStream(new FileOutputStream(file))) {
            objectOutputStream.writeObject(employee);
            objectOutputStream.flush();
        }
    }

    public void printAllEmployee() {
        System.out.println("List:" + employeeList);
    }
}


class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int age;
    private int salary;

    public Employee(int id, String name, int age, int salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
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

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}


class AppendingObjectOutputStream extends ObjectOutputStream {
    public AppendingObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        reset();
    }
}
