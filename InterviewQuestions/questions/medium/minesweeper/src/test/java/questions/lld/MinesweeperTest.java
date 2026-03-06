package questions.lld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.lld.Minesweeper.*;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperTest {

    private Board board;

    @BeforeEach
    void setUp() {
        // 5x5 board with 3 mines
        board = new Board(5, 5, 3);
        board.placeMinesAt(new int[][]{{0, 0}, {2, 2}, {4, 4}});
    }

    @Test
    void testInitialState() {
        assertEquals(GameStatus.IN_PROGRESS, board.getStatus());
        assertEquals(0, board.getRevealedCount());
        assertTrue(board.getCell(0, 0).isMine());
    }

    @Test
    void testRevealSafeCell() {
        board.reveal(0, 2);
        assertTrue(board.getCell(0, 2).isRevealed());
        assertEquals(GameStatus.IN_PROGRESS, board.getStatus());
    }

    @Test
    void testRevealMineCell() {
        board.reveal(0, 0);
        assertEquals(GameStatus.LOST, board.getStatus());
    }

    @Test
    void testAdjacentMineCount() {
        // Cell (1,1) is adjacent to mines at (0,0) and (2,2) → count = 2
        assertEquals(2, board.getCell(1, 1).getAdjacentMines());
    }

    @Test
    void testFloodFill() {
        // Corner (4,0) has 0 adjacent mines — should flood fill
        board.reveal(4, 0);
        // After flood fill, multiple cells should be revealed
        assertTrue(board.getRevealedCount() > 1);
        assertTrue(board.getCell(4, 0).isRevealed());
    }

    @Test
    void testToggleFlag() {
        board.toggleFlag(0, 0);
        assertTrue(board.getCell(0, 0).isFlagged());
        assertEquals(1, board.getFlagCount());

        // Toggle again to remove
        board.toggleFlag(0, 0);
        assertFalse(board.getCell(0, 0).isFlagged());
        assertEquals(0, board.getFlagCount());
    }

    @Test
    void testCannotRevealFlagged() {
        board.toggleFlag(0, 0);
        board.reveal(0, 0); // should be ignored
        assertTrue(board.getCell(0, 0).isFlagged());
        assertEquals(GameStatus.IN_PROGRESS, board.getStatus());
    }

    @Test
    void testCannotRevealAlreadyRevealed() {
        board.reveal(0, 2);
        int countBefore = board.getRevealedCount();
        board.reveal(0, 2); // should be ignored
        assertEquals(countBefore, board.getRevealedCount());
    }

    @Test
    void testWinCondition() {
        // Reveal all non-mine cells (22 cells = 25 - 3 mines)
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (!board.getCell(r, c).isMine()) {
                    board.reveal(r, c);
                }
            }
        }
        assertEquals(GameStatus.WON, board.getStatus());
    }

    @Test
    void testRender() {
        String rendered = board.render();
        assertNotNull(rendered);
        assertTrue(rendered.contains("Flags:"));
    }

    @Test
    void testCellDisplay() {
        Cell cell = new Cell();
        assertEquals('.', cell.getDisplay()); // hidden

        cell.setState(CellState.FLAGGED);
        assertEquals('F', cell.getDisplay());

        cell.setState(CellState.REVEALED);
        cell.setAdjacentMines(3);
        assertEquals('3', cell.getDisplay());

        cell.setAdjacentMines(0);
        assertEquals(' ', cell.getDisplay());
    }

    @Test
    void testMineDisplay() {
        Cell cell = new Cell();
        cell.setMine(true);
        cell.setState(CellState.REVEALED);
        assertEquals('*', cell.getDisplay());
    }

    @Test
    void testTooManyMines() {
        assertThrows(IllegalArgumentException.class, () -> new Board(3, 3, 9));
    }
}
