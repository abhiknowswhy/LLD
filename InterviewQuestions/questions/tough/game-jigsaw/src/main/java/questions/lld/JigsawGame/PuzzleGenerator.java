package questions.lld.JigsawGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generates a valid jigsaw puzzle with matching edges,
 * then shuffles and rotates the pieces.
 */
public class PuzzleGenerator {

    private final int rows;
    private final int cols;
    private int edgeIdCounter = 1;

    public PuzzleGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    /** Generates a solvable puzzle with shuffled pieces. */
    public Puzzle generate() {
        // Build a solved grid, then extract & shuffle pieces
        Edge[][] horizontalEdges = new Edge[rows][cols - 1]; // edges between columns
        Edge[][] verticalEdges = new Edge[rows - 1][cols];   // edges between rows

        // Create internal horizontal edges (left-right connections)
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols - 1; c++) {
                int id = edgeIdCounter++;
                horizontalEdges[r][c] = new Edge(EdgeShape.OUT, id); // right edge of (r,c)
            }
        }

        // Create internal vertical edges (top-bottom connections)
        for (int r = 0; r < rows - 1; r++) {
            for (int c = 0; c < cols; c++) {
                int id = edgeIdCounter++;
                verticalEdges[r][c] = new Edge(EdgeShape.OUT, id); // bottom edge of (r,c)
            }
        }

        Edge flat = new Edge(EdgeShape.FLAT, 0);

        List<Piece> pieces = new ArrayList<>();
        int pieceId = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Edge top = (r == 0) ? flat : new Edge(EdgeShape.IN, verticalEdges[r - 1][c].getMatchId());
                Edge bottom = (r == rows - 1) ? flat : verticalEdges[r][c];
                Edge left = (c == 0) ? flat : new Edge(EdgeShape.IN, horizontalEdges[r][c - 1].getMatchId());
                Edge right = (c == cols - 1) ? flat : horizontalEdges[r][c];
                pieces.add(new Piece(pieceId++, top, right, bottom, left));
            }
        }

        // Shuffle and randomly rotate
        Collections.shuffle(pieces);
        for (Piece p : pieces) {
            int rotations = (int) (Math.random() * 4);
            for (int i = 0; i < rotations; i++) p.rotateClockwise();
        }

        return new Puzzle(rows, cols, pieces);
    }
}
