import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {
        setTitle("Student Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginStudent();
            }
        });

        setVisible(true);
    }

    private void loginStudent() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM students WHERE username = ? AND password = ?")) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                // Open main application window or another GUI
                dispose(); // Close login window
            } else {
                JOptionPane.showMessageDialog(this, "Login failed: Invalid username or password.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Login failed: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}