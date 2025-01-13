package com.codewarriors;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.codewarriors.airport.*;
import com.codewarriors.calculation.*;
import com.codewarriors.users.*;

public class CombinedApplication {
    public static List<Airport> airports = new ArrayList<>();
    public static List<String> insertedAirports = new ArrayList<>();
    public static int numberOfAirplanes = 0;
    public static int numberOfDestinations = 0;
    private static User loggedInUser;

    public static void main(String[] args) {
        User.initializeDefaultUsers();
        initializeDefaultAirports();
        initializeDefaultDestinations();
        SwingUtilities.invokeLater(LoginWindow::showLoginWindow);
    }
   
    private static void initializeDefaultDestinations() {
        String[] defaultDestinations = {
            "1. Αθήνα", "2. Παρίσι", "3. Λονδίνο", "4. Μιλάνο", "5. Βρυξέλλες",
            "6. Βερολίνο", "7. Στοκχόλμη", "8. Όσλο", "9. Μαδρίτη", "10. Άμστερνταμ"
        };
        for (String destination : defaultDestinations) {
            insertedAirports.add(destination);
        }
    }
   

    private static void initializeDefaultAirports() {
        airports.add(new Airport("JFK", "USA", "New York", 40.6413, -73.7781));
        airports.add(new Airport("LAX", "USA", "Los Angeles", 33.9416, -118.4085));
    }
    public static void manageDestinations() {
        JFrame frame = new JFrame("Manage Destinations");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
   
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
   
        // Dimiourgia pinaka aerodromiwn
        for (Airport airport : airports) {
            panel.add(new JLabel(airport.getCity() + " - " + airport.getCountry()));
        }
   
        JButton addDestinationButton = new JButton("Add Destination");
        panel.add(addDestinationButton);
   
        frame.add(panel, BorderLayout.CENTER);
   
       
        addDestinationButton.addActionListener(e -> {
            JTextField codeField = new JTextField();
            JTextField countryField = new JTextField();
            JTextField cityField = new JTextField();
            JTextField latitudeField = new JTextField();
            JTextField longitudeField = new JTextField();
   
            int result = JOptionPane.showConfirmDialog(
                null,
                new Object[] {
                    "Code:", codeField,
                    "Country:", countryField,
                    "City:", cityField,
                    "Latitude:", latitudeField,
                    "Longitude:", longitudeField
                },
                "Add New Destination",
                JOptionPane.OK_CANCEL_OPTION
            );
