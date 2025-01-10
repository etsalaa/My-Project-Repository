import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main2 {
    public static volatile int numberOfAirplanes = -1;  // Αριθμός αεροπλάνων
    public static volatile int numberOfDestinations = -1;  // Αριθμός προορισμών
        private static final Object lock = new Object();  // Κοινό lock για συγχρονισμό
        /**
         * @param args
         */
        public static void main (String [] args) {
            //eisagwgi dedomenwn
            Scanner s = new Scanner(System.in);
            Airplane airplane = new Airplane();
            Airline airline = new Airline();
            Airport airports = new Airport();
            HaversineDistance hd = new HaversineDistance();
            //μενού για register και login
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MenuUI();
                }
    
            });
            // Εμφανίζεται το παράθυρο για τα αεροπλάνα
            OptionPaneDemoNumberOfAirplanes demoAirplanes = new OptionPaneDemoNumberOfAirplanes(null);
            demoAirplanes.mainImpl();
            
            demoAirplanes.setInputListener(input -> {
                synchronized (lock) {
                    numberOfAirplanes = input; // Αποθηκεύουμε την απάντηση
                    System.out.println("Ο αριθμός αεροπλάνων που αποθηκεύτηκε είναι: " + numberOfAirplanes);
                    airline.setNumberOfAirplanes(numberOfAirplanes); 
                    lock.notifyAll(); // Ενημερώνουμε ότι ολοκληρώθηκε η εισαγωγή
                }
            });
            
            //// Thread που περιμένει να απαντηθεί το πρώτο παράθυρο (αεροπλάνα) και μετά ανοίγει το δεύτερο (προορισμοί)
            Thread destinationsThread = new Thread(() -> {
                synchronized (lock) {
                    try {
                        while (numberOfAirplanes == -1) {
                            System.out.println("Αναμονή για την εισαγωγή αριθμού αεροπλάνων...");
                            lock.wait(); // Περιμένει μέχρι να απαντηθεί το πρώτο παράθυρο
                        }
    
                        // Μόλις απαντηθεί, ανοίγει το δεύτερο παράθυρο
                        OptionPaneDemoNumberOfDestinations demoDestinations = new OptionPaneDemoNumberOfDestinations(null);
                        demoDestinations.mainImpl();
                        demoDestinations.setInputListener(input -> {
                            synchronized (lock) {
                                numberOfDestinations = input; // Αποθηκεύουμε την απάντηση
                                System.out.println("Ο αριθμός προορισμών που αποθηκεύτηκε είναι: " + numberOfDestinations);
                                airline.setNumberOfDestinations(numberOfDestinations);
                            }
                        });
                    } catch (InterruptedException e) {
                        System.out.println("Το thread διακόπηκε.");
                    }
                }
            });
            destinationsThread.start(); // Ξεκινάμε το thread για τους προορισμούς
            List<Airplane> aerodromia = new ArrayList<>();
            List<Integer> insertedAerodromia = new ArrayList<>();
    
            Airport[] aerodromia = {
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
            Arrays.fill(visitUpdates, 0); // Initialize with 0 visits for all destinations
            int selectIndex;
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
    
                selectButton.addActionListener(e -> {
                int selectedIndex = destinationList.getSelectedIndex();
                if (selectedIndex != -1) {
                    // Show a dialog to get the number of visits
                    String visitCountInput = JOptionPane.showInputDialog(frame, 
                        "How many times do you want to visit " + destinations[selectedIndex] + "?");
    
                    try {
                        int visitCount = Integer.parseInt(visitCountInput); // Parse the number of visits
                        if (visitCount > 0) {
                            JOptionPane.showMessageDialog(frame, 
                                "You selected: " + destinations[selectedIndex] + " with " + visitCount + " visits.");
                        
                            insertedAerodromia.add(selectedIndex); // Store the selected destination index
                        visitUpdates[selectIndex] = visitCount; // Store the number of visits for the destination
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

        double[][] epiloges = new double[numberOfDestinations][2];
        for (int i = 0; i < insertedAerodromia.size(); i++) {
            int airportIndex = insertedAerodromia.get(i); // Get the index of the selected airport
            String airportLatitude = aerodromia[airportIndex].getLatitude();  // Assuming these are String values
            String airportLongitude = aerodromia[airportIndex].getLongitude(); // Assuming these are String values
            
            // Convert the String values to double using Double.parseDouble
            try {
                epiloges[i][0] = Double.parseDouble(airportLatitude); // Convert latitude to double
                epiloges[i][1] = Double.parseDouble(airportLongitude); // Convert longitude to double
            } catch (NumberFormatException e) {
                System.err.println("Error parsing latitude or longitude for airport: " + aerodromia[airportIndex]);
            }
        }
        
        


        int[][] kilometersDistance = new int[numberOfDestinations][numberOfDestinations];
        // Γεμίζει τον πίνακα με αποστάσεις από κάθε περιοχή σε κάθε περιοχή
        for (int row = 0; row < numberOfDestinations - 1; row++) {
            for (int column = row; column < numberOfDestinations; column++) {
                double distance = HaversineDistance.haversine(
                epiloges[row][0], epiloges[row][1], 
                epiloges[column][0], epiloges[column][1]
                );
                kilometersDistance[row][column] = (int) Math.round(distance); // Μετατροπή σε ακέραιο αν χρειάζεται
                kilometersDistance[column][row] = kilometersDistance[row][column];
            }
        }

        
        int[][] finalDestinations = new int[numberOfAirplanes][3]; //pinakas me tis diadromes pu tha kanei to kathe aeroplano
        for (int plane = 0; plane < numberOfAirplanes; plane++) {
            int min;
            int minrow = -1;
            int mincolumn = -1;
            int i = 0;
            int lastLocation = 0;
            int remainingVisits = 3;
            while (remainingVisits > 0) {
                min = Integer.MAX_VALUE;
                for (int column = 0; column < numberOfDestinations; column++) {
                    if ((kilometersDistance[lastLocation][column] < min) && (kilometersDistance[lastLocation][column] != 0)) {
                        min = kilometersDistance[lastLocation][column];
                        minrow = lastLocation;
                        mincolumn = column;
                        //des to gia athina->parisi
                    }
                }
                remainingVisits = remainingVisits - 1;
                finalDestinations[plane][i] = mincolumn;
                lastLocation = mincolumn;
                i+=1;
                visitUpdates[lastLocation] = visitUpdates[lastLocation] - 1;
                if (visitUpdates[lastLocation] == 0) {
                    for (int row = 0; row == numberOfDestinations; row++) {
                        kilometersDistance[row][lastLocation] = 0;
                        kilometersDistance[lastLocation][row] = 0;
                    }
                }
            }
            System.out.println("Η τελική διαδρομή του αεροπλάνου"+plane+"είναι:");
            for (int k = 0; k == 2; k++) {
                System.out.println(finalDestinations[plane][k]);
            }
        }
    }
}