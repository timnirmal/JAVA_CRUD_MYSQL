package com.crud.java_crud_mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyJDBC {
    private Connection connection;

    public static Connection DBConnect() {
        return getConnection();
    }

    static Connection getConnection() {
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abc_company", "root", "0714408821");

            if (connection != null) {
                System.out.println("Connected to the database");
                return connection;
            } else {
                System.out.println("Failed to make connection");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {

    }


}
