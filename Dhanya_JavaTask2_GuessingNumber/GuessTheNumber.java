 import java.util.Random;
import java.util.Scanner;

/**
 * Guess the Number Game
 * ---------------------
 * A simple console-based number guessing game.
 *
 * Features:
 *   - Random number generated between 1 and 100
 *   - Higher / Lower hints after each guess
 *   - Max 7 attempts per round
 *   - 3 rounds total
 *   - Points based on how few attempts you use
 *   - Final score displayed at the end
 *
 * Author : Your Name
 * Date   : June 2026
 */
public class GuessTheNumber {

    // ── Game Configuration ──────────────────────────────────────────────────
    static final int MIN_NUMBER    = 1;
    static final int MAX_NUMBER    = 100;
    static final int MAX_ATTEMPTS  = 7;
    static final int TOTAL_ROUNDS  = 3;

    // Points awarded = BASE_POINTS - (attempts_used - 1) * PENALTY_PER_ATTEMPT
    static final int BASE_POINTS      = 100;
    static final int PENALTY_PER_ATTEMPT = 10;

    // ── Entry Point ─────────────────────────────────────────────────────────
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random  random  = new Random();

        printWelcomeBanner();

        int totalScore = 0;

        // ── Round Loop ──────────────────────────────────────────────────────
        for (int round = 1; round <= TOTAL_ROUNDS; round++) {

            System.out.println("\n╔══════════════════════════════╗");
            System.out.printf ("║        ROUND  %d  of  %d        ║%n", round, TOTAL_ROUNDS);
            System.out.println("╚══════════════════════════════╝");

            // Generate the secret number for this round
            int secretNumber = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
            int roundScore   = playRound(scanner, secretNumber, round);

            totalScore += roundScore;

            System.out.printf("%n  Round %d score  : %d pts%n", round, roundScore);
            System.out.printf("  Running total  : %d pts%n",   totalScore);
        }

        // ── Final Results ───────────────────────────────────────────────────
        printFinalResults(totalScore);
        scanner.close();
    }

    // ── Play One Round ──────────────────────────────────────────────────────
    /**
     * Handles a single round of the game.
     *
     * @param scanner      Scanner for user input
     * @param secretNumber The number the player must guess
     * @param round        Current round number (used for display only)
     * @return             Points earned in this round (0 if player didn't guess)
     */
    static int playRound(Scanner scanner, int secretNumber, int round) {

        System.out.printf("%n  I'm thinking of a number between %d and %d.%n",
                          MIN_NUMBER, MAX_NUMBER);
        System.out.printf("  You have %d attempts. Good luck!%n%n", MAX_ATTEMPTS);

        int attemptsUsed = 0;
        boolean guessedCorrectly = false;

        // ── Attempt Loop ────────────────────────────────────────────────────
        while (attemptsUsed < MAX_ATTEMPTS) {

            int attemptsLeft = MAX_ATTEMPTS - attemptsUsed;
            System.out.printf("  [Attempt %d/%d]  Enter your guess: ",
                              attemptsUsed + 1, MAX_ATTEMPTS);

            // ── Input Validation ────────────────────────────────────────────
            if (!scanner.hasNextInt()) {
                System.out.println("  ⚠  Please enter a whole number.");
                scanner.next();          // discard invalid token
                continue;
            }

            int guess = scanner.nextInt();

            if (guess < MIN_NUMBER || guess > MAX_NUMBER) {
                System.out.printf("  ⚠  Number must be between %d and %d.%n",
                                  MIN_NUMBER, MAX_NUMBER);
                continue;
            }

            attemptsUsed++;

            // ── Feedback ────────────────────────────────────────────────────
            if (guess == secretNumber) {
                System.out.printf("%n  ✔  Correct! The number was %d.%n", secretNumber);
                System.out.printf("  You got it in %d attempt%s!%n",
                                  attemptsUsed, attemptsUsed == 1 ? "" : "s");
                guessedCorrectly = true;
                break;

            } else if (guess < secretNumber) {
                System.out.printf("  ↑  Too LOW!  ");
            } else {
                System.out.printf("  ↓  Too HIGH! ");
            }

            // Show remaining attempts (only if more remain)
            int remaining = MAX_ATTEMPTS - attemptsUsed;
            if (remaining > 0) {
                System.out.printf("(%d attempt%s left)%n",
                                  remaining, remaining == 1 ? "" : "s");
            }
        }

        // ── Round Result ────────────────────────────────────────────────────
        if (!guessedCorrectly) {
            System.out.printf("%n  ✘  Out of attempts! The number was %d.%n",
                              secretNumber);
            return 0;
        }

        // Calculate points: more attempts used → fewer points
        int earned = Math.max(0, BASE_POINTS - (attemptsUsed - 1) * PENALTY_PER_ATTEMPT);
        System.out.printf("  Points earned  : %d pts  (-%d per extra attempt)%n",
                          earned, PENALTY_PER_ATTEMPT);
        return earned;
    }

    // ── Display Helpers ─────────────────────────────────────────────────────
    static void printWelcomeBanner() {
        System.out.println();
        System.out.println("  ╔═══════════════════════════════════════╗");
        System.out.println("  ║       GUESS  THE  NUMBER  GAME        ║");
        System.out.println("  ╠═══════════════════════════════════════╣");
        System.out.printf ("  ║  Range    : %3d  –  %3d               ║%n",
                           MIN_NUMBER, MAX_NUMBER);
        System.out.printf ("  ║  Attempts : up to %d per round         ║%n", MAX_ATTEMPTS);
        System.out.printf ("  ║  Rounds   : %d                          ║%n", TOTAL_ROUNDS);
        System.out.printf ("  ║  Max score: %d pts (%d × %d)          ║%n",
                           BASE_POINTS * TOTAL_ROUNDS, BASE_POINTS, TOTAL_ROUNDS);
        System.out.println("  ╚═══════════════════════════════════════╝");
    }

    static void printFinalResults(int totalScore) {
        int maxPossible = BASE_POINTS * TOTAL_ROUNDS;

        System.out.println("\n  ╔═══════════════════════════════════════╗");
        System.out.println("  ║             GAME  OVER                ║");
        System.out.println("  ╠═══════════════════════════════════════╣");
        System.out.printf ("  ║  Final Score : %-6d / %-6d         ║%n",
                           totalScore, maxPossible);
        System.out.printf ("  ║  Rating      : %-23s  ║%n", getRating(totalScore, maxPossible));
        System.out.println("  ╚═══════════════════════════════════════╝\n");
    }

    /**
     * Returns a text rating based on the player's percentage score.
     */
    static String getRating(int score, int maxScore) {
        double pct = (double) score / maxScore * 100;
        if (pct == 100) return "⭐⭐⭐  PERFECT!";
        if (pct >= 80)  return "⭐⭐    Great job!";
        if (pct >= 50)  return "⭐      Good effort!";
        if (pct > 0)    return "       Keep practising!";
        return                 "       Better luck next time!";
    }
}
