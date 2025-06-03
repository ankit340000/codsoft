import javax.swing.*;
import java.awt.*;

public class GradeCalculatorGUI extends JFrame {
    private JTextField studentField, subjectField;
    private JPanel inputPanel;
    private JTextArea outputArea;

    public GradeCalculatorGUI() {
        setTitle("Student Grade Calculator");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));

        inputPanel.add(new JLabel("Number of Students:"));
        studentField = new JTextField();
        inputPanel.add(studentField);

        inputPanel.add(new JLabel("Number of Subjects:"));
        subjectField = new JTextField();
        inputPanel.add(subjectField);

        JButton submitBtn = new JButton("Enter Details");
        submitBtn.addActionListener(e -> promptForDetails());
        inputPanel.add(submitBtn);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void promptForDetails() {
        try {
            int numStudents = Integer.parseInt(studentField.getText());
            int numSubjects = Integer.parseInt(subjectField.getText());

            if (numStudents <= 0 || numSubjects <= 0) {
                JOptionPane.showMessageDialog(this, "Enter valid positive integers.");
                return;
            }

            String[] subjectNames = new String[numSubjects];
            for (int i = 0; i < numSubjects; i++) {
                subjectNames[i] = JOptionPane.showInputDialog(this, "Enter name of subject " + (i + 1) + ":");
                if (subjectNames[i] == null || subjectNames[i].trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Subject name cannot be empty.");
                    return;
                }
            }

            outputArea.setText("");

            for (int student = 1; student <= numStudents; student++) {
                int[] marks = new int[numSubjects];
                int total = 0;
                boolean hasFailed = false;

                for (int i = 0; i < numSubjects; i++) {
                    while (true) {
                        try {
                            String input = JOptionPane.showInputDialog(this,
                                    "Enter marks for Student " + student + " in " + subjectNames[i] + " (0-100):");
                            if (input == null) return;

                            int mark = Integer.parseInt(input);
                            if (mark < 0 || mark > 100) {
                                JOptionPane.showMessageDialog(this, "Marks should be between 0 and 100.");
                                continue;
                            }
                            marks[i] = mark;
                            if (mark < 40) hasFailed = true;
                            total += mark;
                            break;
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Enter a valid number.");
                        }
                    }
                }

                double avg = (double) total / numSubjects;
                String grade;
                if (avg >= 90) grade = "A+";
                else if (avg >= 80) grade = "A";
                else if (avg >= 70) grade = "B";
                else if (avg >= 60) grade = "C";
                else if (avg >= 50) grade = "D";
                else grade = "F";

                outputArea.append("=== Student " + student + " ===\n");
                outputArea.append("Total Marks: " + total + " / " + (numSubjects * 100) + "\n");
                outputArea.append(String.format("Average: %.2f%%\n", avg));
                outputArea.append("Grade: " + grade + "\n");
                outputArea.append("Status: " + (hasFailed ? "Fail" : "Pass") + "\n\n");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid integers for number of students and subjects.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GradeCalculatorGUI().setVisible(true));
    }
}
