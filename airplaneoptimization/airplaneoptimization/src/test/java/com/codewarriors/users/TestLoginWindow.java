package com.codewarriors.users;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.codewarriors.CombinedApplication;
import com.codewarriors.admins.AdminMenu;

public class TestLoginWindow {
    public static void showLoginWindow() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        frame.add(panel);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            User user = User.authenticate(username, password);
        
            if (user != null) {
                frame.dispose(); // Close the login window
                if (user.getRole().equals("admin")) {
                    AdminMenu.showAdminMenu(); // Redirect to Admin Menu
                } else {
                    CombinedApplication.showNumberOfAirplanesWindow(); // Redirect to user functionality
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid login details!");
            }
        });
    }
}