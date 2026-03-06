package questions.lld;

import questions.lld.ChessGame.*;

/**
 * Demonstrates a Chess Game with full piece movement, validation,
 * and check/checkmate detection.
 *
 * Features:
 * - 8x8 board with all standard pieces
 * - Legal move validation per piece type
 * - Turn-based play (white/black alternation)
 * - Check and checkmate detection
 * - Move history tracking
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chess Game Demo ===\n");

        Game game = new Game();
        game.printBoard();

        // Scholar's Mate (4-move checkmate)
        System.out.println("\n--- Playing Scholar's Mate ---\n");

        game.makeMove(new Position(1, 4), new Position(3, 4)); // e2→e4
        game.makeMove(new Position(6, 4), new Position(4, 4)); // e7→e5

        game.makeMove(new Position(0, 5), new Position(3, 2)); // Bf1→c4
        game.makeMove(new Position(7, 1), new Position(5, 2)); // Nb8→c6

        game.makeMove(new Position(0, 3), new Position(4, 7)); // Qd1→h5
        game.makeMove(new Position(7, 6), new Position(5, 5)); // Ng8→f6 (tries to defend)

        game.makeMove(new Position(4, 7), new Position(6, 5)); // Qh5xf7# — checkmate!

        System.out.println();
        game.printBoard();

        System.out.println("\nGame status: " + game.getStatus());
        System.out.println("Move count: " + game.getMoveHistory().size());

        System.out.println("\n=== Chess Game Demo Complete ===");
    }
}