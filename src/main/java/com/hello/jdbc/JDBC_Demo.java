package com.hello.jdbc;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class JDBC_Demo {

    public static void main(String[] args) {
//        try {
//            //  new versions automatically loads driver class
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "tagline@123");
//            PreparedStatement preparedStatement = conn.prepareStatement("insert into student values(?,?)");
//            preparedStatement.setInt(1, 2);
//            preparedStatement.setString(2, "happy");
//            int rows = preparedStatement.executeUpdate();
//            System.out.println(rows > 0 ? "Row inserted" : "Row not inserted");
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        try {
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "tagline@123");
//            PreparedStatement preparedStatement = conn.prepareStatement("insert into student values(?,?)");
//            Scanner scanner = new Scanner(System.in);
//            do {
//                System.out.print("Enter the student id:");
//                preparedStatement.setInt(1, scanner.nextInt());
//                scanner.nextLine();
//                System.out.print("Enter the student name:");
//                preparedStatement.setString(2, scanner.nextLine());
//                int rows = preparedStatement.executeUpdate();
//                System.out.println(rows > 0 ? "Row inserted" : "Row not inserted");
//                System.out.print("do you want to add more rows then press (1):");
//            } while (scanner.nextInt() == 1);
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        try {
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "tagline@123");
//            PreparedStatement preparedStatement = conn.prepareStatement("insert into student values(?,?)");
//            Scanner scanner = new Scanner(System.in);
//            do {
//                System.out.print("Enter the student id:");
//                preparedStatement.setInt(1, scanner.nextInt());
//                scanner.nextLine();
//                System.out.print("Enter the student name:");
//                preparedStatement.setString(2, scanner.nextLine());
//                int rows = preparedStatement.executeUpdate();
//                System.out.println(rows > 0 ? "Row inserted" : "Row not inserted");
//                System.out.print("do you want to add more rows then press (1):");
//            } while (scanner.nextInt() == 1);
//            scanner.close();
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            while (e != null) {
//                System.err.println("SQL State  : " + e.getSQLState());
//                System.err.println("Error Code : " + e.getErrorCode());
//                System.err.println("Message    : " + e.getMessage());
//                e = e.getNextException();
//            }
//        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "tagline@123");

//            database metadata
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println("databaseMetaData.getDatabaseProductName() = " + databaseMetaData.getDatabaseProductName());
            System.out.println("databaseMetaData.getURL() = " + databaseMetaData.getURL());
            System.out.println("databaseMetaData.getDriverName() = " + databaseMetaData.getDriverName());

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from student");
            while (resultSet.next()) {
                System.out.println("student { Id:" + resultSet.getInt(1) + "  Name:" + resultSet.getString(2) + " }");
            }

//            resultset metadata
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            System.out.println(resultSetMetaData);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("SQL State  : " + e.getSQLState());
            System.err.println("Error Code : " + e.getErrorCode());
            System.err.println("Message    : " + e.getMessage());
            e = e.getNextException();
        }

//        try {
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "tagline@123");
//            PreparedStatement preparedStatement = conn.prepareStatement("insert into student values(?,?)");
//            preparedStatement.setInt(1, 100);
//            preparedStatement.setString(2, "hello");
//            preparedStatement.addBatch();
//            preparedStatement.setInt(1, 101);
//            preparedStatement.setString(2, "world");
//            preparedStatement.addBatch();
//            preparedStatement.setInt(1, 103);
//            preparedStatement.setString(2, "java");
//            preparedStatement.addBatch();
//            int[] ints = preparedStatement.executeBatch();
//            System.out.println(Arrays.toString(ints));
//        } catch (SQLException e) {
//            System.err.println("SQL State  : " + e.getSQLState());
//            System.err.println("Error Code : " + e.getErrorCode());
//            System.err.println("Message    : " + e.getMessage());
//            e = e.getNextException();
//        }

//        try {
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "tagline@123");
//            conn.setAutoCommit(false);
//            try {
//                PreparedStatement preparedStatement = conn.prepareStatement("insert into student values(?,?)");
//                preparedStatement.setInt(1, 500);
//                preparedStatement.setString(2, "hello");
//                preparedStatement.execute();
//                preparedStatement.setInt(1, 501);
//                preparedStatement.setString(2, "world");
//                preparedStatement.execute();
//                preparedStatement.setInt(1, 103);
//                preparedStatement.setString(2, "java");
//                preparedStatement.execute();
//                conn.commit();
//            } catch (SQLException e) {
//                conn.rollback();
//                System.err.println("SQL State  : " + e.getSQLState());
//                System.err.println("Error Code : " + e.getErrorCode());
//                System.err.println("Message    : " + e.getMessage());
//                e = e.getNextException();
//            }
//        } catch (SQLException e) {
//            System.err.println("SQL State  : " + e.getSQLState());
//            System.err.println("Error Code : " + e.getErrorCode());
//            System.err.println("Message    : " + e.getMessage());
//            e = e.getNextException();
//        }

    }
}
