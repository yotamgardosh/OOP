/**
 * The Board class represents a game board where marks can be placed and queried.
 * It provides methods to interact with the board, such as putting a mark, getting a mark,
 * and checking if the game has ended.
 *
 * @author Yotam
 */
public class Board {

    /*-----------------fields-----------------*/

    /**
     * The 2D array representing the game board.
     */
    private Mark[][] board;

    /**
     * The size of the board (number of rows/columns).
     */
    private int size = 4;

    /**
     * The maximum number of marks allowed on the board.
     */
    private int maxMarks;

    /*-----------------public methods-----------------*/

    /**
     * Constructs a new Board with the default size of 4x4.
     */
    public Board() {
        initializeBoard();
    }

    /**
     * Constructs a new Board with a specified size.
     *
     * @param size The size of the board (number of rows/columns).
     */
    public Board(int size) {
        this.size = size;
        initializeBoard();
    }

    /**
     * Initializes the game board with the specified size.
     * Also sets the maximum number of marks allowed on the board.
     */
    private void initializeBoard() {
        this.board = new Mark[size][size];
        this.maxMarks = size * size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                this.board[row][col] = Mark.BLANK;
            }
        }
    }

    /**
     * Places a mark on the board at the specified coordinates.
     *
     * @param markToPut The mark to be placed on the board.
     * @param row The row coordinate.
     * @param col The column coordinate.
     * @return True if the mark was placed successfully, false otherwise.
     */
    public boolean putMark(Mark markToPut, int row, int col){
        if (validInput(row,col) && getMark(row,col) == Mark.BLANK) {
            this.board[row][col] = markToPut;
            return true; // Placeholder, actual implementation may involve checking for valid moves.
        }
        return false;
    }

    /**
     * Retrieves the mark at the specified coordinates on the board.
     *
     * @param row The row coordinate.
     * @param col The column coordinate.
     * @return The mark at the specified coordinates, or BLANK if the coordinates are invalid.
     */
    public Mark getMark( int row, int col){
        if(!validInput(row,col)){
            return Mark.BLANK;
        }
        return this.board[row][col];
    }

    /**
     * Retrieves the size of the board.
     *
     * @return The size of the board.
     */
    public int getSize(){
        return this.size;
    }

    /*-----------------private methods-----------------*/

    /**
     * Checks if the given coordinates are within the bounds of the board.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return True if the coordinates are valid, false otherwise.
     */
    private boolean validInput(int x, int y){
        return ((0 <= x && x < this.size) && (0 <= y && y < this.size));
    }
}
