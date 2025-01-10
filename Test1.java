import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Test1 {
    public static volatile int numberOfAirplanes = -1;  // Αριθμός αεροπλάνων
    public static volatile int numberOfDestinations = -1;  // Αριθμός προορισμών
    private static final Object lock = new Object();  // Κοινό lock για συγχρονισμό
    private static ArrayList<String> insertedAirports = new ArrayList<>(); // Λίστα για τους προορισμούς

    public static void main(String[] args) {
        // Δημιουργία αντικειμένων για airline και airport
        Airline airline = new Airline();
        Airport2[] airports = {
            new Airport2("Αθήνα", "37.9838", "23.7275"),
            new Airport2("Παρίσι", "48.8566", "2.3522"),
            new Airport2("Λονδίνο", "51.5074", "-0.1278"),
            new Airport2("Μιλάνο", "45.4642", "9.1900"),
            new Airport2("Βρυξέλλες", "50.8503", "4.3517"),
            new Airport2("Βερολίνο", "52.5200", "13.4050"),
            new Airport2("Στοκχόλμη", "59.3293", "18.0686"),
            new Airport2("Όσλο", "59.9139", "10.7522"),
            new Airport2("Μαδρίτη", "40.4168", "-3.7038"),
            new Airport2("Άμστερνταμ", "52.3676", "4.9041")
        };

        // Δημιουργία πίνακα για την αποθήκευση των προορισμών που επιλέγονται
        // Δημιουργία μενού για register και login
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuUI(); // Ανάλογο μενού GUI για καταχώρηση ή σύνδεση
            }
        });

        // Εμφάνιση παραθύρου για τον αριθμό των αεροπλάνων
        OptionPaneDemoNumberOfAirplanes demoAirplanes = new OptionPaneDemoNumberOfAirplanes(null);
        demoAirplanes.mainImpl();
        
        demoAirplanes.setInputListener(input -> {
            synchronized (lock) {
                numberOfAirplanes = input;
                System.out.println("Ο αριθμός αεροπλάνων που αποθηκεύτηκε είναι: " + numberOfAirplanes);
                airline.setNumberOfAirplanes(numberOfAirplanes);
                lock.notifyAll();
            }
        });

        // Thread για την εμφάνιση του παραθύρου προορισμών μετά την εισαγωγή του αριθμού αεροπλάνων
        Thread destinationsThread = new Thread(() -> {
            synchronized (lock) {
                try {
                    while (numberOfAirplanes == -1) {
                        lock.wait();
                    }

                    // Εμφανίζεται το παράθυρο για τους προορισμούς
                    OptionPaneDemoNumberOfDestinations demoDestinations = new OptionPaneDemoNumberOfDestinations(null);
                    demoDestinations.mainImpl();
                    demoDestinations.setInputListener(input -> {
                        synchronized (lock) {
                            numberOfDestinations = input;
                            System.out.println("Ο αριθμός προορισμών που αποθηκεύτηκε είναι: " + numberOfDestinations);
                            airline.setNumberOfDestinations(numberOfDestinations);
                        }
                    });
                } catch (InterruptedException e) {
                    System.out.println("Το thread διακόπηκε.");
                }
            }
        });
        destinationsThread.start();

        // Εμφάνιση παραθύρου επιλογής προορισμών
        JFrame frame = new JFrame("Flight Destinations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Select your Destinations by Number", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Εμφάνιση προορισμών σε JTextArea
        String[] destinations = {
            "1. Αθήνα", "2. Παρίσι", "3. Λονδίνο", "4. Μιλάνο", "5. Βρυξέλλες",
            "6. Βερολίνο", "7. Στοκχόλμη", "8. Όσλο", "9. Μαδρίτη", "10. Άμστερνταμ"
        };
        
        JTextArea destinationArea = new JTextArea();
        for (String destination : destinations) {
            destinationArea.append(destination + "\n");
        }
        destinationArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(destinationArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Κουμπιά και πεδίο εισαγωγής
        JPanel inputPanel = new JPanel();
        JTextField inputField = new JTextField(10);
        JButton selectButton = new JButton("Select Destination");
        inputPanel.add(new JLabel("Enter destination number:"));
        inputPanel.add(inputField);
        inputPanel.add(selectButton);
        panel.add(inputPanel, BorderLayout.SOUTH);

        JLabel selectionLabel = new JLabel("", JLabel.CENTER);
        selectionLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        panel.add(selectionLabel, BorderLayout.NORTH);

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                try {
                    int destinationIndex = Integer.parseInt(input) - 1; // -1 για να είναι 0-based index
                    if (destinationIndex >= 0 && destinationIndex < destinations.length) {
                        String selectedDestination = destinations[destinationIndex].substring(3); // Λαμβάνουμε μόνο το όνομα
                        String timesInput = JOptionPane.showInputDialog(frame, 
                            "How many times do you want to go to " + selectedDestination + "?");
                        int times = Integer.parseInt(timesInput);
                        insertedAirports.add(selectedDestination + " - " + times + " times");
                        selectionLabel.setText("You selected: " + selectedDestination + " " + times + " times.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid destination number. Please try again.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
                }
                inputField.setText(""); // Καθαρισμός πεδίου εισαγωγής
            }
        });

        JButton showSelectionsButton = new JButton("Show All Selections");
        inputPanel.add(showSelectionsButton);

        showSelectionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (insertedAirports.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No destinations selected.");
                } else {
                    StringBuilder selections = new StringBuilder("Your selections:\n");
                    for (String selection : insertedAirports) {
                        selections.append(selection).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, selections.toString());
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
