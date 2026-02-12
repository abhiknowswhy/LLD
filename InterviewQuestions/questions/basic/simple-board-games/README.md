
- Board - abstract class
    - player array
    - moves stack for undo
    - cells 2D array
    - validateMove()
    - placeMove()
    - checkWin()
    - resetBoard()
    - displayBoard()

    concrete Boards:
      - TicTacToeBoard
      - ConnectFourBoard
      - SnakeAndLadderBoard
      - mineSweeperBoard

- Game - abstract class
    - current player
    - board
    - makeMove()
    - switchPlayer()
    - checkWin()
    - undoMove()
    - killGame()
    - resetGame()

    Concrete Games:
      - TicTacToeGame (variable board size, 2 players, 3 in a row to win)
      - ConnectFourGame (fixed board size, 2 players, 4 in a row to win)
      - SnakeAndLadderGame (variable board size, multiple players, reach end to win)
      - MineSweeperGame (variable board size, single player, clear board without hitting mine to win)

player class
    - id
    - name
    - symbol/identifier (custom/enum pluggable)


Patterns 

- flyweight for Cell objects to save memory
- command pattern for moves to support undo functionality
- factory pattern to create different game types
- strategy pattern for win condition checking algorithms
