package com.codewarriors.users;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
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
import com.codewarriors.airport.Airport;

public class DestinationSelectionWindow {
    public static void showDestinationSelectionWindow() {
        JFrame frame = new JFrame("Select Destinations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Select " + CombinedApplication.numberOfDestinations + " Destinations", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        CombinedApplication.initializeDefaultAirports();

        String[] airportNames = CombinedApplication.airports.stream()
        .map(airport -> airport.getCode() + " - " + airport.getCity() + ", " + airport.getCountry())
        .toArray(String[]::new);

        JList<String> destinationList = new JList<>(airportNames);

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
                CombinedApplication.selectedAirportCodes.clear();

                // Find the airport code for each selected destination
        for (String selected : selectedDestinations) {
            for (Airport airport : CombinedApplication.airports) {
                // Check if the selected name matches the airport details
                String airportName = airport.getCode() + " - " + airport.getCity() + ", " + airport.getCountry();
                if (selected.equals(airportName)) {
                    // Add the corresponding airport code to the selectedAirportCodes list
                    CombinedApplication.selectedAirportCodes.add(airport.getCode());
                }
            }
        }

        if (CombinedApplication.selectedAirportCodes.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No valid destinations selected.");
            return;
        }

        // Dispose the frame and move to the next window
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


