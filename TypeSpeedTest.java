import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TypeSpeedTest {
    private JFrame frame;
    private JTextArea textArea;
    private JLabel promptLabel;
    private JLabel resultLabel;
    private JButton startButton;
    private long startTime;
    private String textToType = "I am a Third year CSE Student.";

    public TypeSpeedTest() {
        frame = new JFrame("Typing Speed Test");
        textArea = new JTextArea(5, 30);
        promptLabel = new JLabel("Type the following text:");
        resultLabel = new JLabel("");
        startButton = new JButton("Start Test");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTest();
            }
        });

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (textArea.getText().equals(textToType)) {
                    checkResults();
                }
            }
        });

        frame.setLayout(new FlowLayout());
        frame.add(promptLabel);
        frame.add(new JLabel(textToType));
        frame.add(textArea);
        frame.add(startButton);
        frame.add(resultLabel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private void startTest() {
        textArea.setText("");
        resultLabel.setText("");
        startTime = System.currentTimeMillis();
        textArea.requestFocus();
    }

    private void checkResults() {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime; 
        String userInput = textArea.getText();
        int wordsTyped = userInput.split("\\s+").length;
        int timeTakenInMinutes = (int) (duration / 60000);
        int wpm = (timeTakenInMinutes > 0) ? (wordsTyped / timeTakenInMinutes) : wordsTyped;

        double accuracy = calculateAccuracy(userInput, textToType);
        resultLabel.setText("Speed: " + wpm + " WPM, Accuracy: " + accuracy + "%");
    }

    private double calculateAccuracy(String userInput, String originalText) {
        String[] userWords = userInput.split("\\s+");
        String[] originalWords = originalText.split("\\s+");
        int correctWords = 0;

        for (int i = 0; i < Math.min(userWords.length, originalWords.length); i++) {
            if (userWords[i].equals(originalWords[i])) {
                correctWords++;
            }
        }

        return (double) correctWords / originalWords.length * 100;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TypeSpeedTest());
    }
}