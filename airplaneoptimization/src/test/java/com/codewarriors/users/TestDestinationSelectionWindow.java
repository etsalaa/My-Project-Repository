package com.codewarriors.users;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.codewarriors.CombinedApplication;

public class TestDestinationSelectionWindow {
    public static void showDestinationSelectionWindow() {
        JFrame frame = new JFrame("Select Destinations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Select " + CombinedApplication.numberOfDestinations + " Destinations", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JList<String> destinationList = new JList<>(CombinedApplication.airports.toArray(new String[0]));
        destinationList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(destinationList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit Selection");
        panel.add(submitButton, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> {
            List<String> selectedDestinations = destinationList.getSelectedValuesList();
            if (selectedDestinations.size() == CombinedApplication.numberOfDestinations) {
                CombinedApplication.insertedAirports.clear();
                CombinedApplication.insertedAirports.addAll(selectedDestinations);
                frame.dispose();
                DestinationVisitsWindow.showDestinationVisitsWindow();
            } else {
                JOptionPane.showMessageDialog(frame, "Please select exactly " + CombinedApplication.numberOfDestinations + " destinations.");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}

