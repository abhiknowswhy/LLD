package questions.lld;

import questions.lld.SnakeGame.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Snake Game Demo ===\n");

        GameBoard game = new GameBoard(10, 10);
        System.out.println(game.render());

        // Simulate a few moves
        String[] moves = {"RIGHT", "RIGHT", "DOWN", "DOWN", "LEFT"};
        for (String move : moves) {
            game.setDirection(Direction.valueOf(move));
            boolean alive = game.tick();
            System.out.println("\nMove: " + move);
            System.out.println(game.render());
            if (!alive) {
                System.out.println("GAME OVER! Final score: " + game.getScore());
                return;
            }
        }

        System.out.println("\nGame still running. Score: " + game.getScore());
    }
}