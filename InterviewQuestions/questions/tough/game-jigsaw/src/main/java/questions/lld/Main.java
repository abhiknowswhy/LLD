package questions.lld;

import questions.lld.JigsawGame.*;

/**
 * Demonstrates a Jigsaw Puzzle game.
 *
 * Features:
 * - Grid-based board with edge-matching pieces
 * - Each piece has 4 edges (top, right, bottom, left) with shapes
 * - Pieces must match: convex pairs with concave on adjacent sides
 * - Flat edges on board borders
 * - Piece rotation support
 * - Automatic puzzle solver using backtracking
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Jigsaw Puzzle Demo ===\n");

        // Create a 3x3 puzzle
        int rows = 3, cols = 3;
        PuzzleGenerator generator = new PuzzleGenerator(rows, cols);
        Puzzle puzzle = generator.generate();

        System.out.println("Generated " + rows + "x" + cols + " puzzle with " + puzzle.getPieceCount() + " pieces\n");

        // Show the pieces (shuffled)
        System.out.println("--- Shuffled Pieces ---");
        for (Piece piece : puzzle.getUnplacedPieces()) {
            System.out.println("  " + piece);
        }

        // Solve the puzzle
        System.out.println("\n--- Solving ---");
        PuzzleSolver solver = new PuzzleSolver(puzzle);
        boolean solved = solver.solve();

        if (solved) {
            System.out.println("Puzzle solved!\n");
            puzzle.printBoard();
        } else {
            System.out.println("No solution found.");
        }

        // Manual placement demo
        System.out.println("\n--- Manual Placement Demo ---");
        Puzzle manual = generator.generate();
        Piece first = manual.getUnplacedPieces().get(0);
        System.out.println("Trying to place piece " + first.getId() + " at (0,0)...");
        boolean placed = manual.placePiece(first, 0, 0);
        System.out.println(placed ? "Placed successfully!" : "Does not fit.");

        System.out.println("\n=== Jigsaw Puzzle Demo Complete ===");
    }
}