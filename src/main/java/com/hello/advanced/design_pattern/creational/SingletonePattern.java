package com.hello.advanced.design_pattern.creational;

public class SingletonePattern {

    public static void main(String[] args) {
        Connection connection1 = DriverManager.getConnection();
        Connection connection2 = DriverManager.getConnection();
        System.out.println("connection1==connection2 = " + (connection1 == connection2));
    }

}

class DriverManager {

    private static Connection connection;

    static Connection getConnection() {
        if (connection == null) {
            connection = new Connection();
        }
        return connection;
    }

}

class Connection {

    public Connection() {
        System.out.println("connection instance created.");
    }
}