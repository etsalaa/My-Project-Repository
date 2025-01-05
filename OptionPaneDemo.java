import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OptionPaneDemo extends DemoModule {
    private String userInput;
    private InputListener inputListener; //callback interface

    public OptionPaneDemo(Object swingset) {
        super(swingset, "Αριθμός Αεροπλάνων", "toolbar/JOptionPane.gif");

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
        bp.add(createInputDialogButton());
        bp.add(Box.createRigidArea(new Dimension(500, 100)));

        demo.add(Box.createHorizontalGlue());
        demo.add(bp);
        demo.add(Box.createHorizontalGlue());
    }

    public JButton createInputDialogButton() { // Μέθοδος για αριθμό αεροπλάνων
        Action a = new AbstractAction(getString("Αριθμός αεροπλάνων")) {
            public void actionPerformed(ActionEvent e) {
                String result = JOptionPane.showInputDialog(getDemoPanel(), getString("Δώσε αριθμό διαθέσιμων αεροπλάνων."));
                if ((result != null) && (result.length() > 0)) {
                    // Αποθηκεύουμε την είσοδο του χρήστη
                    userInput = result;
                    JOptionPane.showMessageDialog(getDemoPanel(),
                            result + ": " + getString("Τέλεια, θα σου προτείνουμε τις καλύτερες διαδρομές γι' αυτά τα αεροπλάνα!"));
                    if (inputListener != null) {
                        inputListener.onInputReceived(userInput);//ενημέρωση του listener
                    }
                }
            }
        };
        return createButton(a);
    }

    public void setInputListener(InputListener listener) {
        this.inputListener = listener;
    }
    
    interface InputListener {
        void onInputReceived(String input);
    }
    
    public JButton createButton(Action a) {
        JButton b = new JButton(a);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        return b;
    }
}

class DemoModule extends JPanel {
    public DemoModule(Object obj, String title, String iconPath) {
        setBorder(BorderFactory.createTitledBorder(title));
    }

    public JPanel getDemoPanel() {
        return this;
    }

    protected String getString(String text) {
        return text; // Placeholder για μετάφραση
    }

    public void mainImpl() {
        JFrame frame = new JFrame("Demo Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }
}
