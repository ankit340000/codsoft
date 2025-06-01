import java.util.Scanner;
import java.util.Random;

public class NumberGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        int totalScore = 0;
        int bestScore = Integer.MAX_VALUE;
        boolean playAgain = true;

        System.out.println("\u001B[36m=== Welcome to the Number Guessing Game! ===\u001B[0m");

        while (playAgain) {
            int numberToGuess = rand.nextInt(100) + 1; // Random number between 1 and 100
            int attempts = 0;
            int maxAttempts = 7;
            boolean guessedCorrectly = false;

            System.out.println("\n\u001B[34mI have picked a number between 1 and 100. Can you guess it?\u001B[0m");
            System.out.println("You have " + maxAttempts + " attempts.");

            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int userGuess = sc.nextInt();
                attempts++;

                if (userGuess == numberToGuess) {
                    System.out.println("\u001B[32m🎉 Correct! You've guessed the number in " + attempts + " attempts.\u001B[0m");
                    int roundScore = (maxAttempts - attempts + 1);
                    totalScore += roundScore;
                    guessedCorrectly = true;

                    if (attempts < bestScore) {
                        bestScore = attempts;
                        System.out.println("\u001B[33m🔥 New best score this round: " + bestScore + " attempts!\u001B[0m");
                    }
                    break;
                } else if (userGuess < numberToGuess) {
                    System.out.println("\u001B[31mToo low!\u001B[0m");
                } else {
                    System.out.println("\u001B[31mToo high!\u001B[0m");
                }

                // Give hint after 4th attempt
                if (attempts == 4 && !guessedCorrectly) {
                    if (numberToGuess % 2 == 0) {
                        System.out.println("\u001B[35mHint: The number is even.\u001B[0m");
                    } else {
                        System.out.println("\u001B[35mHint: The number is odd.\u001B[0m");
                    }
                }
            }

            if (!guessedCorrectly) {
                System.out.println("\u001B[31m😢 You've used all attempts. The number was: " + numberToGuess + "\u001B[0m");
            }

            System.out.println("Your current total score: " + totalScore);
            if (bestScore != Integer.MAX_VALUE) {
                System.out.println("Your best performance: " + bestScore + " attempts");
            }

            System.out.print("Do you want to play another round? (yes/no): ");
            String response = sc.next();
            playAgain = response.equalsIgnoreCase("yes");
        }

        System.out.println("\n\u001B[36mThank you for playing! Your final score is: " + totalScore + "\u001B[0m");
        sc.close();
    }
}
