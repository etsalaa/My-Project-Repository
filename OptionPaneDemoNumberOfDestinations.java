import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OptionPaneDemoNumberOfDestinations extends DemoModule {
    private int userNumber;
    private InputListener inputListenerDestinations; //callback interface

    public OptionPaneDemoNumberOfDestinations(Object swingset) {
        super(swingset, "Αριθμός Προορισμών", "toolbar/JOptionPane.gif");

        JPanel demo = getDemoPanel();
        demo.setLayout(new BoxLayout(demo, BoxLayout.X_AXIS));

        JPanel bp = new JPanel() {
            public Dimension getMaximumSize() {
                return new Dimension(getPreferredSize().width, super.getMaximumSize().height);
            }
        };
        bp.setLayout(new BoxLayout(bp, BoxLayout.Y_AXIS));

        bp.add(Box.createRigidArea(new Dimension(300, 100)));
        bp.add(Box.createRigidArea(new Dimension(500, 100)));
        bp.add(createInputDialogButtonNumberOfDestinations());
        bp.add(Box.createRigidArea(new Dimension(500, 100)));

        demo.add(Box.createHorizontalGlue());
        demo.add(bp);
        demo.add(Box.createHorizontalGlue());
    }
    public JButton createInputDialogButtonNumberOfDestinations() { // Μέθοδος για αριθμό προορισμών
        Action a = new AbstractAction(getString("Αριθμός Προορισμών")) {
            public void actionPerformed(ActionEvent e) {
                int result = getIntInputNumberOfDestinations();// Παίρνουμε την ακέραια είσοδο
                if (result > 0) {
                    // Αποθηκεύουμε την είσοδο του χρήστη
                    userNumber = result;
                    JOptionPane.showMessageDialog(getDemoPanel(),
                            userNumber + ": " + getString("Τέλεια, θα πάμε σε όλα αυτα τα μέρη!"));
                    if (inputListenerDestinations != null) {
                        inputListenerDestinations.onInputReceived(userNumber);//ενημέρωση του listener
                    }
                }
            }
        };
        return createButton(a);
    }

    public int getIntInputNumberOfDestinations() {
        while (true) {
            String input = JOptionPane.showInputDialog(getDemoPanel(), getString("Δώστε αριθμό προορισμών"));
            if (input == null) { // Αν ο χρήστης πατήσει Cancel
                JOptionPane.showMessageDialog(getDemoPanel(), getString("Η είσοδος ακυρώθηκε."));
                return -1; // Επιστρέφουμε -1 ως ακύρωση
            }
            try {
                int number = Integer.parseInt(input); // Μετατροπή String -> int
                if (number > 0) {
                    return number; // Επιστρέφουμε τον έγκυρο ακέραιο
                } else {
                    JOptionPane.showMessageDialog(getDemoPanel(), getString("Ο αριθμός πρέπει να είναι θετικός!"));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(getDemoPanel(), getString("Παρακαλώ δώσε έναν έγκυρο αριθμό!"));
            }
        }
    }
    
    public void setInputListener(InputListener listener) {
        this.inputListenerDestinations = listener;
    }

    // Παράδειγμα μεθόδου για την αποστολή της εισόδου στον listener
    public void triggerInputReceived(int input) {
        if (inputListenerDestinations != null) {
            inputListenerDestinations.onInputReceived(input);
        }
    }
    
    public JButton createButton(Action a) {
        JButton b = new JButton(a);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        return b;
    }
}
