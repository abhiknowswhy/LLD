package questions.lld;

import questions.lld.SnakeAndLadder.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Snake & Ladder Demo ===\n");

        Board board = new Board(100);
        board.addSnake(99, 10);
        board.addSnake(65, 25);
        board.addSnake(45, 6);
        board.addLadder(2, 38);
        board.addLadder(7, 14);
        board.addLadder(28, 84);
        board.addLadder(51, 67);

        Game game = new Game(board, 1, List.of("Alice", "Bob"));

        while (!game.isGameOver()) {
            String result = game.playTurn();
            System.out.println(result);
        }

        System.out.println("\nWinner: " + game.getWinner().getName());
    }
}