import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {
    public static volatile int numberOfAirplanes = -1;  // Αριθμός αεροπλάνων
    public static volatile int numberOfDestinations = -1;  // Αριθμός προορισμών
    private static final Object lock = new Object();  // Κοινό lock για συγχρονισμό

    public static void main(String[] args) {
        // Δημιουργία αντικειμένων για airline, airport και haversine
        Airline airline = new Airline();
        Airport[] airports = new Airport[]{
            new Airport("Αθήνα", 37.9838, 23.7275),
            new Airport("Παρίσι", 48.8566, 2.3522),
            new Airport("Λονδίνο", 51.5074, -0.1278),
            new Airport("Μιλάνο", 45.4642, 9.1900),
            new Airport("Βρυξέλλες", 50.8503, 4.3517),
            new Airport("Βερολίνο", 52.5200, 13.4050),
            new Airport("Στοκχόλμη", 59.3293, 18.0686),
            new Airport("Όσλο", 59.9139, 10.7522),
            new Airport("Μαδρίτη", 40.4168, -3.7038),
            new Airport("Άμστερνταμ", 52.3676, 4.9041)
        };

        // Δημιουργία δισδιάστατου πίνακα String για αποθήκευση των δεδομένων
        String[][] airportArray = new String[airports.length][3];
        for (int i = 0; i < airports.length; i++) {
            airportArray[i][0] = airports[i].getName();                // Όνομα
            airportArray[i][1] = String.valueOf(airports[i].getLatitude());  // Γεωγραφικό Πλάτος
            airportArray[i][2] = String.valueOf(airports[i].getLongitude()); // Γεωγραφικό Μήκος
        }

        // Δημιουργία πίνακα για την αποθήκευση των προορισμών που επιλέγονται
        String[] insertedAirports = new String[numberOfDestinations]; 

        // Μενού για register και login
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
        for (int i = 0; i < numberOfDestinations; i++) {
            JFrame frame = new JFrame("Flight Destinations");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            JLabel titleLabel = new JLabel("Select your Destinations", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            panel.add(titleLabel, BorderLayout.NORTH);

            String[] destinations = {
                "1. Αθήνα", "2. Παρίσι", "3. Λονδίνο", "4. Μιλάνο", "5. Βρυξέλλες",
                "6. Βερολίνο", "7. Στοκχόλμη", "8. Όσλο", "9. Μαδρίτη", "10. Άμστερνταμ"
            };

            JList<String> destinationList = new JList<>(destinations);
            destinationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(destinationList);
            panel.add(scrollPane, BorderLayout.CENTER);

            JButton selectButton = new JButton("Show Selections");
            panel.add(selectButton, BorderLayout.SOUTH);

            JLabel selectionLabel = new JLabel("", JLabel.CENTER);
            selectionLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            panel.add(selectionLabel, BorderLayout.SOUTH);

            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<String> selectedDestinations = destinationList.getSelectedValuesList();
                    if (selectedDestinations != null && !selectedDestinations.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "You selected: " + String.join(", ", selectedDestinations));
                        insertedAirports[i] = String.join(", ", selectedDestinations);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please select a destination.");
                    }
                }
            });

            frame.add(panel);
            frame.setVisible(true);
        }
    }
}
