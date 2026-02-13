package questions.lld;

import questions.lld.Game.*;
import questions.lld.Player.Player;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Simple Board Games Demo ===\n");

        Player p1 = new Player(1, "Alice", "X");
        Player p2 = new Player(2, "Bob", "O");

        // --- Tic Tac Toe ---
        System.out.println("--- Tic Tac Toe (3x3) ---");
        Game ttt = GameFactory.createGame(GameType.TIC_TAC_TOE, p1, p2);

        ttt.makeMove(0, 0); // X
        ttt.makeMove(1, 0); // O
        ttt.makeMove(0, 1); // X
        ttt.makeMove(1, 1); // O
        ttt.makeMove(0, 2); // X wins (top row)

        ttt.displayGameState();
        if (ttt.getStatus() == GameStatus.WIN) {
            System.out.println("Winner: " + ttt.getWinner());
        }

        // --- Tic Tac Toe with undo ---
        System.out.println("\n--- Tic Tac Toe with Undo ---");
        Game ttt2 = GameFactory.createTicTacToe(p1, p2, 3);
        ttt2.makeMove(0, 0); // X
        ttt2.makeMove(1, 1); // O
        ttt2.makeMove(0, 1); // X
        System.out.println("Before undo:");
        ttt2.displayGameState();

        ttt2.undoMove();
        System.out.println("After undo:");
        ttt2.displayGameState();

        // --- Connect Four ---
        System.out.println("\n--- Connect Four ---");
        Player red = new Player(1, "Red", "R");
        Player yellow = new Player(2, "Yellow", "Y");
        Game cf = GameFactory.createGame(GameType.CONNECT_FOUR, red, yellow);

        // Red wins vertically in column 0
        cf.makeMove(0, 0); // R
        cf.makeMove(0, 1); // Y
        cf.makeMove(0, 0); // R
        cf.makeMove(0, 1); // Y
        cf.makeMove(0, 0); // R
        cf.makeMove(0, 1); // Y
        cf.makeMove(0, 0); // R — 4 in column 0

        cf.displayGameState();
        if (cf.getStatus() == GameStatus.WIN) {
            System.out.println("Winner: " + cf.getWinner());
        }
    }
}