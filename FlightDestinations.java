import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FlightDestinations {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flight Destinations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Select your Destinations", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] destinations = {
            "London", "Paris", "Barcelona", "Copenhagen", "Rome",
            "Athens", "Moscow", "Bucharest", "Stockholm", "Berlin",
            "Geneva", "Vienna"
        };
        JList<String> destinationList = new JList<>(destinations);
        destinationList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Allow multiple selections
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
                // Retrieve the selected values as a list
                List<String> selectedDestinations = destinationList.getSelectedValuesList();

                // Check if the list is empty or not
                if (!selectedDestinations.isEmpty()) {
                    // Join the list elements into a single string with commas
                    String joinedDestinations = String.join(", ", selectedDestinations);
                    JOptionPane.showMessageDialog(frame, "You selected: " + joinedDestinations);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select at least one destination.");
                }
            }
        });

        frame.add(panel);

        frame.setVisible(true);
    }
}