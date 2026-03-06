package questions.lld;

import questions.lld.Othello.*;

/**
 * Demonstrates an Othello (Reversi) game.
 *
 * Features:
 * - 8x8 board with standard initial setup
 * - Legal move validation with disc flipping in all 8 directions
 * - Turn management with automatic pass when no moves available
 * - Score tracking and game-over detection
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Othello (Reversi) Demo ===\n");

        OthelloGame game = new OthelloGame();
        game.printBoard();
        game.printScore();

        System.out.println("\n--- Playing a sample game ---\n");

        // Play a sequence of moves
        int[][] moves = {
            {2, 3}, {2, 2}, {2, 1}, {2, 4},
            {1, 2}, {2, 5}, {0, 2}, {5, 4},
            {5, 3}, {4, 5}, {3, 5}, {5, 5},
            {5, 2}, {3, 2}, {4, 2}, {5, 1}
        };

        for (int[] move : moves) {
            boolean success = game.makeMove(move[0], move[1]);
            if (!success) {
                System.out.println("  Move (" + move[0] + "," + move[1] + ") failed for " + game.getCurrentPlayer());
            }
            if (game.isGameOver()) break;
        }

        System.out.println("\n--- Current Board ---");
        game.printBoard();
        game.printScore();

        if (game.isGameOver()) {
            Disc winner = game.getWinner();
            System.out.println("Game over! Winner: " + (winner == null ? "TIE" : winner));
        } else {
            System.out.println("Game still in progress. Current turn: " + game.getCurrentPlayer());
            System.out.println("Valid moves: " + game.getValidMoves());
        }

        System.out.println("\n=== Othello Demo Complete ===");
    }
}