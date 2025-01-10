import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTel {
    private static volatile int numberOfAirplanes = -1;
    private static volatile int numberOfDestinations = -1;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Εμφάνιση φόρμας Login
            boolean isLoggedIn = showLoginForm();
            if (!isLoggedIn) {
                System.out.println("Login failed. Exiting application.");
                System.exit(0);
            }

            // 2. Εισαγωγή αριθμού αεροπλάνων
            numberOfAirplanes = showNumberInputDialog("Enter the number of airplanes:");
            System.out.println("Number of airplanes: " + numberOfAirplanes);

            // 3. Εισαγωγή αριθμού προορισμών
            numberOfDestinations = showNumberInputDialog("Enter the number of destinations:");
            System.out.println("Number of destinations: " + numberOfDestinations);

            // 4. Επιλογή προορισμών και αριθμός επισκέψεων
            List<Integer> insertedAerodromia = new ArrayList<>();
            int[] visitUpdates = new int[10];
            Arrays.fill(visitUpdates, 0);

            String[] destinations = {
                "1. Αθήνα", "2. Παρίσι", "3. Λονδίνο", "4. Μιλάνο", "5. Βρυξέλλες",
                "6. Βερολίνο", "7. Στοκχόλμη", "8. Όσλο", "9. Μαδρίτη", "10. Άμστερνταμ"
            };

            for (int i = 0; i < numberOfDestinations; i++) {
                int selectedIndex = showDestinationSelectionDialog(destinations);
                int visitCount = showNumberInputDialog("How many times do you want to visit " + destinations[selectedIndex] + "?");

                insertedAerodromia.add(selectedIndex);
                visitUpdates[selectedIndex] = visitCount;
            }

            // 5. Υπολογισμός αποστάσεων και δρομολογίων
            calculateAndDisplayRoutes(insertedAerodromia, visitUpdates);
        });
    }

    private static boolean showLoginForm() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            return username.equals("admin") && password.equals("admin");
        }

        return false;
    }

    private static int showNumberInputDialog(String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, message);
            try {
                int number = Integer.parseInt(input);
                if (number > 0) {
                    return number;
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }
        }
    }

    private static int showDestinationSelectionDialog(String[] destinations) {
        JList<String> destinationList = new JList<>(destinations);
        destinationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(destinationList);
        int result = JOptionPane.showConfirmDialog(null, scrollPane, "Select a Destination", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int selectedIndex = destinationList.getSelectedIndex();
            if (selectedIndex != -1) {
                return selectedIndex;
            } else {
                JOptionPane.showMessageDialog(null, "Please select a destination.");
            }
        }

        return showDestinationSelectionDialog(destinations);
    }

    private static void calculateAndDisplayRoutes(List<Integer> insertedAerodromia, int[] visitUpdates) {
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

        double[][] coordinates = new double[insertedAerodromia.size()][2];
        for (int i = 0; i < insertedAerodromia.size(); i++) {
            int index = insertedAerodromia.get(i);
            coordinates[i][0] = Double.parseDouble(airportsArray[index].getLatitude());
            coordinates[i][1] = Double.parseDouble(airportsArray[index].getLongitude());
        }

        int[][] distanceMatrix = new int[numberOfDestinations][numberOfDestinations];
        for (int row = 0; row < coordinates.length; row++) {
            for (int col = 0; col < coordinates.length; col++) {
                if (row != col) {
                    double distance = HaversineDistance.haversine(
                        coordinates[row][0], coordinates[row][1],
                        coordinates[col][0], coordinates[col][1]
                    );
                    distanceMatrix[row][col] = (int) Math.round(distance);
                }
            }
        }

        // Display the distance matrix or other calculations as needed
        System.out.println("Distance matrix:");
        for (int[] row : distanceMatrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}

class Airport {
    private final String name;
    private final String latitude;
    private final String longitude;

    public Airport(String name, String latitude, String longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
