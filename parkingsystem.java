package project;

import java.sql.SQLException;

public class parkingsystem {
    public static void main(String[] args) {
        try {
            project.DatabaseManager.getConnection();
            new project.LoginScreen().show();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}