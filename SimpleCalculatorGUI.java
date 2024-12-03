import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculatorGUI {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Simple Calculator");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // First number label and text field
        JLabel label1 = new JLabel("First Number:");
        label1.setBounds(20, 20, 100, 30);
        frame.add(label1);

        JTextField textField1 = new JTextField();
        textField1.setBounds(150, 20, 200, 30);
        frame.add(textField1);

        // Second number label and text field
        JLabel label2 = new JLabel("Second Number:");
        label2.setBounds(20, 60, 100, 30);
        frame.add(label2);

        JTextField textField2 = new JTextField();
        textField2.setBounds(150, 60, 200, 30);
        frame.add(textField2);

        // Operator label and text field
        JLabel operatorLabel = new JLabel("Operator (+, -, *, /):");
        operatorLabel.setBounds(20, 100, 150, 30);
        frame.add(operatorLabel);

        JTextField operatorField = new JTextField();
        operatorField.setBounds(150, 100, 200, 30);
        frame.add(operatorField);

        // Calculate button
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(150, 140, 100, 30);
        frame.add(calculateButton);

        // Result label
        JLabel resultLabel = new JLabel("Result: ");
        resultLabel.setBounds(20, 180, 350, 30);
        frame.add(resultLabel);

        // Action listener for the calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double num1 = Double.parseDouble(textField1.getText());
                    double num2 = Double.parseDouble(textField2.getText());
                    String operator = operatorField.getText();
                    double result;

                    switch (operator) {
                        case "+":
                            result = num1 + num2;
                            resultLabel.setText("Result: " + result);
                            break;
                        case "-":
                            result = num1 - num2;
                            resultLabel.setText("Result: " + result);
                            break;
                        case "*":
                            result = num1 * num2;
                            resultLabel.setText("Result: " + result);
                            break;
                        case "/":
                            if (num2 != 0) {
                                result = num1 / num2;
                                resultLabel.setText("Result: " + result);
                            } else {
                                resultLabel.setText("Error: Division by zero.");
                            }
                            break;
                        default:
                            resultLabel.setText("Error: Invalid operator.");
                            break;
                    }
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Error: Please enter valid numbers.");
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}