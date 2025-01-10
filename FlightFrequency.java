import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlightFrequency {
    private final List<String> destinations;
    private final HashMap<String, Integer> flightFrequency;

    public FlightFrequency(List<String> destinations) {
        this.destinations = destinations;
        this.flightFrequency = new HashMap<>();
    }

    public void showFlightFrequencyWindow() {
        JFrame frame = new JFrame("Flight Frequency");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(destinations.size() + 1, 2));
        panel.add(new JLabel("Destination"));
        panel.add(new JLabel("Number of Flights"));

        ArrayList<JTextField> inputFields = new ArrayList<>();
        for (String destination : destinations) {
            panel.add(new JLabel(destination));
            JTextField inputField = new JTextField();
            inputFields.add(inputField);
            panel.add(inputField);
        }

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                boolean valid = true;
                flightFrequency.clear();

                for (int i = 0; i < destinations.size(); i++) {
                    String destination = destinations.get(i);
                    String input = inputFields.get(i).getText();
                    int flights = Integer.parseInt(input);

                    if (flights <= 0) {
                        valid = false;
                        break;
                    }

                    flightFrequency.put(destination, flights);
                }

                if (valid) {
                    JOptionPane.showMessageDialog(frame, "Flight frequencies saved!");
                    frame.dispose();
                    showSummaryWindow(); // Display the summary window
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter positive numbers for all destinations.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for all destinations.");
            }
        });

        frame.add(new JScrollPane(panel), BorderLayout.CENTER);
        frame.add(submitButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void showSummaryWindow() {
        JFrame frame = new JFrame("Flight Frequency Summary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Summary of Flight Frequencies", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        StringBuilder summaryText = new StringBuilder("Flight Frequencies:\n");
        for (String destination : flightFrequency.keySet()) {
            summaryText.append(destination).append(": ").append(flightFrequency.get(destination)).append("\n");
        }
        summaryArea.setText(summaryText.toString());
        panel.add(new JScrollPane(summaryArea), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> frame.dispose());

        panel.add(closeButton, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setVisible(true);
    }
}
