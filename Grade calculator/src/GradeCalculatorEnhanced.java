import java.util.Scanner;

public class GradeCalculatorEnhanced {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int numStudents = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.print("Enter number of subjects: ");
        int numSubjects = sc.nextInt();
        sc.nextLine(); // Consume newline

        String[] subjectNames = new String[numSubjects];

        // Input subject names
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter name of subject " + (i + 1) + ": ");
            subjectNames[i] = sc.nextLine();
        }

        // Process each student
        for (int student = 1; student <= numStudents; student++) {
            System.out.println("\n--- Enter marks for Student " + student + " ---");
            int[] marks = new int[numSubjects];
            int totalMarks = 0;
            boolean hasFailed = false;

            // Input marks per subject
            for (int i = 0; i < numSubjects; i++) {
                System.out.print("Enter marks for " + subjectNames[i] + " (out of 100): ");
                marks[i] = sc.nextInt();

                // Input validation
                while (marks[i] < 0 || marks[i] > 100) {
                    System.out.print("Invalid input! Re-enter marks between 0 and 100: ");
                    marks[i] = sc.nextInt();
                }

                if (marks[i] < 40) {
                    hasFailed = true;
                }

                totalMarks += marks[i];
            }

            // Calculate average
            double average = (double) totalMarks / numSubjects;

            // Assign grade
            String grade;
            if (average >= 90) grade = "A+";
            else if (average >= 80) grade = "A";
            else if (average >= 70) grade = "B";
            else if (average >= 60) grade = "C";
            else if (average >= 50) grade = "D";
            else grade = "F";

            // Display results
            System.out.println("\n\u001B[36m=== Result for Student " + student + " ===\u001B[0m");
            System.out.println("Total Marks: " + totalMarks + " / " + (numSubjects * 100));
            System.out.printf("Average Percentage: %.2f%%\n", average);
            System.out.println("Grade: " + grade);
            System.out.println("Status: " + (hasFailed ? "\u001B[31mFail\u001B[0m" : "\u001B[32mPass\u001B[0m"));
        }

        sc.close();
    }
}
