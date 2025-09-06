import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Loginsys extends JFrame {
    public JTextField idField;
    // private JTextField idField1;
    private JTextField usernameField;
    private JPasswordField passwordField;
    // private JTextField registerField;
    // private JPasswordField passwordField1;
    private int loginAttempts = 3; // Maximum login attempts

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/ls";
    static final String USER = "root";
    static final String PASS = "root";

    public Loginsys() {
        setTitle("Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(8, 7));
        add(panel, BorderLayout.CENTER);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JLabel spaceLabel = new JLabel(" ");
        // JLabel idLabel1 = new JLabel("ID:");
        // idField1 = new JTextField();
        // JLabel registerLabel = new JLabel("Register ");
        // registerField = new JTextField();
        // JLabel passwordLabel1 = new JLabel("Password:");
        // passwordField1 = new JPasswordField();
        JButton registerButton = new JButton("Register");
        JButton forgotButton=new JButton("Forgot Password?");
        panel.add(idLabel);
        panel.add(idField);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(spaceLabel);
        // panel.add(spaceLabel);

        // panel.add(registerButton);
        panel.add(forgotButton);

        class InvalidPasswordException extends Exception {
            public InvalidPasswordException(String message) {
                super(message);
            }
        }
        class ErrorMessageGUI extends JFrame {
            public ErrorMessageGUI(String errorMessage) {
                setTitle("Error Message");
                setSize(500, 400);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null); // Center the window
                setLayout(new BorderLayout());

                JPanel panel = new JPanel(new GridLayout(1, 1));
                add(panel, BorderLayout.CENTER);

                JLabel errorLabel = new JLabel(errorMessage);
                panel.add(errorLabel);

                setVisible(true);
            }
        }
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt(idField.getText());
                String username = usernameField.getText();

                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                int a = password.length();
                System.out.println(a);

                try {
                    Connection conn = null;
                    Statement stmt = null;
                    Class.forName(JDBC_DRIVER);
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();
                    String checkUserSQL = "SELECT * FROM logsys WHERE id = " + id + " AND user = '" + username
                            + "'";
                    ResultSet resultSet = stmt.executeQuery(checkUserSQL);
                    if (resultSet.next()) {
                        new ErrorMessageGUI("User already exists!");
                    } else {
                        if (password.length() < 8 || !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                            throw new InvalidPasswordException(
                                    " Password should be at least 8 characters long and contain a special character.");
                        }

                        String insertSQL = "INSERT INTO logsys VALUES (" + id + ",'" + username + "', '" + password
                                + "')";
                        stmt.executeUpdate(insertSQL);
                        System.out.println("Registered successfully.");
                        stmt.close();
                        conn.close();

                        // Open a new interface for successful registration
                        dispose(); // Close the current window
                        new RegistrationSuccessGUI(username, a);
                        resetFields();
                    }
                } catch (InvalidPasswordException ie) {
                    ie.printStackTrace();
                    new ErrorMessageGUI(ie.getMessage());
                } catch (SQLException se) {
                    se.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                try {
                    Connection conn = null;
                    Statement stmt = null;
                    Class.forName(JDBC_DRIVER);
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();

                    String selectSQL = "SELECT * FROM logsys WHERE user='" + username + "' AND password='"
                            + password + "'";
                    ResultSet resultSet = stmt.executeQuery(selectSQL);

                    if (resultSet.next()) {
                        // Open a new interface for successful login
                        dispose(); // Close the current window
                        new LoginSuccessGUI(username);
                        resetFields();
                    } else {
                        System.out.println("Invalid username or password.");
                        JOptionPane.showMessageDialog(Loginsys.this, "Invalid username or password \n Login failed. Attempts left: " + (--loginAttempts));
                        if (loginAttempts == 0) {
                            JOptionPane.showMessageDialog(Loginsys.this, "You have reached the maximum number of login attempts. Please contact support.");
                            System.exit(0); // Exit the application
                        }
                    }
                    
                    resultSet.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException se) {
                    se.printStackTrace();
                }
            }

        });
forgotButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();

        String newPassword = JOptionPane.showInputDialog("Enter your new password:");
        if (newPassword != null) {
            try {
                Connection conn = null;
                Statement stmt = null;
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();

                String updateSQL = "UPDATE logsys SET password='" + newPassword + "' WHERE user='" + username + "'";
                int rows = stmt.executeUpdate(updateSQL);

                if (rows > 0) {
                    JOptionPane.showMessageDialog(Loginsys.this, "Password updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(Loginsys.this, "Failed to update password.");
                }

                stmt.close();
                conn.close();
            } catch (SQLException | ClassNotFoundException se) {
                se.printStackTrace();
            }
        }
    }
});

// Existing code...


        setVisible(true);
    }

    private void resetFields() {
        usernameField.setText("");
        passwordField.setText("");

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Loginsys();
            }
        });
    }
}

class LoginSuccessGUI extends JFrame {
    public LoginSuccessGUI(String username) {
        setTitle("Login Successful");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 1));
        add(panel, BorderLayout.CENTER);

        JLabel successLabel = new JLabel("Login Successful!");
        JLabel usernameLabel = new JLabel("Logged-in Username: " + username);

        panel.add(successLabel);
        panel.add(usernameLabel);

        setVisible(true);
    }
}

class RegistrationSuccessGUI extends JFrame {
    public RegistrationSuccessGUI(String username, int x) {
        setTitle("Registration Successful");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 1));
        add(panel, BorderLayout.CENTER);

        JLabel successLabel = new JLabel("Registration Successful!");
        JLabel usernameLabel = new JLabel("Registered Username: " + username);
        JLabel passwordlen = new JLabel("Valid length= " + x);

        panel.add(successLabel);
        panel.add(usernameLabel);
        panel.add(passwordlen);
        setVisible(true);
    }
}
