import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Test4 {
    public static volatile int numberOfAirplanes = -1;
    public static volatile int numberOfDestinations = -1;
    private static final ArrayList<String> insertedAirports = new ArrayList<>();
    private static User loggedInUser = null;
    private static final List<User> users = new ArrayList<>();
    private static final List<Airport> airports = new ArrayList<>();

    static {
        users.add(new User("admin", "admin123", "admin"));
        users.add(new User("user1", "user123", "user"));
        airports.add(new Airport("JFK", "USA", "New York", "40.6413", "-73.7781"));
        airports.add(new Airport("LAX", "USA", "Los Angeles", "33.9416", "-118.4085"));
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Test4::showLoginWindow);
    }

    // Login window
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
            User user = authenticateUser(username, password);
            if (user != null) {
                loggedInUser = user;
                JOptionPane.showMessageDialog(frame, "Login successful!");
                frame.dispose();
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password!");
            }
        });

        frame.setVisible(true);
    }

    // Main menu (After Login)
    private static void showMainMenu() {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        if (loggedInUser.getRole().equals("admin")) {
            JButton manageUsersButton = new JButton("Manage Users");
            manageUsersButton.addActionListener(e -> showManageUsersWindow());
            panel.add(manageUsersButton);

            JButton manageAirportsButton = new JButton("Manage Airports");
            manageAirportsButton.addActionListener(e -> showManageAirportsWindow());
            panel.add(manageAirportsButton);

        } else {
            JOptionPane.showMessageDialog(frame, "Login successful!");
                frame.dispose();
                showNumberOfAirplanesWindow();
        }

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            loggedInUser = null;
            JOptionPane.showMessageDialog(frame, "Logged out successfully!");
            frame.dispose();
            showLoginWindow();
        });

        panel.add(logoutButton);
        frame.add(panel);
        frame.setVisible(true);
    }

    // Manage users (Admin only)
    private static void showManageUsersWindow() {
        JFrame frame = new JFrame("Manage Users");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Role:"));
        String[] roles = {"user", "admin"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        panel.add(roleComboBox);

        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();
            User user = new User(username, password, role);
            users.add(user);
            JOptionPane.showMessageDialog(frame, "User added successfully!");
            frame.dispose();
        });

        panel.add(addUserButton);
        frame.add(panel);
        frame.setVisible(true);
    }

    // Manage airports (Admin only)
    private static void showManageAirportsWindow() {
        JFrame frame = new JFrame("Manage Airports");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Airport Name:"));
        JTextField nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("City:"));
        JTextField cityField = new JTextField();
        panel.add(cityField);

        panel.add(new JLabel("Country:"));
        JTextField countryField = new JTextField();
        panel.add(countryField);

        panel.add(new JLabel("Latitude:"));
        JTextField latitudeField = new JTextField();
        panel.add(latitudeField);

        panel.add(new JLabel("Longitude:"));
        JTextField longitudeField = new JTextField();
        panel.add(longitudeField);

        JButton addAirportButton = new JButton("Add Airport");
        addAirportButton.addActionListener(e -> {
            String name = nameField.getText();
            String city = cityField.getText();
            String country = countryField.getText();
            String latitude = latitudeField.getText();
            String longitude = longitudeField.getText();
            Airport airport = new Airport(name, country, city, latitude, longitude);
            airports.add(airport);
            JOptionPane.showMessageDialog(frame, "Airport added successfully!");
            frame.dispose();
        });

        panel.add(addAirportButton);
        frame.add(panel);
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
                    frame.dispose();
                    showDestinationSelectionWindow();
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
            List<String> selectedDestinations = destinationList.getSelectedValuesList();
            if (selectedDestinations.size() == numberOfDestinations) {
                insertedAirports.clear();
                insertedAirports.addAll(selectedDestinations);
                JOptionPane.showMessageDialog(frame, "Selected destinations: " + insertedAirports);
                frame.dispose();
                showDestinationVisitsWindow();
            } else {
                JOptionPane.showMessageDialog(frame, "Please select exactly " + numberOfDestinations + " destinations.");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void showDestinationVisitsWindow() {
        JFrame frame = new JFrame("Number of Visits for Each Destination");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(numberOfDestinations + 1, 2));
        panel.add(new JLabel("Destination"));

        // Προσθήκη πεδίων για τον αριθμό επισκέψεων για κάθε προορισμό
        JTextField[] visitFields = new JTextField[numberOfDestinations];
        for (int i = 0; i < numberOfDestinations; i++) {
            panel.add(new JLabel(insertedAirports.get(i)));
            visitFields[i] = new JTextField("1");
            panel.add(visitFields[i]);
        }

        JButton submitButton = new JButton("Submit Visits");
        panel.add(submitButton);

        frame.add(panel);
        frame.setVisible(true);

        submitButton.addActionListener(e -> {
            try {
                int[] visits = new int[numberOfDestinations];
                int totalVisits = 0;

                for (int i = 0; i < numberOfDestinations; i++) {
                    visits[i] = Integer.parseInt(visitFields[i].getText());
                    if (visits[i] <= 0) {
                        JOptionPane.showMessageDialog(frame, "Please enter a positive number for visits!");
                        return;
                    }
                    totalVisits += visits[i];
                }

                // Έλεγχος αν οι συνολικές επισκέψεις είναι έγκυρες (να μην ξεπερνούν το όριο)
                if (totalVisits <= numberOfAirplanes * 3) {
                    JOptionPane.showMessageDialog(frame, "Total visits are valid: " + totalVisits);
                    frame.dispose();
                    calculateDistancesAndAssignments(visits); // Μεταβίβαση του πίνακα επισκέψεων
                } else {
                    JOptionPane.showMessageDialog(frame, "Total visits exceed the available visit slots for all airplanes!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers!");
            }
        });
    }

    private static void calculateDistancesAndAssignments(int[] visits) {
        // Προσομοίωση δεδομένων για τις συντεταγμένες των προορισμών (latitude και longitude)
        double[][] epiloges = new double[numberOfDestinations][2];
        String[] mockLatitudes = {"37.9838", "48.8566", "51.5074", "45.4642", "50.8503", "52.5200", "59.3293", "59.9139", "40.4168", "52.3676"};
        String[] mockLongitudes = {"23.7275", "2.3522", "-0.1278", "9.1900", "4.3517", "13.4050", "18.0686", "10.7522", "-3.7038", "4.9041"};

        for (int i = 0; i < numberOfDestinations; i++) {
            epiloges[i][0] = Double.parseDouble(mockLatitudes[i]);
            epiloges[i][1] = Double.parseDouble(mockLongitudes[i]);
        }

        int[][] kilometersDistance = new int[numberOfDestinations][numberOfDestinations];

        for (int row = 0; row < numberOfDestinations; row++) {
            for (int column = row; column < numberOfDestinations; column++) {
                if (row != column) {
                    double distance = HaversineDistance.haversine(epiloges[row][0], epiloges[row][1], epiloges[column][0], epiloges[column][1]);
                    kilometersDistance[row][column] = (int) Math.round(distance);
                    kilometersDistance[column][row] = kilometersDistance[row][column];
                }
            }
        }

        assignDestinationsToAirplanes(kilometersDistance, visits);
    }

    private static void assignDestinationsToAirplanes(int[][] kilometersDistance, int[] visits) {
        int[][] finalDestinations = new int[numberOfAirplanes][3];
        int[] visitUpdates = new int[numberOfDestinations];

        for (int i = 0; i < numberOfDestinations; i++) {
            visitUpdates[i] = visits[i]; // Ενημέρωση με τις επισκέψεις για κάθε προορισμό
        }

        for (int plane = 0; plane < numberOfAirplanes; plane++) {
            int lastLocation = 0;
            double totalDistance = 0;

            for (int visit = 0; visit < 3; visit++) {
                int minDistance = Integer.MAX_VALUE;
                int nextDestination = -1;

                for (int dest = 0; dest < numberOfDestinations; dest++) {
                    if (visitUpdates[dest] > 0 && dest != lastLocation && kilometersDistance[lastLocation][dest] < minDistance) {
                        minDistance = kilometersDistance[lastLocation][dest];
                        nextDestination = dest;
                    }
                }

                if (nextDestination != -1) {
                    finalDestinations[plane][visit] = nextDestination;
                    visitUpdates[nextDestination]--;
                    totalDistance += minDistance;
                    lastLocation = nextDestination;
                }
            }

            // Εμφάνιση του αποτελέσματος
            StringBuilder result = new StringBuilder("Airplane ").append(plane + 1).append(" visits: ");
            for (int visit = 0; visit < 3; visit++) {
                int destinationIndex = finalDestinations[plane][visit];
                result.append(insertedAirports.get(destinationIndex));
                if (visit < 2) {
                    result.append(", ");
                }
            }
            result.append("\nTotal Distance: ").append(totalDistance).append(" km");
            JOptionPane.showMessageDialog(null, result.toString(), "Assignments", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    static class User {
        private final String username;
        private final String password;
        private final String role;

        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }
    }

    private static User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    static class Airport {
        private final String name;
        private final String country;
        private final String city;
        private final String latitude;
        private final String longitude;

        public Airport(String name, String country, String city, String latitude, String longitude) {
            this.name = name;
            this.country = country;
            this.city = city;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public String getCity() {
            return city;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }
    }



}
