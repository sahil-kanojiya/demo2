package com.hello.basic.programs;

import java.sql.*;
import java.util.Scanner;

public class P9_JDBCStudentRecordManager {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            StudentDAO studentDAO = new StudentDAO();
            loop:
            while (true) {
                System.out.println("ENTER NUMBERS TO PERFORM ACTIONS \n" +
                        "1) insert\n" +
                        "2) update\n" +
                        "3) delete\n" +
                        "4) view all\n" +
                        "5) exit");
                System.out.print("enter no:");
                switch (scanner.nextInt()) {
                    case 1 -> {
                        System.out.print("id: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("name: ");
                        String name = scanner.nextLine();
                        System.out.print("marks: ");
                        int marks = scanner.nextInt();
                        scanner.nextLine();
                        studentDAO.insert(new Student(id, name, marks));
                    }
                    case 2 -> {
                        System.out.print("student id to update: ");
                        int oldId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("new id: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("new name: ");
                        String name = scanner.nextLine();
                        System.out.print("new marks: ");
                        int marks = scanner.nextInt();
                        scanner.nextLine();
                        studentDAO.update(oldId, new Student(id, name, marks));
                    }
                    case 3 -> {
                        System.out.print("student id to delete: ");
                        int id = scanner.nextInt();
                        studentDAO.delete(id);
                    }
                    case 4 -> {
                        studentDAO.viewAll();
                    }
                    case 5 -> {
                        break loop;
                    }
                    default -> System.out.println("invalid no");
                }
            }

//            studentDAO.insert(new Student(1, "sahil kanojiya", 90));
//            studentDAO.insert(new Student(2, "hello world", 90));
//            studentDAO.viewAll();
//            studentDAO.update(1, new Student(1, "SK", 90));
//            studentDAO.viewAll();
//            studentDAO.delete(2);
//            studentDAO.viewAll();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class StudentDAO {

    private final Connection connection;

    private final PreparedStatement insert_st;
    private final PreparedStatement update_st;
    private final PreparedStatement delete_st;
    private final PreparedStatement select_all_st;

    public StudentDAO() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "tagline@123");
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE IF EXISTS student;");
        statement.execute("CREATE TABLE student(" +
                "id INTEGER PRIMARY KEY," +
                "name VARCHAR(25) NOT NULL," +
                "marks INTEGER NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");");
        this.insert_st = connection.prepareStatement("INSERT INTO student (id, name, marks) VALUES (?,?,?);");
        this.update_st = connection.prepareStatement("UPDATE student SET id=? , name=? , marks=? WHERE id=?;");
        this.delete_st = connection.prepareStatement("DELETE FROM student WHERE id=?;");
        this.select_all_st = connection.prepareStatement("SELECT * FROM student;");
    }

    public void insert(Student student) throws Exception {
        insert_st.setInt(1, student.getId());
        insert_st.setString(2, student.getName());
        insert_st.setInt(3, student.getMarks());
        int i = insert_st.executeUpdate();
        if (i == 0) {
            throw new Exception("record not inserted");
        } else {
            System.out.println("record inserted successfully");
        }
    }

    public void update(int studentId, Student student) throws Exception {
        update_st.setInt(1, student.getId());
        update_st.setString(2, student.getName());
        update_st.setInt(3, student.getMarks());
        update_st.setInt(4, studentId);
        int i = update_st.executeUpdate();
        if (i == 0) {
            throw new Exception("record not inserted");
        } else {
            System.out.println("record updated successfully");
        }
    }

    public void delete(int studentId) throws Exception {
        delete_st.setInt(1, studentId);
        int i = delete_st.executeUpdate();
        if (i == 0) {
            throw new Exception("record not inserted");
        } else {
            System.out.println("record deleted successfully");
        }
    }

    public void viewAll() throws SQLException {
        ResultSet resultSet = select_all_st.executeQuery();
        while (resultSet.next()) {
            System.out.println("Student{" +
                    "id=" + resultSet.getInt(1) +
                    ", name='" + resultSet.getString(2) + "'" +
                    ", marks=" + resultSet.getInt(3) +
                    ", createdAt=" + resultSet.getTimestamp(4) +
                    '}');
        }
    }

}

class Student {
    private int id;
    private String name;
    private int marks;
    private Date createdAt;

    public Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
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

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", marks=" + marks +
                ", createdAt=" + createdAt +
                '}';
    }

}