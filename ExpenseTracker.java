import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Expense {
    String category;
    double amount;
    String date;
    String notes;

    public Expense(String category, double amount, String date, String notes) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.notes = notes;
    }
}

public class ExpenseTracker extends JFrame {
    private DefaultTableModel tableModel;
    private JTable expenseTable;
    private JTextField categoryField, amountField, dateField, notesField;
    private List<Expense> expenses;

    public ExpenseTracker() {
        expenses = new ArrayList<>();
        initializeUI();
        loadExpenses();
    }

    private void initializeUI() {
        setTitle("Expense Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        inputPanel.add(categoryField);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Notes:"));
        notesField = new JTextField();
        inputPanel.add(notesField);

        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(new AddExpenseListener());
        inputPanel.add(addButton);

        JButton saveButton = new JButton("Save Expenses");
        saveButton.addActionListener(new SaveExpensesListener());
        inputPanel.add(saveButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Category", "Amount", "Date", "Notes"}, 0);
        expenseTable = new JTable(tableModel);
        add(new JScrollPane(expenseTable), BorderLayout.CENTER);

        // Total Expenses
        JLabel totalLabel = new JLabel("Total Expenses: $0.00");
        add(totalLabel, BorderLayout.SOUTH);

        // Update total on table data change
        tableModel.addTableModelListener(e -> updateTotal(totalLabel));
    }

    private void updateTotal(JLabel totalLabel) {
        double total = 0.0;
        for (Expense expense : expenses) {
            total += expense.amount;
        }
        totalLabel.setText("Total Expenses: $" + String.format("%.2f", total));
    }

    private void loadExpenses() {
        try (BufferedReader br = new BufferedReader(new FileReader("expenses.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    Expense expense = new Expense(data[0], Double.parseDouble(data[1]), data[2], data[3]);
                    expenses.add(expense);
                    tableModel.addRow(new Object[]{expense.category, expense.amount, expense.date, expense.notes});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class AddExpenseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String category = categoryField.getText();
            double amount;
            String date = dateField.getText();
            String notes = notesField.getText();

            try {
                amount = Double.parseDouble(amountField.getText());
                if (amount < 0) throw new NumberFormatException();
                
                Expense expense = new Expense(category, amount, date, notes);
                expenses.add(expense);
                tableModel.addRow(new Object[]{category, amount, date, notes});

                // Clear fields after adding
                categoryField.setText("");
                amountField.setText("");
                dateField.setText("");
                notesField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ExpenseTracker.this, "Please enter valid amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class SaveExpensesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("expenses.csv"))) {
                for (Expense expense : expenses) {
                    bw.write(expense.category + "," + expense.amount + "," + expense.date + "," + expense.notes);
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(ExpenseTracker.this, "Expenses saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ExpenseTracker.this, "Error saving expenses.", "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExpenseTracker tracker = new ExpenseTracker();
            tracker.setVisible(true);
        });
    }
}