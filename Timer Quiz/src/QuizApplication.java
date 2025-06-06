import javax.swing.*;
import java.awt.*;

public class QuizApplication extends JFrame {
    private String[][] questions = {
            {"What is the capital of France?", "Paris", "London", "Berlin", "Rome", "1"},
            {"Which planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Venus", "2"},
            {"Which language is used for Android development?", "Swift", "Python", "Java", "C#", "3"}
    };

    private int currentQuestion = 0;
    private int score = 0;
    private int[] userAnswers;
    private JLabel questionLabel, timerLabel;
    private JRadioButton[] options;
    private ButtonGroup group;
    private JButton nextButton;
    private Timer timer;
    private int timeLeft = 10;

    public QuizApplication() {
        setTitle("Quiz Application with Timer");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        questionLabel = new JLabel("Question will appear here");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        timerLabel = new JLabel("Time Left: 10s", SwingConstants.RIGHT);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(questionLabel, BorderLayout.WEST);
        topPanel.add(timerLabel, BorderLayout.EAST);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        options = new JRadioButton[4];
        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            optionsPanel.add(options[i]);
        }

        nextButton = new JButton("Next");
        nextButton.addActionListener(e -> handleAnswer());

        add(topPanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(nextButton, BorderLayout.SOUTH);

        userAnswers = new int[questions.length];
        for (int i = 0; i < userAnswers.length; i++) userAnswers[i] = -1;

        loadQuestion();
        startTimer();
    }

    private void loadQuestion() {
        if (currentQuestion >= questions.length) {
            showResults();
            return;
        }

        questionLabel.setText("Q" + (currentQuestion + 1) + ": " + questions[currentQuestion][0]);
        for (int i = 0; i < 4; i++) {
            options[i].setText(questions[currentQuestion][i + 1]);
            options[i].setSelected(false);
        }

        timeLeft = 10;
        timerLabel.setText("Time Left: " + timeLeft + "s");
    }

    private void handleAnswer() {
        int selected = -1;
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selected = i + 1;
                break;
            }
        }

        userAnswers[currentQuestion] = selected;
        if (selected == Integer.parseInt(questions[currentQuestion][5])) {
            score++;
        }

        currentQuestion++;
        loadQuestion();
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft + "s");
            if (timeLeft <= 0) {
                handleAnswer(); // Auto submit
            }
        });
        timer.start();
    }

    private void showResults() {
        timer.stop();
        StringBuilder result = new StringBuilder();
        result.append("Final Score: " + score + " / " + questions.length + "\n\n");

        for (int i = 0; i < questions.length; i++) {
            result.append("Q" + (i + 1) + ": " + questions[i][0] + "\n");
            result.append("Your answer: " +
                    (userAnswers[i] == -1 ? "No answer" : questions[i][userAnswers[i]]) + "\n");
            result.append("Correct answer: " + questions[i][Integer.parseInt(questions[i][5])] + "\n\n");
        }

        JOptionPane.showMessageDialog(this, result.toString(), "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizApplication().setVisible(true));
    }
}
