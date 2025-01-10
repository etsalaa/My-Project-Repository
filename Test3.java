import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Test3 {
    public static volatile int numberOfAirplanes = -1;  // Αριθμός αεροπλάνων
    public static volatile int numberOfDestinations = -1;  // Αριθμός προορισμών
    private static final ArrayList<String> insertedAirports = new ArrayList<>(); // Λίστα για τους προορισμούς

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> showLoginWindow());
    }

    private static void showLoginWindow() {
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

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (!username.isEmpty() && !password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                frame.dispose(); // Κλείσιμο του παραθύρου login
                showNumberOfAirplanesWindow(); // Εμφάνιση παραθύρου αριθμού αεροπλάνων
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields!");
            }
        });

        frame.setVisible(true);
    }

    private static void showNumberOfAirplanesWindow() {
        JFrame frame = new JFrame("Number of Airplanes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Enter the number of airplanes:"));

        JTextField inputField = new JTextField();
        panel.add(inputField);

        JButton submitButton = new JButton("Submit");
        panel.add(submitButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(submitButton, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> {
            try {
                numberOfAirplanes = Integer.parseInt(inputField.getText());
                if (numberOfAirplanes > 0) {
                    JOptionPane.showMessageDialog(frame, "Number of airplanes saved: " + numberOfAirplanes);
                    frame.dispose(); // Κλείσιμο του παραθύρου
                    showNumberOfDestinationsWindow(); // Εμφάνιση παραθύρου αριθμού προορισμών
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a positive number!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number!");
            }
        });

        frame.setVisible(true);
    }

    private static void showNumberOfDestinationsWindow() {
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
                if (numberOfDestinations > 0 && numberOfDestinations <= 10) {
                    JOptionPane.showMessageDialog(frame, "Number of destinations saved: " + numberOfDestinations);
                    frame.dispose(); // Κλείσιμο του παραθύρου
                    showDestinationSelectionWindow(); // Εμφάνιση παραθύρου επιλογής προορισμών
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a number between 1 and 10!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number!");
            }
        });

        frame.setVisible(true);
    }

    private static void showDestinationSelectionWindow() {
        List<Integer> insertedAerodromia = new ArrayList<>();
    
        Airport[] airportsArray = {
            new Airport("Αθήνα", "37.9838", "23.7275"),
            new Airport("Παρίσι", "48.8566", "2.3522"),
            new Airport("Λονδίνο", "51.5074", "-0.1278"),
            new Airport("Μιλάνο", "45.4642", "9.1900"),
            new Airport("Βρυξέλλες", "50.8503", "4.3517"),
            new Airport("Βερολίνο", "52.5200", "13.4050"),
            new Airport("Στοκχόλμη", "59.3293", "18.0686"),
            new Airport("Όσλο", "59.9139", "10.7522"),
            new Airport("Μαδρίτη", "40.4168", "-3.7038"),
            new Airport("Άμστερνταμ", "52.3676", "4.9041")
        };

    int[] visitUpdates = new int[numberOfDestinations];
    Arrays.fill(visitUpdates, 0); // Αρχικοποίηση με 0 επισκέψεις για όλους τους προορισμούς
    for (int i = 0; i < numberOfDestinations; i++) {
        JFrame frame = new JFrame("Select Destinations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Select " + numberOfDestinations + " Destinations", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] destinations = {
            "1. Αθήνα", "2. Παρίσι", "3. Λονδίνο", "4. Μιλάνο", "5. Βρυξέλλες",
            "6. Βερολίνο", "7. Στοκχόλμη", "8. Όσλο", "9. Μαδρίτη", "10. Άμστερνταμ"
        };

        JList<String> destinationList = new JList<>(destinations);
        destinationList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(destinationList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton selectButton = new JButton("Submit Selection");
        panel.add(selectButton, BorderLayout.SOUTH);

        selectButton.addActionListener(e -> {
            int selectedIndex = destinationList.getSelectedIndex(); // Παίρνει την επιλογή του χρήστη
                    if (selectedIndex != -1) {
                        // Εμφανίζει διάλογο για εισαγωγή αριθμού επισκέψεων
                        String visitCountInput = JOptionPane.showInputDialog(frame, 
                        "How many times do you want to visit " + destinations[selectedIndex] + "?");

                        try {
                            int visitCount = Integer.parseInt(visitCountInput); // Μετατρέπει την είσοδο σε ακέραιο αριθμό
                            if (visitCount > 0) {
                                JOptionPane.showMessageDialog(frame, 
                                "You selected: " + destinations[selectedIndex] + " with " + visitCount + " visits.");
                    
                                insertedAerodromia.add(selectedIndex); // Αποθηκεύει τον δείκτη της επιλεγμένης περιοχής
                                visitUpdates[selectedIndex] = visitCount; // Αποθηκεύει τον αριθμό επισκέψεων για την περιοχή
                            } else {
                                JOptionPane.showMessageDialog(frame, 
                                "Please enter a positive number of visits.");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, 
                    "Invalid input. Please enter a valid number.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please select a destination.");
                    }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
    }

}
