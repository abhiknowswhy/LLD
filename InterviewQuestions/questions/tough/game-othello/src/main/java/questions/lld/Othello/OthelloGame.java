package questions.lld.Othello;

import java.util.List;

/**
 * Manages an Othello game — turns, scoring, and game-over detection.
 *
 * Rules:
 * - Black moves first
 * - A move must flip at least one opponent disc
 * - If a player has no valid moves, their turn is passed
 * - Game ends when neither player can move, or the board is full
 */
public class OthelloGame {

    private final Board board;
    private Disc currentPlayer;
    private boolean gameOver;

    public OthelloGame() {
        this.board = new Board();
        this.currentPlayer = Disc.BLACK;
        this.gameOver = false;
    }

    public Disc getCurrentPlayer() { return currentPlayer; }
    public boolean isGameOver() { return gameOver; }

    /** Makes a move at the given position. Returns true if successful. */
    public boolean makeMove(int row, int col) {
        if (gameOver) return false;

        int flipped = board.placeDisc(row, col, currentPlayer);
        if (flipped == 0) return false;

        System.out.println("  " + currentPlayer + " plays (" + row + "," + col + ") — flipped " + flipped);

        // Switch turns
        Disc next = currentPlayer.opposite();
        if (!board.getValidMoves(next).isEmpty()) {
            currentPlayer = next;
        } else if (!board.getValidMoves(currentPlayer).isEmpty()) {
            System.out.println("  " + next + " has no valid moves — " + currentPlayer + " plays again");
        } else {
            gameOver = true;
        }

        if (board.isFull()) gameOver = true;

        return true;
    }

    /** Returns valid moves for the current player. */
    public List<int[]> getValidMoves() {
        return board.getValidMoves(currentPlayer);
    }

    /** Returns the winner, or null if it's a tie. */
    public Disc getWinner() {
        int black = board.count(Disc.BLACK);
        int white = board.count(Disc.WHITE);
        if (black > white) return Disc.BLACK;
        if (white > black) return Disc.WHITE;
        return null;
    }

    /** Prints the current board state. */
    public void printBoard() { board.print(); }

    /** Prints the current score. */
    public void printScore() {
        System.out.println("Score — Black: " + board.count(Disc.BLACK) + ", White: " + board.count(Disc.WHITE));
    }
}
