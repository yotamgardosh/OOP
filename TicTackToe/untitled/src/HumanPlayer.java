/**
 * The HumanPlayer class represents a player controlled by human input.
 * It prompts the user to input coordinates for placing marks on the game board.
 * It ensures that the entered coordinates are valid and the selected cell is unoccupied.
 *
 * @author Yotam
 */
public class HumanPlayer implements Player {

    /*-----------------public methods-----------------*/

    private int[] rowColArray;

    /**
     * Constructs a HumanPlayer object.
     */
    public HumanPlayer(){
    }

    /**
     * Plays a turn by prompting the user to input coordinates for placing a mark on the game board.
     * It validates the input, ensuring the selected cell is unoccupied.
     *
     * @param board The game board on which the player places the mark.
     * @param mark The mark to be placed on the board.
     */
    public void playTurn(Board board, Mark mark){

        String stringMark = mark.toString();
        System.out.println(Constants.playerRequestInputString(stringMark));

        while(true) {
            int[] rowCol = getCoordinates(board);
            if (rowCol == null){
                continue;
            }
            if (unoccupiedCoordinates(rowCol[0], rowCol[1], board)){
                rowColArray = rowCol;
                break;
            }
            else{
                System.out.println(Constants.OCCUPIED_COORDINATE);
            }
        }

        board.putMark(mark,rowColArray[0], rowColArray[1]);
    }


    /*-----------------private methods-----------------*/

    /**
     * Checks if the specified coordinates on the board are unoccupied.
     *
     * @param row The row coordinate.
     * @param col The column coordinate.
     * @param board The game board.
     * @return True if the cell is unoccupied, false otherwise.
     */
    private boolean unoccupiedCoordinates(int row , int col, Board board){
        return board.getMark(row, col) == Mark.BLANK;
    }

    /**
     * Retrieves and validates user input for row and column coordinates.
     *
     * @param board The game board.
     * @return An array containing valid row and column coordinates, or null if input is invalid.
     */
    private int[] getCoordinates(Board board){
        while (true) {
            int input = KeyboardInput.readInt();
            int row = input / 10;
            int col = input % 10;
            if (row < 0 || row >= board.getSize() || col < 0 || col >= board.getSize()) {
                System.out.println(Constants.INVALID_COORDINATE);
                return null;
            }
            else{
                return new int[] {row,col};
            }
        }
    }

}
