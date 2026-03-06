package questions.lld;

import questions.lld.Minesweeper.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Minesweeper Demo ===\n");

        Board board = new Board(5, 5, 4);
        // Place mines at known positions for demo
        board.placeMinesAt(new int[][]{{0, 0}, {1, 3}, {3, 1}, {4, 4}});

        System.out.println(board.render());

        // Reveal some cells
        System.out.println("\nRevealing (2,2)...");
        board.reveal(2, 2);
        System.out.println(board.render());

        System.out.println("\nFlagging (0,0)...");
        board.toggleFlag(0, 0);
        System.out.println(board.render());

        System.out.println("\nRevealing (4,0)...");
        board.reveal(4, 0);
        System.out.println(board.render());

        System.out.println("\nStatus: " + board.getStatus());
    }
}