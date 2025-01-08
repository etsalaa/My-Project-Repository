import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LoginUI {
    private Login loginSystem;
     public LoginUI(Login loginSystem) {
        this.loginSystem = loginSystem;

        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JTextField usernameField = new JTextField(20);
        JLabel usernameLabel = new JLabel("Username:");
        JPasswordField passwordField = new JPasswordField(20);
        JLabel passwordLabel = new JLabel("Password:");

        panel.add(usernameField);
        panel.add(usernameLabel);
        panel.add(passwordField);
        panel.add(passwordLabel);

        JLabel messageLabel = new JLabel("");
        frame.add(messageLabel);

        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");
    
        panel.add(loginButton);
        panel.add(cancelButton);

        frame.add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                if (loginSystem.authenticate(username, password)) {
                    JOptionPane.showMessageDialog( frame, "Login Successful!");
                    messageLabel.setForeground(Color.GREEN);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Username or Password.", "Error", JOptionPane.ERROR_MESSAGE);
                    messageLabel.setForeground(Color.RED);
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
