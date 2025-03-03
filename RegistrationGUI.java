import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationGUI extends JFrame {
    private JTextField usernameField, nameField, ageField, guardianNameField, guardianContactField;
    private JPasswordField passwordField;
    private JCheckBox minorCheckBox;

    public RegistrationGUI() {
        setTitle("Student Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Age:"));
        ageField = new JTextField();
        add(ageField);

        minorCheckBox = new JCheckBox("Minor");
        add(minorCheckBox);
        add(new JLabel(""));

        add(new JLabel("Guardian Name:"));
        guardianNameField = new JTextField();
        add(guardianNameField);
        guardianNameField.setVisible(false);

        add(new JLabel("Guardian Contact:"));
        guardianContactField = new JTextField();
        add(guardianContactField);
        guardianContactField.setVisible(false);

        JButton registerButton = new JButton("Register");
        add(registerButton);

        minorCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isMinor = minorCheckBox.isSelected();
                guardianNameField.setVisible(isMinor);
                guardianContactField.setVisible(isMinor);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerStudent();
            }
        });

        setVisible(true);
    }

    private void registerStudent() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        boolean isMinor = minorCheckBox.isSelected();
        String guardianName = guardianNameField.getText();
        String guardianContact = guardianContactField.getText();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO students (username, password, name, age, is_minor, guardian_name, guardian_contact) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, name);
            statement.setInt(4, age);
            statement.setBoolean(5, isMinor);
            statement.setString(6, guardianName);
            statement.setString(7, guardianContact);

            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration successful!");
            new LoginGUI(); // Open login window
            dispose(); // Close registration window
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Registration failed: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new RegistrationGUI();
    }
}