package questions.lld.SnakeGame;

import java.util.*;

public class GameBoard {
    private final int rows;
    private final int cols;
    private final Snake snake;
    private Position food;
    private int score;
    private boolean gameOver;
    private final Random random;

    public GameBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.snake = new Snake(new Position(rows / 2, cols / 2));
        this.score = 0;
        this.gameOver = false;
        this.random = new Random();
        spawnFood();
    }

    public void setDirection(Direction direction) {
        if (!gameOver) {
            snake.setDirection(direction);
        }
    }

    /**
     * Advances the game by one tick. Returns true if the game is still going.
     */
    public boolean tick() {
        if (gameOver) return false;

        Position nextHead = snake.getHead().move(snake.getCurrentDirection());

        // Check wall collision
        if (nextHead.getRow() < 0 || nextHead.getRow() >= rows ||
            nextHead.getCol() < 0 || nextHead.getCol() >= cols) {
            gameOver = true;
            return false;
        }

        boolean eatFood = nextHead.equals(food);
        snake.move(eatFood);

        // Check self-collision
        if (snake.isSelfCollision()) {
            gameOver = true;
            return false;
        }

        if (eatFood) {
            score++;
            spawnFood();
        }

        return true;
    }

    /**
     * Place food at a given position (for testing).
     */
    public void setFood(Position pos) {
        this.food = pos;
    }

    private void spawnFood() {
        Position pos;
        do {
            pos = new Position(random.nextInt(rows), random.nextInt(cols));
        } while (snake.occupies(pos));
        this.food = pos;
    }

    public int getScore() { return score; }
    public boolean isGameOver() { return gameOver; }
    public Snake getSnake() { return snake; }
    public Position getFood() { return food; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }

    /**
     * Renders the board as a string grid for display.
     */
    public String render() {
        char[][] grid = new char[rows][cols];
        for (char[] row : grid) Arrays.fill(row, '.');

        for (Position p : snake.getBody()) {
            grid[p.getRow()][p.getCol()] = 'O';
        }
        Position head = snake.getHead();
        grid[head.getRow()][head.getCol()] = '@';

        if (food != null) {
            grid[food.getRow()][food.getCol()] = '*';
        }

        StringBuilder sb = new StringBuilder();
        for (char[] row : grid) {
            sb.append(new String(row)).append('\n');
        }
        sb.append("Score: ").append(score);
        return sb.toString();
    }
}
