import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankGUI extends JFrame {
    private double balance;
    private double prevTransaction;
    private String customerName;
    private String customerId;
    private JTextArea textArea;

    public BankGUI(String customerName, String customerId) {
        this.customerName = customerName;
        this.customerId = customerId;

        setTitle("Bank Management System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("Welcome " + customerName);
        JLabel idLabel = new JLabel("Your ID: " + customerId);
        textArea = new JTextArea(10, 30);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton prevTransactionButton = new JButton("Previous Transaction");
        JButton exitButton = new JButton("Exit");

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBalance();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositAmount();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdrawAmount();
            }
        });

        prevTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPreviousTransaction();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(nameLabel);
        panel.add(idLabel);
        panel.add(checkBalanceButton);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(prevTransactionButton);
        panel.add(exitButton);

        add(panel, BorderLayout.NORTH);
        add(textArea, BorderLayout.CENTER);
    }

    private void displayBalance() {
        textArea.append("Balance: " + balance + "\n");
    }

    private void depositAmount() {
        String input = JOptionPane.showInputDialog("Enter the amount to deposit:");
        if (input != null) {
            double amount = Double.parseDouble(input);
            if (amount > 0) {
                balance += amount;
                prevTransaction = amount;
                textArea.append("Deposited: " + amount + "\n");
            }
        }
    }

    private void withdrawAmount() {
        String input = JOptionPane.showInputDialog("Enter the amount to withdraw:");
        if (input != null) {
            double amount = Double.parseDouble(input);
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                prevTransaction = -amount;
                textArea.append("Withdrawn: " + amount + "\n");
            } else {
                textArea.append("Bank balance insufficient\n");
            }
        }
    }

    private void displayPreviousTransaction() {
        if (prevTransaction > 0) {
            textArea.append("Deposited: " + prevTransaction + "\n");
        } else if (prevTransaction < 0) {
            textArea.append("Withdrawn: " + Math.abs(prevTransaction) + "\n");
        } else {
            textArea.append("No transaction occurred\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String customerName = JOptionPane.showInputDialog("Enter your Name:");
            String customerId = JOptionPane.showInputDialog("Enter your Customer ID:");
            BankGUI bankGUI = new BankGUI(customerName, customerId);
            bankGUI.setVisible(true);
        });
    }
}