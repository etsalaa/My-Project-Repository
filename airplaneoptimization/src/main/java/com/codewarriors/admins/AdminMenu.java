package com.codewarriors.admins;

import javax.swing.*;

import com.codewarriors.CombinedApplication;

public class AdminMenu {
    public static void showAdminMenu() {
        JFrame frame = new JFrame("Admin Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();

        JButton addUserButton = new JButton("Add User");
        JButton addDestinationButton = new JButton("Add Destination");

        // Ensure logoutButton is defined only once
        JButton logoutButton = new JButton("Logout"); 
        logoutButton.addActionListener(e -> {
            CombinedApplication.logout(frame); // Call the logout method
        });

        panel.add(addUserButton);
        panel.add(addDestinationButton);
        panel.add(logoutButton); // Add the logout button to the panel

        addUserButton.addActionListener(e -> {
            UserManagement.addUser();
        });

        addDestinationButton.addActionListener(e -> {
            CombinedApplication.manageDestinations();
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
