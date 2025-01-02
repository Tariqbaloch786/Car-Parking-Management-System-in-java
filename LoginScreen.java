package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen {
    public void show() {
        JFrame loginFrame = new JFrame("Login Page");
        loginFrame.setSize(300, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JLabel lblUsername = new JLabel("Username:");
        JTextField txtUsername = new JTextField();
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField txtPassword = new JPasswordField();
        JButton btnLogin = new JButton("Login");

        loginFrame.setLayout(new GridLayout(3, 2));
        loginFrame.add(lblUsername);
        loginFrame.add(txtUsername);
        loginFrame.add(lblPassword);
        loginFrame.add(txtPassword);
        loginFrame.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            if (username.equals("manager") && password.equals("manager123") || username.equals("Tariq") && password.equals("Tariq123")) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                loginFrame.dispose();
                new MainMenu().show();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials.");
            }
        });

        loginFrame.setVisible(true);
    }
}