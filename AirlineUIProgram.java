import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class AirlineUIProgram {
    public static volatile int numberOfAirplanes = -1;  // Number of airplanes
    public static volatile int numberOfDestinations = -1;  // Number of destinations
    private static final ArrayList<String> insertedAirports = new ArrayList<>(); // List of selected destinations

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AirlineUIProgram::showLoginWindow);
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
                frame.dispose();
                showNumberOfAirplanesWindow();
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
                showSummaryWindow();
            } else {
                JOptionPane.showMessageDialog(frame, "Please select exactly " + numberOfDestinations + " destinations.");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void showSummaryWindow() {
        JFrame frame = new JFrame("Summary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Summary of Your Choices", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        StringBuilder summaryText = new StringBuilder("Number of Airplanes: " + numberOfAirplanes + "\n");
        summaryText.append("Number of Destinations: ").append(numberOfDestinations).append("\n");
        summaryText.append("Selected Destinations:\n");
        for (String destination : insertedAirports) {
            summaryText.append(destination).append("\n");
        }
        summaryArea.setText(summaryText.toString());
        panel.add(new JScrollPane(summaryArea), BorderLayout.CENTER);

        JButton nextButton = new JButton("Next");
        panel.add(nextButton, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> {
            frame.dispose();
            new FlightFrequency(insertedAirports).showFlightFrequencyWindow();
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    // Implement FlightFrequency or other next-step logic here.
}
