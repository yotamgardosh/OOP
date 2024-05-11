/**
 * The Game class represents a tic-tac-toe game with two players, a game board,
 * and a renderer for displaying the board. The players take turns making moves,
 * and the game continues until a player wins or the board is filled.
 *
 * @author Yotam
 */
public class Game {

    /**
     * The renderer responsible for displaying the game board.
     */
    public Renderer boardRenderer;

    /**
     * The game board on which the players make their moves.
     */
    public Board board;

    /**
     * The Player representing 'X'.
     */
    public Player playerX;

    /**
     * The Player representing 'O'.
     */
    public Player playerO;

    /**
     * Array containing the two players.
     */
    private Player[] players;

    /**
     * The length of the winning streak required to win the game.
     */
    private int winStreak = 3;

    /**
     * The size of the game board (number of rows/columns).
     */
    private int boardSize = 4;

    /**
     * Flag indicating whether the game has ended.
     */
    private boolean gameEnded = false;

    /**
     * The winner of the game.
     */
    private Mark winner = Mark.BLANK;

    /**
     * The maximum number of marks allowed on the board.
     */
    private int maxMarks;

    /**
     * The total number of marks placed on the board.
     */
    private int marksPlaced = 0;

    /**
     * Array containing the two marks used in the game ('X' and 'O').
     */
    private Mark[] marks = new Mark[] {Mark.X, Mark.O};

    /**
     * Constructs a new Game with the default board size (4x4) and winning streak length (3).
     *
     * @param playerX The player representing 'X'.
     * @param playerO The player representing 'O'.
     * @param renderer The renderer for displaying the game board.
     */
    public Game(Player playerX, Player playerO, Renderer renderer){
        this.board = new Board();
        this.boardRenderer = renderer;
        this.playerX = playerX;
        this.playerO = playerO;
        this.players = new Player[]{playerX, playerO};
        this.maxMarks = boardSize * boardSize;
    }

    /**
     * Constructs a new Game with a specified board size and winning streak length.
     *
     * @param playerX The player representing 'X'.
     * @param playerO The player representing 'O'.
     * @param size The size of the game board (number of rows/columns).
     * @param winStreak The length of the winning streak required to win the game.
     * @param renderer The renderer for displaying the game board.
     */
    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer){
        this.board = new Board(size);
        this.boardSize = size;
        if (winStreak < 2 || winStreak > size){
            this.winStreak = size;
        } else {
            this.winStreak = winStreak;
        }
        this.boardRenderer = renderer;
        this.playerX = playerX;
        this.playerO = playerO;
        this.players = new Player[]{playerX, playerO};
        this.maxMarks = boardSize * boardSize;
    }

    /*-----------------public methods-----------------*/

    /**
     * Runs the game, allowing players to take turns until a winner is determined or the board is filled.
     *
     * @return The mark of the winning player or BLANK if it's a tie.
     */
    public Mark run(){
        int i = 0;
        while(!this.gameEnded){
            this.players[i % 2].playTurn(this.board, marks[i % 2]);
            this.boardRenderer.renderBoard(this.board);
            checkForWinner();
            i++;
        }
        return this.winner;
    }

    /**
     * Retrieves the length of the winning streak required to win the game.
     *
     * @return The length of the winning streak.
     */
    public int getWinStreak() {
        return winStreak;
    }

    /**
     * Retrieves the size of the game board (number of rows/columns).
     *
     * @return The size of the game board.
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Checks for a winner by examining rows, columns, and diagonals on the game board.
     * Updates the game state if a winner is found or if the board is filled.
     */
    public void checkForWinner() {
        for (int i = 0; i < this.boardSize; i++) {

            for(int j = 0; j < this.boardSize; j++) {
                // Check rows
                if (checkStreak(board.getMark(i,j), i, j, 0, 1)) return;

                // Check columns
                if (checkStreak(board.getMark(j,i), j, i, 1, 0)) return;

                //Check diagonals
                if (checkStreak(board.getMark(i,j), i, j, 1, 1)) return; // main
                if (checkStreak(board.getMark(i,this.boardSize - 1 - j), i,
                        this.boardSize - 1 - j, 1, -1)) return; // secondary
            }
        }
        if(marksPlaced == maxMarks){
            gameEnded = true;
        }
    }

    /*-----------------private methods-----------------*/

    /**
     * Checks for a winning streak of a specific mark starting from the specified position.
     * Updates the game state if a winning streak is found.
     *
     * @param mark The mark to check for a winning streak.
     * @param startRow The starting row.
     * @param startCol The starting column.
     * @param rowIncrement The increment for the row direction.
     * @param colIncrement The increment for the column direction.
     * @return True if a winning streak is found, false otherwise.
     */
    private boolean checkStreak(Mark mark, int startRow, int startCol, int rowIncrement, int colIncrement) {
        int endRow = startRow + (winStreak - 1) * rowIncrement;
        int endCol = startCol + (winStreak - 1) * colIncrement;

        if (mark == Mark.BLANK || !validInput(endRow, endCol)) {
            return false;
        }

        for (int i = 1; i < winStreak; i++) {
            int currentRow = startRow + i * rowIncrement;
            int currentCol = startCol + i * colIncrement;

            if (board.getMark(currentRow,currentCol) != mark || !validInput(currentRow, currentCol)) {
                return false;
            }
        }

        gameEnded = true;
        winner = mark;
        return true;
    }

    /**
     * Checks if the given coordinates are valid for the current game board size.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return True if the coordinates are valid, false otherwise.
     */
    private boolean validInput(int x, int y){
        return ((0 <= x && x < this.boardSize) && (0 <= y && y < this.boardSize));
    }
}
