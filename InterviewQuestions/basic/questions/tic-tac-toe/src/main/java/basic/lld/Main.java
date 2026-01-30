package basic.lld;

import java.util.Scanner;

import basic.lld.Game.Game;
import basic.lld.Game.GameStatus;
import basic.lld.Player.Player;
import basic.lld.Player.Symbol;

public class Main {
    public static void main(String[] args) {
        // Create players
        Player player1 = new Player(1, "Player 1", Symbol.X);
        Player player2 = new Player(2, "Player 2", Symbol.O);

        // Create game with 3x3 board
        Game game = new Game(player1, player2, 3);

        // Demo mode
        System.out.println("=== Tic Tac Toe Game ===");
        System.out.println("Player 1: " + player1);
        System.out.println("Player 2: " + player2);
        System.out.println();

        playInteractiveGame(game);
    }

    private static void playInteractiveGame(Game game) {
        Scanner scanner = new Scanner(System.in);

        while (!game.isGameOver()) {
            game.displayGameState();

            Player currentPlayer = game.getCurrentPlayer();
            System.out.print(currentPlayer.getName() + ", enter move (row col) [0-2] or 'undo': ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("undo")) {
                try {
                    game.undoMove();
                    System.out.println("Move undone!");
                } catch (IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                continue;
            }

            try {
                String[] parts = input.split(" ");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);

                if (game.makeMove(row, col)) {
                    System.out.println("Move successful!");
                } else {
                    System.out.println("Cell is already occupied. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }

        game.displayGameState();

        if (game.getStatus() == GameStatus.WIN) {
            Player winner = game.getWinner();
            System.out.println("🎉 " + winner.getName() + " (" + winner.getSymbol() + ") wins!");
        } else if (game.getStatus() == GameStatus.DRAW) {
            System.out.println("It's a draw!");
        }

        scanner.close();
    }
}