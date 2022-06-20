package com.crud.java_crud_mysql;

import java.sql.*;


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

    // Read data from database
    public static void readData() {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM department");
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getString("Dname") + " \t"
                        + resultSet.getString("Dnumber") + " \t"
                        + resultSet.getString("Mgr_ssn") + " \t"
                        + resultSet.getString("Mgr_start_due") + " \t"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void readDataL() {
            try {
                Connection connection = DBConnect();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM dept_location");
                while (resultSet.next()) {
                    System.out.println(
                            resultSet.getString("Dnumber") + " \t"
                            + resultSet.getString("Dlocation")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    /*public void insert(String name, String email, String phone) {
        try {
            Connection connection = DBConnect();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, email, phone) VALUES (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.executeUpdate();
            System.out.println("Inserted");
        } catch (SQLException e) {
            e.printStackTrace();*/

    public static void main(String[] args) {
        System.out.println("Hello World!");

        // Create a connection to the database
        //Connection connection = DBConnect();

        // Read data from database
        readDataL();


        /*
        // Create a statement to execute SQL commands
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create a result set to hold the result of the query
        ResultSet resultSet = null;

        // Execute the query
        try {
            resultSet = statement.executeQuery("SELECT * FROM department");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Print the result of the query
        try {
            while (resultSet.next()) {
                System.out.println(resultSet.getString("Dname") + " " + resultSet.getString("Dnumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Close the connection
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        /*try {
            // 2. Create a statement
            //Statement statement = connection.createStatement();

            // 3. Execute a query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM department");

            // 4. Process the result set
            while (resultSet.next()) {
                //System.out.println(resultSet.getString("Dname"));
                //System.out.println(resultSet.getString("Dnumber"));
            }

            // 5. Close the connection
            connection.close();





                        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM department");

            ResultSet resultSet = preparedStatement.executeQuery();

            // 4. Process the result set
            while (resultSet.next()) {
                System.out.println(resultSet.getString("Dname"));
                System.out.println(resultSet.getString("Dnumber"));
            }





        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }*/
    }


}
