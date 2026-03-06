package questions.lld.ChessGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages a chess game including turns, move validation, check/checkmate detection.
 *
 * Design:
 * - Board holds piece state
 * - Each Piece subclass generates its own candidate moves (polymorphism)
 * - Game validates that a move doesn't leave own king in check
 * - Detects checkmate (no legal moves while in check) and stalemate
 */
public class Game {

    private final Board board;
    private Color currentTurn;
    private GameStatus status;
    private final List<Move> moveHistory = new ArrayList<>();

    public Game() {
        this.board = new Board();
        this.currentTurn = Color.WHITE;
        this.status = GameStatus.ACTIVE;
    }

    public Board getBoard() { return board; }
    public Color getCurrentTurn() { return currentTurn; }
    public GameStatus getStatus() { return status; }
    public List<Move> getMoveHistory() { return Collections.unmodifiableList(moveHistory); }

    /**
     * Attempts to make a move from 'from' to 'to'.
     * Validates piece ownership, legal movement, and check rules.
     */
    public boolean makeMove(Position from, Position to) {
        if (status != GameStatus.ACTIVE) {
            System.out.println("  Game is over: " + status);
            return false;
        }

        Piece piece = board.getPiece(from);
        if (piece == null) {
            System.out.println("  No piece at " + from);
            return false;
        }
        if (piece.getColor() != currentTurn) {
            System.out.println("  Not " + piece.getColor() + "'s turn");
            return false;
        }

        List<Position> candidateMoves = piece.getCandidateMoves(from, board);
        if (!candidateMoves.contains(to)) {
            System.out.println("  Illegal move for " + piece + " from " + from + " to " + to);
            return false;
        }

        // Simulate the move and check if it leaves own king in check
        Piece captured = board.movePiece(from, to);
        if (board.isInCheck(currentTurn)) {
            // Undo — move puts own king in check
            board.movePiece(to, from);
            board.setPiece(to, captured);
            System.out.println("  Move " + from + "→" + to + " would leave " + currentTurn + " king in check");
            return false;
        }

        // Move is legal
        Move move = new Move(from, to, piece, captured);
        moveHistory.add(move);
        String captureStr = captured != null ? " captures " + captured : "";
        System.out.println("  " + currentTurn + ": " + piece + " " + from + "→" + to + captureStr);

        // Check for check, checkmate, stalemate
        Color opponent = currentTurn.opposite();
        if (board.isInCheck(opponent)) {
            if (hasNoLegalMoves(opponent)) {
                status = (currentTurn == Color.WHITE) ? GameStatus.WHITE_WINS : GameStatus.BLACK_WINS;
                System.out.println("  *** CHECKMATE! " + currentTurn + " wins! ***");
            } else {
                System.out.println("  " + opponent + " is in CHECK");
            }
        } else if (hasNoLegalMoves(opponent)) {
            status = GameStatus.STALEMATE;
            System.out.println("  *** STALEMATE! ***");
        }

        currentTurn = opponent;
        return true;
    }

    /** Returns true if the given color has no legal moves. */
    private boolean hasNoLegalMoves(Color color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board.getPiece(new Position(r, c));
                if (p != null && p.getColor() == color) {
                    Position from = new Position(r, c);
                    for (Position to : p.getCandidateMoves(from, board)) {
                        // Simulate
                        Piece captured = board.movePiece(from, to);
                        boolean stillInCheck = board.isInCheck(color);
                        board.movePiece(to, from);
                        board.setPiece(to, captured);
                        if (!stillInCheck) return false; // found at least one legal move
                    }
                }
            }
        }
        return true;
    }

    public void printBoard() { board.print(); }
}
