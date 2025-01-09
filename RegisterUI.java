import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUI {
    private Login loginSystem;

    public RegisterUI(Login loginSystem) {
        this.loginSystem = loginSystem;
    
        JFrame frame = new JFrame("Register New User");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350,200);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel usernameLabel = new JLabel("New Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("New Password:");
        JTextField passwordField = new JTextField(20);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton registerButton = new JButton("Register");
        JButton cancelButton = new JButton ("Cancel");

        panel.add(registerButton);
        panel.add(cancelButton);

        frame.add(panel, BorderLayout.CENTER);

        JLabel messageLabel = new JLabel("");
        frame.add(messageLabel, BorderLayout.SOUTH);

        registerButton.addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e) {
                String newUsername = usernameField.getText();
                String newPassword = passwordField.getText();
                if (loginSystem.getUserDatabase().containsKey(newUsername)) {
                    JOptionPane.showMessageDialog(frame, "This username already exists. Try another.", "Error", JOptionPane.ERROR_MESSAGE);
                    messageLabel.setText("Register failed!");
                    messageLabel.setForeground(Color.RED);
                } else {
                    loginSystem.getUserDatabase().put(newUsername, newPassword);
                    JOptionPane.showMessageDialog(frame, "Successful register of ne user!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    messageLabel.setText("Successful Register!");
                    messageLabel.setForeground(Color.GREEN);
                    usernameField.setText("");
                    passwordField.setText("");
                    frame.dispose();
                }
            }
        });
    
        cancelButton.addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
                frame.dispose();
                }
        });
        frame.setVisible(true);
    }

}

