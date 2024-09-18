/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipmcclinic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Software
 */
public class SQLServer {

    // Attributes or Fields
    private static final String DATABASE_URL = "jdbc:sqlserver://localhost:50641;databaseName=hospital;encrypt=true;trustServerCertificate=true;";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "SWE";
    private static Connection connection;
    public static int rowCountRS = 0;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Create
    public static void insertData(String tableName, String columnNames, String values) {
        try {
            String query = "INSERT INTO " + tableName + "(" + columnNames + ") VALUES (" + values + ")";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
            System.out.println("Data inserted successfully");
            JOptionPane.showMessageDialog(null, "Operation successful", "Operation Successful", 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read
    public static MyTableModel selectData(String tableName) {
        ResultSet resultSet = null;
        MyTableModel tableModel = null;

        try {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery();
            tableModel = new MyTableModel(resultSet);
            JOptionPane.showMessageDialog(null, "Table loaded successfully", "Loading Table", 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableModel;
    }


    public static MyTableModel selectData(String columns, String tableName) {
        ResultSet resultSet = null;
        MyTableModel tableModel = null;

        try {
            String query = "SELECT " + columns + " FROM " + tableName;
            PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery();
            tableModel = new MyTableModel(resultSet);
            JOptionPane.showMessageDialog(null, "Table loaded successfully", "Loading Table", 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableModel;
    }

    // Update
    public static void updateData(String tableName, String columnNamesAndValues, String condition) {
        try {
            String query = "UPDATE " + tableName + " SET " + columnNamesAndValues + " WHERE " + condition;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
            System.out.println("Data updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete
    public static void deleteData(String tableName, String condition) {
        try {
            String query = "DELETE FROM " + tableName + " WHERE " + condition;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
            System.out.println("Data deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch Data
    public static ResultSet fetchData(String query) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Login
    public static boolean login(String username, String password) {
        try {
            String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true; // Login successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Login failed
    }

    public static boolean login(String id, String password, String dpt) {
        try {
            String query = "SELECT * FROM staff WHERE Staff_id = ? AND Staff_password = ? AND Staff_dpt = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, password);
            statement.setString(3, dpt);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true; // Login successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Login failed
    }
}
