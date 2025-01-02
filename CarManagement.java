package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CarManagement {
    public void parkCar(JFrame parentFrame) {
        JFrame parkFrame = new JFrame("Check-in Car");
        parkFrame.setSize(300, 250);
        parkFrame.setLocationRelativeTo(parentFrame);

        JTextField txtOwnerName = new JTextField();
        JTextField txtCarNumber = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JButton btnPark = new JButton("Park Car");

        parkFrame.setLayout(new GridLayout(4, 2));
        parkFrame.add(new JLabel("Owner Name:"));
        parkFrame.add(txtOwnerName);
        parkFrame.add(new JLabel("Car Number:"));
        parkFrame.add(txtCarNumber);
        parkFrame.add(new JLabel("Password:"));
        parkFrame.add(txtPassword);
        parkFrame.add(btnPark);

        btnPark.addActionListener(e -> {
            String ownerName = txtOwnerName.getText();
            String carNumber = txtCarNumber.getText();
            String password = new String(txtPassword.getPassword());

            try (Connection conn = DatabaseManager.getConnection()) {
                String query = "INSERT INTO cars (car_owner_name, car_number, password) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, ownerName);
                stmt.setString(2, carNumber);
                stmt.setString(3, password);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(parkFrame, "Car parked successfully!");
                parkFrame.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(parkFrame, "Error parking the car.");
                ex.printStackTrace();
            }
        });

        parkFrame.setVisible(true);
    }

    public void checkoutCar(JFrame parentFrame) {
        JFrame checkoutFrame = new JFrame("Checkout Car");
        checkoutFrame.setSize(300, 200);
        checkoutFrame.setLocationRelativeTo(parentFrame);

        JTextField txtCarNumber = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JButton btnCheckout = new JButton("Checkout");

        checkoutFrame.setLayout(new GridLayout(3, 2));
        checkoutFrame.add(new JLabel("Car Number:"));
        checkoutFrame.add(txtCarNumber);
        checkoutFrame.add(new JLabel("Password:"));
        checkoutFrame.add(txtPassword);
        checkoutFrame.add(btnCheckout);

        btnCheckout.addActionListener(e -> {
            String carNumber = txtCarNumber.getText();
            String password = new String(txtPassword.getPassword());

            try (Connection conn = DatabaseManager.getConnection()) {
                String query = "SELECT * FROM cars WHERE car_number = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, carNumber);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String deleteQuery = "DELETE FROM cars WHERE car_number = ?";
                    PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                    deleteStmt.setString(1, carNumber);
                    deleteStmt.executeUpdate();
                    JOptionPane.showMessageDialog(checkoutFrame, "Car checked out successfully!");
                    checkoutFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(checkoutFrame, "Invalid car number or password.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(checkoutFrame, "Error checking out the car.");
                ex.printStackTrace();
            }
        });

        checkoutFrame.setVisible(true);
    }

    public void showAllCars(JFrame parentFrame) {
        JFrame showCarsFrame = new JFrame("All Parked Cars");
        showCarsFrame.setSize(400, 300);
        showCarsFrame.setLocationRelativeTo(parentFrame);

        JTable carTable = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Owner Name", "Car Number", "Password"}, 0);
        carTable.setModel(model);
        showCarsFrame.add(new JScrollPane(carTable));

        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM cars";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("car_owner_name"),
                        rs.getString("car_number"),
                        rs.getString("password")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(showCarsFrame, "Error fetching car data.");
            ex.printStackTrace();
        }

        showCarsFrame.setVisible(true);
    }
    public void UpdateCars(JFrame parentFrame) {
        JFrame updateFrame = new JFrame("Update Car Details");
        updateFrame.setSize(300, 250);
        updateFrame.setLocationRelativeTo(parentFrame);

        JTextField txtCarNumber = new JTextField(); // Existing car number
        JTextField txtNewCarNumber = new JTextField(); // New car number
        JPasswordField txtPassword = new JPasswordField(); // New password
        JButton btnUpdate = new JButton("Update Details");

        updateFrame.setLayout(new GridLayout(4, 2));
        updateFrame.add(new JLabel("Existing Car Number:"));
        updateFrame.add(txtCarNumber);
        updateFrame.add(new JLabel("New Car Number:"));
        updateFrame.add(txtNewCarNumber);
        updateFrame.add(new JLabel("New Password:"));
        updateFrame.add(txtPassword);
        updateFrame.add(btnUpdate);

        btnUpdate.addActionListener(e -> {
            String carNumber = txtCarNumber.getText();
            String newCarNumber = txtNewCarNumber.getText();
            String password = new String(txtPassword.getPassword());

            if (carNumber.isEmpty() || newCarNumber.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(updateFrame, "All fields are required!");
                return;
            }

            try (Connection conn = DatabaseManager.getConnection()) {
                // Check if the car exists
                String checkQuery = "SELECT * FROM cars WHERE car_number = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                checkStmt.setString(1, carNumber);
                ResultSet rs = checkStmt.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(updateFrame, "Car not found!");
                    return;
                }

                // Update car details
                String updateQuery = "UPDATE cars SET car_number = ?, password = ? WHERE car_number = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, newCarNumber);
                updateStmt.setString(2, password);
                updateStmt.setString(3, carNumber);
                updateStmt.executeUpdate();

                JOptionPane.showMessageDialog(updateFrame, "Car details updated successfully!");
                updateFrame.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(updateFrame, "Error updating car details.");
                ex.printStackTrace();
            }
        });

        updateFrame.setVisible(true);
    }
    public void searchCar(JFrame parentFrame) {
        String carNumber = JOptionPane.showInputDialog(parentFrame, "Enter Car Number to Search:");

        if (carNumber == null || carNumber.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Car number cannot be empty!");
            return;
        }

        JFrame searchResultFrame = new JFrame("Search Result");
        searchResultFrame.setSize(400, 200);
        searchResultFrame.setLocationRelativeTo(parentFrame);

        JTable resultTable = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Owner Name", "Car Number", "Password"}, 0);
        resultTable.setModel(model);
        searchResultFrame.add(new JScrollPane(resultTable));

        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM cars WHERE car_number = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, carNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("car_owner_name"),
                        rs.getString("car_number"),
                        rs.getString("password")
                });
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Car not found!");
                return;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Error searching for the car.");
            ex.printStackTrace();
        }

        searchResultFrame.setVisible(true);
    }



}



