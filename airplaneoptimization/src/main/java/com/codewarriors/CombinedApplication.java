package com.codewarriors;

/**
 * Hello world!
 *
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.codewarriors.airport.*;
import com.codewarriors.calculation.*;
import com.codewarriors.users.*;

public class CombinedApplication {
    public static List<String> selectedAirportCodes = new ArrayList<>();
    public static List<Airport> airports = new ArrayList<>();
    public static List<String> insertedAirports = new ArrayList<>();
    public static int numberOfAirplanes = 0;
    public static int numberOfDestinations = 0;
    private static User loggedInUser;

    public static void main(String[] args) {
        User.initializeDefaultUsers();
        initializeDefaultAirports();
        SwingUtilities.invokeLater(LoginWindow::showLoginWindow);
    }
    

    public static void initializeDefaultAirports() {
        if (airports.isEmpty()) {
            airports.add(new Airport("ATH", "Greece", "Athens", 37.9838, 23.7275));
            airports.add(new Airport("LAX", "USA", "Los Angeles", 33.9416, -118.4085));
            airports.add(new Airport("CDG", "France", "Paris", 48.8566, 2.3522));
            airports.add(new Airport("LHR", "United Kingdom", "London", 51.5074, -0.1278));
            airports.add(new Airport("MXP", "Italy", "Milan", 45.4642, 9.1900));
            airports.add(new Airport("BRU", "Belgium", "Brussels", 50.8503, 4.3517));
            airports.add(new Airport("BER", "Germany", "Berlin", 52.5200, 13.4050));
            airports.add(new Airport("ARN", "Sweeden", "Stockholm", 59.3292, 18.0686));
            airports.add(new Airport("OSL", "Norway", "Oslo", 59.9139, 10.7522));
            airports.add(new Airport("MAD", "Spain", "Madrid", 40.4168, -3.7038));
            airports.add(new Airport("AMS", "Netherlands", "Amsterdam", 52.3676, 4.9041));
        }
    }


    public static void manageDestinations() {
        JFrame frame = new JFrame("Manage Destinations");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        // Dixnei ta aerodromia
        for (Airport airport : airports) {
            panel.add(new JLabel(airport.getCode() + " - " + airport.getCity() + " - " + airport.getCountry()));
        }
    
        JButton addDestinationButton = new JButton("Add Destination");
        panel.add(addDestinationButton);
    
        frame.add(panel, BorderLayout.CENTER);
    
        // Prosthetei proorismous o admin
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
    
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String code = codeField.getText().trim();
                    String country = countryField.getText().trim();
                    String city = cityField.getText().trim();
                    double latitude = Double.parseDouble(latitudeField.getText().trim());
                    double longitude = Double.parseDouble(longitudeField.getText().trim());
    
                    if (code.isEmpty() || country.isEmpty() || city.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Airport newAirport = new Airport(code, country, city, latitude, longitude);
                        airports.add(newAirport);
                        JOptionPane.showMessageDialog(frame, "New destination added: " + code + ", " + city + ", " + country);
                        frame.dispose();
                        manageDestinations(); 
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid latitude or longitude!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.setVisible(true);
    }
    
    // parathiro pou dixnei tin eisagwgi arithmou aeroplanwn
    public static void showNumberOfAirplanesWindow() {
        JFrame frame = new JFrame("Number of Airplanes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
    
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Enter the number of airplanes:"));
    
        JTextField inputField = new JTextField();
        panel.add(inputField);
    
        JButton submitButton = new JButton("Submit");
        frame.add(panel, BorderLayout.CENTER);
        frame.add(submitButton, BorderLayout.SOUTH);
    
        submitButton.addActionListener(e -> {
            try {
                numberOfAirplanes = Integer.parseInt(inputField.getText());
                if (numberOfAirplanes > 0) {
                    JOptionPane.showMessageDialog(frame, "Number of airplanes saved: " + numberOfAirplanes);
                    frame.dispose();
                    showNumberOfDestinationsWindow();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a positive number!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number!");
            }
        });
    
        frame.setVisible(true);
    }
    public static void addNewDestination(String destination) {
        if (destination != null && !destination.trim().isEmpty()) {
            insertedAirports.add(destination); 
            JOptionPane.showMessageDialog(null, "New destination added: " + destination);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid destination!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //kanei logout kai redirect
    public static void logout(JFrame currentFrame) {
        loggedInUser = null; 
        currentFrame.dispose(); 
        SwingUtilities.invokeLater(LoginWindow::showLoginWindow); 
    }
    
    //parathiro pou dixnei tin eisagwgi arithmou proorismwn
    public static void showNumberOfDestinationsWindow() {
        JFrame frame = new JFrame("Number of Destinations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
    
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Enter the number of destinations:"));
    
        JTextField inputField = new JTextField();
        panel.add(inputField);
    
        JButton submitButton = new JButton("Submit");
        frame.add(panel, BorderLayout.CENTER);
        frame.add(submitButton, BorderLayout.SOUTH);
    
        submitButton.addActionListener(e -> {
            try {
                numberOfDestinations = Integer.parseInt(inputField.getText());
                if (numberOfDestinations > 0 && numberOfDestinations <= numberOfAirplanes*3) {
                    JOptionPane.showMessageDialog(frame, "Number of destinations saved: " + numberOfDestinations);
                    frame.dispose();
                    DestinationSelectionWindow.showDestinationSelectionWindow();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a number between 1 and " + numberOfAirplanes*3);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number!");
            }
        });
    
        frame.setVisible(true);
    }

    //ypologizei thn xiliometriki apostasi mesw geografikou mikous kai platous twn epilgemenwn proorismwn apo ton xrhsth
    public static void calculateDistancesAndAssignments(int[] visits) {
        double[][] destinationCoordinates = new double[numberOfDestinations][2];
        double[] mockLatitudes = new double[numberOfDestinations];
        double[] mockLongitudes = new double[numberOfDestinations];

        int i = 0;
        for (String selectedCode : selectedAirportCodes) {
            for (Airport airport : CombinedApplication.airports) { 
                if (airport.getCode().equals(selectedCode)) {
                    mockLatitudes[i] = airport.getLatitude();
                    mockLongitudes[i] = airport.getLongitude();
                    i++;  
                    break;  
                }
            }
        }
    
        for (i = 0; i < numberOfDestinations; i++) {
            destinationCoordinates[i][0] = (mockLatitudes[i]);
            destinationCoordinates[i][1] = (mockLongitudes[i]);
        }
    
        int[][] distanceMatrix = new int[numberOfDestinations][numberOfDestinations];
    
        for (i = 0; i < numberOfDestinations; i++) {
            for (int j = i + 1; j < numberOfDestinations; j++) {
                double distance = HaversineDistance2.haversine(
                    destinationCoordinates[i][0], destinationCoordinates[i][1],
                    destinationCoordinates[j][0], destinationCoordinates[j][1]
                );
                distanceMatrix[i][j] = (int) Math.round(distance);
                distanceMatrix[j][i] = distanceMatrix[i][j];
            }
        }
    
        assignDestinationsToAirplanes(distanceMatrix, visits);
    }
    
    public static void assignDestinationsToAirplanes(int[][] distanceMatrix, int[] visits) {
        int[][] airplaneAssignments = new int[numberOfAirplanes][3]; // kathe aeroplanw mporei na kanei 3 diadromes
        int[] remainingVisits = visits.clone(); 
    
        for (int airplane = 0; airplane < numberOfAirplanes; airplane++) {
            int lastLocation = -1; 
            double totalDistance = 0;
            StringBuilder result = new StringBuilder("Airplane " + (airplane + 1) + " visits: ");
    
            for (int assignment = 0; assignment < 3; assignment++) {
                int nearestDestination = -1;
                int minimumDistance = Integer.MAX_VALUE;
    
                for (int destination = 0; destination < numberOfDestinations; destination++) {
                    if (remainingVisits[destination] > 0 && destination != lastLocation &&
                        distanceMatrix[lastLocation == -1 ? 0 : lastLocation][destination] < minimumDistance) {
                        nearestDestination = destination;
                        minimumDistance = distanceMatrix[lastLocation == -1 ? 0 : lastLocation][destination];
                    }
                }
    
                if (nearestDestination != -1) {
                    airplaneAssignments[airplane][assignment] = nearestDestination;
                    remainingVisits[nearestDestination]--; 
                    totalDistance += minimumDistance;
                    lastLocation = nearestDestination; 
    
                    result.append(insertedAirports.get(nearestDestination)).append(", ");
                } else {
                    result.append("No more destinations available, ");
                }
            }
    
            if (result.length() > 0) {
                result.setLength(result.length() - 2);
            }
    
            result.append("\nTotal Distance: ").append(totalDistance).append(" km");
    
            JOptionPane.showMessageDialog(null, result.toString(), "Airplane Assignments", JOptionPane.INFORMATION_MESSAGE);
        }
    
        for (int destination = 0; destination < numberOfDestinations; destination++) {
            if (remainingVisits[destination] > 0) {
                JOptionPane.showMessageDialog(null, "Remaining visits for " +
                        insertedAirports.get(destination) + ": " + remainingVisits[destination],
                        "Unfulfilled Visits", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}

