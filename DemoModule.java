import javax.swing.*;

 public class DemoModule extends JPanel {
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
